package backend.repository.market;

import backend.models.market.Product;
import backend.models.market.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    List<Product> findByIdCategory_CategoryName(String categoryName);

    List<Product> findByConditionTrue();

    List<Product> findByUserDpiDpi(String userDpi);

    List<Product> findByStatus(ProductStatus status);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
