package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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
public class RatingTests {

	@Autowired
	private RatingService ratingService;

	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		ratingService.save(rating);
		Assertions.assertNotNull(rating.getId());
        Assertions.assertTrue(rating.getOrderNumber() == 10);

		// Update
		Rating ratingNew = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		ratingNew.setOrderNumber(20);
		ratingService.update(rating, ratingNew);
        Assertions.assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingService.findAll();
        Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingService.delete(rating);
		Optional<Rating> ratingList = ratingService.findById(id);
        Assertions.assertFalse(ratingList.isPresent());
	}
}
