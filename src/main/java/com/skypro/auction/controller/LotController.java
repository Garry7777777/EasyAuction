package com.skypro.auction.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.skypro.auction.DTO.*;
import com.skypro.auction.enums.*;
import com.skypro.auction.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;


@RestController
@RequestMapping("/lot")
public class LotController {

    @Autowired
    private LotService lotService;
    @Autowired
    private BidService bidService;

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

    @PostMapping ("/{id}/stop")
    public ResponseEntity<String> stopLotBidding (@PathVariable Long id ){
        if (lotService.stopLotBidding(id)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<FullLotDTO> getLotById (@PathVariable Long id ){
        if (lotService.getLotById(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lotService.getLotById(id));
    }

    @GetMapping("/{id}/first")
    public ResponseEntity<BidDTO> getFirstByLotId (@PathVariable Long id ){
        if (bidService.getFirstByLotId(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bidService.getFirstByLotId(id));
    }

    @GetMapping("/{id}/frequent")
    public ResponseEntity<BidDTO> getFrequentByLotId (@PathVariable Long id ){
        if (bidService.getFrequentByLotId(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bidService.getFrequentByLotId(id));
    }

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = "lots.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<ExportLotDTO> writer = new StatefulBeanToCsvBuilder<ExportLotDTO>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        writer.write(lotService.listExportLots());
    }
}
