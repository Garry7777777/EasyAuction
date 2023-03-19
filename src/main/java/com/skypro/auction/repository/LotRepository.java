package com.skypro.auction.repository;

import com.skypro.auction.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository <Lot, Long> {
}
