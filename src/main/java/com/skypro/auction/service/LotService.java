package com.skypro.auction.service;

import com.skypro.auction.DTO.BidDTO;
import com.skypro.auction.DTO.CreateLotDTO;
import com.skypro.auction.DTO.FullLotDTO;
import com.skypro.auction.DTO.LotDTO;
import com.skypro.auction.model.Lot;
import com.skypro.auction.enums.Status;
import com.skypro.auction.repository.BidRepository;
import com.skypro.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class LotService {

    @Autowired
    public LotRepository lotRepository;
    @Autowired
    public BidRepository bidRepository;


    public LotDTO createLot(CreateLotDTO createLotDTO) {
    Lot lot = createLotDTO.toLot();
    lot.setStatus(Status.CREATED);
    return LotDTO.fromLot(lotRepository.save(lot));
    }

    public boolean startLotBidding(Long id) {
        if (lotRepository.findById(id).orElse(null) == null) return false;
        if (lotRepository.findById(id).get().getStatus() != Status.STARTED)
            lotRepository.findById(id).get().setStatus(Status.STARTED);
        return true;
    }
    public boolean stopLotBidding(Long id) {
        if (lotRepository.findById(id).orElse(null) == null) return false;
        if (lotRepository.findById(id).get().getStatus() != Status.STOPPED)
            lotRepository.findById(id).get().setStatus(Status.STOPPED);
        return true;
    }

    public boolean checkLot(CreateLotDTO createLotDTO) {
        return createLotDTO.getBidPrice() > 0 &&
                createLotDTO.getStartPrice() > 0 &&
                !createLotDTO.getDescription().isEmpty() &&
                !createLotDTO.getTitle().isEmpty();
    }


    public Collection<LotDTO> getLots(Pageable pageable, Status status) {
        return lotRepository.findAllByStatusEquals(status, pageable).stream().
                map(LotDTO::fromLot).collect(Collectors.toList());
    }

    public FullLotDTO getLotById(Long id) {
        Lot lot = lotRepository.findById(id).get();
        FullLotDTO fullLotDTO = FullLotDTO.fromLot(lot);

        fullLotDTO.setCurrentPrice(lot.getStartPrice()
                + lot.getBidPrice()  * (int)bidRepository.findByLotId(id).stream().count() );
        fullLotDTO.setLastBid(
                bidRepository.findByLotIdOrderByDateDesc(id).stream().limit(1).map(BidDTO::fromBid).collect(Collectors.toList()));
        return fullLotDTO ;
    }
}
