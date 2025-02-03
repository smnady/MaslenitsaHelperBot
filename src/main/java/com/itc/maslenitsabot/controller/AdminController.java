package com.itc.maslenitsabot.controller;


import com.itc.maslenitsabot.controller.dto.AdminPanelDto;
import com.itc.maslenitsabot.user.BotUserDAO;
import com.itc.maslenitsabot.feedback.FeedbackDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Обработчик запросов к админке.
 *
 * @author smnadya
 * @since 2025.02.02
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BotUserDAO botUserDAO;
    private final FeedbackDAO feedbackDAO;

    public AdminController(BotUserDAO botUserDAO, FeedbackDAO feedbackDAO) {
        this.botUserDAO = botUserDAO;
        this.feedbackDAO = feedbackDAO;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("dto", new AdminPanelDto(feedbackDAO.findAll(), botUserDAO.findAll()));
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}

