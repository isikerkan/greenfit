package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.UserService;
import ch.pa5.greenfit.util.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

  private final UserService userService;
  private final ActivityService activityService;

  @GetMapping("/dashboard")
  @Operation(summary = "Returns the main content for the dashboard.")
  public String dashboard(Model model, HttpServletResponse response) {
    val yesterday = LocalDate.now().minusDays(1);
    var user = userService.findUser();
    model.addAttribute("user", user);
    model.addAttribute("selectedDate", LocalDate.now().toString());
    model.addAttribute("previousDate", yesterday.toString());
    model.addAttribute("previousDateLabel", DateUtils.computeDateLabel(yesterday));

    if(user.getOptions().getWeight() == null){
      response.setHeader("HX-Redirect", "/options");
    }
    return "dashboard";
  }

  @GetMapping("/calories")
  @Operation(summary = "Returns the main content for the calories page grouping by slot.")
  public String caloriesSlot(Model model) {
    val yesterday = LocalDate.now().minusDays(1);

    model.addAttribute("user", userService.findUser());
    model.addAttribute("selectedDate", LocalDate.now().toString());
    model.addAttribute("previousDate", yesterday.toString());
    model.addAttribute("previousDateLabel", DateUtils.computeDateLabel(yesterday));

    return "calorie-slot-page";
  }

  @GetMapping("/calories/add")
  @Operation(summary = "Returns the main content for the page where new calories are entered.")
  public String calories(
      Model model,
      @Parameter(example = "99") @RequestParam(name="slot", required = false) String slot,
      @Parameter(example = "2024-12-01") @RequestParam(name = "date", required = false) LocalDate date) {
    val currentTime = LocalTime.now();
    if (date == null) {
      date = LocalDate.now();
    }
    if (slot == null) {
      slot = "99";
    }
    model.addAttribute("user", userService.findUser());
    model.addAttribute("slotId", slot);
    model.addAttribute("date", date.atTime(currentTime).toInstant(ZoneOffset.UTC).toString());
    return "calories-add-page";
  }

  @GetMapping("/activities")
  @Operation(summary = "Returns the main page where new activities can be logged.")
  public String activities(Model model) {
    val user = userService.findUser();
    model.addAttribute("user", user);
    model.addAttribute(
        "activeTracker", activityService.findActiveTracker(user.getId()).orElse(null));
    return "activity-page";
  }

  @GetMapping("/options")
  @Operation(summary = "Returns the main content for the options page")
  public String options(Model model) {
    val user = userService.findUser();
    log.info("Loading options for user {}", user);
    model.addAttribute("user", user);
    return "options-page";
  }

  @GetMapping("/exit")
  @Operation(summary = "Small page after a user account got deleted to allow the user to leave. Reentering the page with a valid session will recreate the user account.")
  public String exit(){
    return "exit";
  }
}
