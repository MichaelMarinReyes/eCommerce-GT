package backend.controller;

import backend.dto.management.SanctionCreateDTO;
import backend.dto.management.SanctionDTO;
import backend.service.SanctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sanctions")
@CrossOrigin(origins = {"http://localhost:4200",  "https://*.ngrok-free.app", "https://*.ngrok-free.dev", "https://e-comgt.netlify.app"}, allowedHeaders = "*", allowCredentials = "true")public class SanctionController {
    private final SanctionService sanctionService;

    public SanctionController(SanctionService sanctionService) {
        this.sanctionService = sanctionService;
    }

    @GetMapping("/all-sanctions")
    public ResponseEntity<List<SanctionDTO>> getAllSanctions() {
        List<SanctionDTO> sanctions = sanctionService.getAllSanctions();
        return ResponseEntity.ok(sanctions);
    }

    @PostMapping("/apply-sanction")
    public ResponseEntity<SanctionDTO> applySanction(@RequestBody SanctionCreateDTO dto) {
        if (dto.getUserDpi() == null || dto.getModeratorDpi() == null
                || dto.getViolationType() == null || dto.getReason() == null) {
            return ResponseEntity.badRequest().build();
        }

        SanctionDTO sanction = sanctionService.applySanction(
                dto.getUserDpi(),
                dto.getModeratorDpi(),
                dto.getViolationType(),
                dto.getReason(),
                dto.getEndDate()
        );
        return ResponseEntity.ok(sanction);
    }

    @PatchMapping("/{sanctionId}")
    public ResponseEntity<SanctionDTO> updateSanctionStatus(@PathVariable Long sanctionId, @RequestParam boolean status) {
        SanctionDTO updated = sanctionService.updateSanctionStatus(sanctionId, status);
        return ResponseEntity.ok(updated);
    }
}