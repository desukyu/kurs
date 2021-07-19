package io.kabanyo.kurs.service;

import io.kabanyo.kurs.exception.KursException;
import io.kabanyo.kurs.model.Kurs;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ReutersKursService {

	public static final String SOURCE_CURRENCY = "USD";
	public static final String REUTERS_KURS_URL = "https://www.reuters.com/markets/currencies";

	private static final Logger LOG = Logger.getLogger(ReutersKursService.class);

	public Kurs latest() {
		Kurs result = new Kurs();
		result.setSourceCurrency(SOURCE_CURRENCY);
		result.setUpdateDate(findLatestUpdateDate());
		result.setRates(findLatestKurs());
		return result;
	}

	private LocalDate findLatestUpdateDate() {
		return LocalDate.now();
	}

	private Map<String, Double> findLatestKurs() {
		Document doc = null;
		try {
			doc = Jsoup.connect(REUTERS_KURS_URL).get();
		} catch (IOException e) {
			LOG.error(KursException.FAILED_TO_CONNECT);
			throw new KursException(KursException.FAILED_TO_CONNECT);
		}

		NumberFormat numberFormat = NumberFormat.getNumberInstance();

		Map<String, Double> result = new HashMap<>();
		Elements rateTable = doc.select("tr.data");

		rateTable.forEach(element -> {
			Elements rateRow = element.getElementsByTag("td");

				String currency = rateRow.get(0).text();

				Double value = null;
				try {
					value = numberFormat.parse(rateRow.get(1).text()).doubleValue();
				} catch (ParseException e) {
					throw new KursException(KursException.FAILED_TO_PARSE_NUMBER);
				} finally {
					result.put(currency, value);
				}

		});
		return result;
	}
}
