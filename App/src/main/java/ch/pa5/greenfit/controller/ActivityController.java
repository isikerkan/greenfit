package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity;
import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.UserService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ActivityController {

  private final ActivityService activityService;
  private final UserService userService;

  @GetMapping("/activity")
  public String getAllActivities(
      @RequestParam(required = false) Long selectedActivity, Model model) {
    val activities = activityService.getAllActivities();
    log.info("This activity is selected: {}", selectedActivity);
    model.addAttribute("activities", activities);
    model.addAttribute("selected", selectedActivity);
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
    val savedActivityOpt =
        activityService.saveActivityLog(
            // the ui sends this in minutes
            activityLog.withDuration(activityLog.getDuration().multiply(new BigInteger("60"))));
    savedActivityOpt.ifPresent(
        activity -> {
          model.addAttribute("userActivity", activity);
        });
    return "fragment/user-activity";
  }

  @PostMapping("/activity-timer/start")
  public String startActivityTimer(
      @RequestBody ActivityTrackerEntity activityTrackerEntity, Model model) {
    val activeTracker = activityService.startActivityTimer(activityTrackerEntity);

    model.addAttribute("user", userService.findUser());
    model.addAttribute("activeTracker", activeTracker);
    return "activity-page";
  }

  @PostMapping("/activity-timer/stop")
  public String stopActivityTimer(
      @RequestBody ActivityTrackerEntity activityTrackerEntity, Model model) {
    val activityTracker =
        activityService.stopActivityTimer(activityTrackerEntity.getUser().getId());

    activityService.saveActivityLog(
        ActivityLogEntity.builder()
            .activity(activityTracker.getActivity())
            .user(activityTracker.getUser())
            .duration(
                BigInteger.valueOf(
                    Duration.between(activityTracker.getStartUtc(), activityTracker.getStopUtc())
                        .getSeconds()))
            .build());

    model.addAttribute("user", userService.findUser());
    model.addAttribute("activeTracker", null);
    return "activity-page";
  }
}
