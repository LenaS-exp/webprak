package ru.msu.cmc.webprak.model;

import ru.msu.cmc.webprak.model.dao.AircraftDAO;
import ru.msu.cmc.webprak.model.dao.AirlinesDAO;
import ru.msu.cmc.webprak.model.dao.AirportsDAO;
import ru.msu.cmc.webprak.model.dao.BonusCardDAO;
import ru.msu.cmc.webprak.model.dao.FlightsDAO;
import ru.msu.cmc.webprak.model.dao.TicketsDAO;
import ru.msu.cmc.webprak.model.dao.PassangersDAO;
import ru.msu.cmc.webprak.model.dao.impl.AircraftDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.AirlinesDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.AirportsDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.BonusCardDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.FlightsDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.TicketsDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.PassangersDAOImpl;

public class DAOFactory {
    private static AircraftDAOImpl aircraftDAO = null;
    private static AirlinesDAOImpl airlinesDAO = null;
    private static AirportsDAOImpl airportsDAO = null;
    private static BonusCardDAOImpl bonusCardDAO = null;
    private static FlightsDAOImpl flightsDAO = null;
    private static TicketsDAOImpl ticketsDAO = null;
    private static PassangersDAOImpl passangersDAO = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance(){
        if (instance == null){
            instance = new DAOFactory();
        }
        return instance;
    }

    public AircraftDAO getAircraftDAO(){
        if (aircraftDAO == null){
            aircraftDAO = new AircraftDAOImpl();
        }
        return aircraftDAO;
    }

    public AirlinesDAO getAirlinesDAO(){
        if (airlinesDAO == null){
            airlinesDAO = new AirlinesDAOImpl();
        }
        return airlinesDAO;
    }

    public AirportsDAO getAirportsDAO(){
        if (airportsDAO == null){
            airportsDAO = new AirportsDAOImpl();
        }
        return airportsDAO;
    }

    public BonusCardDAO getBonusCardDAO(){
        if (bonusCardDAO == null){
            bonusCardDAO = new BonusCardDAOImpl();
        }
        return bonusCardDAO;
    }

    public FlightsDAO getFlightsDAO(){
        if (flightsDAO == null){
            flightsDAO = new FlightsDAOImpl();
        }
        return flightsDAO;
    }

    public TicketsDAO getTicketsDAO(){
        if (ticketsDAO == null){
            ticketsDAO = new TicketsDAOImpl();
        }
        return ticketsDAO;
    }

    public PassangersDAO getPassangersDAO(){
        if (passangersDAO == null){
            passangersDAO = new PassangersDAOImpl();
        }
        return passangersDAO;
    }
}
