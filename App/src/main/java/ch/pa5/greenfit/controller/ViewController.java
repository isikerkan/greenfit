package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.UserService;
import ch.pa5.greenfit.util.DateUtils;
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
  public String dashboard(Model model) {
    val yesterday = LocalDate.now().minusDays(1);
    var user = userService.findUser();
    model.addAttribute("user", user);
    model.addAttribute("selectedDate", LocalDate.now().toString());
    model.addAttribute("previousDate", yesterday.toString());
    model.addAttribute("previousDateLabel", DateUtils.computeDateLabel(yesterday));
    return "dashboard";
  }

  @GetMapping("/calories")
  public String caloriesSlot(Model model) {
    val yesterday = LocalDate.now().minusDays(1);

    model.addAttribute("user", userService.findUser());
    model.addAttribute("selectedDate", LocalDate.now().toString());
    model.addAttribute("previousDate", yesterday.toString());
    model.addAttribute("previousDateLabel", DateUtils.computeDateLabel(yesterday));

    return "calorie-slot-page";
  }

  @GetMapping("/calories/add")
  public String calories(
      Model model,
      @RequestParam(name="slot", required = false) String slot,
      @RequestParam(name = "date", required = false) LocalDate date) {
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
  public String activities(Model model) {
    val user = userService.findUser();
    model.addAttribute("user", user);
    model.addAttribute(
        "activeTracker", activityService.findActiveTracker(user.getId()).orElse(null));
    return "activity-page";
  }

  @GetMapping("/options")
  public String options(Model model) {
    val user = userService.findUser();
    log.info("Loading options for user {}", user);
    model.addAttribute("user", user);
    return "options-page";
  }
}
