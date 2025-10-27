package backend.repository.market;

import backend.models.market.CartProduct;
import backend.models.market.Product;
import backend.models.market.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByCartAndProduct(ShoppingCart cart, Product product);

    //public List<CartProduct> getCartByIdAndUser(Long cartId, String userDpi)
}