package ru.msu.cmc.webprak.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cmc.webprak.model.dao.AircraftDAO;
import ru.msu.cmc.webprak.model.dao.FlightsDAO;
import ru.msu.cmc.webprak.model.entity.Aircraft;
import ru.msu.cmc.webprak.model.entity.Flights;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AircraftDAOTest {
    AircraftDAO dao;
    Aircraft aircraftTest1;
    Aircraft aircraftTest2;
    String runId;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getAircraftDAO();

        this.aircraftTest1 = new Aircraft();
        this.aircraftTest1.setModelName("TestName1");
        this.aircraftTest1.setMaxRange(1.0);
        this.aircraftTest1.setCruisingSpeed(2.0);
        this.aircraftTest1.setMaxAltitude(3.0);



        this.aircraftTest2 = new Aircraft();
        this.aircraftTest2.setModelName("TestName2");
        this.aircraftTest2.setMaxRange(6.0);
        this.aircraftTest2.setCruisingSpeed(5.0);
        this.aircraftTest2.setMaxAltitude(4.0);

        this.dao.add(this.aircraftTest1);
        this.dao.add(this.aircraftTest2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.aircraftTest1);
        this.dao.delete(this.aircraftTest2);

        this.dao = null;
        this.aircraftTest1 = null;
        this.aircraftTest2 = null;
    }

    @Test
    public void testGetByName() {
        Collection<Aircraft> all = this.dao.getAircraftByFilter(
                AircraftDAO.getFilterBuilder()
                        .modelName(null)
                        .build()
        );
        Set<Aircraft> expected = new HashSet<>();
        expected.add(this.aircraftTest1);
        expected.add(this.aircraftTest2);

        Set<Aircraft> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Aircraft> onlyTest1 = this.dao.getAircraftByFilter(
                AircraftDAO.getFilterBuilder()
                        .modelName("TestName1")
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.aircraftTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByCruisingSpeed() {
        Collection<Aircraft> test1 = this.dao.getAircraftByFilter(
                AircraftDAO.getFilterBuilder()
                        .cruisingSpeed(2.0)
                        .build()
        );

        Set<Aircraft> expected = new HashSet<>();
        expected.add(this.aircraftTest1);


        Set<Aircraft> got = new HashSet<>(test1);

        assertEquals(expected, got);

    }

    @Test
    public void testGetByMaxRange() {
        Collection<Aircraft> test1 = this.dao.getAircraftByFilter(
                AircraftDAO.getFilterBuilder()
                        .maxRange(1.0)
                        .build()
        );

        Set<Aircraft> expected = new HashSet<>();
        expected.add(this.aircraftTest1);


        Set<Aircraft> got = new HashSet<>(test1);

        assertEquals(expected, got);

    }

    @Test
    public void testGetByMaxAltitude() {
        Collection<Aircraft> test1 = this.dao.getAircraftByFilter(
                AircraftDAO.getFilterBuilder()
                        .maxAltitude(3.0)
                        .build()
        );

        Set<Aircraft> expected = new HashSet<>();
        expected.add(this.aircraftTest1);

        Set<Aircraft> got = new HashSet<>(test1);

        assertEquals(expected, got);

    }


}
