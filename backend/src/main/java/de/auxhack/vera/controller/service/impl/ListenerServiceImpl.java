package de.auxhack.vera.controller.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.auxhack.vera.Speech2Text;
import de.auxhack.vera.controller.service.ListenerService;

@Service
public class ListenerServiceImpl implements ListenerService {

	@Override
	public void listen(String text) {
		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = "http://localhost:8081/listen";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("text", text);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);

		ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, String.class);

		response.getBody();
	}

	@Override
	public void setListen(boolean doListen) {
		if (doListen)
			try {
				String result = Speech2Text.streamingMicRecognize();

				if (result != null && !result.isEmpty())
					listen(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
