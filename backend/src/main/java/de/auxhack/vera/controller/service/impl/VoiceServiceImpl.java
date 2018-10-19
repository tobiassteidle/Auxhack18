package de.auxhack.vera.controller.service.impl;

import de.auxhack.vera.controller.service.VoiceService;
import de.auxhack.vera.domain.TalkValue;
import org.springframework.stereotype.Service;

/**
 * backend
 * Author: tsteidle
 * Created: 19.10.18
 */
@Service
public class VoiceServiceImpl implements VoiceService {

    @Override
    public void talk(TalkValue talkValue) {
        System.out.println(talkValue.getText());
    }
}
