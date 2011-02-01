package net.vivin.starship.controller;

import net.vivin.starship.domain.Starship;
import net.vivin.validation.service.ValidationConstraintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StarshipController {

    @Autowired
    private ValidationConstraintsService validationConstraintsService;

    @RequestMapping
    public void create(Model model) {
        model.addAttribute("starship", new Starship());
    }
}
