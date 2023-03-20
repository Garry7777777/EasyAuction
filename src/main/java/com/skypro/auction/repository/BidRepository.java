package com.skypro.auction.repository;

import com.skypro.auction.model.Bid;
import com.skypro.auction.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Lot> {
    Collection<Bid> findByLotIdOrderByDateDesc(Long id);

    Collection<Bid> findByLotId(Long id);

}
