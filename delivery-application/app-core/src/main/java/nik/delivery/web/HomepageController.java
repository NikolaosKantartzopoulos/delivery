package nik.delivery.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomepageController {

    @Value("${env.company-name}")
    private String companyName;

    private final Environment environment;

    public HomepageController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("companyName", companyName);
        model.addAttribute("activeProfile", environment.getActiveProfiles().length > 0 ? environment.getActiveProfiles()[0] : "default");
        return "home";
    }
}
