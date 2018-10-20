package de.auxhack.vera.controller;

import de.auxhack.vera.controller.service.ListenerService;
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
    
    @Autowired
    private ListenerService listenerService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
    
    @RequestMapping("/listen")
    public Greeting listen(@RequestParam(value="doListen", defaultValue="true") boolean doListen) {
    	this.listenerService.setListen(doListen);
        return new Greeting(counter.incrementAndGet(),
                String.format(template, doListen));
    }

    @RequestMapping(value = "/talk", method = RequestMethod .POST)
    public ResponseEntity<TalkValue> update(@RequestBody TalkValue talkValue) {
    	this.listenerService.setListen(false);
        this.voiceService.talk(talkValue);
        this.listenerService.setListen(true);
        return new ResponseEntity<TalkValue>(talkValue, HttpStatus.OK);
    }

}
