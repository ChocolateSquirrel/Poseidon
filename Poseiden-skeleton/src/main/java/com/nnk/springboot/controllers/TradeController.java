package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, Principal user) {
        log.info("Request : /trade/list");
        model.addAttribute("connectedUser", PrincipalUtils.getUserName(user));
        model.addAttribute("isAdmin", PrincipalUtils.isAdmin(user));
        model.addAttribute("tradeList", tradeService.findAll());
        log.info("Response for /trade/list : there are " + tradeService.findAll().size() + " trades");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model) {
        log.info("Request GET : /trade/add");
        model.addAttribute("trade", trade);
        log.info("Response : OK");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        log.info("Request POST : /trade/validate");
        if (!result.hasErrors()){
            tradeService.save(trade);
            model.addAttribute("tradeList", tradeService.findAll());
            log.info("Response : trade added " + trade.getTradeId()
                    + " (account:" + trade.getAccount()
                    + ", type:" + trade.getType()
                    + ", quantity:" + trade.getBuyQuantity() + ")");
            return "redirect:/trade/list";
        }
        log.error("Response : " + result.getErrorCount() + "errors");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /trade/update/{id}");
        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id : " + id));
        model.addAttribute("trade", trade);
        log.info("Response : trade to update : " + trade.getTradeId());
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        log.info("Request POST : /trade/update/{id}");
        Trade tradeToUpdate = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id : " + id));
        if (!result.hasErrors()){
            tradeService.update(tradeToUpdate, trade);
            model.addAttribute("tradeList", tradeService.findAll());
            log.info("Response : trade updated " + tradeToUpdate.getTradeId()
                    + " (account:" + tradeToUpdate.getAccount()
                    + ", type:" + tradeToUpdate.getType()
                    + ", quantity:" + tradeToUpdate.getBuyQuantity() + ")");
            return "redirect:/trade/list";
        }
        model.addAttribute("trade", tradeToUpdate);
        log.error("Response : " + result.getErrorCount() + "errors");
        return "redirect:/trade/update";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /trade/delete/{id}");
        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id : " + id));
        tradeService.delete(trade);
        model.addAttribute("tradeList", tradeService.findAll());
        log.info("Response : trade to delete : " + trade.getTradeId());
        return "redirect:/trade/list";
    }
}
