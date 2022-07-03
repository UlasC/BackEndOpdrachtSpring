INSERT INTO public.medewerker (gebruikersnaam, wachtwoord, role, enabled)
VALUES ('Johnny','$2a$12$W6TnxfMBZqKGEC9Zeeeu8O.wk.6XOXE9w3fQh8HIJOLirxt.zPXxG','MONTEUR',true),
('Denise', '$2a$12$W6TnxfMBZqKGEC9Zeeeu8O.wk.6XOXE9w3fQh8HIJOLirxt.zPXxG','BALIEMEDEWERKER',true),
('Astraea', '$2a$12$W6TnxfMBZqKGEC9Zeeeu8O.wk.6XOXE9w3fQh8HIJOLirxt.zPXxG','BACKENDMEDEWERKER',true);
INSERT INTO public.bestaande_handeling (handeling, prijs)
VALUES ('remblok vervangen', 10.00),
('wielen vervangen', 20.00);
INSERT INTO public.onderdeel (prijs, name, voorraad)
VALUES (5.00, 'vloeistof', 3),
(0.10,'schroef',97);
INSERT INTO public.klant (first_name, last_name, adres, bsn)
VALUES ('Cihan', 'Ulas', 'Lampenstraat 23 Scheveningen', 123123123),
('Jan', 'Met de korte achternaam', 'Lichtstraat 24 Schiermonnikoog', 454567678),
('Anne', 'Marie', 'Lumenstraat 25 Schaijk', 555555555);
INSERT INTO public.auto (kenteken, brandstof, merk, model, klant_bsn)
VALUES ('AA-BB-12', 'Diesel', 'BMW', '5 serie', 123123123),
('36-378-B', 'Benzine', 'Kia', 'Fiat', 454567678);
INSERT INTO public.mankement (betalingstatus, reparatie_fase, auto_kenteken)
VALUES ('Niet betaald', 'In reparatie', 'AA-BB-12'),
('Betaald', 'Gerepareerd', '36-378-B');
INSERT INTO public.afspraak (tijd, dag, maand, jaar, klant_bsn, medewerker_gebruikersnaam, soort_afspraak)
VALUES ('13:00',1,8,2022,123123123,'Johnny','reparatie klaar'),
('14:00',2,7,2022,454567678,'Johnny','aanleveren auto voor onderzoek');
INSERT INTO public.mankement_handelingen(mankementen_mankement_id,handelingen_handelingsnummer)
VALUES (1, 1),
(2, 1),
(2, 2);
INSERT INTO public.mankement_onderdelen(mankementen_mankement_id,onderdelen_artikelnummer)
VALUES (1, 1),
(2, 1),
(2, 2);
INSERT INTO public.overige_handeling(handeling, prijs)
VALUES ('de auto door de wasstraat sturen',50.00);
INSERT INTO public.mankement_overige_handelingen(mankementen_mankement_id, overige_handelingen_handelingsnummer)
VALUES (2, 1);