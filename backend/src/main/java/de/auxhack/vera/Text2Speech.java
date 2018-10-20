package de.auxhack.vera;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import de.auxhack.vera.controller.service.impl.ListenerServiceImpl;

//import javafx.application.Application;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.stage.Stage;

/**
 * backend Author: tsteidle Created: 19.10.18
 */
public class Text2Speech {
	public static void main(String... args) throws Exception {

	}

	public void speak(String text, String locale, boolean isMasculine) throws Exception {
		// final JFXPanel fxPanel = new JFXPanel();

		// Instantiates a client
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
			// Set the text input to be synthesized
			SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

			// Build the voice request, select the language code ("en-US") and the ssml
			// voice gender
			// ("neutral")
			SsmlVoiceGender gender = SsmlVoiceGender.FEMALE;
			String name = "";
			if (isMasculine) {
				gender = SsmlVoiceGender.MALE;

				if ("de-DE".equalsIgnoreCase(locale))
					name = "de-DE-Wavenet-B";
				else
					if ("en-US".equalsIgnoreCase(locale))
					name = "en-US-Wavenet-B";
			} else {
				if ("de-DE".equalsIgnoreCase(locale))
					name = "de-DE-Wavenet-A";
				else if ("en-US".equalsIgnoreCase(locale))
					name = "en-US-Wavenet-C";
				else if ("fr-FR".equalsIgnoreCase(locale))
					name = "fr-FR-Wavenet-A";
			}

			VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode(locale).setSsmlGender(gender)
					.setName(name).build();

			// Select the type of audio file you want returned
			AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();

			// Perform the text-to-speech request on the text input with the selected voice
			// parameters and
			// audio file type
			SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

			// Get the audio contents from the response
			ByteString audioContents = response.getAudioContent();

			// Write the response to the output file.
			File file = new File("./output.wav");
			try (OutputStream out = new FileOutputStream(file)) {
				out.write(audioContents.toByteArray());
				out.close();
				System.out.println("Audio content written to file \"" + file.getName() + "\"");

//				 String bip = "output.mp3";
//				 Media hit = new Media(new File(bip).toURI().toString());
//				 MediaPlayer mediaPlayer = new MediaPlayer(hit);
//				 mediaPlayer.play();

				// InputStream audioSrc =
				// getClass().getResourceAsStream("/home/vitek/output.wav");
				// add buffer for mark/reset support
				InputStream bufferedIn = new BufferedInputStream(new FileInputStream(file));

				AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
				// clip.close();
				audioIn.close();
				bufferedIn.close();
//                ListenerServiceImpl impl = new ListenerServiceImpl();
//                impl.listen(text);
			}

		}
	}
}