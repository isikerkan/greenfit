package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.ActivityLogEntity;
import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity;
import ch.pa5.greenfit.service.ActivityService;
import ch.pa5.greenfit.service.UserService;
import ch.pa5.greenfit.util.DateUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class DateController {

  @GetMapping("/change-date")
  public String changeDate(HttpServletResponse response, Model model, @RequestParam(required = false) LocalDate date) {
    if(date == null) {
      date = LocalDate.now();
    }
    val dayBefore = date.minusDays(1);
    model.addAttribute("selectedDate", date.toString());
    model.addAttribute("previousDate", dayBefore.toString());
    model.addAttribute("previousDateLabel", DateUtils.computeDateLabel(dayBefore));

    response.setHeader("HX-Trigger-After-Swap", "date-changed");

    return "fragment/date-navigation";
  }
}
