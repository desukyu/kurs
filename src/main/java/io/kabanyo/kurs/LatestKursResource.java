package io.kabanyo.kurs;

import io.kabanyo.kurs.model.Kurs;
import io.kabanyo.kurs.service.BankIndonesiaKursService;
import io.kabanyo.kurs.service.PajakKursService;
import io.kabanyo.kurs.service.ReutersKursService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/latest")
public class LatestKursResource {

    @Inject
    BankIndonesiaKursService bankIndonesiaKursService;

    @Inject
    ReutersKursService reutersKursService;

    @Inject
    PajakKursService pajakKursService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs latestDefault() {
        return bankIndonesiaKursService.latest();
    }
    @Path("/bi")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs latestBIKurs() {
        return bankIndonesiaKursService.latest();
    }

    @Path("/reuters")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs latestReutersKurs() {
        return reutersKursService.latest();
    }

    @Path("/pajak")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs latestPajakKurs() {
        return pajakKursService.latest();
    }
}
