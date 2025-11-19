package com.yasinkucuker.exchange_service.controller;

import com.yasinkucuker.exchange_service.model.Conversion;
import com.yasinkucuker.exchange_service.service.ExchangeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/rate")
    public double getRate(@RequestParam String from,
                          @RequestParam String to) {
        return exchangeService.getExchangeRate(from, to);
    }


    @PostMapping("/convert")
    public Conversion convert(@RequestParam double amount,
                              @RequestParam String from,
                              @RequestParam String to) {
        return exchangeService.convert(amount, from, to);
    }

    @GetMapping("/conversion/{id}")
    public Conversion getConversion(@PathVariable Long id) {
        return exchangeService.getConversionById(id);
    }

    @GetMapping("/conversions")
    public List<Conversion> getConversions(@RequestParam String date) {
        LocalDateTime localDate = LocalDateTime.parse(date + "T00:00:00");
        return exchangeService.getConversionsByDate(localDate);
    }
}
