


INSERT INTO Aircrafts (model, max_range, max_altitude, speed)
VALUES ('Boeing 777-300ER', 11200, 13.1, 905),
       ('Boeing 737-800', 4500, 12.5, 900),
       ('Airbus A350-900', 12400, 13.1,910),
       ('Airbus A330-300', 9500, 12.5, 900),
       ('Airbus A321', 3800, 12.1,830);

INSERT INTO Airports (airport_name, city)
VALUES ('Domodedovo International Airport', 'Moscow'),
       ('Sheremetievo International Airport', 'Moscow'),
       ('Pulkovo Airport', 'Saint Petersburg');

INSERT INTO Passangers (passanger_name, passanger_surname, phone_number, email, password_hash)
VALUES ('Ivan', 'Ivanov', '89251111111', 'ii@mail.ru', 'hash1'),
       ('Peter', 'Petrov', '89251111112', 'pp@mail.ru', 'hash2');

INSERT INTO Airlines (airline_name, email)
VALUES ('Aeroflot', 'aeroflot@mail.ru'),
       ('S7 Airlines', 's7@mail.ru');

INSERT INTO Flights (airline_id, departure_airport, arrival_airport, scheduled_departure, scheduled_arrival, aircraft_id, seat_num, available_seat_num)
VALUES (1, 1, 2, '2025-01-01 01:01', '2025-02-02 02:02', 1, 1000, 500),
       (2, 2, 1, '2025-03-03 03:03', '2025-03-03 04:04', 2, 1500, 1400);


INSERT INTO  Tickets (flight_id, passenger_id, status, fare_conditions, price)
VALUES (1, 1, 'not paid', 'business', 2000),
       (2, 2, 'paid', 'economy', 1000);

INSERT INTO BonusCard (passenger_id, airline_id, status, total_km, used_km)
VALUES (1, 1, 'active', 100000, 900),
       (1, 2, 'active', 1000, 0),
       (2, 2, 'inactive', 1111, 1000);