package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }


    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal user) {
        log.info("Request : /curvePoint/list");
        model.addAttribute("connectedUser", PrincipalUtils.getUserName(user));
        model.addAttribute("isAdmin", PrincipalUtils.isAdmin(user));
        model.addAttribute("curvePointList", curvePointService.findAll());
        log.info("Response for : there are " + curvePointService.findAll().size() + " curvePoints");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint, Model model) {
        log.info("Request GET : /curvePoint/add");
        model.addAttribute("curvePoint", curvePoint);
        log.info("Response : OK");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("Request POST : /curvePoint/validate");
        if (!result.hasErrors()){
            curvePointService.save(curvePoint);
            model.addAttribute("curvePointList",  curvePointService.findAll());
            log.info("Response : curvePoint added : " + curvePoint.getId()
                    + " (curveId: " + curvePoint.getCurveId()
                    + ", term: " + curvePoint.getTerm() + ", value: "
                    + curvePoint.getValue() + ")");
            return "redirect:/curvePoint/list";
        }
        log.error("Response : " + result.getErrorCount() + "errors");
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /curvePoint/update/{id}");
        CurvePoint curvePoint = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id :" + id));
        model.addAttribute("curvePoint", curvePoint);
        log.info("Response : curvePoint to update : " + curvePoint.getId());
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        log.info("Request POST : /curvePoint/update/{id}");
        CurvePoint curvePointToUpdate = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id :" + id));
        if (!result.hasErrors()){
            curvePointService.update(curvePointToUpdate, curvePoint);
            model.addAttribute("curvePointList", curvePointService.findAll());
            log.info("Response : curvePoint updated : " + curvePointToUpdate.getId()
                    + " (curveId: " + curvePointToUpdate.getCurveId()
                    + ", term: " + curvePointToUpdate.getTerm() + ", value: "
                    + curvePointToUpdate.getValue() + ")");
            return "redirect:/curvePoint/list";
        }
        model.addAttribute("curvePoint", curvePointToUpdate);
        log.error("Response : " + result.getErrorCount() + "errors");
        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /curvePoint/delete/{id}");
        CurvePoint curvePointToDelete = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id :" + id));
        curvePointService.delete(curvePointToDelete);
        model.addAttribute("curvePointList", curvePointService.findAll());
        log.info("Response : curvePoint to delete : " + curvePointToDelete.getId());
        return "redirect:/curvePoint/list";
    }
}
