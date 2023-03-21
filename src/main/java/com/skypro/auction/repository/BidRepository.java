package com.skypro.auction.repository;

import com.skypro.auction.model.Bid;
import com.skypro.auction.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Lot> {

    List<Bid> findByLotIdOrderByDateDesc(Long id);
    List<Bid> findByLotId(Long id);
    List<Bid> findByLotIdOrderByDateAsc(Long id);
    List<Bid> findByBidderAndLot_IdOrderByDateDesc(String bidder,Long id);

    @Query(value =
    "SELECT bidder FROM (select * from bid WHERE lot_id= ?1) AS b GROUP BY bidder ORDER BY count(*) DESC LIMIT 1",
    nativeQuery = true)
    String findFrequentByLotId(Long lot_id);
}
