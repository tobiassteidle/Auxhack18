using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AvatarController : MonoBehaviour {

    public enum AvatarMode {
        Idle,
        Follow,
        Speak,
        Multiply,
        Chicken
    }

    public GameObject avatarHead;
    public GameObject speechBubble;
    public GameObject multipleHeads;
    public GameObject masculineHead;

    private const float x_min = -730;
    private const float x_max = 2200;

    private const float x_neutral = (x_min + x_max) / 2;

    private const float y_fix = 2000;

    private float x_current;
    private AvatarMode avatarMode;

	// Use this for initialization
	void Start () {
        this.x_current = x_neutral;
        this.avatarMode = AvatarMode.Idle;
	}
	
    void Update()
    {
        switch(this.avatarMode) {
            case AvatarMode.Idle:
                break;

            case AvatarMode.Follow:
                executeLookAt();
                break;

            case AvatarMode.Speak:
                executeSpeak();
                break;

            case AvatarMode.Multiply:
                executeMultiply();
                break;

            case AvatarMode.Chicken:
                executeChicken();
                break;
        }

    }

    private void executeLookAt() {
        float midPoint = calcMidPoint(this.avatarHead.transform);
        Vector3 lookAtPosition = calculateLookAtPosition(this.avatarHead.transform, midPoint);
        this.avatarHead.transform.LookAt(lookAtPosition);
        this.avatarHead.transform.Rotate(new Vector3(-45, 0));  
    }

    private void executeSpeak() {
        this.speechBubble.SetActive(true);
    }

    private void executeMultiply() {
        this.multipleHeads.SetActive(true);
    }

    private void executeChicken() {
        this.avatarHead.SetActive(false);
        this.masculineHead.SetActive(true);
    }

    private float calcMidPoint(Transform transformEye)
    {
        return (transformEye.position - Camera.main.transform.position).magnitude * 0.5f;
    }

    private Vector3 calculateLookAtPosition(Transform transformEye, float midPoint)
    {
        Vector3 position = Input.mousePosition;
        position.y = y_fix;
        position.x = this.x_current;

        Ray mouseRay = Camera.main.ScreenPointToRay(position);
        Vector3 lookAtPosition = mouseRay.origin + mouseRay.direction * midPoint;
        return lookAtPosition;
    }

    public void idle() {
        this.x_current = x_neutral;
        this.avatarHead.SetActive(true);
        this.speechBubble.SetActive(false);
        this.multipleHeads.SetActive(false);
        this.masculineHead.SetActive(false);
    }

    public void changeMode(AvatarMode avatarMode) {
        this.idle();
        this.avatarMode = avatarMode;
    }

	public void lookAt(float x) {
        Debug.Log("normalized_x" + x);

        float denormalized = denormalize(x, x_max, x_min);
        Debug.Log("denormalized_x" + denormalized);
        this.x_current = denormalized;
    }

    private float denormalize(float normalized, float max, float min)
    {
        return (normalized * (max - min) + min);
    }
}
