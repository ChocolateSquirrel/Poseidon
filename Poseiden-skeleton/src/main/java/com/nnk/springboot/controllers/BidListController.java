package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {

    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal user) {
        log.info("Request : /bidList/list");
        model.addAttribute("connectedUser", PrincipalUtils.getUserName(user));
        model.addAttribute("bidList", bidListService.findAllBids());
        log.info("Response for /bidList/list : there are " + bidListService.findAllBids().size() + " bidLists");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        log.info("Request GET : /bidList/add");
        model.addAttribute("bidList", bid);
        log.info("Response : OK");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        log.info("Request POST : /bidList/validate");
        if (!result.hasErrors()){
            bidListService.save(bid);
            model.addAttribute("bidList", bidListService.findAllBids());
            log.info("Response : bidList added : " + bid.getBidListId()
                    + " (account:" + bid.getAccount()
                    + ", type:" + bid.getType()
                    + ", quantity:" +  bid.getBidQuantity() + ")");
            return "redirect:/bidList/list";
        }
        log.error("Response : " + result.getErrorCount() + "errors");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /bidList/update/{id}");
        BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id :" + id));
        model.addAttribute("bidList", bid);
        log.info("Response : bidListId to update : " + bid.getBidListId());
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        log.info("Request POST : /bidList/update/{id}");
        BidList bidToUpdate = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id :" + id));
        if (!result.hasErrors()){
            bidListService.update(bidToUpdate, bidList);
            model.addAttribute("bidList", bidListService.findAllBids());
            log.info("Response : bidList updated : " + bidToUpdate.getBidListId()
                    + "(account: " + bidToUpdate.getAccount()
                    + ", type: " + bidToUpdate.getType()
                    + ", quantity: " +  bidToUpdate.getBidQuantity() + ")");
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidList", bidToUpdate);
        log.error("Response : " + result.getErrorCount() + "errors");
        return "/bidList/update";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Request GET : /bidList/delete/{id}");
        BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id :" + id));
        bidListService.delete(bid);
        model.addAttribute("bidList", bidListService.findAllBids());
        log.info("Response : bidListId to delete : " + bid.getBidListId());
        return "redirect:/bidList/list";
    }
}
