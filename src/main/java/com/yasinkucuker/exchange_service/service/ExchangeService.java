package com.yasinkucuker.exchange_service.service;

import com.yasinkucuker.exchange_service.model.Conversion;

import java.time.LocalDateTime;
import java.util.List;

public interface ExchangeService {
    double getExchangeRate(String from, String to);

    Conversion convert(double amount, String from, String to);

    Conversion getConversionById(Long id);

    List<Conversion> getConversionsByDate(LocalDateTime date);
}