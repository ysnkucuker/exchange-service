package com.yasinkucuker.exchange_service.service.serviceImpl;

import com.yasinkucuker.exchange_service.exception.ApiException;
import com.yasinkucuker.exchange_service.model.Conversion;
import com.yasinkucuker.exchange_service.model.CurrencyResponse;
import com.yasinkucuker.exchange_service.repository.ConversionRepository;
import com.yasinkucuker.exchange_service.service.ExchangeService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final ConversionRepository conversionRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeServiceImpl(ConversionRepository conversionRepository) {
        this.conversionRepository = conversionRepository;
    }

    @Override
    public double getExchangeRate(String from, String to) {
        String url = "https://api.frankfurter.dev/v1/latest?from=" + from + "&to=" + to;
        CurrencyResponse response = restTemplate.getForObject(url, CurrencyResponse.class);

        if (response == null || response.getRates() == null || !response.getRates().containsKey(to)) {
            throw new ApiException("RATE_NOT_FOUND", "Exchange rate not found for " + from + " to " + to);
        }

        return response.getRates().get(to);
    }

    @Override
    public Conversion convert(double amount, String from, String to) {
        double rate = getExchangeRate(from, to);
        double targetAmount = amount * rate;

        Conversion conversion = Conversion.builder()
                .sourceCurrency(from)
                .targetCurrency(to)
                .sourceAmount(amount)
                .targetAmount(targetAmount)
                .transactionDate(LocalDateTime.now())
                .build();

        return conversionRepository.save(conversion);
    }

    @Override
    public Conversion getConversionById(Long id) {
        return conversionRepository.findById(id)
                .orElseThrow(() -> new ApiException("CONVERSION_NOT_FOUND", "Conversion with id " + id + " not found"));
    }

    @Override
    public List<Conversion> getConversionsByDate(LocalDateTime date) {
        return conversionRepository.findByTransactionDateBetween(
                date.withHour(0).withMinute(0),
                date.withHour(23).withMinute(59)
        );
    }
}
