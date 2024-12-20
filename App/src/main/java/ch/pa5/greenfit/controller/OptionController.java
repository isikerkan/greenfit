package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.PersonEntity;
import ch.pa5.greenfit.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class OptionController {

  private final UserService userService;

  @PostMapping("/options")
  public String saveOptions(
      @RequestBody PersonEntity userOptionEntity, Model model, HttpServletResponse response) {

    val oldUser = userService.findUser();
    if (oldUser.getOptions().getWeight() == null) {
      response.setHeader("HX-Redirect", "/");
    }

    userService.saveUserOption(userOptionEntity).orElseThrow();
    val user = userService.findUser();

    model.addAttribute("user", user);

    return "options-page";
  }
}
