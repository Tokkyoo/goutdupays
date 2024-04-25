package com.tpjavaspring.tpjavaspring.controller;
import com.tpjavaspring.tpjavaspring.model.Candidat;
import com.tpjavaspring.tpjavaspring.repository.CandidatRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/candidats")
public class CandidatController {

    private CandidatRepository candidatRepository;

    public CandidatController(CandidatRepository candidatRepository){
        this.candidatRepository = candidatRepository;
    }
    // Get All

    @GetMapping("/")
    List<Candidat> all() {
        return candidatRepository.findAll();
    }

    @GetMapping("/{candidat}")
    public Candidat getOne(@PathVariable(name="candidat", required = false) Candidat candidat)
    {
        if(candidat == null) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidat not found" );
        }
        return candidat;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Candidat> saveCandidate(@Valid @RequestBody Candidat candidate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            candidatRepository.save(candidate);
            return new ResponseEntity<>(candidate, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/{candidatId}")
    public ResponseEntity<Candidat> updateCandidat(@PathVariable(name = "candidatId", required = false) Candidat candidat, @Valid @RequestBody Candidat candidatUpdate, BindingResult bindingResult) {
        if (candidat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidat introuvable");
        } else {
            if (bindingResult.hasErrors()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
            } else {
                candidatUpdate.setId(candidat.getId());
                candidatRepository.save(candidatUpdate);
                return new ResponseEntity<>(candidatUpdate, HttpStatus.CREATED);
            }
        }
    }

    @DeleteMapping(value = "/{candidatId}")
    public void deleteOne(@PathVariable(name = "candidat", required = false) Candidat candidat) {
        if (candidat == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Candidat introuvable"
            );
        } else {
            candidatRepository.delete(candidat);
        }
    }



    // Get One
    // Create
    // Update
    //Delete
}