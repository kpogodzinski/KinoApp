# KinoApp
KinoApp to aplikacja przeznaczona dla administratorów kin, umożliwiająca skuteczne zarządzanie seansami filmowymi, rezerwacjami miejsc oraz danymi klientów. Aplikacja została zaprojektowana tak, by ułatwić codzienną pracę personelu kinowego, poprawić efektywność operacji i podnieść jakość obsługi klienta.

## Funkcje
<ul>
  <li><strong>Zarządzanie filmami:</strong> możliwość dodawania nowych filmów do bazy danych, edytowania ich szczegółów oraz usuwania nieaktualnych tytułów.</li>
  <li><strong>Zarządzanie seansami:</strong> możliwość dodawania, edytowania i usuwania seansów filmowych – każdy seans jest powiązany z konkretną salą kinową oraz terminem.</li>
  <li><strong>Rezerwacje miejsc:</strong> możliwość dodawania, edytowania i usuwania rezerwacji miejsc dla wybranych seansów – system automatycznie sprawdza dostępność miejsc i zapobiega podwójnym rezerwacjom.</li>
  <li><strong>Zarządzanie salami:</strong> możliwość dodawania, edytowania i usuwania sal kinowych, co umożliwia efektywne zarządzanie przestrzenią kinową.</li>
  <li><strong>Obsługa klientów:</strong> możliwość tworzenia i zarządzania kontami klientów, aktualizowania ich danych kontaktowych oraz przeglądania historii rezerwacji.</li>
  <li><strong>Bezpieczeństwo danych:</strong> aplikacja zapewnia bezpieczne przechowywanie danych oraz ochronę przed atakami SQL injection.</li>
</ul>

## Technologie
<ul>
	<li><strong>Baza danych:</strong> serwer PostgreSQL z relacyjnym modelem danych, składającym się z tabel takich jak Filmy, Sale, Seanse, Klienci i Rezerwacje oraz odpowiednimi indeksami i wyzwalaczami.</li>
	<li><strong>Backend:</strong> aplikacja napisana w języku Java, wykorzystująca w komunikacji z serwerem bazodanowym framework Hibernate (JPA).</li>
	<li><strong>Frontend:</strong> przyjazny interfejs użytkownika umożliwiający łatwą nawigację i interakcję z aplikacją, przygotowany za pomocą JavaFX.</li>
</ul>

## Autorzy
Projekt stworzony przez Kacpra Pogodzińskiego.
<br />
Ikona aplikacji zaprojektowana przez Freepik i wykorzystana zgodnie z licencją.
<br />
Przykładowe dane zawarte w bazie danych wygenerowane przez OpenAI ChatGPT.

## Uwagi
Wszystkie informacje, takie jak tytuły filmów czy nazwiska, są tylko przykładowe i służą demonstracyjnemu przedstawieniu działania aplikacji.
