package com.expo2020.controllers;

import com.expo2020.models.Jury;
import com.expo2020.models.Pin;
import com.expo2020.models.Stand;
import com.expo2020.models.Stemme;
import com.expo2020.services.JuryService;
import com.expo2020.services.PinService;
import com.expo2020.services.StandService;
import com.expo2020.services.StemmeService;
import com.expo2020.util.CookieUtil;
import com.expo2020.util.JuryUtils;
import com.expo2020.util.ScoreMapUtils;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

// TODO: Legge til timestamp på når kommentarene er skrevet?

@Controller
public class JuryController {

    @Autowired
    JuryService juryService;

    @Autowired
    PinService pinService;

    @Autowired
    StandService standService;

    @Autowired
    StemmeService stemmeService;

    String error;

    @GetMapping("/jury")
    public String jury(Model model,
                       @ModelAttribute(name = "error") String error,
                       @CookieValue(name = "juryid", required = false) Cookie cookie) {

       if (sjekkCookie(cookie)) {
           return "redirect:/jury/resultater";
       }
       model.addAttribute("pin", new Pin());
       return "jurysignin.html";
    }

    @RequestMapping("/jury/login")
    public View login(@ModelAttribute Pin pin,
                      @CookieValue(name = "juryid", required = false) Cookie cookie,
                      RedirectAttributes redirectAttributes, HttpServletResponse response) {
        RedirectView redirectView;
        error = "";
        Long inntastetPin;
        if (pin.getKode() == null) {
            inntastetPin = 0L;
        } else {
            inntastetPin = Long.valueOf(Encode.forJava(String.valueOf(pin.getKode())));
        }
        if (pinService.correctPin(inntastetPin)) {
            cookie = CookieUtil.cookieBuilder(cookie, "juryid");
            Jury jury = new Jury();
            jury.setCookievalue(Long.valueOf(cookie.getValue()));
            if (!juryService.juryExists(cookie.getValue())) {
                juryService.add(jury);
            }
            response.addCookie(cookie);
            redirectView = new RedirectView("/jury/resultater", true);
        }
        else {
            redirectView = new RedirectView("/jury", true);
            error = "1";
            redirectAttributes.addAttribute("error", error);
        }
        return redirectView;
    }

    @RequestMapping("/jury/resultater")
    public String resultater(@CookieValue(name = "juryid", required = false) Cookie cookie,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (sjekkCookie(cookie)) {

            List<Stand> standList = standService.findAll();
            HashMap<Long, Double> scoreMap = new HashMap<>();
            Double score;
            for (Stand stand : standList) {
                Long id = stand.getId();
                List<Stemme> stemmeList = stemmeService.findAllByStandID(id);
                score = JuryUtils.regnUtScore(stemmeList);
                scoreMap.put(id, score);
            }
            for (Map.Entry<Long, Double> entry : scoreMap.entrySet()) {
                for (Stand s : standList) {
                    if (s.getId().equals(entry.getKey())) {
                        s.setGjennomsnitt(entry.getValue());
                    }
                }
            }
            standList.sort(ScoreMapUtils.standComparator);

            model.addAttribute("stands", standList);
            model.addAttribute("scoremap", scoreMap);
            return "juryresultater.html";
        }
        else {
            error = "2";
            redirectAttributes.addAttribute("error", error);
            return "redirect:/jury";
        }
    }

    @RequestMapping("/jury/resultater/{id}")
    public String detaljer(@CookieValue(required = false, name = "juryid") Cookie cookie,
                           Model model, @PathVariable(name = "id") Long id,
                           RedirectAttributes redirectAttributes) {
        if (sjekkCookie(cookie)) {
            List<Stemme> stemmeList = stemmeService.findAllByStandID(id);
            Stand s = null;
            Optional<Stand> optional = standService.findById(id);
            if (optional.isPresent()) {
                s = optional.get();
            }
            model.addAttribute("stand", s);
            model.addAttribute("stemmer", stemmeList);
            return "juryresultater_detalj.html";
        } else {
            error = "2";
            redirectAttributes.addAttribute("error", error);
            return "redirect:/jury";
        }
    }


    public boolean sjekkCookie(Cookie checkCookie) {
        return checkCookie != null && juryService.juryExists(checkCookie.getValue());
    }
}
