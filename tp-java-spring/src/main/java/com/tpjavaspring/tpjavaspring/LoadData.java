package com.tpjavaspring.tpjavaspring;
import com.tpjavaspring.tpjavaspring.model.Candidat;
import com.tpjavaspring.tpjavaspring.repository.CandidatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.time.LocalDate;

@Configuration
public class LoadData {
    private final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initDatabase(CandidatRepository repository) throws ParseException {
        log.info("Chargement des données");

        if (repository.count() == 0) {

            // crée un objet date à partir d'une string
            LocalDate dateNaissance = LocalDate.parse("1974-06-05");

            // crée l'objet candidat
            Candidat candidat = new Candidat("Chevalier", "Nicolas",
                    dateNaissance, "Place Charles Hernu", "Villeurbanne", "69100");

            return args -> {
                // sauvegarde le candidat dans la base de données
                log.info("Chargement de " + repository.save(candidat));
            };
        }
        else {
            return args -> {
                log.info("Données déjà chargées");
            };
        }
    }
}
