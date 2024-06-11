-- Uporządkowanie bazy przed seedowaniem
START TRANSACTION;

DROP TABLE IF EXISTS Rezerwacje;
DROP TABLE IF EXISTS Klienci;
DROP TABLE IF EXISTS Seanse;
DROP TABLE IF EXISTS Sale;
DROP TABLE IF EXISTS Filmy;

DROP FUNCTION IF EXISTS sprawdzPojemnosc();
DROP FUNCTION IF EXISTS unikalnyEmail();

DROP TRIGGER IF EXISTS trg_sprawdzPojemnosc ON Rezerwacje;
DROP TRIGGER IF EXISTS trg_unikalnyEmail ON Klienci;

DROP SEQUENCE IF EXISTS seq_filmy;
DROP SEQUENCE IF EXISTS seq_sale;
DROP SEQUENCE IF EXISTS seq_seanse;
DROP SEQUENCE IF EXISTS seq_klienci;
DROP SEQUENCE IF EXISTS seq_rezerwacje;

COMMIT;

-- Sekwencje
START TRANSACTION;

CREATE SEQUENCE seq_filmy
INCREMENT BY 1
START WITH 1
NO CYCLE;

CREATE SEQUENCE seq_sale
INCREMENT BY 1
START WITH 1
NO CYCLE;

CREATE SEQUENCE seq_seanse
INCREMENT BY 1
START WITH 1
NO CYCLE;

CREATE SEQUENCE seq_klienci
INCREMENT BY 1
START WITH 1
NO CYCLE;

CREATE SEQUENCE seq_rezerwacje
INCREMENT BY 1
START WITH 1
NO CYCLE;

COMMIT;

-- Tabele
START TRANSACTION;

CREATE TABLE Filmy (
    id_filmu INT PRIMARY KEY DEFAULT nextval('seq_filmy'),
    tytul VARCHAR(100) NOT NULL,
    gatunek VARCHAR(50) NOT NULL,
    czas_trwania INT NOT NULL
);

CREATE TABLE Sale (
    numer_sali INT PRIMARY KEY DEFAULT nextval('seq_sale'),
    pojemnosc INT NOT NULL
);

CREATE TABLE Seanse (
    id_seansu INT PRIMARY KEY DEFAULT nextval('seq_seanse'),
    id_filmu INT NOT NULL,
    data_godzina TIMESTAMP NOT NULL,
    numer_sali INT NOT NULL,
    FOREIGN KEY (id_filmu) REFERENCES Filmy(id_filmu),
    FOREIGN KEY (numer_sali) REFERENCES Sale(numer_sali)
);

CREATE TABLE Klienci (
    id_klienta INT PRIMARY KEY DEFAULT nextval('seq_klienci'),
    imie VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefon VARCHAR(15) NOT NULL
);

CREATE TABLE Rezerwacje (
    id_rezerwacji INT PRIMARY KEY DEFAULT nextval('seq_rezerwacje'),
    id_seansu INT NOT NULL,
    id_klienta INT NOT NULL,
    numer_fotela INT NOT NULL,
    FOREIGN KEY (id_seansu) REFERENCES Seanse(id_seansu),
    FOREIGN KEY (id_klienta) REFERENCES Klienci(id_klienta),
	UNIQUE (id_seansu, numer_fotela)
);

COMMIT;

-- Indeksy
START TRANSACTION;

CREATE INDEX idx_filmy_gatunek ON Filmy(gatunek);
CREATE INDEX idx_seanse_data_godzina ON Seanse(data_godzina);
CREATE INDEX idx_klienci_email ON Klienci(email);

COMMIT;

-- Wyzwalacze
START TRANSACTION;

CREATE OR REPLACE FUNCTION sprawdzPojemnosc()
RETURNS TRIGGER AS $$
DECLARE
    wolne_miejsca INT;
BEGIN
    SELECT Sale.pojemnosc - COUNT(*) INTO wolne_miejsca
    FROM Rezerwacje
    JOIN Seanse USING (id_seansu)
    JOIN Sale USING (numer_sali)
    WHERE Seanse.id_seansu = NEW.id_seansu
    GROUP BY Sale.pojemnosc;

    IF wolne_miejsca <= 0 THEN
        RAISE EXCEPTION 'Brak wolnych miejsc na wybrany seans.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_sprawdzPojemnosc
BEFORE INSERT ON Rezerwacje
FOR EACH ROW
EXECUTE FUNCTION sprawdzPojemnosc();


CREATE OR REPLACE FUNCTION unikalnyEmail() 
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM Klienci WHERE email = NEW.email) THEN
        RAISE EXCEPTION 'Adres email jest już zarejestrowany w bazie.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_unikalnyEmail
BEFORE INSERT ON Klienci
FOR EACH ROW
EXECUTE FUNCTION unikalnyEmail();

COMMIT;

-- Wypelnienie tabel
START TRANSACTION;

INSERT INTO Filmy (tytul, gatunek, czas_trwania) VALUES
('Matrix', 'Sci-Fi', 136),
('Incepcja', 'Thriller', 148),
('Interstellar', 'Sci-Fi', 169),
('Titanic', 'Romans', 195),
('Avengers: Koniec gry', 'Akcja', 181),
('Joker', 'Dramat', 122),
('Toy Story', 'Animacja', 81),
('Mroczny rycerz', 'Akcja', 152),
('Pulp Fiction', 'Kryminalny', 154),
('Forrest Gump', 'Dramat', 142),
('Król Lew', 'Animacja', 88),
('Skazani na Shawshank', 'Dramat', 142),
('Gladiator', 'Akcja', 155),
('Braveheart - Waleczne serce', 'Dramat', 178),
('Ojciec chrzestny', 'Kryminalny', 175),
('Władca Pierścieni: Drużyna Pierścienia', 'Fantasy', 178),
('Władca Pierścieni: Dwie wieże', 'Fantasy', 179),
('Władca Pierścieni: Powrót Króla', 'Fantasy', 201),
('Gwiezdne Wojny: Nowa nadzieja', 'Sci-Fi', 121),
('Gwiezdne Wojny: Imperium kontratakuje', 'Sci-Fi', 124),
('Gwiezdne Wojny: Powrót Jedi', 'Sci-Fi', 131),
('Harry Potter i Kamień Filozoficzny', 'Fantasy', 152),
('Harry Potter i Komnata Tajemnic', 'Fantasy', 161),
('Harry Potter i Więzień Azkabanu', 'Fantasy', 142),
('Piraci z Karaibów: Klątwa Czarnej Perły', 'Przygodowy', 143),
('Piraci z Karaibów: Skrzynia umarlaka', 'Przygodowy', 151),
('Piraci z Karaibów: Na krańcu świata', 'Przygodowy', 169),
('Iron Man', 'Akcja', 126),
('Kapitan Ameryka: Pierwsze starcie', 'Akcja', 124),
('Thor', 'Akcja', 115),
('Doktor Strange', 'Fantasy', 115),
('Spider-Man: Homecoming', 'Akcja', 133),
('Spider-Man: Daleko od domu', 'Akcja', 129),
('Deadpool', 'Komedia', 108),
('Deadpool 2', 'Komedia', 119),
('Czarna Pantera', 'Akcja', 134),
('Ant-Man', 'Sci-Fi', 117),
('Ant-Man i Osa', 'Sci-Fi', 118),
('Strażnicy Galaktyki', 'Sci-Fi', 121),
('Strażnicy Galaktyki Vol. 2', 'Sci-Fi', 136),
('Alicja w Krainie Czarów', 'Fantasy', 108),
('Alicja po drugiej stronie lustra', 'Fantasy', 113),
('Kraina lodu', 'Animacja', 102),
('Kraina lodu II', 'Animacja', 103);

INSERT INTO Sale (numer_sali, pojemnosc) VALUES
(1, 100),
(2, 150),
(3, 200),
(4, 250),
(5, 300),
(6, 120),
(7, 130),
(8, 180),
(9, 140),
(10, 160),
(11, 170),
(12, 110),
(13, 190),
(14, 210),
(15, 220),
(16, 230),
(17, 240),
(18, 260),
(19, 270),
(20, 280);

INSERT INTO Seanse (id_filmu, data_godzina, numer_sali) VALUES
(1, '2024-06-01 18:00', 1),
(1, '2024-06-01 20:30', 1),
(2, '2024-06-02 18:00', 2),
(2, '2024-06-02 20:30', 2),
(3, '2024-06-03 17:00', 3),
(3, '2024-06-03 20:00', 3),
(4, '2024-06-04 18:00', 1),
(5, '2024-06-05 18:00', 2),
(5, '2024-06-05 21:00', 2),
(6, '2024-06-06 18:00', 3),
(7, '2024-06-07 16:00', 1),
(7, '2024-06-07 19:00', 1),
(8, '2024-06-08 18:00', 2),
(8, '2024-06-08 21:00', 2),
(9, '2024-06-09 18:00', 3),
(10, '2024-06-10 18:00', 1),
(10, '2024-06-10 21:00', 1),
(11, '2024-06-11 17:00', 2),
(11, '2024-06-11 20:00', 2),
(12, '2024-06-12 18:00', 3),
(12, '2024-06-12 21:00', 3),
(13, '2024-06-13 18:00', 1),
(14, '2024-06-14 18:00', 2),
(14, '2024-06-14 21:00', 2),
(15, '2024-06-15 18:00', 3),
(15, '2024-06-15 21:00', 3),
(16, '2024-06-16 17:00', 1),
(17, '2024-06-17 18:00', 2),
(17, '2024-06-17 21:00', 2),
(18, '2024-06-18 18:00', 3),
(18, '2024-06-18 21:00', 3),
(19, '2024-06-19 17:00', 1),
(20, '2024-06-20 18:00', 2),
(20, '2024-06-20 21:00', 2),
(21, '2024-06-21 18:00', 3),
(21, '2024-06-21 21:00', 3),
(22, '2024-06-22 17:00', 1),
(23, '2024-06-23 18:00', 2),
(23, '2024-06-23 21:00', 2),
(24, '2024-06-24 18:00', 3),
(24, '2024-06-24 21:00', 3),
(25, '2024-06-25 17:00', 1),
(26, '2024-06-26 18:00', 2),
(26, '2024-06-26 21:00', 2),
(27, '2024-06-27 18:00', 3),
(27, '2024-06-27 21:00', 3),
(28, '2024-06-28 17:00', 1),
(29, '2024-06-29 18:00', 2),
(29, '2024-06-29 21:00', 2),
(30, '2024-06-30 18:00', 3),
(30, '2024-06-30 21:00', 3),
(31, '2024-07-01 17:00', 1),
(32, '2024-07-02 18:00', 2),
(32, '2024-07-02 21:00', 2),
(33, '2024-07-03 18:00', 3),
(33, '2024-07-03 21:00', 3),
(34, '2024-07-04 17:00', 1),
(35, '2024-07-05 18:00', 2),
(35, '2024-07-05 21:00', 2),
(36, '2024-07-06 18:00', 3),
(36, '2024-07-06 21:00', 3),
(37, '2024-07-07 17:00', 1),
(38, '2024-07-08 18:00', 2),
(38, '2024-07-08 21:00', 2),
(39, '2024-07-09 18:00', 3),
(39, '2024-07-09 21:00', 3),
(40, '2024-07-10 17:00', 1);

INSERT INTO Klienci (imie, nazwisko, email, telefon) VALUES
('Jan', 'Kowalski', 'jan.kowalski@example.com', '123456789'),
('Anna', 'Nowak', 'anna.nowak@example.com', '987654321'),
('Piotr', 'Wiśniewski', 'piotr.wisniewski@example.com', '555666777'),
('Katarzyna', 'Zielińska', 'katarzyna.zielinska@example.com', '444555666'),
('Tomasz', 'Mazur', 'tomasz.mazur@example.com', '111222333'),
('Maria', 'Kwiatkowska', 'maria.kwiatkowska@example.com', '333444555'),
('Adam', 'Jabłoński', 'adam.jablonski@example.com', '666777888'),
('Ewa', 'Wójcik', 'ewa.wojcik@example.com', '999000111'),
('Michał', 'Zając', 'michal.zajac@example.com', '222333444'),
('Agnieszka', 'Król', 'agnieszka.krol@example.com', '777888999'),
('Rafał', 'Wróbel', 'rafal.wrobel@example.com', '123123123'),
('Paulina', 'Dąbrowska', 'paulina.dabrowska@example.com', '321321321'),
('Krzysztof', 'Włodarczyk', 'krzysztof.wlodarczyk@example.com', '456456456'),
('Magdalena', 'Borkowska', 'magdalena.borkowska@example.com', '654654654'),
('Jakub', 'Stępień', 'jakub.stepien@example.com', '789789789'),
('Natalia', 'Sokołowska', 'natalia.sokolowska@example.com', '987987987'),
('Marek', 'Pawlak', 'marek.pawlak@example.com', '147147147'),
('Justyna', 'Kowalczyk', 'justyna.kowalczyk@example.com', '741741741'),
('Wojciech', 'Baran', 'wojciech.baran@example.com', '159159159'),
('Dorota', 'Sawicka', 'dorota.sawicka@example.com', '951951951'),
('Sebastian', 'Wiśniewski', 'sebastian.wisniewski@example.com', '161161161'),
('Karolina', 'Kaczmarek', 'karolina.kaczmarek@example.com', '181181181'),
('Mateusz', 'Wojciechowski', 'mateusz.wojciechowski@example.com', '191191191'),
('Izabela', 'Kowal', 'izabela.kowal@example.com', '202202202'),
('Damian', 'Zalewski', 'damian.zalewski@example.com', '212212212'),
('Marta', 'Sikora', 'marta.sikora@example.com', '222222222'),
('Łukasz', 'Lis', 'lukasz.lis@example.com', '232232232'),
('Alicja', 'Szymańska', 'alicja.szymanska@example.com', '242242242'),
('Jakub', 'Domański', 'jakub.domanski@example.com', '252252252'),
('Zuzanna', 'Błaszczyk', 'zuzanna.blaszczyk@example.com', '262262262'),
('Jacek', 'Majewski', 'jacek.majewski@example.com', '272272272'),
('Monika', 'Sadowska', 'monika.sadowska@example.com', '282282282'),
('Filip', 'Cieślak', 'filip.cieslak@example.com', '292292292'),
('Sylwia', 'Mazurek', 'sylwia.mazurek@example.com', '303303303'),
('Patryk', 'Borkowski', 'patryk.borkowski@example.com', '313313313'),
('Oliwia', 'Maciejewska', 'oliwia.maciejewska@example.com', '323323323'),
('Bartosz', 'Szczepański', 'bartosz.szczepanski@example.com', '333333333'),
('Joanna', 'Krupa', 'joanna.krupa@example.com', '343343343'),
('Kamil', 'Kołodziej', 'kamil.kolodziej@example.com', '353353353'),
('Wiktoria', 'Żak', 'wiktoria.zak@example.com', '363363363');

INSERT INTO Rezerwacje (id_seansu, id_klienta, numer_fotela) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(2, 4, 1),
(2, 5, 2),
(2, 6, 3),
(3, 7, 1),
(3, 8, 2),
(3, 9, 3),
(4, 10, 1),
(4, 11, 2),
(4, 12, 3),
(5, 13, 1),
(5, 14, 2),
(5, 15, 3),
(6, 16, 1),
(6, 17, 2),
(6, 18, 3),
(7, 19, 1),
(7, 20, 2),
(7, 1, 3),
(8, 2, 1),
(8, 3, 2),
(8, 4, 3),
(9, 5, 1),
(9, 6, 2),
(9, 7, 3),
(10, 8, 1),
(10, 9, 2),
(10, 10, 3),
(11, 11, 1),
(11, 12, 2),
(11, 13, 3),
(12, 14, 1),
(12, 15, 2),
(12, 16, 3),
(13, 17, 1),
(13, 18, 2),
(13, 19, 3),
(14, 20, 1),
(14, 1, 2),
(14, 2, 3),
(15, 3, 1),
(15, 4, 2),
(15, 5, 3),
(16, 6, 1),
(16, 7, 2),
(16, 8, 3),
(17, 9, 1),
(17, 10, 2),
(17, 11, 3),
(18, 12, 1),
(18, 13, 2),
(18, 14, 3),
(19, 15, 1),
(19, 16, 2),
(19, 17, 3),
(20, 18, 1),
(20, 19, 2),
(20, 20, 3),
(21, 1, 1),
(21, 2, 2),
(21, 3, 3),
(22, 4, 1),
(22, 5, 2),
(22, 6, 3),
(23, 7, 1),
(23, 8, 2),
(23, 9, 3),
(24, 10, 1),
(24, 11, 2),
(24, 12, 3),
(25, 13, 1),
(25, 14, 2),
(25, 15, 3),
(26, 16, 1),
(26, 17, 2),
(26, 18, 3),
(27, 19, 1),
(27, 20, 2),
(27, 1, 3),
(28, 2, 1),
(28, 3, 2),
(28, 4, 3),
(29, 5, 1),
(29, 6, 2),
(29, 7, 3),
(30, 8, 1),
(30, 9, 2),
(30, 10, 3),
(31, 11, 1),
(31, 12, 2),
(31, 13, 3),
(32, 14, 1),
(32, 15, 2),
(32, 16, 3),
(33, 17, 1),
(33, 18, 2),
(33, 19, 3),
(34, 20, 1),
(34, 1, 2),
(34, 2, 3),
(35, 3, 1),
(35, 4, 2),
(35, 5, 3),
(36, 6, 1),
(36, 7, 2),
(36, 8, 3),
(37, 9, 1),
(37, 10, 2),
(37, 11, 3),
(38, 12, 1),
(38, 13, 2),
(38, 14, 3),
(39, 15, 1),
(39, 16, 2),
(39, 17, 3),
(40, 18, 1),
(40, 19, 2),
(40, 20, 3);

COMMIT;
