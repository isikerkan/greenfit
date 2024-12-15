package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.UserOptionEntity;
import ch.pa5.greenfit.service.UserService;
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
  public String saveOptions(@RequestBody UserOptionEntity userOptionEntity, Model model) {
    val user = userService.saveUserOption(userOptionEntity).orElseThrow();

    model.addAttribute("user", user);

    return "options-page";
  }
}
