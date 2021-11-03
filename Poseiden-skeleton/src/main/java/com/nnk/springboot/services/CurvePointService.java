package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public List<CurvePoint> findAll(){
        return curvePointRepository.findAll();
    }

    public void save(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public Optional<CurvePoint> findById(Integer id) {
        return curvePointRepository.findByCurveId(id);
    }

    public void update(CurvePoint curvePointToUpdate, CurvePoint curvePoint) {
        curvePointToUpdate.setCurveId(curvePoint.getCurveId());
        curvePointToUpdate.setTerm(curvePoint.getTerm());
        curvePointToUpdate.setValue(curvePoint.getValue());
        curvePointRepository.save(curvePointToUpdate);
    }

    public void delete(CurvePoint curvePointToDelete) {
        curvePointRepository.delete(curvePointToDelete);
    }
}
