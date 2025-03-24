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

