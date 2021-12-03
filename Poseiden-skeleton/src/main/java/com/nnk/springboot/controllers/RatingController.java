package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model, Principal user) {
        log.info("Request : /rating/list");
        model.addAttribute("connectedUser", PrincipalUtils.getUserName(user));
        model.addAttribute("ratingList", ratingService.findAll());
        log.info("Response for /rating/list : there are " + ratingService.findAll().size() + " ratings");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        log.info("Request GET : /rating/add");
        model.addAttribute("rating", rating);
        log.info("Response : OK");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        log.info("Request POST : /rating/validate");
        if (!result.hasErrors()){
            ratingService.save(rating);
            model.addAttribute("ratingList", ratingService.findAll());
            log.info("Response : rating added " + rating.getId()
                    + " (moodys:" + rating.getMoodysRating()
                    + ", sandP:" + rating.getSandPRating()
                    + ", fitch:" + rating.getFitchRating()
                    + ", order:" + rating.getOrderNumber() + ")");
            return "redirect:/rating/list";
        }
        log.error("Response : " + result.getErrorCount() + "errors");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /rating/update/{id}");
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id : " + id));
        model.addAttribute("rating", rating);
        log.info("Response : rating to update : " + rating.getId());
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        log.info("Request POST : /rating/update/{id}");
        Rating ratingToUpdate = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id : " + id));
        if (!result.hasErrors()){
            ratingService.update(ratingToUpdate, rating);
            model.addAttribute("ratingList", ratingService.findAll());
            log.info("Response : rating updated " + ratingToUpdate.getId()
                    + " (moodys:" + ratingToUpdate.getMoodysRating()
                    + ", sandP:" + ratingToUpdate.getSandPRating()
                    + ", fitch:" + ratingToUpdate.getFitchRating()
                    + ", order:" + ratingToUpdate.getOrderNumber() + ")");
            return "redirect:/rating/list";
        }
        model.addAttribute("rating", ratingToUpdate);
        log.error("Response : " + result.getErrorCount() + "errors");
        return "rating/update";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /rating/delete/{id}");
        Rating ratingToDelete = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id : " + id));
        ratingService.delete(ratingToDelete);
        model.addAttribute("ratingList", ratingService.findAll());
        log.info("Response : rating to delete : " + ratingToDelete.getId());
        return "redirect:/rating/list";
    }
}
