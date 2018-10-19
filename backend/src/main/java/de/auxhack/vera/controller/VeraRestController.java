package de.auxhack.vera.controller;

import de.auxhack.vera.controller.service.VoiceService;
import de.auxhack.vera.domain.Greeting;
import de.auxhack.vera.domain.TalkValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * backend
 * Author: tsteidle
 * Created: 19.10.18
 */
@RestController
public class VeraRestController {

    @Autowired
    private VoiceService voiceService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value = "/talk", method = RequestMethod .POST)
    public ResponseEntity<TalkValue> update(@RequestBody TalkValue talkValue) {
        this.voiceService.talk(talkValue);
        return new ResponseEntity<TalkValue>(talkValue, HttpStatus.OK);
    }

}
