package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeService tradeService;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");

		// Save
		tradeService.save(trade);
        Assertions.assertNotNull(trade.getTradeId());
        Assertions.assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		Trade tradeNew = new Trade("Trade Account", "Type");
		tradeNew.setAccount("Trade Account Update");
		tradeService.update(trade, tradeNew);
        Assertions.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeService.findAll();
        Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeService.delete(trade);
		Optional<Trade> tradeList = tradeService.findById(id);
        Assertions.assertFalse(tradeList.isPresent());
	}
}
