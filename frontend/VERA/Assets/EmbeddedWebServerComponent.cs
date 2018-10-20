using UnityEngine;
using System.Collections;
using System.Net.Sockets;
using System.Threading;
using System.Collections.Generic;
using System.IO;
using System;

namespace UniWebServer
{


    public class EmbeddedWebServerComponent : MonoBehaviour
    {
        private const String COMMAND_IDLE = "idle";

        private const String COMMAND_FOLLOW = "follow";
        private const String COMMAND_LOOKAT = "lookAt";

        private const String COMMAND_SPEAK = "speak";

        private const String COMMAND_MULTIPLY = "multiply";

        private const String COMMAND_CHICKEN = "chicken";

        public bool startOnAwake = true;
        public int port = 8079;
        public int workerThreads = 2;
        public bool processRequestsInMainThread = true;
        public bool logRequests = true;

        WebServer server;
        Dictionary<string, IWebResource> resources = new Dictionary<string, IWebResource> ();

        private AvatarController avatarController;

        void Start ()
        {
            this.avatarController = (AvatarController)GameObject.Find("AvatarController").GetComponent(typeof(AvatarController));

            if (processRequestsInMainThread)
                Application.runInBackground = true;
            server = new WebServer (port, workerThreads, processRequestsInMainThread);
            server.logRequests = logRequests;
            server.HandleRequest += HandleRequest;
            if (startOnAwake) {
                server.Start ();
            }
        }

        void OnApplicationQuit ()
        {
            server.Dispose ();
        }

        void Update ()
        {
            if (server.processRequestsInMainThread) {
                server.ProcessRequests ();    
            }
        }

        void HandleRequest (Request request, Response response)
        {         
            string[] words = request.uri.LocalPath.Split('/');
            if(words.Length >= 2) {
                // Command found
                string command = words[2];
                string parameter = "";

                Debug.Log("Command: " + command);

                if(words.Length >= 3) {
                    parameter = words[3];

                    Debug.Log("Parameter: " + parameter);
                }

                if(command.Equals(COMMAND_IDLE, StringComparison.OrdinalIgnoreCase)) {
                    this.avatarController.changeMode(AvatarController.AvatarMode.Idle);
                }

                if (command.Equals(COMMAND_FOLLOW, StringComparison.OrdinalIgnoreCase)) {
                    this.avatarController.changeMode(AvatarController.AvatarMode.Follow);
                }

                if(command.Equals(COMMAND_LOOKAT, StringComparison.OrdinalIgnoreCase)) {
                    this.avatarController.lookAt(float.Parse(words[3]));
                }

                if (command.Equals(COMMAND_SPEAK, StringComparison.OrdinalIgnoreCase)) {
                    this.avatarController.changeMode(AvatarController.AvatarMode.Speak);
                }

                if (command.Equals(COMMAND_MULTIPLY, StringComparison.OrdinalIgnoreCase)) {
                    this.avatarController.changeMode(AvatarController.AvatarMode.Multiply);
                }

                if (command.Equals(COMMAND_CHICKEN, StringComparison.OrdinalIgnoreCase))
                {
                    this.avatarController.changeMode(AvatarController.AvatarMode.Chicken);
                }
            }

            response.statusCode = 200;
 
        }

        public void AddResource (string path, IWebResource resource)
        {
            resources [path] = resource;
        }

    }



}