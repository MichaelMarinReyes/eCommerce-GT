package backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class AdminController {
    @PostMapping("/get-report")
    void getReports() {
        System.out.println("getReports");
    }
}
