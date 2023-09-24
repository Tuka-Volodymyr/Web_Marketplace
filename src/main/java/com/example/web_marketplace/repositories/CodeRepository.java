package com.example.web_marketplace.repositories;

import com.example.web_marketplace.entities.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CodeRepository extends JpaRepository<Code,Long> {
    Optional<Code> findByUserId(Long userId);
    Optional<Code> findByCode(String code);
}
