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
import java.util.List;

@Slf4j
@Controller
public class BidListController {
    // TODO: Inject Bid service
    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        log.info("Request : /bidList/list");
        model.addAttribute("bidList", bidListService.findAllBids());
        log.info("Response : ");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!result.hasErrors()){
            bidListService.save(bid);
            model.addAttribute("bidList", bidListService.findAllBids());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id :" + id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        BidList bidToUpdate = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id :" + id));
        if (!result.hasErrors()){
            bidListService.update(bidToUpdate, bidList);
            model.addAttribute("bidList", bidListService.findAllBids());
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidList", bidToUpdate);
        return "/bidList/update";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        BidList bid = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id :" + id));
        bidListService.delete(bid);
        model.addAttribute("bidList", bidListService.findAllBids());
        return "redirect:/bidList/list";
    }
}
