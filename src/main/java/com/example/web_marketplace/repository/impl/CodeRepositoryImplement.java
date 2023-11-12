package com.example.web_marketplace.repository.impl;

import com.example.web_marketplace.model.entities.Code;
import com.example.web_marketplace.exceptions.BadRequestException;
import com.example.web_marketplace.repository.CodeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CodeRepositoryImplement {
    private final CodeRepository sentCodeInfoRepository;

    public CodeRepositoryImplement(CodeRepository sentCodeInfoRepository) {
        this.sentCodeInfoRepository = sentCodeInfoRepository;
    }

    public void save(Code sentCode){
        sentCodeInfoRepository.save(sentCode);
    }

    public void delete(Code code){
        sentCodeInfoRepository.delete(code);
    }
    public Optional<Code> findCodeByAccountId(long userId){
        return sentCodeInfoRepository.findByUserId(userId);
    }
    public void findByCode(String code){
        Optional<Code> cod = sentCodeInfoRepository.findByCode(code);
        if (cod.isEmpty())throw new BadRequestException("Code is wrong");
    }

}
