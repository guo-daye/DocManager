package com.great.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class UrlController {
    @RequestMapping("/url/{uri}")
    public String url(@PathVariable(value = "uri")String path) {
        return path;
    }
}
