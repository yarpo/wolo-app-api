package pl.pjwstk.woloappapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomSwaggerUIController {

    @GetMapping("/swagger-ui-with-data")
    public String swaggerUIWithData(Model model) {
        // Tutaj możesz pobrać dane z tabeli (na przykład z bazy danych) i przekazać je do widoku
        // model.addAttribute("data", yourDataService.getData());

        // Przekazanie do widoku również parametrów Swagger UI
        return "swagger-ui-with-data";
    }
}
