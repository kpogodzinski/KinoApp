package com.example.kinoapp.database;

import jakarta.persistence.*;

@Entity
public class Filmy {
    @Id
    private long id_filmu;
    private String tytul;
    private String gatunek;
    private int czas_trwania;
}
