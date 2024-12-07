package ch.pa5.greenfit.controller;

import ch.pa5.greenfit.repository.entity.PortionEntity;
import ch.pa5.greenfit.service.CalorieService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalorieController {

  private final CalorieService calorieService;

  @GetMapping("/get-foods")
  public String getFoods(Model model) {

    model.addAttribute("recipes", calorieService.getAllCalories());

    return "fragment/food-option";
  }

  @GetMapping("/calorie-log/{userId}")
  public String getAllUserCalories(@PathVariable Long userId, Model model) {
    val userPortions = calorieService.getAllPortions(userId);
    model.addAttribute("userPortions", userPortions);
    return "fragment/user-portions";
  }

  @PostMapping("/calorie-log")
  public String saveCalorieLog(@RequestBody PortionEntity portionEntity, Model model) {
    val savedPortionOpt = calorieService.savePortion(portionEntity);
    savedPortionOpt.ifPresent(
        portion -> {
          model.addAttribute("userPortion", portion);
        });
    return "fragment/user-portion";
  }
}
