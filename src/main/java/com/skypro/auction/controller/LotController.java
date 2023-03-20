package com.skypro.auction.controller;


import com.skypro.auction.DTO.CreateLotDTO;
import com.skypro.auction.DTO.FullLotDTO;
import com.skypro.auction.DTO.LotDTO;
import com.skypro.auction.enums.Status;
import com.skypro.auction.service.BidService;
import com.skypro.auction.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/lot")
public class LotController {

    @Autowired
    private LotService lotService;
    @Autowired
    private BidService bidService;

    @PostMapping("/{id}/bid")
    public ResponseEntity<String> lotBid (@PathVariable Long id, @RequestBody String name ){

        Status lotStatus = bidService.createBid(id, name);
        if (lotStatus == null) return ResponseEntity.notFound().build();
        if (lotStatus != Status.STARTED) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<String> startLotBidding (@PathVariable Long id ){
        if (lotService.startLotBidding(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping ("/{id}/stop")
    public ResponseEntity<String> stopLotBidding (@PathVariable Long id ){
        if (lotService.stopLotBidding(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }


    @PostMapping
    public ResponseEntity<LotDTO> createLot(@RequestBody CreateLotDTO createLotDTO) {
        if ( lotService.checkLot(createLotDTO))
             return ResponseEntity.ok(lotService.createLot(createLotDTO));
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<Collection<LotDTO>> getLots(@RequestParam(required = false)
                                                      @PageableDefault Pageable pageable,
                                                      @RequestParam(required = false) Status status){

    return ResponseEntity.ok(lotService.getLots(pageable, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullLotDTO> getLotById (@PathVariable Long id ){
        if (lotService.getLotById(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lotService.getLotById(id));
    }

}
