package com.skypro.auction.repository;

import com.skypro.auction.model.Lot;
import com.skypro.auction.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LotRepository extends JpaRepository <Lot, Long> {
    List<Lot> findAllByStatusEquals(Status status, Pageable pageable);
}
