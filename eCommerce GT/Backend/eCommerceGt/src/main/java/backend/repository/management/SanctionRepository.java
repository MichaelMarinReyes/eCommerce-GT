package backend.repository.management;

import backend.models.management.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Long> {

}