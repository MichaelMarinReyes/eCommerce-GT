package backend.controller;

import backend.dto.users.UserDTO;
import backend.models.users.User;
import backend.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:4200",  "https://*.ngrok-free.app", "https://*.ngrok-free.dev", "https://e-comgt.netlify.app"}, allowedHeaders = "*", allowCredentials = "true")public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/all-employees")
    public UserDTO addEmployee(@RequestBody User user) {
        return adminService.addEmployee(user);
    }

    @PutMapping("/employees/{dpi}")
    public UserDTO updateEmployee(@PathVariable String dpi, @RequestBody User user) {
        user.setDpi(dpi);
        return adminService.updateEmployee(user);
    }

    @GetMapping("/employees-history")
    public List<UserDTO> getEmployeeHistory() {
        return adminService.getEmployeeHistory();
    }

    @DeleteMapping("/employees/{dpi}")
    public void deleteEmployee(@PathVariable String dpi) {
        adminService.deleteEmployee(dpi);
    }
}