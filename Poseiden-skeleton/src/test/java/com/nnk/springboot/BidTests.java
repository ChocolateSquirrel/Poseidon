package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BidTests {

	@Autowired
	private BidListService bidListService;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bidListService.save(bid);
		Assertions.assertNotNull(bid.getBidListId());
		Assertions.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		BidList bidNew = new BidList("Account Test", "Type Test", 10d);
		bidNew.setBidQuantity(20d);
		bidListService.update(bid, bidNew);
		Assertions.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListService.findAllBids();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListService.delete(bid);
		Optional<BidList> bidList = bidListService.findById(id);
		Assertions.assertFalse(bidList.isPresent());
	}
}
