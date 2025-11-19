package com.yasinkucuker.exchange_service.model;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyResponse {
    private double amount;
    private String base;
    private String date;
    private Map<String, Double> rates;
}
