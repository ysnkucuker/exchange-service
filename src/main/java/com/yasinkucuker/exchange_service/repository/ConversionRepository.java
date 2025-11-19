package com.yasinkucuker.exchange_service.repository;

import com.yasinkucuker.exchange_service.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    List<Conversion> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
}
