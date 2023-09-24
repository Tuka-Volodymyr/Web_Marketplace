package com.example.web_marketplace.data;

import com.example.web_marketplace.entities.Code;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.repositories.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CodeData {
    @Autowired
    private CodeRepository sentCodeInfoRepository;
    public Optional<Code> findCodeByAccountId(long userId){
        return sentCodeInfoRepository.findByUserId(userId);
    }
    public void findByCode(String code){
        Optional<Code> cod = sentCodeInfoRepository.findByCode(code);
        if (cod.isEmpty())throw new BadRequestException("Code is wrong");
    }
    public void saveCode(Code sentCode){
        sentCodeInfoRepository.save(sentCode);
    }
    public void deleteCode(Code code){
        sentCodeInfoRepository.delete(code);
    }
}
