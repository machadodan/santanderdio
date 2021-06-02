package com.bootcamp.santanderdio.service;

import com.bootcamp.santanderdio.exception.BusinessException;
import com.bootcamp.santanderdio.exception.NotFoundException;
import com.bootcamp.santanderdio.mapper.StockMapper;
import com.bootcamp.santanderdio.model.Stock;
import com.bootcamp.santanderdio.model.dto.StockDTO;
import com.bootcamp.santanderdio.repository.StockRepository;
import com.bootcamp.santanderdio.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());
        if(optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXITS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDTO(stock);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {
        Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
        if(optionalStock.isPresent()) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXITS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return  mapper.toDTO(stock);
    }



    //@Transactional (readOnly = true)
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        List<StockDTO> stockDTOList = mapper.toDTO(repository.findAll());
        if(stockDTOList.size() == 0) {
            throw new BusinessException(MessageUtils.STOCK_ALREADY_EXITS);
        }
        return stockDTOList;
    }

    //@Transactional(readOnly = true)
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    //@Transactional
    public StockDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public StockDTO delete(Long id) {
        StockDTO dto = this.findById(id);
        repository.deleteById(dto.getId());
        return dto;
    }

    //@Transactional(readOnly = true)
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<StockDTO> findByToday() {
        return repository.findByToday(LocalDate.now()).map(mapper::toDTO).orElseThrow(NotFoundException::new);
    }
}