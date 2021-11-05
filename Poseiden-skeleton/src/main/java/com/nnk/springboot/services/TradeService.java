package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() { return tradeRepository.findAll(); }

    public void save(Trade trade) { tradeRepository.save(trade); }

    public Optional<Trade>findById(Integer id) { return tradeRepository.findByTradeId(id); }

    public void update(Trade tradeToUpdate, Trade trade){
        tradeToUpdate.setAccount(trade.getAccount());
        tradeToUpdate.setType(trade.getType());
        tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
        tradeToUpdate.setSellQuantity(trade.getSellQuantity());
        tradeToUpdate.setBuyPrice(trade.getBuyPrice());
        tradeToUpdate.setSellPrice(trade.getSellPrice());
        tradeToUpdate.setBenchmark(trade.getBenchmark());
        tradeToUpdate.setTradeDate(trade.getTradeDate());
        tradeToUpdate.setSecurity(trade.getSecurity());
        tradeToUpdate.setStatus(trade.getStatus());
        tradeToUpdate.setTrader(trade.getTrader());
        tradeToUpdate.setBook(trade.getBook());
        tradeToUpdate.setCreationName(trade.getCreationName());
        tradeToUpdate.setCreationDate(trade.getCreationDate());
        tradeToUpdate.setRevisionName(trade.getRevisionName());
        tradeToUpdate.setRevisionDate(trade.getRevisionDate());
        tradeToUpdate.setDealName(trade.getDealName());
        tradeToUpdate.setDealType(trade.getDealType());
        tradeToUpdate.setSourceListId(trade.getSourceListId());
        tradeToUpdate.setSide(trade.getSide());
        tradeRepository.save(tradeToUpdate);
    }

    public void delete(Trade trade) { tradeRepository.delete(trade); }


}
