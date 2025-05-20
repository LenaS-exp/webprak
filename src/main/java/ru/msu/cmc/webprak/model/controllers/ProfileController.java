package ru.msu.cmc.webprak.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.webprak.model.dao.*;
import ru.msu.cmc.webprak.model.entity.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private PassangersDAO usersDAO;

    @Autowired
    private TicketsDAO ticketsDAO;

    @Autowired
    private BonusCardDAO bonusCardsDAO;

    public ProfileController(PassangersDAO usersDAO, TicketsDAO ticketsDAO,
                             BonusCardDAO bonusCardsDAO, FlightsDAO flightsDAO) {
        this.usersDAO = usersDAO;
        this.ticketsDAO = ticketsDAO;
        this.bonusCardsDAO = bonusCardsDAO;

    }

    @GetMapping
    public String profile(Model model) {
        Integer userId = 1;

        if (userId == null) {
            model.addAttribute("notRegistered", true);
            return "profile";
        }
        Passangers user = usersDAO.getById(userId);

        Collection<Tickets> allTickets = ticketsDAO.getTicketsByFilter(
                TicketsDAO.getFilterBuilder()
                        .passangerId(userId)
                        .build()
        );

        List<Tickets> completedTickets = allTickets.stream()
                .filter(t -> t.getFlightId().getTimeArr().isBefore(java.time.LocalDateTime.now()))
                .collect(Collectors.toList());

        List<Tickets> activeTickets = allTickets.stream()
                .filter(t -> t.getFlightId().getTimeArr().isAfter(java.time.LocalDateTime.now()))
                .collect(Collectors.toList());
        Collection<BonusCard> bonusCards = bonusCardsDAO.getBonusCardByFilter(BonusCardDAO.getFilterBuilder().userId(userId).build());

        model.addAttribute("user", user);
        model.addAttribute("completedTickets", completedTickets);
        model.addAttribute("activeTickets", activeTickets);
        model.addAttribute("bonusCards", bonusCards);

        return "profile";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {

        Integer userId = 1;
        Passangers user = usersDAO.getById(userId);
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String phoneNumber,
            Model model) {


        Integer userId = 1;
        Passangers user = usersDAO.getById(userId);
        if (user == null) {
            model.addAttribute("error", "Пользователь не найден");
            return "profile-edit";
        }
        user.setName(name);
        user.setSurname(surname);
        user.setPhoneNumber(phoneNumber);

        try {
            usersDAO.update(user);
            return "redirect:/profile";
        } catch (Exception e) {
            return "redirect:/flights";
        }

    }

    @PostMapping("/cancel-ticket/{ticketId}")
    public String cancelTicket(@PathVariable Integer ticketId) {
        Integer userId = 1;
        if (userId != null) {
            ticketsDAO.delete(ticketsDAO.getById(ticketId));
        }
        return "redirect:/profile";
    }

    @PostMapping("/pay-ticket/{ticketId}")
    public String payTicket(@PathVariable Integer ticketId) {
        Integer userId = 1;
        if (userId != null) {
            ticketsDAO.payTicket(ticketId);
        }
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteProfile() {
        Integer userId = 1;
        if (userId != null) {
            usersDAO.delete(usersDAO.getById(userId));
        }
        return "redirect:/flights";
    }
}