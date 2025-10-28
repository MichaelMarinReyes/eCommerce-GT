package backend.service;

import backend.dto.management.SanctionDTO;
import backend.models.management.Sanction;
import backend.models.users.User;
import backend.repository.management.SanctionRepository;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanctionService {
    private final SanctionRepository  sanctionRepository;
    private final UserRepository userRepository;
    private final SanctionEmailService sanctionEmailService;

    public SanctionService(SanctionRepository sanctionRepository, UserRepository userRepository, SanctionEmailService sanctionEmailService) {
        this.sanctionRepository = sanctionRepository;
        this.userRepository = userRepository;
        this.sanctionEmailService = sanctionEmailService;
    }

    public List<SanctionDTO> getAllSanctions() {
        return sanctionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SanctionDTO applySanction(String userDpi, String moderatorDpi, String violationType, String reason, Date endDate) {
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        User moderator = userRepository.findById(moderatorDpi)
                .orElseThrow(() -> new RuntimeException("Moderador no encontrado"));

        Sanction sanction = new Sanction();
        sanction.setUserDpi(user);
        sanction.setModeratorDpi(moderator);
        sanction.setViolationType(violationType);
        sanction.setReason(reason);
        sanction.setStatus(true);
        sanction.setStartDate(new Date());
        sanction.setEndDate(endDate);

        sanction = sanctionRepository.save(sanction);

        user.setStatus(false);
        userRepository.save(user);

        sanctionEmailService.sendSanctionEmail(user, violationType, reason, endDate);
        return toDTO(sanction);
    }

    public SanctionDTO updateSanctionStatus(Long sanctionId, boolean status) {
        Sanction sanction = sanctionRepository.findById(sanctionId)
                .orElseThrow(() -> new RuntimeException("Sanción no encontrada"));

        sanction.setStatus(status);
        if (!status) {
            sanction.setEndDate(new Date());
        }
        sanction = sanctionRepository.save(sanction);

        User user = sanction.getUserDpi();
        user.setStatus(!status);
        userRepository.save(user);
        return toDTO(sanction);
    }

    public SanctionDTO toDTO(Sanction sanction) {
        if (sanction == null) return null;

        SanctionDTO dto = new SanctionDTO();
        dto.setIdSanction(sanction.getIdSanction());
        dto.setUserDpi(sanction.getUserDpi().getDpi());
        dto.setUserName(sanction.getUserDpi().getName());
        dto.setModeratorDpi(sanction.getModeratorDpi().getDpi());
        dto.setModeratorName(sanction.getModeratorDpi().getName());
        dto.setReason(sanction.getReason());
        dto.setStatus(sanction.isStatus());
        dto.setStartDate(sanction.getStartDate());
        dto.setEndDate(sanction.getEndDate());
        dto.setViolationType(sanction.getViolationType());

        return dto;
    }
}