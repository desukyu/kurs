package org.kabanyo.kurs.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public class Kurs {

	private String baseCurrency;
	private LocalDate updateDate;
	private Map<String, Double> rates;

	public Kurs() {
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}
}
