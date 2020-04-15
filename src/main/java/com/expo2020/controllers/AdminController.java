package com.expo2020.controllers;

import com.expo2020.models.Stand;
import com.expo2020.services.JuryService;
import com.expo2020.services.StandService;
import com.expo2020.util.QR;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * TODO: sette bytearray av qrkode på objekt.
 */
@Controller
public class AdminController {

    @Autowired
    StandService standService;

    @Autowired
    JuryService juryService;


    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("stand", new Stand());

        return "admin.html";
    }

    @RequestMapping(value = "/admin/hentqr", produces = "image/jpg")
    public @ResponseBody byte[] getImage(@ModelAttribute Stand stand) throws IOException, WriterException {
        return QR.generateQrImage("http://data1.hib.no:9090/dat109_v20_prosjekt09/stem/" + stand.getNavn());
    }

    @GetMapping("/admin/hentqr")
    public String adminHentQR() {
        return "admin_hentqr.html";
    }

    @GetMapping("/admin/add")
    public String adminAdd(Model model) {
       model.addAttribute("stand", new Stand());
       return "admin_leggtil.html";
    }

    @PostMapping("/admin/add")
    public String admin(@ModelAttribute Stand modelStand) {
        String navn = modelStand.getNavn();
        String adresse = modelStand.getAdresse();
        String bransje = modelStand.getBransje();
        String beskrivelse = modelStand.getBeskrivelse();
        Stand stand = new Stand(navn,adresse,bransje,beskrivelse);
        standService.save(stand);
        return "admin_leggtilresultat.html";
    }
}
