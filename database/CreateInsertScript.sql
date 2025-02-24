DROP TABLE IF EXISTS Airports CASCADE;
CREATE TABLE IF NOT EXISTS Airports(
    airport_id SERIAL PRIMARY KEY,
    airport_name TEXT NOT NULL,
    city TEXT NOT NULL
);

DROP TABLE IF EXISTS Airlines CASCADE;
CREATE TABLE IF NOT EXISTS Airlines(
    airline_id SERIAL PRIMARY KEY,
    airline_name TEXT NOT NULL,
    email TEXT
);

DROP TABLE IF EXISTS Aircrafts CASCADE;
CREATE TABLE IF NOT EXISTS Aircrafts(
    aircraft_id SERIAL PRIMARY KEY,
    model TEXT NOT NULL,
	  max_range NUMERIC,
	  max_altitude NUMERIC,
	  speed NUMERIC
);

DROP TABLE IF EXISTS Passangers CASCADE;
CREATE TABLE IF NOT EXISTS Passangers(
    passanger_id SERIAL PRIMARY KEY,
    passanger_name TEXT NOT NULL,
	  passanger_surname TEXT NOT NULL,
	  phone_number TEXT NOT NULL,
    email TEXT NOT NULL,
	  address TEXT,
    password_hash TEXT NOT NULL
);

DROP TABLE IF EXISTS BonusCard CASCADE;
CREATE TABLE IF NOT EXISTS BonusCard(
    bonus_card_id SERIAL PRIMARY KEY,
    passenger_id INTEGER REFERENCES Passangers ON DELETE CASCADE NOT NULL,
    airline_id INTEGER REFERENCES Airlines ON DELETE  CASCADE NOT NULL,
   	status TEXT NOT NULL,
    total_km INTEGER NOT NULL CONSTRAINT check_km CHECK (total_km >= 0) ,
    used_km INTEGER NOT NULL CONSTRAINT check_used_km CHECK (used_km >= 0 AND used_km <= total_km) 
);


DROP TABLE IF EXISTS Flights CASCADE;
CREATE TABLE IF NOT EXISTS Flights(
    flight_id SERIAL PRIMARY KEY,
    airline_id INTEGER NOT NULL REFERENCES Airlines ON DELETE CASCADE ,
    departure_airport INTEGER NOT NULL REFERENCES Airports ON DELETE CASCADE,
    arrival_airport INTEGER NOT NULL REFERENCES Airports ON DELETE CASCADE,
  	scheduled_departure TIMESTAMP NOT NULL,
	  scheduled_arrival TIMESTAMP NOT NULL,
    aircraft_id INTEGER NOT NULL REFERENCES Aircrafts ON DELETE CASCADE,
    seat_num INTEGER CONSTRAINT check_seat CHECK (seat_num > 0) NOT NULL,
    available_seat_num INTEGER CONSTRAINT check_available_seat CHECK (available_seat_num >= 0 AND available_seat_num <= seat_num) NOT NULL
);

DROP TABLE IF EXISTS Tickets CASCADE;
CREATE TABLE IF NOT EXISTS Tickets(
    ticket_id SERIAL PRIMARY KEY,
    flight_id INTEGER NOT NULL REFERENCES Flights ON DELETE CASCADE,
	  passenger_id INTEGER NOT NULL REFERENCES Passangers ON DELETE CASCADE,
    status TEXT NOT NULL, 
	  fare_conditions TEXT NOT NULL, 
	  price NUMERIC CONSTRAINT check_price CHECK (price > 0) NOT NULL

);


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
