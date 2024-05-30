package com.goutdupays.goutdupays.dto;

import com.goutdupays.goutdupays.modele.Categorie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class    CategorieDto {
    private Long id;


    private String nom;

    public CategorieDto(Categorie categorie) {
        this.id = categorie.getId();
        this.nom = categorie.getNom();
    }
}
