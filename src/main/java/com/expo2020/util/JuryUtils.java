package com.expo2020.util;

import com.expo2020.models.Stemme;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class JuryUtils {

    public static double regnUtScore(List<Stemme> stemmer) {
        if (stemmer.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (Stemme stemme : stemmer) {
            sum += stemme.getStemmeverdi();
        }
        sum = sum / stemmer.size();
        BigDecimal bd = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
        System.out.println(bd.doubleValue());
        return bd.doubleValue();
    }

}
