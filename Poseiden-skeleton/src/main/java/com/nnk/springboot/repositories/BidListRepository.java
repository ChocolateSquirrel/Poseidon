package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    @Override
    List<BidList> findAll();

    @Override
    BidList save(BidList bidToSave);

    @Override
    void delete(BidList bidToDelete);

    Optional<BidList> findByBidListId(Integer integer);
}
