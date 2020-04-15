package com.expo2020.services;

import com.expo2020.models.Jury;
import com.expo2020.repositories.JuryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("juryService")
@Transactional
public class JuryService implements IJuryService {

    @Autowired
    JuryRepository juryRepository;

    @Override
    public boolean juryExists(String cookievalue) {
        return juryRepository.existsById(Long.valueOf(cookievalue));
    }

    @Override
    public void add(Jury jury) {
        juryRepository.save(jury);
    }


}
