package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {

    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }


    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal user) {
        log.info("Request : /ruleName/list");
        model.addAttribute("connectedUser", PrincipalUtils.getUserName(user));
        model.addAttribute("isAdmin", PrincipalUtils.isAdmin(user));
        model.addAttribute("ruleList", ruleNameService.findAll());
        log.info("Response for /ruleName/list : there are " + ruleNameService.findAll().size() + " ruleNames");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        log.info("Request GET : /ruleName/add");
        model.addAttribute("ruleName", ruleName);
        log.info("Response : OK");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("Request POST : /ruleName/validate");
        if (!result.hasErrors()){
            ruleNameService.save(ruleName);
            model.addAttribute("ruleList", ruleNameService.findAll());
            log.info("Response : ruleName added " + ruleName.getId() + " (name:" + ruleName.getName() + ")");
            return "redirect:/ruleName/list";
        }
        log.error("Response : " + result.getErrorCount() + "errors");
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /ruleName/update/{id}");
        RuleName rule = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id : " + id));
        model.addAttribute("ruleName", rule);
        log.info("Response : ruleName to update : " + rule.getId());
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        log.info("Request POST : /ruleName/update/{id}");
        RuleName ruleToUpdate = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id : " + id));
        if (!result.hasErrors()){
            ruleNameService.update(ruleToUpdate, ruleName);
            model.addAttribute("ruleList", ruleNameService.findAll());
            log.info("Response : ruleName updated " + ruleToUpdate.getId() + " (name:" + ruleToUpdate.getName() + ")");
            return "redirect:/ruleName/list";
        }
        model.addAttribute("ruleName", ruleToUpdate);
        log.error("Response : " + result.getErrorCount() + "errors");
        return "ruleName/update";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /ruleName/delete/{id}");
        RuleName rule = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id : " + id));
        ruleNameService.delete(rule);
        model.addAttribute("ruleList", ruleNameService.findAll());
        log.info("Response : ruleName to delete : " + rule.getId());
        return "redirect:/ruleName/list";
    }
}
