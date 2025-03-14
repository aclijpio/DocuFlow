package com.github.aclijpio.docuflow.config.source;

import lombok.Getter;

public class FinancialConfig {

    private String defaultCurrency;
    @Getter
    private double commission;

    public CurrencyCode getDefaultCurrency() {
        return CurrencyCode.valueOf(defaultCurrency);
    }
}
