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
import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class PajakKursService {

    public static final String TARGET_CURRENCY = "IDR";
    public static final String PAJAK_KURS_URL = "https://fiskal.kemenkeu.go.id/informasi-publik/kurs-pajak";

    public static final String JPY = "JPY";

    private static final Logger LOG = Logger.getLogger(PajakKursService.class);

    public Kurs latest() {
        Kurs result = new Kurs();
        result.setTargetCurrency(TARGET_CURRENCY);
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
            doc = Jsoup.connect(PAJAK_KURS_URL).get();
        } catch (IOException e) {
            LOG.error(KursException.FAILED_TO_CONNECT);
            throw new KursException(KursException.FAILED_TO_CONNECT);
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("in", "ID"));

        Map<String, Double> result = new HashMap<>();
        Elements rateTable = doc.select(".main-content table tbody tr");

        rateTable.forEach(element -> {
            Elements rateRow = element.getElementsByTag("td");

            String currency = rateRow.get(1).getElementsByTag("span").get(1).text();

            Double value = null;
            try {
                value = numberFormat.parse(rateRow.get(2).getElementsByTag("div").get(0).text()).doubleValue();
                if (currency.equals(JPY)) {
                    value = value / 100;
                }
            } catch (ParseException e) {
                throw new KursException(KursException.FAILED_TO_PARSE_NUMBER);
            } finally {
                result.put(currency, value);
            }

        });
        return result;
    }
}
