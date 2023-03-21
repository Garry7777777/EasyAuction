package com.skypro.auction.service;

import com.skypro.auction.DTO.*;
import com.skypro.auction.model.Lot;
import com.skypro.auction.enums.Status;
import com.skypro.auction.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
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
        return  createLotDTO.getBidPrice() >= 1 &&
                createLotDTO.getStartPrice() >= 1 &&
                !createLotDTO.getDescription().isBlank() &&
                !createLotDTO.getTitle().isBlank();
    }


    public Collection<LotDTO> getLots(Pageable pageable, Status status) {
        return lotRepository.findAllByStatusEquals(status, pageable).stream().
                map(LotDTO::fromLot).collect(Collectors.toList());
    }

    public FullLotDTO getLotById(Long id) {
        Lot lot = lotRepository.findById(id).get();
        FullLotDTO fullLotDTO = FullLotDTO.fromLot(lot);
        fullLotDTO.setCurrentPrice(getCurrentPrice(id));
        fullLotDTO.setLastBid(getLastBid(id));
        return  fullLotDTO;
    }

    public List<ExportLotDTO> listExportLots() {

        return lotRepository.findAll().stream().map(ExportLotDTO::fromLot)
                .peek(exportLotDTO -> exportLotDTO.setCurrentPrice(getCurrentPrice(exportLotDTO.getId())))
                .peek(exportLotDTO -> exportLotDTO.setLastBidder(getLastBid(exportLotDTO.getId()).getBidder()))
                .collect(Collectors.toList());
    }

    private BidDTO getLastBid(Long id) {
        return bidRepository.findByLotIdOrderByDateDesc(id).stream().findFirst().map(BidDTO::fromBid).get();
    }

    private Integer getCurrentPrice(Long id) {
        Lot lot = lotRepository.findById(id).get();
        return lot.getStartPrice() + lot.getBidPrice() * bidRepository.findByLotId(id).size();
    }


}
