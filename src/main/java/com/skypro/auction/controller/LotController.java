package com.skypro.auction.controller;


import com.skypro.auction.DTO.LotDTO;
import com.skypro.auction.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lot")
public class LotController {
    @Autowired
    private LotService lotService;


    @PostMapping ("/{id}/start")
    public ResponseEntity<String> startLotBidding (@PathVariable Long id ){

        if (lotService.startLotBidding(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }


    @PostMapping
    public ResponseEntity<LotDTO> createStudent(@RequestBody LotDTO lotDTO) {
        return ResponseEntity.ok(lotService.createLot(lotDTO));
    }


}
