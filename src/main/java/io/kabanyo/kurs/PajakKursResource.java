package io.kabanyo.kurs;

import io.kabanyo.kurs.model.Kurs;
import io.kabanyo.kurs.service.BankIndonesiaKursService;
import io.kabanyo.kurs.service.PajakKursService;
import io.kabanyo.kurs.service.ReutersKursService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Path("/pajak")
public class PajakKursResource {
    @Inject
    PajakKursService pajakKursService;

    @Path("/latest")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs latestPajakKurs() {
        return pajakKursService.latest();
    }

    @Path("/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs findPajakKursByDate(@PathParam("date") String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateParameter = LocalDate.parse(dateString, formatter);
        return pajakKursService.findByDate(dateParameter);
    }
}
