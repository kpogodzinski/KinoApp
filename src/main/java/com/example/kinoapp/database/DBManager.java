package com.example.kinoapp.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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
        List<?> id = switch (table) {
            case "Filmy" -> EntityManager.createQuery("SELECT MAX(f.id_filmu) FROM Filmy f").getResultList();
            case "Seanse" -> EntityManager.createQuery("SELECT MAX(s.id_seansu) FROM Seanse s").getResultList();
            case "Klienci" -> EntityManager.createQuery("SELECT MAX(k.id_klienta) FROM Klienci k").getResultList();
            case "Sale" -> EntityManager.createQuery("SELECT MAX(s.numer_sali) FROM Sale s").getResultList();
            case "Rezerwacje" -> EntityManager.createQuery("SELECT MAX(r.id_rezerwacji) FROM Rezerwacje r").getResultList();
            default -> null;
        };
        if (id == null || id.isEmpty())
            return -1;
        return (long) id.get(0) + 1;
    }
}
