package ru.maxim.clicker.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maxim.clicker.domain.Count;
import ru.maxim.clicker.domain.CounterService;
import ru.maxim.clicker.web.model.ClickerResponse;

@RestController
@RequestMapping(path = "")
public class ClickerRest {

    CounterService counterService;


    Logger logger = LoggerFactory.getLogger(ClickerRest.class);

    @Autowired
    public ClickerRest(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping(path = "/count")
    @ResponseBody
    public ResponseEntity<ClickerResponse> getCount() {
        Count response = counterService.getNumOfClicks();
        logger.info("Request on url: /count with result: " + response.getValue());
        return ResponseEntity.ok(new ClickerResponse(response.getValue()));
    }

    @PostMapping(path = "/click")
    @ResponseBody
    public ResponseEntity<ClickerResponse> makeClick() {
        Count response = counterService.makeClick();
        logger.info("Request on url: /click with result: " + response);
        return ResponseEntity.ok(new ClickerResponse(response.getValue()));
    }

}
