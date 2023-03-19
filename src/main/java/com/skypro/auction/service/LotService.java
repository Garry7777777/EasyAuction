package com.skypro.auction.service;

import com.skypro.auction.DTO.LotDTO;
import com.skypro.auction.repository.LotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LotService {

    public LotRepository lotRepository;

    public LotDTO createLot(LotDTO lotDTO) {
        return null;
    }

    public boolean startLotBidding(Long id) {
        return true;
    }
}

