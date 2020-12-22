package ru.maxim.clicker.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.maxim.clicker.domain.Count;
import ru.maxim.clicker.domain.CounterService;

@Controller
public class WebController {
    CounterService counterService;


    Logger logger = LoggerFactory.getLogger(ClickerRest.class);

    @Autowired
    public WebController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping(value="/")
    public String homepage(Model model){
        Count count = counterService.getNumOfClicks();
        logger.info("Request on url: /count with result: " + count.getValue());
        model.addAttribute("count", count.getValue());
        return "index";
    }
}
