package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class CurveController {
    // TODO: Inject Curve Point service
    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }


    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvePointList", curvePointService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()){
            curvePointService.save(curvePoint);
            model.addAttribute("curvePointList",  curvePointService.findAll());
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id :" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        CurvePoint curvePointToUpdate = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id :" + id));
        if (!result.hasErrors()){
            curvePointService.update(curvePointToUpdate, curvePoint);
            model.addAttribute("curvePointList", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }
        model.addAttribute("curvePoint", curvePointToUpdate);
        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        CurvePoint curvePointToDelete = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id :" + id));
        curvePointService.delete(curvePointToDelete);
        model.addAttribute("curvePointList", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
}
