package pers.jd.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jensen_deng
 */
@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping
  public String test() {
    return "test success!";
  }
}
