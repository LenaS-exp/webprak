package ru.msu.cmc.webprak.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cmc.webprak.model.dao.*;
import ru.msu.cmc.webprak.model.entity.*;
import ru.msu.cmc.webprak.utils.TimeConvertUtil;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketsDAOTest {
    TicketsDAO dao;
    String runId;
    Airlines airlines1;
    Airlines airlines2;
    Airports airports1;
    Airports airports2;
    Aircraft aircraft1;
    Aircraft aircraft2;
    Flights flights1;
    Flights flights2;
    Passangers passangers1;
    Passangers passangers2;
    Tickets tickets1;
    Tickets tickets2;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getTicketsDAO();
        this.runId = UUID.randomUUID().toString();

        this.airlines1 = new Airlines();
        this.airlines1.setAirlineName("TestName1" + this.runId);
        this.airlines1.setAirlineEmail("test1" + this.runId + "@mail.com");

        this.airports1 = new Airports();
        this.airports1.setAirportName("TestName1" + this.runId);
        this.airports1.setAirportCity("TestCity1" + this.runId);

        this.aircraft1 = new Aircraft();
        this.aircraft1.setModelName("TestName1" + this.runId);
        this.aircraft1.setMaxRange(1.0);
        this.aircraft1.setCruisingSpeed(2.0);
        this.aircraft1.setMaxAltitude(3.0);

        this.flights1 = new Flights();
        this.flights1.setAirlineId(airlines1);
        this.flights1.setDepAirport(airports1);
        this.flights1.setArrAirport(airports1);
        this.flights1.setAircraftId(aircraft1);
        this.flights1.setTimeDep(TimeConvertUtil.fromString("2022-04-12T00:01"));
        this.flights1.setTimeArr(TimeConvertUtil.fromString("2022-04-12T00:02"));
        this.flights1.setSeatNum(11);
        this.flights1.setAvailableSeatNum(11);


        this.passangers1 = new Passangers();
        this.passangers1.setName("TestName1" + this.runId);
        this.passangers1.setSurname("TestSurname1" + this.runId);
        this.passangers1.setAddress("TestAddress1" + this.runId);
        this.passangers1.setEmail("test1" + this.runId + "@mail.com");
        this.passangers1.setPhoneNumber("+1" + this.runId);
        this.passangers1.setPassword("TestPassword1" + this.runId);

        this.tickets1 = new Tickets();
        this.tickets1.setFlightId(flights1);
        this.tickets1.setPassangerId(passangers1);
        this.tickets1.setTicketStatus("TestStatus1" + this.runId);
        this.tickets1.setFareConditions("TestFC1" + this.runId);
        this.tickets1.setTicketPrice(2.0);
        


        this.airlines2 = new Airlines();
        this.airlines2.setAirlineName("TestName2" + this.runId);
        this.airlines2.setAirlineEmail("test2" + this.runId + "@mail.com");
      

        this.airports2 = new Airports();
        this.airports2.setAirportName("TestName2" + this.runId);
        this.airports2.setAirportCity("TestCity2" + this.runId);

        this.aircraft2 = new Aircraft();
        this.aircraft2.setModelName("TestName2" + this.runId);
        this.aircraft2.setMaxRange(1.0);
        this.aircraft2.setCruisingSpeed(2.0);
        this.aircraft2.setMaxAltitude(3.0);

        this.flights2 = new Flights();
        this.flights2.setAirlineId(airlines2);
        this.flights2.setDepAirport(airports2);
        this.flights2.setArrAirport(airports2);
        this.flights2.setAircraftId(aircraft2);
        this.flights2.setTimeDep(TimeConvertUtil.fromString("2022-04-12T00:11"));
        this.flights2.setTimeArr(TimeConvertUtil.fromString("2022-04-12T00:12"));
        this.flights2.setSeatNum(12);
        this.flights2.setAvailableSeatNum(12);

        this.passangers2 = new Passangers();
        this.passangers2.setName("TestName2" + this.runId);
        this.passangers2.setSurname("TestSurname2" + this.runId);
        this.passangers2.setAddress("TestAddress2" + this.runId);
        this.passangers2.setEmail("test2" + this.runId + "@mail.com");
        this.passangers2.setPhoneNumber("+2" + this.runId);
        this.passangers2.setPassword("TestPassword2" + this.runId);

        this.tickets2 = new Tickets();
        this.tickets2.setFlightId(flights2);
        this.tickets2.setPassangerId(passangers2);
        this.tickets2.setTicketStatus("TestStatus2" + this.runId);
        this.tickets2.setFareConditions("TestFC2" + this.runId);
        this.tickets2.setTicketPrice(4.0);



        AirlinesDAO airlinesDAO = DAOFactory.getInstance().getAirlinesDAO();
        airlinesDAO.add(this.airlines1);
        airlinesDAO.add(this.airlines2);

        AirportsDAO airportsDAO = DAOFactory.getInstance().getAirportsDAO();
        airportsDAO.add(this.airports1);
        airportsDAO.add(this.airports2);

        AircraftDAO aircraftDAO = DAOFactory.getInstance().getAircraftDAO();
        aircraftDAO.add(this.aircraft1);
        aircraftDAO.add(this.aircraft2);

        FlightsDAO flightsDAO = DAOFactory.getInstance().getFlightsDAO();
        flightsDAO.add(this.flights1);
        flightsDAO.add(this.flights2);

        PassangersDAO passangersDAO = DAOFactory.getInstance().getPassangersDAO();
        passangersDAO.add(this.passangers1);
        passangersDAO.add(this.passangers2);

        this.dao.add(this.tickets1);
        this.dao.add(this.tickets2);

    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.tickets1);
        this.dao.delete(this.tickets2);

        FlightsDAO flightsDAO = DAOFactory.getInstance().getFlightsDAO();
        flightsDAO.delete(this.flights1);
        flightsDAO.delete(this.flights2);

        PassangersDAO passangersDAO = DAOFactory.getInstance().getPassangersDAO();
        passangersDAO.delete(this.passangers1);
        passangersDAO.delete(this.passangers2);

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
        this.passangers1 = null;
        this.passangers2 = null;
        this.tickets1 = null;
        this.tickets2 = null;
    }

    @Test
    public void testGetByStatus() {
        Collection<Tickets> all = this.dao.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .ticketStatus(this.runId)
                        .build()
        );
        Set<Tickets> expected = new HashSet<>();
        expected.add(this.tickets1);
        expected.add(this.tickets2);

        Set<Tickets> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Tickets> onlyTest1 = this.dao.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .ticketStatus("TestStatus1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.tickets1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByFareConditions() {
        Collection<Tickets> all = this.dao.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .fareConditions(this.runId)
                        .build()
        );
        Set<Tickets> expected = new HashSet<>();
        expected.add(this.tickets1);
        expected.add(this.tickets2);

        Set<Tickets> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Tickets> onlyTest1 = this.dao.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .fareConditions("TestFC1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.tickets1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByTicketPrice() {
        Collection<Tickets> all = this.dao.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .priceMin(1.0)
                        .build()
        );
        Set<Tickets> expected = new HashSet<>();
        expected.add(this.tickets1);
        expected.add(this.tickets2);

        Set<Tickets> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Tickets> onlyTest1 = this.dao.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .priceMax(3.0)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.tickets1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }
}
