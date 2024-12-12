package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

  private final UserService userService;


  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    var user = userService.findUser();
    model.addAttribute("user", user);
    return "dashboard";
  }

  @GetMapping("/calories")
  public String calories(Model model) {
    model.addAttribute("user", userService.findUser());
    return "calorie-page";
  }

  @GetMapping("/activities")
  public String activities(Model model) {
    model.addAttribute("user", userService.findUser());
    return "activity-page";
  }
}
