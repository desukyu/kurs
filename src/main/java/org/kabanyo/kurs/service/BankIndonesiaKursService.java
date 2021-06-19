package org.kabanyo.kurs.service;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.kabanyo.kurs.exception.KursException;
import org.kabanyo.kurs.model.Kurs;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class BankIndonesiaKursService {

	public static final String BASE_CURRENCY = "IDR";
	public static final String BI_KURS_URL = "https://www.bi.go.id/id/statistik/informasi-kurs/transaksi-bi/default.aspx";

	private static final Logger LOG = Logger.getLogger(BankIndonesiaKursService.class);

	public Kurs latest() {
		Kurs kurs = new Kurs();
		kurs.setBaseCurrency(BASE_CURRENCY);
		kurs.setUpdateDate(findLatestUpdateDate());
		kurs.setRates(findLatestKurs());
		return kurs;
	}

	private Map<String, Double> findLatestKurs() {
		Document doc = null;
		try {
			doc = Jsoup.connect(BI_KURS_URL).get();
		} catch (IOException e) {
			LOG.error(KursException.FAILED_TO_CONNECT);
			throw new KursException(KursException.FAILED_TO_CONNECT);
		}

		Map<String, Double> result = new HashMap<>();
		Elements rateTable = doc.select("#tableData .page-table:eq(1) table tbody tr");

		rateTable.remove(0);
		rateTable.forEach(element -> {
			Elements rateRow = element.getElementsByTag("td");
			if (rateRow.size() >=4 ) {
				String currency = rateRow.get(0).text();

				Double value = Double.valueOf(rateRow.get(1).text().replace(",", ".").replace(".",""));
				Double sell = Double.valueOf(rateRow.get(2).text().replace(",", ".").replace(".",""));
				Double buy = Double.valueOf(rateRow.get(3).text().replace(",", ".").replace(".",""));
				result.put(currency, (sell + buy) / (2 * value));
			}
		});
		return result;
	}

	private LocalDate findLatestUpdateDate() throws KursException {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.bi.go.id/id/statistik/informasi-kurs/transaksi-bi/default.aspx").get();
		} catch (IOException e) {
			LOG.error(KursException.FAILED_TO_CONNECT);
			throw new KursException(KursException.FAILED_TO_CONNECT);
		}
		Elements elements = doc.select("#tableData .form-group span");

		Locale.setDefault(new Locale("id", "ID"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

		LocalDate localDate = LocalDate.now();
		try {
			localDate = LocalDate.parse(elements.get(0).text(), formatter);
		} catch (Exception e) {
			LOG.error(KursException.LAST_UPDATE_DATE_NOT_FOUND);
			throw new KursException(KursException.LAST_UPDATE_DATE_NOT_FOUND);
		} finally {
			return localDate;
		}
	}
}
