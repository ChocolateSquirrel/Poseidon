package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() { return ruleNameRepository.findAll(); }

    public void save(RuleName rule) { ruleNameRepository.save(rule); }

    public Optional<RuleName> findById(Integer id) { return ruleNameRepository.findById(id); }

    public void update(RuleName ruleToUpdate, RuleName rule){
        ruleToUpdate.setName(rule.getName());
        ruleToUpdate.setDescription(rule.getDescription());
        ruleToUpdate.setJson(rule.getJson());
        ruleToUpdate.setTemplate(rule.getTemplate());
        ruleToUpdate.setSqlStr(rule.getSqlStr());
        ruleToUpdate.setSqlPart(rule.getSqlPart());
        ruleNameRepository.save(ruleToUpdate);
    }

    public void delete(RuleName rule) { ruleNameRepository.delete(rule); }
}
