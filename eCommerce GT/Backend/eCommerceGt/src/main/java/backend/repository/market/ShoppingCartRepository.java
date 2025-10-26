package backend.repository.market;

import backend.models.market.ShoppingCart;
import backend.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserDpi(User userDpi);

    Optional<ShoppingCart> findByUserDpiAndStatus(User userDpi, boolean status);
}