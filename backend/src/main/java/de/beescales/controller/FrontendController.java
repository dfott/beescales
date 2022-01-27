package de.beescales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

  @GetMapping(value = {"/dashboard", "/settings"})
  public String getDashboard() {
    return "index.html";
  }
}
