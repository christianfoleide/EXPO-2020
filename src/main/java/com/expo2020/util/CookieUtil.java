package com.expo2020.util;

import javax.servlet.http.Cookie;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CookieUtil {

    final static String lexicon = "12345674890";
    final static Set<String> identifiers = new HashSet<>();

    /**
     *
     * cookieBuilder skiller mellom personcookie og jurycookie, sikrer gjenbruk.
     *
     * @param cookie Cookien som vi ønsker å bygge.
     * @param cookiename Navnet på cookien (stemmecookie eller jurycookie?)
     * @return ferdig bygget cookie
     */
    public static Cookie cookieBuilder(Cookie cookie, String cookiename) {
        int number;
        if (cookiename.equalsIgnoreCase("personid")) {
            if (cookie == null) {
                number = 5;
                cookie = new Cookie(cookiename, cookieBaker(number));
                cookie.setPath("/");
            }
            cookie.setMaxAge(7 * 60 * 60 * 24);
        }
        if (cookiename.equalsIgnoreCase("juryid")) {
            if (cookie == null) {
                number = 10;
                cookie = new Cookie(cookiename, cookieBaker(number));
                cookie.setPath("/");
                cookie.setMaxAge(600); //10 minutter.
            }
        }
        return cookie;
    }

    public static String cookieBaker(int number) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        while (builder.toString().length() == 0) {
            int length = random.nextInt(5) + number;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(random.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        identifiers.add(builder.toString());
        return builder.toString();
    }

}
