package com.expo2020.services;

import com.expo2020.repositories.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("pinService")
@Transactional
public class PinService implements IPinService {

    @Autowired
    PinRepository pinRepository;

    @Override
    public boolean correctPin(Long pin) {
       return pinRepository.existsById(pin);
    }
}
