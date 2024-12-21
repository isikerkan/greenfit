package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.model.ShortActivity;
import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity;
import ch.pa5.greenfit.repository.entity.UserEntity;
import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
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
  @Operation(summary = "Lists all activities and marks the selected one")
  public String getAllActivities(
      @Parameter(
              example = "4",
              description = "if you wish to mark an activity as selected, pass this value")
          @RequestParam(required = false)
          Long selectedActivity,
      Model model) {
    val activities = activityService.getAllActivities();
    log.info("This activity is selected: {}", selectedActivity);
    model.addAttribute("activities", activities);
    model.addAttribute("selected", selectedActivity);
    return "fragment/activity-option";
  }

  @GetMapping("/activity-log/{userId}")
  @Operation(summary = "Lists all logged activities of a user.", deprecated = true)
  public String getAllUserActivities(@PathVariable Long userId, Model model) {
    val userActivities = activityService.getAllActivityLogs(userId);
    model.addAttribute("userActivities", userActivities);
    return "fragment/user-activities";
  }

  @PostMapping("/activity-log")
  @Operation(
      summary = "Saves the input activity log for a user.",
      description =
          "Duration is sent in minutes by the UI. The controller converts it to seconds.\nUser and activity are only sent by their id.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns empty. Redirects to the dashboard."))
  public String saveActivity(
      @RequestBody ActivityLogEntity activityLog, HttpServletResponse response) {
    activityService.saveActivityLog(
        // the ui sends this in minutes
        activityLog.withDuration(activityLog.getDuration().multiply(new BigInteger("60"))));

    response.setHeader("HX-Redirect", "/");
    return "empty";
  }

  @PostMapping("/activity-timer/start")
  @Operation(
      summary = "Starts a tracker with type TIMER.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns the same activity page. This time with an active tracker."))
  public String startActivityTimer(
      @RequestBody ActivityTrackerEntity activityTrackerEntity, Model model) {
    val activeTracker = activityService.startActivityTimer(activityTrackerEntity);

    model.addAttribute("user", userService.findUser());
    model.addAttribute("activeTracker", activeTracker);
    return "activity-page";
  }

  @PostMapping("/activity-timer/stop")
  @Operation(
      summary = "Stops an active tracker and creates the activity log.",
      description =
          "The activity log is created based on the activity the tracker was created for and the duration between the start and the stop timestamps",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns empty. Redirects to the dashboard"))
  public String stopActivityTimer(
      @RequestBody ActivityTrackerEntity activityTrackerEntity, HttpServletResponse response) {
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

    response.setHeader("HX-Redirect", "/");
    return "empty";
  }

  @GetMapping("/activity-short-list")
  @Operation(
      summary = "Loads a shortlist of activities and their burnt calories.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "Returns a list of duration and burnt calories tuples."))
  public String activityShortList(
      Model model, @Parameter(example = "2024-12-01") @RequestParam LocalDate date) {
    val user = userService.findUser();

    val userActivities =
        activityService.getAllActivities(user.getId(), date).stream()
            .map(
                activityLogEntity -> {
                  val burn =
                      activityService
                          .calculateBurn(activityLogEntity)
                          .setScale(0, RoundingMode.FLOOR);
                  return new ShortActivity(
                      Duration.ofSeconds(activityLogEntity.getDuration().intValue()), burn);
                })
            .toList();

    model.addAttribute("userActivities", userActivities);

    return "fragment/activity-short-list";
  }
}
