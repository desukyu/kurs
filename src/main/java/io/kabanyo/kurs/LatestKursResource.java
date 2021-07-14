package io.kabanyo.kurs;

import io.kabanyo.kurs.model.Kurs;
import io.kabanyo.kurs.service.BankIndonesiaKursService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/latest")
public class LatestKursResource {

    @Inject
    private BankIndonesiaKursService bankIndonesiaKursService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Kurs latestKurs() {
        return bankIndonesiaKursService.latest();
    }
}
