package de.auxhack.vera.controller.service.impl;

import de.auxhack.vera.Text2Speech;
import de.auxhack.vera.controller.service.VoiceService;
import de.auxhack.vera.domain.TalkValue;
import org.springframework.stereotype.Service;

/**
 * backend Author: tsteidle Created: 19.10.18
 */
@Service
public class VoiceServiceImpl implements VoiceService {

	@Override
	public void talk(TalkValue talkValue) {
		Text2Speech text2speech = new Text2Speech();
		
		try {
			text2speech.speak(talkValue.getText(), talkValue.getLocale(), talkValue.isMasculine());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
