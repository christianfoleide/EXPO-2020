package com.expo2020.services;

import com.expo2020.models.Stemme;
import com.expo2020.repositories.StemmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * @author christian foleide
 * Service som skiller businesslogic fra controlleren. Metodene utfører CRUD-operasjoner med Stemme-objekter på databasen.
 */


@Service
@Transactional
public class StemmeService implements IStemmeService {

    @Autowired
    private StemmeRepository stemmerepo;

    @Override
    public Optional<Stemme> findById(Long id) {
        return stemmerepo.findById(id);
    }

    @Override
    public List<Stemme> findAll() {
        return (List<Stemme>) stemmerepo.findAll();
    }

    @Override
    public Long count() {
        return stemmerepo.count();
    }

    @Override
    public void save(Stemme entity) {
        stemmerepo.save(entity);
    }

    @Override
    public void delete(Long id) {

        Optional<Stemme> stemme = stemmerepo.findById(id);
        if (stemme.isPresent()) {
            stemmerepo.deleteById(id);
        }
    }

    @Override
    public List<Stemme> findAllByStandID(Long id) {
        List<Stemme> stemmeList = (List<Stemme>) stemmerepo.findAll();

        return stemmeList.stream().filter(stemme -> stemme.getStandid().equals(id))
                .collect(Collectors.toList());
    }

    public List<Stemme> findByPersonId(String personid) {

        List<Stemme> stemmer = (List<Stemme>) stemmerepo.findAll();

        return stemmer
                .stream()
                    .filter(stemme -> stemme.getPersonid().equals(personid))
                        .collect(Collectors.toList());

    }

}
