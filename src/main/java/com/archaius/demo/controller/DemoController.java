package com.archaius.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.archaius.demo.configuration.PropertyManagerComponent;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private PropertyManagerComponent configuration;

    @RequestMapping(value = "/getValue", method = RequestMethod.GET)
    public String initSession(HttpServletRequest request) {

        return configuration.getString("local.demo", " MESSAGE FOR PROP NOT FOUND");
    }
}
