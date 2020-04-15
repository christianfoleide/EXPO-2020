package com.expo2020.services;

import com.expo2020.models.Jury;

public interface IJuryService {

    boolean juryExists(String cookievalue);

    void add(Jury jury);



}
