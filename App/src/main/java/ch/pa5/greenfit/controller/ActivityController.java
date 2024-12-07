package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.service.ActivityService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ActivityController {

  private final ActivityService activityService;


  @GetMapping("/activity")
  public String getAllActivities(Model model) {
    val activities = activityService.getAllActivities();
    log.debug("got these activities: {}", activities);
    model.addAttribute("activities", activities);
    return "fragment/activity-option";
  }

  @GetMapping("/activity-log/{userId}")
  public String getAllUserActivities(@PathVariable Long userId, Model model) {
    val userActivities = activityService.getAllActivityLogs(userId);
    model.addAttribute("userActivities", userActivities);
    return "fragment/user-activities";
  }

  @PostMapping("/activity-log")
  public String saveActivity(@RequestBody ActivityLogEntity activityLog, Model model) {
    val savedActivityOpt = activityService.saveActivityLog(activityLog);
    savedActivityOpt.ifPresent(
        activity -> {
          model.addAttribute("userActivity", activity);
        });
    return "fragment/user-activity";
  }
}
