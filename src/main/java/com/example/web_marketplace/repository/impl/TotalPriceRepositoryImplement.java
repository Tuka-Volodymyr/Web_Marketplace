package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.model.entities.TotalPrice;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.repository.TotalPriceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TotalPriceRepositoryImplement {
    private final TotalPriceRepository totalPriceRepository;

    public TotalPriceRepositoryImplement(TotalPriceRepository totalPriceRepository) {
        this.totalPriceRepository = totalPriceRepository;
    }
    public void deleteList(List<TotalPrice> data) {
        totalPriceRepository.deleteAll(data);
    }

    public void delete(TotalPrice data) {
        totalPriceRepository.delete(data);
    }


    public void save(TotalPrice data) {
        totalPriceRepository.save(data);
    }
    public TotalPrice findByUserID(long idUser){
         Optional<TotalPrice> totalPrice=totalPriceRepository.findTotalPriceByIdUser(idUser);
        if(totalPrice.isEmpty())throw new BadRequestException("User basket is empty!");
        return totalPrice.get();

    }
    public boolean totalPriceExist(long idUser){
        Optional<TotalPrice> totalPrice=totalPriceRepository.findTotalPriceByIdUser(idUser);
        return totalPrice.isPresent();

    }
}
