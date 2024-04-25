package com.tpjavaspring.tpjavaspring.repository;

import com.tpjavaspring.tpjavaspring.model.Candidat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CandidatRepository extends CrudRepository<Candidat, Long> {
    @Override
    List<Candidat> findAll();

}