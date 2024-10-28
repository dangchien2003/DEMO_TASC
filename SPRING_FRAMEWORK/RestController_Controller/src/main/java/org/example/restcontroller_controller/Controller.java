package org.example.restcontroller_controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/controller")
    public String rest(Model model) {
        model.addAttribute("message", "chien");
        return "tem";
    }

    @GetMapping("/controller1")
    @ResponseBody
    public String rest1(Model model) {
        model.addAttribute("message", "chien");
        return "tem";
    }

    @GetMapping("/controller2")
    public ModelAndView rest2() {
        ModelAndView modelAndView = new ModelAndView("tem2");
        modelAndView.addObject("message", "chien");
        return modelAndView;
    }
}
