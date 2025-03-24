package ru.msu.cmc.webprak.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cmc.webprak.model.dao.AircraftDAO;
import ru.msu.cmc.webprak.model.dao.AirlinesDAO;
import ru.msu.cmc.webprak.model.dao.AirportsDAO;
import ru.msu.cmc.webprak.model.dao.FlightsDAO;
import ru.msu.cmc.webprak.model.entity.Aircraft;
import ru.msu.cmc.webprak.model.entity.Airlines;
import ru.msu.cmc.webprak.model.entity.Airports;
import ru.msu.cmc.webprak.model.entity.Flights;
import ru.msu.cmc.webprak.utils.TimeConvertUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightsDAOTest {
    FlightsDAO dao;
    String runId;
    Airlines airlines1;
    Airlines airlines2;
    Airports airports1;
    Airports airports2;
    Aircraft aircraft1;
    Aircraft aircraft2;
    Flights flights1;
    Flights flights2;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getFlightsDAO();
        this.runId = UUID.randomUUID().toString();

        this.airlines1 = new Airlines();
        this.airlines1.setAirlineName("TestName1" + this.runId);
        this.airlines1.setAirlineEmail("test1" + this.runId + "@mail.com");

        this.airports1 = new Airports();
        this.airports1.setAirportName("TestName1" + this.runId);
        this.airports1.setAirportCity("TestCity1" + this.runId);

        this.aircraft1 = new Aircraft();
        this.aircraft1.setModelName("TestName1" + this.runId);

        this.flights1 = new Flights();
        this.flights1.setAirlineId(airlines1);
        this.flights1.setDepAirport(airports1);
        this.flights1.setArrAirport(airports1);
        this.flights1.setAircraftId(aircraft1);
        this.flights1.setTimeDep(TimeConvertUtil.fromString("2022-04-12T00:01"));
        this.flights1.setTimeArr(TimeConvertUtil.fromString("2022-04-12T00:02"));
        this.flights1.setSeatNum(11);
        this.flights1.setAvailableSeatNum(11);


        this.airlines2 = new Airlines();
        this.airlines2.setAirlineName("TestName2" + this.runId);
        this.airlines2.setAirlineEmail("test2" + this.runId + "@mail.com");


        this.airports2 = new Airports();
        this.airports2.setAirportName("TestName2" + this.runId);
        this.airports2.setAirportCity("TestCity2" + this.runId);

        this.aircraft2 = new Aircraft();
        this.aircraft2.setModelName("TestName2" + this.runId);

        this.flights2 = new Flights();
        this.flights2.setAirlineId(airlines2);
        this.flights2.setDepAirport(airports2);
        this.flights2.setArrAirport(airports2);
        this.flights2.setAircraftId(aircraft2);
        this.flights2.setTimeDep(TimeConvertUtil.fromString("2022-04-12T00:11"));
        this.flights2.setTimeArr(TimeConvertUtil.fromString("2022-04-12T00:12"));
        this.flights2.setSeatNum(12);
        this.flights2.setAvailableSeatNum(12);

        AirlinesDAO airlinesDAO = DAOFactory.getInstance().getAirlinesDAO();
        airlinesDAO.add(this.airlines1);
        airlinesDAO.add(this.airlines2);

        AirportsDAO airportsDAO = DAOFactory.getInstance().getAirportsDAO();
        airportsDAO.add(this.airports1);
        airportsDAO.add(this.airports2);

        AircraftDAO aircraftDAO = DAOFactory.getInstance().getAircraftDAO();
        aircraftDAO.add(this.aircraft1);
        aircraftDAO.add(this.aircraft2);

        this.dao.add(this.flights1);
        this.dao.add(this.flights2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.flights1);
        this.dao.delete(this.flights2);

        AirlinesDAO airlinesDAO = DAOFactory.getInstance().getAirlinesDAO();
        airlinesDAO.delete(this.airlines1);
        airlinesDAO.delete(this.airlines2);

        AirportsDAO airportsDAO = DAOFactory.getInstance().getAirportsDAO();
        airportsDAO.delete(this.airports1);
        airportsDAO.delete(this.airports2);

        AircraftDAO aircraftDAO = DAOFactory.getInstance().getAircraftDAO();
        aircraftDAO.delete(this.aircraft1);
        aircraftDAO.delete(this.aircraft2);

        this.dao = null;
        this.runId = null;
        this.airlines1 = null;
        this.airlines2 = null;
        this.airports1 = null;
        this.airports2 = null;
        this.aircraft1 = null;
        this.aircraft2 = null;
        this.flights1 = null;
        this.flights2 = null;
    }

    @Test
    public void testGetByTimeDep() {
        Collection<Flights> all = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .timeDepMin(TimeConvertUtil.fromString("2022-04-12T00:00"))
                        .build()
        );
        Set<Flights> expected = new HashSet<>();
        expected.add(this.flights1);
        expected.add(this.flights2);

        Set<Flights> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Flights> onlyTest1 = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .timeDepMax(TimeConvertUtil.fromString("2022-04-12T00:02"))
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.flights1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByTimeArr() {
        Collection<Flights> all = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .timeArrMin(TimeConvertUtil.fromString("2022-04-12T00:00"))
                        .build()
        );
        Set<Flights> expected = new HashSet<>();
        expected.add(this.flights1);
        expected.add(this.flights2);

        Set<Flights> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Flights> onlyTest1 = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .timeArrMax(TimeConvertUtil.fromString("2022-04-12T00:04"))
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.flights1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }


    @Test
    public void testGetBySeatNum() {
        Collection<Flights> all = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .seatMin(10)
                        .build()
        );
        Set<Flights> expected = new HashSet<>();
        expected.add(this.flights1);
        expected.add(this.flights2);

        Set<Flights> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Flights> onlyTest1 = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .seatMax(11)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.flights1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByAvailableSeatNum() {
        Collection<Flights> all = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .availableSeatsMin(10)
                        .build()
        );
        Set<Flights> expected = new HashSet<>();
        expected.add(this.flights1);
        expected.add(this.flights2);

        Set<Flights> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Flights> onlyTest1 = this.dao.getFlightsByFilter(
                FlightsDAO.getFilterBuilder()
                        .availableSeatsMax(11)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.flights1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }
}
