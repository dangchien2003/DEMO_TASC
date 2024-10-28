package org.example.init;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
    @GetMapping
    public String rest(){
        return "hello";
    }
}
