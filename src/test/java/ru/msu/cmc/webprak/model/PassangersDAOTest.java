package ru.msu.cmc.webprak.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.entity.Passangers;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassangersDAOTest {
    PassangersDAO dao;
    Passangers passangersTest1;
    Passangers passangersTest2;
    String runId;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getPassangersDAO();
        this.runId = UUID.randomUUID().toString();

        this.passangersTest1 = new Passangers();
        this.passangersTest1.setName("TestName1" + this.runId);
        this.passangersTest1.setSurname("TestSurname1" + this.runId);
        this.passangersTest1.setAddress("TestAddress1" + this.runId);
        this.passangersTest1.setEmail("test1" + this.runId + "@mail.com");
        this.passangersTest1.setPhoneNumber("+1" + this.runId);
        this.passangersTest1.setPassword("TestPassword1" + this.runId);

        this.passangersTest2 = new Passangers();
        this.passangersTest2.setName("TestName2" + this.runId);
        this.passangersTest2.setSurname("TestSurname2" + this.runId);
        this.passangersTest2.setAddress("TestAddress2" + this.runId);
        this.passangersTest2.setEmail("test2" + this.runId + "@mail.com");
        this.passangersTest2.setPhoneNumber("+2" + this.runId);
        this.passangersTest2.setPassword("TestPassword2" + this.runId);

        this.dao.add(this.passangersTest1);
        this.dao.add(this.passangersTest2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.passangersTest1);
        this.dao.delete(this.passangersTest2);

        this.dao = null;
        this.runId = null;
        this.passangersTest1 = null;
        this.passangersTest2 = null;
    }

    @Test
    public void testGetByName() {
        Collection<Passangers> all = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .name(this.runId)
                        .build()
        );
        Set<Passangers> expected = new HashSet<>();
        expected.add(this.passangersTest1);
        expected.add(this.passangersTest2);

        Set<Passangers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passangers> onlyTest1 = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .name("TestName1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.passangersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetBySurname() {
        Collection<Passangers> all = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .surname(this.runId)
                        .build()
        );
        Set<Passangers> expected = new HashSet<>();
        expected.add(this.passangersTest1);
        expected.add(this.passangersTest2);

        Set<Passangers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passangers> onlyTest1 = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .surname("TestSurname1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.passangersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }


    @Test
    public void testGetByAddress() {
        Collection<Passangers> all = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .address(this.runId)
                        .build()
        );
        Set<Passangers> expected = new HashSet<>();
        expected.add(this.passangersTest1);
        expected.add(this.passangersTest2);

        Set<Passangers> got = new HashSet<>(all);
        assertEquals(expected, got);

        Collection<Passangers> onlyTest1 = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .address("TestAddress1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.passangersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByEmail() {
        Collection<Passangers> all = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .email(this.runId)
                        .build()
        );
        Set<Passangers> expected = new HashSet<>();
        expected.add(this.passangersTest1);
        expected.add(this.passangersTest2);

        Set<Passangers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passangers> onlyTest1 = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .email("test1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.passangersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByPhoneNumber() {
        Collection<Passangers> all = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .phoneNumber(this.runId)
                        .build()
        );
        Set<Passangers> expected = new HashSet<>();
        expected.add(this.passangersTest1);
        expected.add(this.passangersTest2);

        Set<Passangers> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<Passangers> onlyTest1 = this.dao.getPassangersByFilter(
                PassangersDAO.getFilterBuilder()
                        .phoneNumber("+1" + this.runId)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.passangersTest1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

}
