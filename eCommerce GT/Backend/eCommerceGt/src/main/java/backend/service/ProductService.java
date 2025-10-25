package backend.service;

import backend.dto.market.ProductCreateDTO;
import backend.dto.market.ProductResponseDTO;
import backend.dto.market.ProductUpdateDTO;
import backend.models.market.Product;
import backend.models.market.ProductStatus;
import backend.models.users.User;
import backend.repository.market.ProductRepository;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO, String userDpi, MultipartFile image) throws IOException {
        User user = userRepository.findByDpi(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Date now = new Date();

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = fileStorageService.saveFile(image);
        }

        Product product = Product.builder()
                .productName(productCreateDTO.getProductName())
                .description(productCreateDTO.getDescription())
                .price(productCreateDTO.getPrice())
                .stock(productCreateDTO.getStock())
                .condition(productCreateDTO.isCondition())
                .status(ProductStatus.PENDING)
                .idCategory(productCreateDTO.getCategory())
                .image(imagePath)
                .userDpi(user)
                .createdAt(now)
                .updatedAt(now)
                .build();

        productRepository.save(product);
        return mapToResponseDTO(product);
    }

    public ProductResponseDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setProductName(productUpdateDTO.getProductName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setPrice(productUpdateDTO.getPrice());
        product.setStock(productUpdateDTO.getStock());
        product.setCondition(product.isCondition());
        product.setStatus(ProductStatus.PENDING);
        product.setIdCategory(productUpdateDTO.getCategory());
        product.setUpdatedAt(new Date());

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.saveFile(image);
            product.setImage(imagePath);
        } else {
            product.setImage(productUpdateDTO.getImage());
        }

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
                .status(product.getStatus())
                .category(product.getIdCategory())
                .image(product.getImage())
                .averageRating(product.getRatings() != null ?
                        product.getRatings().stream().mapToInt(r -> r.getStarts()).average().orElse(0.0) : 0.0)
                .build();
    }

    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToResponseDTO(product);
    }

    public List<ProductResponseDTO> getAllActiveProductsExceptUser(String userDpi) {
        return productRepository.findByConditionTrue()
                .stream()
                .filter(product -> !product.getUserDpi().getDpi().equals(userDpi))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
}