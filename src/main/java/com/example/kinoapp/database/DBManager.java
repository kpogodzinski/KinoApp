package com.example.kinoapp.database;

import jakarta.persistence.*;

import java.util.List;

public class DBManager {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("MyUnit");
    private static final EntityManager EntityManager = FACTORY.createEntityManager();

    public static List<FilmyDB> getFilms() {
        return EntityManager.createQuery("SELECT f FROM Filmy f").getResultList();
    }
    public static void updateFilm(FilmyDB f) {
        EntityTransaction transaction = EntityManager.getTransaction();
        transaction.begin();
        EntityManager.merge(f);
        transaction.commit();
    }

    public static void deleteFilm(long id_filmu) {
        EntityTransaction transaction = EntityManager.getTransaction();
        transaction.begin();
        EntityManager.remove(EntityManager.find(FilmyDB.class, id_filmu));
        transaction.commit();
    }

    public static long getNextId(String table) {
        switch (table) {
            case "Filmy": case "Seanse": case "Klienci": case "Sale": case "Rezerwacje":
                return EntityManager.createQuery("SELECT t FROM " + table + " t").getResultList().size()+1;
        }
        return -1;
    }
}
