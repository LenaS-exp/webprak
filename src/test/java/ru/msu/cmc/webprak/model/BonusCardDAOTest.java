package ru.msu.cmc.webprak.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.msu.cmc.webprak.model.dao.AirlinesDAO;
import ru.msu.cmc.webprak.model.dao.BonusCardDAO;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.entity.Airlines;
import ru.msu.cmc.webprak.model.entity.BonusCard;
import ru.msu.cmc.webprak.model.entity.Passangers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BonusCardDAOTest {
    BonusCardDAO dao;
    String runId;
    Airlines airlines1;
    Airlines airlines2;
    Passangers passangers1;
    Passangers passangers2;
    BonusCard bonusCard1;
    BonusCard bonusCard2;

    @BeforeEach
    public void setUp() {
        this.dao = DAOFactory.getInstance().getBonusCardDAO();
        this.runId = UUID.randomUUID().toString();

        this.airlines1 = new Airlines();
        this.airlines1.setAirlineName("TestName1" + this.runId);
        this.airlines1.setAirlineEmail("test1" + this.runId + "@mail.com");

        this.passangers1 = new Passangers();
        this.passangers1.setName("TestName1" + this.runId);
        this.passangers1.setSurname("TestSurname1" + this.runId);
        this.passangers1.setAddress("TestAddress1" + this.runId);
        this.passangers1.setEmail("test1" + this.runId + "@mail.com");
        this.passangers1.setPhoneNumber("+1" + this.runId);
        this.passangers1.setPassword("TestPassword1" + this.runId);
        this.passangers1.setPassword(this.runId);

        this.bonusCard1 = new BonusCard();
        this.bonusCard1.setCardStatus("TestStatus1" + this.runId);
        this.bonusCard1.setPassangerId(passangers1);
        this.bonusCard1.setAirlineId(airlines1);
        this.bonusCard1.setTotalkm(11);
        this.bonusCard1.setUsedkm(11);


        this.airlines2 = new Airlines();
        this.airlines2.setAirlineName("TestName2" + this.runId);
        this.airlines2.setAirlineEmail("test2" + this.runId + "@mail.com");
        

        this.passangers2 = new Passangers();
        this.passangers2.setName("TestName2" + this.runId);
        this.passangers2.setSurname("TestSurname2" + this.runId);
        this.passangers2.setAddress("TestAddress2" + this.runId);
        this.passangers2.setEmail("test2" + this.runId + "@mail.com");
        this.passangers2.setPhoneNumber("+2" + this.runId);
        this.passangers2.setPassword(this.runId);

        this.bonusCard2 = new BonusCard();
        this.bonusCard2.setCardStatus("TestStatus2" + this.runId);
        this.bonusCard2.setPassangerId(passangers2);
        this.bonusCard2.setAirlineId(airlines2);
        this.bonusCard2.setTotalkm(12);
        this.bonusCard2.setUsedkm(12);

        AirlinesDAO airlinesDAO = DAOFactory.getInstance().getAirlinesDAO();
        airlinesDAO.add(this.airlines1);
        airlinesDAO.add(this.airlines2);

        PassangersDAO passangersDAO = DAOFactory.getInstance().getPassangersDAO();
        passangersDAO.add(this.passangers1);
        passangersDAO.add(this.passangers2);

        this.dao.add(this.bonusCard1);
        this.dao.add(this.bonusCard2);
    }

    @AfterEach
    public void cleanUp() {
        this.dao.delete(this.bonusCard1);
        this.dao.delete(this.bonusCard2);

        AirlinesDAO airlinesDAO = DAOFactory.getInstance().getAirlinesDAO();
        airlinesDAO.delete(this.airlines1);
        airlinesDAO.delete(this.airlines2);

        PassangersDAO passangersDAO = DAOFactory.getInstance().getPassangersDAO();
        passangersDAO.delete(this.passangers1);
        passangersDAO.delete(this.passangers2);

        this.dao = null;
        this.runId = null;
        this.airlines1 = null;
        this.airlines2 = null;
        this.passangers1 = null;
        this.passangers2 = null;
        this.bonusCard1 = null;
        this.bonusCard2 = null;
    }


    @Test
    public void testGetByTotalkm() {
        Collection<BonusCard> all = this.dao.getBonusCardByFilter(
                BonusCardDAO.getFilterBuilder()
                        .totalkmMin(10)
                        .build()
        );
        Set<BonusCard> expected = new HashSet<>();
        expected.add(this.bonusCard1);
        expected.add(this.bonusCard2);

        Set<BonusCard> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<BonusCard> onlyTest1 = this.dao.getBonusCardByFilter(
                BonusCardDAO.getFilterBuilder()
                        .totalkmMax(11)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.bonusCard1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }

    @Test
    public void testGetByUsedkm() {
        Collection<BonusCard> all = this.dao.getBonusCardByFilter(
                BonusCardDAO.getFilterBuilder()
                        .usedkmMin(10)
                        .build()
        );
        Set<BonusCard> expected = new HashSet<>();
        expected.add(this.bonusCard1);
        expected.add(this.bonusCard2);

        Set<BonusCard> got = new HashSet<>(all);

        assertEquals(expected, got);

        Collection<BonusCard> onlyTest1 = this.dao.getBonusCardByFilter(
                BonusCardDAO.getFilterBuilder()
                        .usedkmMax(11)
                        .build()
        );

        expected = new HashSet<>();
        expected.add(this.bonusCard1);

        got = new HashSet<>(onlyTest1);

        assertEquals(expected, got);
    }
}
