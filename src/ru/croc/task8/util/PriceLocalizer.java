package ru.croc.task8.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PriceLocalizer {
    private BigDecimal value;
    private Locale locale;
    private NumberFormat numberFormat;
    private final Map<String, Locale> localeMap =
            new HashMap<>();

    {
        localeMap.put("usa", Locale.US);
        localeMap.put("germany", Locale.GERMANY);
        localeMap.put("uk", Locale.UK);
        localeMap.put("korea", Locale.KOREA);
        localeMap.put("canada", Locale.CANADA);
        localeMap.put("japan", Locale.JAPAN);
        localeMap.put("italy", Locale.ITALY);
        localeMap.put("france", Locale.FRANCE);
        localeMap.put("china", Locale.CHINA);
    }

    public PriceLocalizer(String stringValue, String locale) {
        if (!localeMap.containsKey(locale.toLowerCase())) {
            throw new IllegalArgumentException("Incorrect locale: " + locale);
        }

        this.value = new BigDecimal(stringValue);
        if (locale.isEmpty()) {
            this.locale = Locale.US;
        }

        this.locale = localeMap.get(locale.toLowerCase());
        this.numberFormat = NumberFormat.getCurrencyInstance(this.locale);
    }

    public String getLocalizedPrice() {
        return numberFormat.format(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = new BigDecimal(value);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        if (!localeMap.containsKey(locale.toLowerCase())) {
            throw new IllegalArgumentException("Incorrect locale: " + locale);
        }
        this.locale = localeMap.get(locale.toLowerCase());
        this.numberFormat = NumberFormat.getCurrencyInstance(this.locale);
    }
}
