package com.expo2020.util;

import com.expo2020.models.Stand;
import com.expo2020.models.Stemme;
import java.util.List;

/**
 * @author christian foleide
 * Klasse som definerer metoder som hjelper med oppdatering av stemmer
 */

public class StemmeUtil {

    /***
     *
     * @param stemmer stemmene til denne brukeren
     * @param stand standen som brukeren stemmer på
     * @return stemmen som tilhører en stand man har stemt på før, null dersom man ikke har stemt på denne standen før.
     */

    public static Stemme hasVotedForStand(List<Stemme> stemmer, Stand stand) {

        if (stemmer != null) {

            for (Stemme stemme : stemmer) {
                if (stemme.getStandid().equals(stand.getId())) {
                    return stemme;
                }

            }

        }
        return null;
    }
}
