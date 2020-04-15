package com.expo2020.controllers;

import com.expo2020.models.Stand;
import com.expo2020.services.StandService;
import com.expo2020.services.StemmeService;
import com.expo2020.util.QR;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Optional;

@Controller
public class StandController {

    @Autowired
    private StemmeService stemmeService;
    @Autowired
    private StandService standService;

    @GetMapping("/stands")
    public String stands(Model model) {
        model.addAttribute("stands", standService.findAll());
        model.addAttribute("stand", new Stand());
        return "stands.html";
    }

    @GetMapping("/stands/{id}")
    public String detaljertStand(@PathVariable Long id, Model model) {
        Optional<Stand> optional = standService.findById(id);
        if (optional.isPresent()) {
            Stand stand = optional.get();
            model.addAttribute("stand", stand);
        }
        return "stands_detaljert.html";
    }

    /**
     * TODO: Tilordne id til qr-kode.
     * @param pathId
     * @return
     * @throws IOException
     * @throws WriterException
     */
    @RequestMapping(value = "/stands/hentqr/{id}", produces = "image/jpg")
    public @ResponseBody
    byte[] getImageWithoutInput(@PathVariable("id") Long pathId)
            throws IOException, WriterException {
        String url = "http://data1.hib.no:9090/dat109_v20_prosjekt09/stem/" + pathId;
        return QR.generateQrImage(url);
    }
}
