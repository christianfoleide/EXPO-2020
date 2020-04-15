package com.expo2020.util;

import com.expo2020.models.Stand;

import java.util.*;

public class ScoreMapUtils {

    public static Comparator<Stand> standComparator = (e1, e2) -> {
        Double f1 = e1.getGjennomsnitt();
        Double f2 = e2.getGjennomsnitt();
        return f2.compareTo(f1);
    };



}
