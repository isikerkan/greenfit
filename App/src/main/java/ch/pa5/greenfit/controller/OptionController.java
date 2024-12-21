package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.PersonEntity;
import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.CalorieService;
import ch.pa5.greenfit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class OptionController {

  private final UserService userService;
  private final CalorieService calorieService;
  private final ActivityService activityService;

  @PostMapping("/options")
  @Operation(
      summary = "Stores the options of the user",
      description =
          "If the weight was previously null the user is in the first time access of the site and gets redirected to the dashboard afterwards. Otherwise the user can change the information on this page at will.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns the rendered content part of the options page."))
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

  @DeleteMapping("/user-data")
  @Operation(
      summary = "Deletes all of the users data.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns the rendered content part of the options page."))
  public String deleteUserdata(Model model) {
    val user = userService.findUser();
    calorieService.deleteUserdata(user.getId());
    activityService.deleteUserdata(user.getId());
    userService.deleteUserdata(user);

    model.addAttribute("user", userService.findUser());

    return "options-page";
  }

  @DeleteMapping("/user-account")
  @Operation(
      summary = "Deletes all of the users data and then removes the user from the database.",
      description =
          "If the user reenters the page after deleting the user account, a new account will be created.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description =
                  "Redirects to the exit page where the user can close the tab in peace."))
  public String deleteUserAccount(HttpServletResponse response) {
    val user = userService.findUser();
    calorieService.deleteUserdata(user.getId());
    activityService.deleteUserdata(user.getId());
    userService.deleteUser(user.getId());
    response.setHeader("HX-Redirect", "/exit");
    return "empty";
  }
}
