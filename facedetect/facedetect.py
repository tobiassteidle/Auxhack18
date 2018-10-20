import cv2
import dlib
from AvatarCommunicator import AvatarCommunicator
import time

avatarCom = AvatarCommunicator()
faceCascade = cv2.CascadeClassifier('haarcascades/haarcascade_frontalface_default.xml')

FRAME_DETECT_FREQ = 10

def normalize(value, min, max):
    return (value - min) / (max - min)

def doRecognizePerson(faceNames, fid):
    time.sleep(2)
    faceNames[ fid ] = "Person " + str(fid)

def detectAndTrackFace():
    # Open the first webcame device
    capture = cv2.VideoCapture(0)

    # variables holding the current frame number and the current faceid
    frameCounter = 0
    currentFaceID = 0

    # Variables holding the correlation trackers and the name per faceid
    faceTrackers = {}
    faceNames = {}

    try:
        while True:
            # Retrieve the latest image from the webcam
            rc,fullSizeBaseImage = capture.read()

            # Resize the image to 320x240
            baseImage = cv2.resize( fullSizeBaseImage, ( 320, 240))

            # Result image is the image we will show the user, which is a
            # combination of the original image from the webcam and the
            # overlayed rectangle for the largest face
            resultImage = baseImage.copy()

            # Increase the framecounter
            frameCounter += 1

            # Update all the trackers and remove the ones for which the update
            # indicated the quality was not good enough
            fidsToDelete = []
            for fid in faceTrackers.keys():
                trackingQuality = faceTrackers[ fid ].update( baseImage )

                #If the tracking quality is good enough, we must delete this tracker
                if trackingQuality < 7:
                    fidsToDelete.append( fid )

            for fid in fidsToDelete:
                faceTrackers.pop( fid , None )

            if (frameCounter % FRAME_DETECT_FREQ) == 0:
                # For the face detection, we need to make use of a gray
                # colored image so we will convert the baseImage to a
                # gray-based image
                gray = cv2.cvtColor(baseImage, cv2.COLOR_BGR2GRAY)

                # Now use the haar cascade detector to find all faces in the image
                faces = faceCascade.detectMultiScale(gray, 1.3, 5)

                # Loop over all faces and check if the area for this
                # face is the largest so far
                # We need to convert it to int here because of the
                # requirement of the dlib tracker. If we omit the cast to
                # int here, you will get cast errors since the detector
                # returns numpy.int32 and the tracker requires an int
                for (_x,_y,_w,_h) in faces:
                    x = int(_x)
                    y = int(_y)
                    w = int(_w)
                    h = int(_h)

                    print("Face detected")

                    # Create and store the tracker
                    tracker = dlib.correlation_tracker()
                    tracker.start_track(baseImage,
                                        dlib.rectangle( x-10,
                                                        y-20,
                                                        x+w+10,
                                                        y+h+20))

                    faceTrackers[ currentFaceID ] = tracker

            # Now loop over all the trackers we have and draw the rectangle
            # around the detected faces. If we 'know' the name for this person
            # (i.e. the recognition thread is finished), we print the name
            # of the person, otherwise the message indicating we are detecting
            # the name of the person
            for fid in faceTrackers.keys():
                tracked_position =  faceTrackers[fid].get_position()

                t_x = int(tracked_position.left())
                t_w = int(tracked_position.width())

                avatarCom.lookat(normalize(t_x + t_w/2, 240, 320))

    except KeyboardInterrupt as e:
        pass

if __name__ == '__main__':
    detectAndTrackFace()