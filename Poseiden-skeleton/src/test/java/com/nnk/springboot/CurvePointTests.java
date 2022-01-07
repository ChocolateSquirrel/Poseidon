package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointTests {

	@Autowired
	private CurvePointService curvePointService;

	@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

		// Save
		curvePointService.save(curvePoint);
		Assertions.assertNotNull(curvePoint.getId());
		Assertions.assertTrue(curvePoint.getCurveId() == 10);

		// Update
		CurvePoint curveNew = new CurvePoint(10, 10d, 30d);
		curveNew.setCurveId(20);
		curvePointService.update(curvePoint, curveNew);
		Assertions.assertTrue(curvePoint.getCurveId() == 20);

		// Find
		List<CurvePoint> listResult = curvePointService.findAll();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointService.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointService.findById(id);
        Assertions.assertFalse(curvePointList.isPresent());
	}

}
