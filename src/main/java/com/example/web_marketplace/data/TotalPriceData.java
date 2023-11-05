package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.TotalPrice;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.repositories.TotalPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TotalPriceData implements Data<TotalPrice>{
    private final TotalPriceRepository totalPriceRepository;

    public TotalPriceData(TotalPriceRepository totalPriceRepository) {
        this.totalPriceRepository = totalPriceRepository;
    }
    public void deleteList(List<TotalPrice> data) {
        totalPriceRepository.deleteAll(data);
    }
    @Override
    public void delete(TotalPrice data) {
        totalPriceRepository.delete(data);
    }

    @Override
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
