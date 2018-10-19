import requests

AVATAR_URL = "http://localhost:8079/avatar/"
LOOKAT_URL = AVATAR_URL + "lookAt/"
IDLE_URL = AVATAR_URL + "idle/"
OFFSET = .5

class AvatarCommunicator:

    def __init__(self):
        pass

    def lookat(self, normalized):
        print(-(normalized + OFFSET))
        requests.get(LOOKAT_URL + str(-(normalized + OFFSET)))

    def reset(self):
        requests.get(IDLE_URL)
