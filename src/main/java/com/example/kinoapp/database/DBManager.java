package com.example.kinoapp.database;

import com.example.kinoapp.tableview.Seanse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DBManager {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("MyUnit");
    private static final EntityManager EntityManager = FACTORY.createEntityManager();

    public static List<FilmyDB> getFilms() {
        return EntityManager.createQuery("SELECT f FROM Filmy f").getResultList();
    }
    public static List<SaleDB> getRooms() {
        return EntityManager.createQuery("SELECT s FROM Sale s").getResultList();
    }
    public static List<KlienciDB> getClients() {
        return EntityManager.createQuery("SELECT k FROM Klienci k").getResultList();
    }
    public static List<SeanseDB> getScreenings() {
        return EntityManager.createQuery("SELECT s FROM Seanse s").getResultList();
    }
    public static List<RezerwacjeDB> getBookings() {
        return EntityManager.createQuery("SELECT r FROM Rezerwacje r").getResultList();
    }

    public static <Entity> void update(Entity e) {
        EntityTransaction transaction = EntityManager.getTransaction();
        transaction.begin();
        EntityManager.merge(e);
        transaction.commit();
    }

    public static void delete(Class<?> entity, long id) {
        EntityTransaction transaction = EntityManager.getTransaction();
        transaction.begin();
        EntityManager.remove(EntityManager.find(entity, id));
        transaction.commit();
    }

    public static long getNextId(String table) {
        List id = new ArrayList();
        switch (table) {
            case "Filmy":
                id = EntityManager.createQuery("SELECT MAX(f.id_filmu) FROM Filmy f").getResultList();
                break;
            case "Seanse":
                id = EntityManager.createQuery("SELECT MAX(s.id_seansu) FROM Seanse s").getResultList();
                break;
            case "Klienci":
                id = EntityManager.createQuery("SELECT MAX(k.id_klienta) FROM Klienci k").getResultList();
                break;
            case "Sale":
                id = EntityManager.createQuery("SELECT MAX(s.numer_sali) FROM Sale s").getResultList();
                break;
            case "Rezerwacje":
        }
        if (id.isEmpty())
            return -1;
        return (long) id.get(0) + 1;
//        return -1;
    }
}
