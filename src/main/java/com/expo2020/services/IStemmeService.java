package com.expo2020.services;

import com.expo2020.models.Stemme;

import java.util.List;
import java.util.Optional;

public interface IStemmeService {

    Optional<Stemme> findById(Long id);

    List<Stemme> findAll();

    Long count();

    void save(Stemme entity);

    void delete(Long id);

    List<Stemme> findAllByStandID(Long id);
}
