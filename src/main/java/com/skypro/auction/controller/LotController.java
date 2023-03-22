package com.skypro.auction.controller;

import com.skypro.auction.DTO.*;
import com.skypro.auction.enums.*;
import com.skypro.auction.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import static com.skypro.auction.enums.Status.*;



@RestController
@RequestMapping("/lot")
public class LotController {

    @Autowired
    private LotService lotService;
    @Autowired
    private BidService bidService;

    @PostMapping
    public ResponseEntity<LotDTO> createLot(@RequestBody CreateLotDTO createLotDTO) {
        if (lotService.checkLot(createLotDTO)) return
                ResponseEntity.ok(lotService.createLot(createLotDTO));
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<Collection<LotDTO>> getLots(
            @RequestParam(required = false)@PageableDefault Pageable pageable,
            @RequestParam(required = false) Status status){
    return ResponseEntity.ok(lotService.getLots(pageable, status));
    }

    @PostMapping ("/{id}/stop")
    public ResponseEntity<String> stopLotBidding (@PathVariable Long id ){
        if (lotService.setStatusLotBidding(id, STOPPED))
             return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/bid")
    public ResponseEntity<String> lotBid (@PathVariable Long id, @RequestBody String nameDTO ){

        Status lotStatus = bidService.createBid(id, nameDTO);
        if (lotStatus == null) return ResponseEntity.notFound().build();
        if (lotStatus != STARTED) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<String> startLotBidding (@PathVariable Long id ){

        if (lotService.setStatusLotBidding(id, STARTED))
             return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullLotDTO> getLotById (@PathVariable Long id ){
        var lot = lotService.getLotById(id);
        if ( lot == null) return ResponseEntity.notFound().build();
                     else return ResponseEntity.ok(lot);
    }

    @GetMapping("/{id}/first")
    public ResponseEntity<BidDTO> getFirstByLotId (@PathVariable Long id ){
        var bid = bidService.getFirstByLotId(id);
        if ( bid == null) return ResponseEntity.notFound().build();
                     else return ResponseEntity.ok(bid);
    }

    @GetMapping("/{id}/frequent")
    public ResponseEntity<BidDTO> getFrequentByLotId (@PathVariable Long id ){
        var bid = bidService.getFrequentByLotId(id);
        if ( bid == null) return ResponseEntity.notFound().build();
                     else return ResponseEntity.ok(bid);
    }

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        lotService.exportLots(response);
    }
}
