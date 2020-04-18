package com.expo2020.controllers;

import com.expo2020.models.Stand;
import com.expo2020.models.Stemme;
import com.expo2020.services.StandService;
import com.expo2020.services.StemmeService;
import com.expo2020.util.CookieUtil;
import com.expo2020.util.StemmeUtil;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author christian foleide / kristoffer davidsen
 *
 * Controller-klasse som definerer URL-endepunkt for stemming og tilgang til disse.
 */

@Controller
public class StemmeController {

    @Autowired
    StemmeService stemmeService;

    @Autowired
    StandService standService;

    @RequestMapping("/stem/{id}")
    public String stand(@PathVariable(name = "id") Long id, Model model,
                        @CookieValue(name = "personid", required = false) Cookie cookie,
                        HttpServletResponse response) {
        Optional<Stand> stand = standService.findById(id);

        if (stand.isPresent()) {
            Stand s = stand.get();

            model.addAttribute("stand", s);
            model.addAttribute("stemme", new Stemme());
            cookie = CookieUtil.cookieBuilder(cookie, "personid");
            response.addCookie(cookie);
        }
        return "stempaastand.html";
    }

    @PostMapping("/stemmebekreftelse/{id}")
    public String stemmeBekreftelse (@PathVariable Long id, Model
            model, @CookieValue(required = false, name = "personid") Cookie cookie,
                                     @ModelAttribute Stemme stemme) {
        String personid = null;
        int stemmeVerdi = stemme.getStemmeverdi();
        String kommentar = Encode.forJava(stemme.getKommentar());

        Optional<Stand> stannd = standService.findById(id);
        if (cookie != null) {
            personid = cookie.getValue();
        }

        if (stannd.isPresent()) {
            Stand standOptional = stannd.get();

            model.addAttribute("stand", standOptional);

            // HAR STEMT
            // HENT STEMMER SOM TILHÃ˜RER DEG

            List<Stemme> stemmer = stemmeService.findByPersonId(personid);

            //SJEKK OM NOEN AV STEMMENE TILHÃ˜RER STAND I PARAMETER

            Stemme s = StemmeUtil.hasVotedForStand(stemmer, standOptional);
            if (s == null) {

                // HAR IKKE STEMT PÃ… DENNE STANDEN FÃ˜R

                Stemme ball = new Stemme(personid, id, stemmeVerdi, kommentar); //
                stemmeService.save(ball);
                model.addAttribute("metode", "registrerte din stemme på ");
            } else {
                // HAR STEMT PÃ… DENNE STANDEN FÃ˜R
                s.setStemmeverdi(stemmeVerdi);
                s.setKommentar(kommentar);
                stemmeService.save(s);
                model.addAttribute("metode", "oppdaterte din stemme på ");
            }

        }
        return "stemmebekreftelse.html";
    }


}