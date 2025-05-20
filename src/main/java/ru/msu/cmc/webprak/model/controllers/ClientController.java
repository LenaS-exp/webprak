package ru.msu.cmc.webprak.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.msu.cmc.webprak.model.dao.*;

import ru.msu.cmc.webprak.model.entity.BonusCard;
import ru.msu.cmc.webprak.model.entity.Passangers;
import ru.msu.cmc.webprak.model.entity.Tickets;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private PassangersDAO passangersDAO;

    @Autowired
    private TicketsDAO ticketsDAO;

    @Autowired
    private BonusCardDAO bonusCardsDAO;

    @Autowired
    private FlightsDAO flightsDAO;

    @Autowired
    private AirlinesDAO airlinesDAO;

    @GetMapping
    public String showClientsPage(
            @RequestParam(name = "flightNumber", required = false) String flightNumber,
            @RequestParam(name = "airlineId", required = false) Integer airlineId,
            @RequestParam(name = "ticketStatus", required = false) String ticketStatus,
            Model model) {

        Collection<Passangers> allClients = passangersDAO.getPassangersByFilter(PassangersDAO.getFilterBuilder().build());
        model.addAttribute("allAirlines", airlinesDAO.getAirlinesByFilter(AirlinesDAO.getFilterBuilder().build()));


        if (flightNumber != null || airlineId != null || ticketStatus != null) {
            Collection<Passangers> filteredClients = passangersDAO.findClientsByFilters(
                    flightNumber, airlineId, ticketStatus);
            model.addAttribute("clients", filteredClients);
        } else {
            model.addAttribute("clients", allClients);
        }

        return "clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Passangers client = passangersDAO.getById(id);
        if (client == null) {
            return "redirect:/clients";
        }
        model.addAttribute("user", client);
        return "profile-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateClient(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String phoneNumber) {

        try {
            Passangers client = passangersDAO.getById(id);
            if (client != null) {
                client.setName(name);
                client.setSurname(surname);
                client.setPhoneNumber(phoneNumber);
                passangersDAO.update(client);
            }
            return "redirect:/clients";
        } catch (Exception e) {
            return "redirect:/flights";
        }

    }

    @GetMapping("/view/{id}")
    public String viewClientProfile(@PathVariable Integer id, Model model) {
        Passangers client = passangersDAO.getById(id);
        if (client == null) {
            return "redirect:/clients";
        }

        Collection<Tickets> allTickets = ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .passangerId(id)
                        .build()
        );

        List<Tickets> completedTickets = allTickets.stream()
                .filter(t -> t.getFlightId().getTimeArr().isBefore(java.time.LocalDateTime.now()))
                .collect(Collectors.toList());

        List<Tickets> activeTickets = allTickets.stream()
                .filter(t -> t.getFlightId().getTimeArr().isAfter(java.time.LocalDateTime.now()))
                .collect(Collectors.toList());

        Collection<BonusCard> bonusCards = bonusCardsDAO.getBonusCardByFilter(BonusCardDAO.getFilterBuilder().userId(id).build());

        model.addAttribute("user", client);
        model.addAttribute("completedTickets", completedTickets);
        model.addAttribute("activeTickets", activeTickets);
        model.addAttribute("bonusCards", bonusCards);

        return "profile";
}

    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id) {
        passangersDAO.delete(passangersDAO.getById(id));
        return "redirect:/clients";
    }
}
