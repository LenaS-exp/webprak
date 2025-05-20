package ru.msu.cmc.webprak.model.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.cmc.webprak.model.dao.*;
import ru.msu.cmc.webprak.model.entity.*;
import ru.msu.cmc.webprak.model.dao.impl.FlightsDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.TicketsDAOImpl;
import ru.msu.cmc.webprak.model.dao.impl.PassangersDAOImpl;
import ru.msu.cmc.webprak.model.entity.Flights;
import ru.msu.cmc.webprak.model.entity.Tickets;
import ru.msu.cmc.webprak.model.entity.Passangers;
import ru.msu.cmc.webprak.utils.TimeConvertUtil;


import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class FlightController {
    private static final Logger log = LoggerFactory.getLogger(FlightController.class);


    @Autowired
    private FlightsDAO flightsDAO;

    @Autowired
    private AirportsDAO airportsDAO;

    @Autowired
    private AirlinesDAO airlinesDAO;

    @Autowired
    private AircraftDAO aircraftDAO;

    @Autowired
    private BonusCardDAO bonusCardDAO;

    @Autowired
    private PassangersDAO usersDAO;

    @Autowired
    private TicketsDAO ticketsDAO;

    @GetMapping("/flights")
    public String flights(
            @RequestParam(name = "departureCity", required = false) String departureCity,
            @RequestParam(name = "arrivalCity", required = false) String arrivalCity,
            @RequestParam(name = "minSeats", required = false) Integer minSeats,
            @RequestParam(name = "departureTimeFrom", required = false) String departureTimeFrom,
            @RequestParam(name = "departureTimeTo", required = false) String  departureTimeTo,
            Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeString;
        LocalDateTime dateTime;
        Collection<Airports> depAir = airportsDAO.getAirportsByFilter(AirportsDAO.getFilterBuilder().airportCity(departureCity).build());
        Collection<Airports> arrAir = airportsDAO.getAirportsByFilter(AirportsDAO.getFilterBuilder().airportCity(arrivalCity).build());
        FlightsDAO.Filter.FilterBuilder filterBuilder = FlightsDAO.getFilterBuilder();

        if (departureTimeFrom != null && !departureTimeFrom.isEmpty()) {
            try {

                dateTimeString = departureTimeFrom+" 00:00";
                 dateTime = LocalDateTime.parse(dateTimeString, formatter);
                filterBuilder.timeDepMin(dateTime);

            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Неверный формат даты вылета (от)");
                return "flights";
            }
        }
        else {
            filterBuilder.timeDepMin(java.time.LocalDateTime.now());
        }

        if (departureTimeTo != null && !departureTimeTo.isEmpty()) {
            try {
                dateTimeString = departureTimeTo+" 00:00";
                dateTime = LocalDateTime.parse(dateTimeString, formatter);
                filterBuilder.timeDepMax(dateTime);
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Неверный формат даты вылета (до)");
                return "flights";
            }
        }

        if (minSeats != null) {
            filterBuilder.availableSeatsMin(minSeats);
        }

        Collection<Flights> allFlights = flightsDAO.getFlightsByFilter(filterBuilder.build());
        Collection<Flights> filteredFlights = new ArrayList<>();

        for (Flights flight : allFlights) {
            boolean matchesDeparture = (departureCity == null || departureCity.isEmpty()) ||
                    depAir.contains(flight.getDepAirport());
            boolean matchesArrival = (arrivalCity == null || arrivalCity.isEmpty()) ||
                    arrAir.contains(flight.getArrAirport());


            if (matchesDeparture && matchesArrival) {
                filteredFlights.add(flight);
            }

        }

        model.addAttribute("flights", filteredFlights);
        model.addAttribute("airports", airportsDAO.getAirportsByFilter(AirportsDAO.getFilterBuilder().build()));
        model.addAttribute("airlines", airlinesDAO.getAirlinesByFilter(AirlinesDAO.getFilterBuilder().build()));
        model.addAttribute("aircrafts", aircraftDAO.getAircraftByFilter(AircraftDAO.getFilterBuilder().build()));

        return "flights";
    }

    @GetMapping("/flights/add")
    public String showAddForm(Model model) {
        model.addAttribute("flight", new Flights());
        model.addAttribute("airports", airportsDAO.getAirportsByFilter(AirportsDAO.getFilterBuilder().build()));
        model.addAttribute("airlines", airlinesDAO.getAirlinesByFilter(AirlinesDAO.getFilterBuilder().build()));
        model.addAttribute("aircrafts", aircraftDAO.getAircraftByFilter(AircraftDAO.getFilterBuilder().build()));
        return "flight-form";
    }

    @PostMapping("/flights/add")
    public String addFlight(
            @RequestParam("depAirportId") Integer depAirportId,
            @RequestParam("arrAirportId") Integer arrAirportId,
            @RequestParam("airlineId") Integer airlineId,
            @RequestParam("aircraftId") Integer aircraftId,
            @RequestParam("timeDep") String timeDep,
            @RequestParam("timeArr") String timeArr,
            @RequestParam("seatNum") Integer seatNum,
            RedirectAttributes redirectAttributes) {

        try {
            log.debug("Adding flight with params: depAirportId={}, arrAirportId={}, airlineId={}, aircraftId={}, timeDep={}, timeArr={}, seatNum={}",
                    depAirportId, arrAirportId, airlineId, aircraftId, timeDep, timeArr, seatNum);

            if (depAirportId == null || arrAirportId == null || airlineId == null ||
                    aircraftId == null || timeDep == null || timeArr == null || seatNum == null) {
                throw new IllegalArgumentException("Все поля обязательны для заполнения");
            }

            Airports depAirport = airportsDAO.getById(depAirportId);
            Airports arrAirport = airportsDAO.getById(arrAirportId);
            Airlines airline = airlinesDAO.getById(airlineId);
            Aircraft aircraft = aircraftDAO.getById(aircraftId);

            if (depAirport == null || arrAirport == null || airline == null || aircraft == null) {
                throw new IllegalArgumentException("Одна из связанных сущностей не найдена в БД");
            }

            Flights flight = new Flights();
            flight.setDepAirport(depAirport);
            flight.setArrAirport(arrAirport);
            flight.setAirlineId(airline);
            flight.setAircraftId(aircraft);
            flight.setTimeArr(TimeConvertUtil.fromString(timeArr));
            flight.setTimeDep(TimeConvertUtil.fromString(timeDep));
            flight.setSeatNum(seatNum);
            flight.setAvailableSeatNum(seatNum);

            flightsDAO.add(flight);

            redirectAttributes.addFlashAttribute("success", "Рейс успешно добавлен");
            return "redirect:/flights";

        } catch (Exception e) {
            log.error("Ошибка при добавлении рейса", e);
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка: " + e.getMessage() + ". Подробности в логах сервера.");
            return "redirect:/flights/add";
        }
    }

    @GetMapping("/flights/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Flights flight = flightsDAO.getById(id);
        if (flight == null) {
            return "redirect:/flights";
        }

        model.addAttribute("flight", flight);
        model.addAttribute("airports", airportsDAO.getAirportsByFilter(AirportsDAO.getFilterBuilder().build()));
        model.addAttribute("airlines", airlinesDAO.getAirlinesByFilter(AirlinesDAO.getFilterBuilder().build()));
        model.addAttribute("aircrafts", aircraftDAO.getAircraftByFilter(AircraftDAO.getFilterBuilder().build()));
        return "flight-form";
    }

    @PostMapping("/flights/edit/{id}")
    public String updateFlight(
            @PathVariable("id") Integer id,
            @RequestParam("depAirportId") Integer depAirportId,
            @RequestParam("arrAirportId") Integer arrAirportId,
            @RequestParam("airlineId") Integer airlineId,
            @RequestParam("aircraftId") Integer aircraftId,
            @RequestParam("timeDep") String timeDep,
            @RequestParam("timeArr") String timeArr,
            @RequestParam("seatNum") Integer seatNum,
            @RequestParam("availableSeatNum") Integer availableSeatNum,
            RedirectAttributes redirectAttributes) {

        try {
            Flights flight = flightsDAO.getById(id);
            if (flight == null) {
                redirectAttributes.addFlashAttribute("error", "Рейс не найден");
                return "redirect:/flights";
            }

            flight.setDepAirport(airportsDAO.getById(depAirportId));
            flight.setArrAirport(airportsDAO.getById(arrAirportId));
            flight.setAirlineId(airlinesDAO.getById(airlineId));
            flight.setAircraftId(aircraftDAO.getById(aircraftId));
            if (!timeArr.isEmpty()) flight.setTimeArr(TimeConvertUtil.fromString(timeArr));
            if (!timeDep.isEmpty()) flight.setTimeDep(TimeConvertUtil.fromString(timeDep));
            flight.setSeatNum(seatNum);
            flight.setAvailableSeatNum(availableSeatNum);

            flightsDAO.update(flight);

            redirectAttributes.addFlashAttribute("success", "Рейс успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении рейса: " + e.getMessage());
        }

        return "redirect:/flights";
    }

    @GetMapping("/flights/delete/{id}")
    public String deleteFlight(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            Flights flight = flightsDAO.getById(id);
            if (flight != null) {
                flightsDAO.delete(flight);
                redirectAttributes.addFlashAttribute("success", "Рейс успешно удален");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении рейса: " + e.getMessage());
        }
        return "redirect:/flights";
    }



    @GetMapping("/flights/book/{id}")
    public String showBookTicketForm(@PathVariable("id") Integer flightId,
                                     Model model) {
        Integer userId = 1;
        Flights flight = flightsDAO.getById(flightId);
        if (flight == null || flight.getAvailableSeatNum() <= 0) {
            return "redirect:/flights";
        }
        double roundedPrice = 5000;
        Collection<BonusCard> userBonusCards = bonusCardDAO.getBonusCardByFilter(
                BonusCardDAO.getFilterBuilder().userId(userId).build());

        model.addAttribute("flight", flight);
        model.addAttribute("userBonusCards", userBonusCards);
        model.addAttribute("ticketPrice", roundedPrice);
        model.addAttribute("maxSeatNumber", flight.getSeatNum());

        return "book-ticket";
    }

    @PostMapping("/flights/book/{id}")
    public String bookTicket(@PathVariable("id") Integer flightId,
                             @RequestParam("seatNumber") Integer seatNumber,
                             @RequestParam(value = "bonusCardId", required = false) Integer bonusCardId,
                             @RequestParam(value = "bonusPointsUsed", defaultValue = "0") Integer bonusPointsUsed,
                             @RequestParam("fareConditions") String fareConditions,
                             RedirectAttributes redirectAttributes) {
        Integer userId = 1;

        try {
            Flights flight = flightsDAO.getById(flightId);
            if (flight == null || flight.getAvailableSeatNum() <= 0) {
                redirectAttributes.addFlashAttribute("error", "Рейс не найден или нет свободных мест");
                return "redirect:/flights";
            }

            if (seatNumber == null || seatNumber < 1 || seatNumber > flight.getAvailableSeatNum()) {
                redirectAttributes.addFlashAttribute("error", "Некорректный номер места");
                return "redirect:/flights" + flightId;
            }

            double finalPrice = 5000;
            if (bonusCardId != null && bonusPointsUsed > 0) {
                finalPrice = Math.max(0, finalPrice - bonusPointsUsed);
            }

            Tickets ticket = new Tickets();
            ticket.setFlightId(flight);
            ticket.setPassangerId(usersDAO.getById(1));
            ticket.setTicketStatus("UNPAID");
            ticket.setFareConditions(fareConditions);
            ticket.setTicketPrice(finalPrice);

            ticketsDAO.add(ticket);

            flight.setAvailableSeatNum(flight.getAvailableSeatNum() - 1);
            flightsDAO.update(flight);

            if (bonusCardId != null && bonusPointsUsed > 0) {

                BonusCard card = bonusCardDAO.getById(bonusCardId);
                if (card != null) {
                    card.setUsedkm(card.getUsedkm() + bonusPointsUsed);
                    bonusCardDAO.update(card);
                }
            }

            flight.setAvailableSeatNum(flight.getAvailableSeatNum()-1);
            flightsDAO.update(flight);

            redirectAttributes.addFlashAttribute("success", "Билет успешно забронирован!");
            return "redirect:/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при бронировании: " + e.getMessage());
            return "redirect:/flights" + flightId;
        }
    }



}