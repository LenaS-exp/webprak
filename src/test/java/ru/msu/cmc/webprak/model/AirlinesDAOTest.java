package ru.msu.cmc.webprak.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cmc.webprak.model.dao.AirlinesDAO;
import ru.msu.cmc.webprak.model.entity.Airlines;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirlinesDAOTest {
    AirlinesDAO dao;
    Airlines airlinesTest1;
    Airlines airlinesTest2;
    String runId;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getAirlinesDAO();
        this.runId = UUID.randomUUID().toString();

        this.airlinesTest1 = new Airlines();
        this.airlinesTest1.setAirlineName("TestName1" + this.runId);
        this.airlinesTest1.setAirlineEmail("test1" + this.runId + "@mail.com");

        this.airlinesTest2 = new Airlines();
        this.airlinesTest2.setAirlineName("TestName2" + this.runId);
        this.airlinesTest2.setAirlineEmail("test2" + this.runId + "@mail.com");


        this.dao.add(this.airlinesTest1);
        this.dao.add(this.airlinesTest2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.airlinesTest1);
        this.dao.delete(this.airlinesTest2);

        this.dao = null;
        this.runId = null;
        this.airlinesTest1 = null;
        this.airlinesTest2 = null;
    }

    @Test
    public void testGetByName() {
        Collection<Airlines> allTestNames = this.dao.getAirlinesByFilter(
                AirlinesDAO.getFilterBuilder()
                        .airlineName(this.runId)
                        .build()
        );
        Set<Airlines> expected = new HashSet<>();
        expected.add(this.airlinesTest1);
        expected.add(this.airlinesTest2);

        Set<Airlines> got = new HashSet<>(allTestNames);

        assertEquals(expected, got);

        Collection<Airlines> test1 = this.dao.getAirlinesByFilter(
                AirlinesDAO.getFilterBuilder()
                        .airlineName("TestName1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.airlinesTest1);

        got = new HashSet<>(test1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByEmail() {
        Collection<Airlines> allTestEmails = this.dao.getAirlinesByFilter(
                AirlinesDAO.getFilterBuilder()
                        .airlineEmail(this.runId)
                        .build()
        );

        Set<Airlines> expected = new HashSet<>();
        expected.add(this.airlinesTest1);
        expected.add(this.airlinesTest2);

        Set<Airlines> got = new HashSet<>(allTestEmails);

        assertEquals(expected, got);

        Collection<Airlines> test1 = this.dao.getAirlinesByFilter(
                AirlinesDAO.getFilterBuilder()
                        .airlineEmail("test1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.airlinesTest1);

        got = new HashSet<>(test1);

        assertEquals(expected, got);
    }

}
