using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Twink : MonoBehaviour {

    private bool isTwinking;

    private enum Mode {
        DOWN,
        UP
    }

    private const int SPEED = 10;
    private Mode mode;

    public GameObject augenLied1;
    public GameObject augenLied2;
    private const double Z_OPEN1 = 0.6986883f;
    private const double Z_CLOSED1 = 0.3762783f;
     
    void Awake() {
        this.isTwinking = false;
        this.mode = Mode.DOWN;

        InvokeRepeating("RandomRun", 2f, 2F);
    }

    void RandomRun() {
        int x = Random.Range(0, 3);

        if (x == 0) {
            executeTwink();
        }
    }

    void executeTwink() {
        if(!isTwinking) {
            this.mode = Mode.DOWN;
            this.isTwinking = true;

            Debug.Log("Start");           
        }
    }

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        double rot1 = this.augenLied1.transform.localRotation.z;



        if (this.isTwinking)
        {
            Debug.Log("Z: " + this.augenLied1.transform.localRotation.z);

            if(this.mode != Mode.UP) {
                if (rot1 < Z_CLOSED1)
                {
                    Debug.Log("DOWN");
                    this.mode = Mode.UP;
                } 
            } else {
                if (rot1 > Z_OPEN1)
                {
                    this.isTwinking = false;
                } 
            }
        }

        int angle = 0;

        if (this.isTwinking)
        {
            if (this.mode.Equals(Mode.DOWN))
            {
                angle = SPEED;
            }
            else
            {
                angle = -SPEED;
            }
        }

        this.augenLied1.transform.Rotate(0, 0, angle);
        this.augenLied2.transform.Rotate(0, 0, angle);
	}
}
