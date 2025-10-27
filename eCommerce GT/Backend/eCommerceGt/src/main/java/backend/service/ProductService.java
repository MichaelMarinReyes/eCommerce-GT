package backend.service;

import backend.dto.market.ProductCreateDTO;
import backend.dto.market.ProductResponseDTO;
import backend.dto.market.ProductUpdateDTO;
import backend.dto.rating.RatingDTO;
import backend.models.market.Category;
import backend.models.market.Product;
import backend.models.market.ProductStatus;
import backend.models.market.Rating;
import backend.models.users.User;
import backend.repository.market.CategoryRepository;
import backend.repository.market.ProductRepository;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final CategoryRepository categoryRepository;
    private final EmailService emailService;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, FileStorageService fileStorageService, CategoryRepository categoryRepository, EmailService emailService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.categoryRepository = categoryRepository;
        this.emailService = emailService;
    }

    /**
     * Sirve para crear un producto, valida los campos necesarios y posteriormente al dto correspondiente.
     *
     * @param productCreateDTO es un objeto que contiene los datos del producto recibido del controlador.
     * @param userDpi          es el dpi del usuario que creará el producto.
     * @param image            es la imagen del producto con la que se venderá.
     * @return un ProductResponseDTO indicando los datos guardados.
     * @throws IOException ocurre en caso de que image no pueda ser guardado.
     */
    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO, String userDpi, MultipartFile image) throws IOException {
        User user = userRepository.findByDpi(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Date now = new Date();

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = fileStorageService.saveFile(image, productCreateDTO.getProductName());
        }

        Category category = categoryRepository.findByCategoryName(productCreateDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + productCreateDTO.getCategory()));


        Product product = Product.builder()
                .productName(productCreateDTO.getProductName())
                .description(productCreateDTO.getDescription())
                .price(productCreateDTO.getPrice())
                .stock(productCreateDTO.getStock())
                .condition(productCreateDTO.isCondition())
                .status(ProductStatus.PENDING)
                .idCategory(category)
                .image(imagePath)
                .userDpi(user)
                .createdAt(now)
                .updatedAt(now)
                .build();

        productRepository.save(product);
        return mapToResponseDTO(product);
    }

    /**
     * Sirve para actualizar los datos de un producto.
     *
     * @param productId        es el identificador del producto que se va a modificar sus datos.
     * @param productUpdateDTO es el producto que contiene los datos actualizados.
     * @param image            es la imagen del producto.
     * @param userDpi          es el dpi del usuario el cuál se utiliza para validar que sea el que creó el producto
     *                         y no otro usuario el que vaya a modificar el producto.
     * @return un ProductResponseDTO con los datos actualizados.
     * @throws IOException en caso que la imagen no pueda ser guardada.
     */
    @Transactional
    public ProductResponseDTO updateProduct(Long productId, ProductUpdateDTO productUpdateDTO, MultipartFile image, String userDpi) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!product.getUserDpi().getDpi().equals(userDpi)) {
            throw new RuntimeException("No tienes permiso para actualizar este producto");
        }

        Category category = categoryRepository.findByCategoryName(productUpdateDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + productUpdateDTO.getCategory()));

        product.setProductName(productUpdateDTO.getProductName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setPrice(productUpdateDTO.getPrice());
        product.setStock(productUpdateDTO.getStock());
        product.setCondition(productUpdateDTO.isCondition());
        product.setStatus(ProductStatus.PENDING);
        product.setIdCategory(category);
        product.setUpdatedAt(new Date());

        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.saveFile(image, productUpdateDTO.getProductName());
            product.setImage(imagePath);
        }

        productRepository.save(product);
        return mapToResponseDTO(product);
    }

    /**
     * Se utiliza para eliminar un producto del sistema, elimina también su stock.
     *
     * @param productId es el identificador del producto que se eliminará.
     * @param userDpi   es el dpi del usuario el cual se utiliza para verificar que el usuario que creó el producto
     *                  sea el que lo elimine.
     * @return true si el producto fue eliminado correctamente, false de lo contrario.
     */
    @Transactional
    public boolean deleteProduct(Long productId, String userDpi) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return false;
        }

        if (!product.getUserDpi().getDpi().equals(userDpi)) {
            return false;
        }

        String imagePath = "src/main/uploads/images/" + product.getImage();
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }

        productRepository.delete(product);
        return true;
    }

    /**
     * Sirve para obtener un listado de productos según el usuario que esté relacionado.
     *
     * @param userDpi es el dpi del usuario que se usará para buscar los productos.
     * @return el listado de los productos encontrados.
     */
    public List<ProductResponseDTO> getProductsByUser(String userDpi) {
        return productRepository.findByUserDpiDpi(userDpi)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Se usa para obtener todos los productos que hayan sido aprobados por un moderador.
     *
     * @return el listado de todos los productos aprobados.
     */
    public List<ProductResponseDTO> getAllActiveProducts() {
        return productRepository.findByConditionTrue()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Sirve para mapear un producto a un ProductResponseDTO.
     *
     * @param product es el producto que contiene los datos de un producto.
     * @return un ProductResponseDTO.
     */
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
                .sellerName(product.getUserDpi().getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    /**
     * Sirve para obtener un producto en específico según su identificador.
     *
     * @param productId es el identificador del producto que se buscará.
     * @return un ProductResponseDTO con los datos del producto encontrado.
     */
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToResponseDTO(product);
    }

    /**
     * Sirve para devolver los productos excepto los de un dpi en específico, se utiliza para que el usuario logueado
     * no vea los productos que él vende.
     *
     * @param userDpi es el dpi del usuario logueado en el sistema.
     * @return un listado de productos exlcuyendo los que coinciden con el dpi ingresado.
     */
    public List<ProductResponseDTO> getAllActiveProductsExceptUser(String userDpi) {
        return productRepository.findByConditionTrue()
                .stream()
                .filter(product -> !product.getUserDpi().getDpi().equals(userDpi))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Sirve para obtener un producto con su promedio de calificaciones y lista de comentarios.
     *
     * @param productId es el identificador del producto que se obtendrán los datos.
     * @return un ProductResponseDTO con los datos completos.
     */
    public ProductResponseDTO getProductWithRatings(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        double avg = product.getRatings().stream()
                .mapToInt(Rating::getStarts)
                .average()
                .orElse(0.0);

        List<RatingDTO> ratingDTOs = product.getRatings().stream()
                .map(r -> {
                    RatingDTO dto = new RatingDTO();
                    dto.setIdRating(r.getIdRating());
                    dto.setUserName(r.getUserDpi().getName());
                    dto.setStars(r.getStarts());
                    dto.setComment(r.getComment());
                    dto.setCreatedAt(r.getProductId().getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getProductId());
        dto.setName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCondition(product.isCondition());
        dto.setStatus(product.getStatus());
        dto.setCategory(product.getIdCategory());
        dto.setImage(product.getImage());
        dto.setAverageRating(avg);
        dto.setRatings(ratingDTOs);

        return dto;
    }

    /**
     * Obtener todos los productos pendientes de aprobación.
     *
     * @return lista de productos con estado PENDING.
     */
    public List<ProductResponseDTO> getPendingProducts() {
        return productRepository.findByStatus(ProductStatus.PENDING)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Aprobar un producto pendiente.
     *
     * @param productId ID del producto a aprobar.
     * @return Producto aprobado en formato DTO.
     */
    @Transactional
    public ProductResponseDTO approveProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setStatus(ProductStatus.APPROVED);
        product.setUpdatedAt(new Date());
        productRepository.save(product);

        String to = product.getUserDpi().getEmail(); // suponiendo que Product tiene un User
        String subject = "Producto aprobado";
        String text = "Hola " + product.getUserDpi().getName() +
                ", tu producto '" + product.getProductName() + "' ha sido aprobado.";
        emailService.sendEmail(to, subject, text);

        return mapToResponseDTO(product);
    }

    /**
     * Rechazar un producto pendiente.
     *
     * @param productId ID del producto a rechazar.
     * @return Producto rechazado en formato DTO.
     */
    @Transactional
    public ProductResponseDTO rejectProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setStatus(ProductStatus.REJECTED);
        productRepository.save(product);
        String to = product.getUserDpi().getEmail();
        String subject = "Producto rechazado";
        String text = "Hola " + product.getUserDpi().getName() +
                ", tu producto '" + product.getProductName() + "' ha sido rechazado.";
        emailService.sendEmail(to, subject, text);
        return mapToResponseDTO(product);
    }
}