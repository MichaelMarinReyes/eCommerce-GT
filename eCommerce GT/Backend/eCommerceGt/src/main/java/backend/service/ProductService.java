package backend.service;

import backend.dto.product.ProductCreateDTO;
import backend.dto.product.ProductResponseDTO;
import backend.dto.product.ProductUpdateDTO;
import backend.models.market.Product;
import backend.models.users.User;
import backend.repository.market.ProductRepository;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO, String userDpi) {
        User user = userRepository.findByDpi(userDpi).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = Product.builder()
                .productName(productCreateDTO.getProductName())
                .description(productCreateDTO.getDescription())
                .price(productCreateDTO.getPrice())
                .stock(productCreateDTO.getStock())
                .condition(false)
                .idCategory(productCreateDTO.getCategory())
                .image(productCreateDTO.getImage())
                .userDpi(user)
                .build();

        productRepository.save(product);
        return mapToResponseDTO(product);
    }

    public ProductResponseDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setProductName(productUpdateDTO.getProductName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setPrice(productUpdateDTO.getPrice());
        product.setStock(productUpdateDTO.getStock());
        product.setCondition(false);
        product.setIdCategory(productUpdateDTO.getCategory());
        product.setImage(productUpdateDTO.getImage());

        productRepository.save(product);
        return mapToResponseDTO(product);
    }

    public List<ProductResponseDTO> getProductsByUser(String userDpi) {
        return productRepository.findByUserDpiDpi(userDpi)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getAllActiveProducts() {
        return productRepository.findByConditionTrue()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getProductId())
                .name(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .condition(product.isCondition())
                .category(product.getIdCategory())
                .image(product.getImage())
                .averageRating(product.getRatings() != null ?
                        product.getRatings().stream().mapToInt(r -> r.getStarts()).average().orElse(0.0) : 0.0)
                .build();
    }
}