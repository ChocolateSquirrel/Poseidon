package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> findAllBids(){
        return bidListRepository.findAll();
    }

    public void save(BidList bid){
        bidListRepository.save(bid);
    }

    public Optional<BidList> findById(Integer id){
        return bidListRepository.findByBidListId(id);
    }

    public void update(BidList bidToUpdate, BidList bid){
        bidToUpdate.setAccount(bid.getAccount());
        bidToUpdate.setType(bid.getType());
        bidToUpdate.setBidQuantity(bid.getBidQuantity());
        bidListRepository.save(bidToUpdate);
    }

    public void delete(BidList bidList){
        bidListRepository.delete(bidList);
    }
}
