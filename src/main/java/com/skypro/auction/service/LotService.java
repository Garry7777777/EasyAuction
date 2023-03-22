package com.skypro.auction.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.skypro.auction.DTO.*;
import com.skypro.auction.enums.Status;
import com.skypro.auction.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
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
    return LotDTO.fromLot(lotRepository.save(createLotDTO.toLot()));
    }

    public boolean setStatusLotBidding(Long id, Status status) {
        var lot = lotRepository.findById(id);
        if (lot.isEmpty()) return false;
        lot.get().setStatus(status);
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
        var lot = lotRepository.findById(id).orElse(null);
        if (lot == null) return null;
        var fullLotDTO = FullLotDTO.fromLot(lot);
        fullLotDTO.setCurrentPrice(getCurrentPrice(id));
        fullLotDTO.setLastBid(getLastBid(id));
        return  fullLotDTO;
    }

    public void ExportLots(HttpServletResponse response) throws Exception {

        var listExportLots = lotRepository.findAll().stream().map(ExportLotDTO::fromLot)
                .peek(exportLotDTO -> exportLotDTO.setCurrentPrice(getCurrentPrice(exportLotDTO.getId())))
                .peek(exportLotDTO -> exportLotDTO.setLastBidder(getLastBid(exportLotDTO.getId()).getBidder()))
                .collect(Collectors.toList());

        //set file name and content type
        String filename = "lots.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<ExportLotDTO> writer = new StatefulBeanToCsvBuilder<ExportLotDTO>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false).build();
        writer.write(listExportLots);

    }

    private BidDTO getLastBid(Long id) {
        return bidRepository.findByLotIdOrderByDateDesc(id).stream().findFirst().map(BidDTO::fromBid).
                orElse(new BidDTO(" ", null));
    }

    private Integer getCurrentPrice(Long id) {
        var lot = lotRepository.findById(id);
        return lot.map(value -> value.getStartPrice() + value.getBidPrice() * bidRepository.findByLotId(id).size())
                .orElse(0);
    }
}
