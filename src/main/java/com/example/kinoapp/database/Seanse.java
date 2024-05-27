package com.example.kinoapp.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Seanse {
    @Id
    private long id_seansu;
    private long id_filmu;
    private Timestamp data_godzina;
    private long numer_sali;
}
