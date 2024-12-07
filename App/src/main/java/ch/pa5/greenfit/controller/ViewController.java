package ch.pa5.greenfit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/dashboard")
  public String dashboard() {
    return "dashboard";
  }

  @GetMapping("/calories")
  public String calories(Model model) {
    model.addAttribute("userId", "1");
    return "calorie-page";
  }

  @GetMapping("/activities")
  public String activities(Model model) {
    model.addAttribute("userId", "1");
    return "activity-page";
  }
}
