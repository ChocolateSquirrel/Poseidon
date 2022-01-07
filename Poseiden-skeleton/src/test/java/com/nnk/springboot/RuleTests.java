package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleTests {

	@Autowired
	private RuleNameService ruleNameService;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		ruleNameService.save(rule);
		Assertions.assertNotNull(rule.getId());
        Assertions.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		RuleName ruleNew = new RuleName();
		ruleNew.setName("Rule Name Update");
		ruleNameService.update(rule, ruleNew);
        Assertions.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameService.findAll();
        Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameService.delete(rule);
		Optional<RuleName> ruleList = ruleNameService.findById(id);
        Assertions.assertFalse(ruleList.isPresent());
	}
}
