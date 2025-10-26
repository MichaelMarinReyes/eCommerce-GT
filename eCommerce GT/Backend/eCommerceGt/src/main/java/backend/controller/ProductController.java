package backend.controller;

import backend.dto.market.ProductCreateDTO;
import backend.dto.market.ProductResponseDTO;
import backend.dto.market.ProductUpdateDTO;
import backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Es un endpoint que contiene los datos para crear un producto nuevo.
     * @param productName es el nombre del producto nuevo.
     * @param description es la descripción del producto nuevo.
     * @param price es el precio al que se pondrá en venta el producto nuevo.
     * @param stock es la cantidad del mismo producto que habrá.
     * @param condition indica si el producto es nuevo o usado, true = nuevo, false = usado.
     * @param categoryName indica la categoría a la que pertenece el producto.
     * @param userDpi es el dpi del usuario que venderá el producto.
     * @param image es la imagen que se verá para vender el producto.
     * @return una respuesta para el frontend.
     * @throws IOException en caso de que la imagen no pueda ser guardada.
     */
    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("condition") boolean condition,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("userDpi") String userDpi,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ProductCreateDTO dto = new ProductCreateDTO();
        dto.setProductName(productName);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setStock(stock);
        dto.setCondition(condition);
        dto.setCategory(categoryName);

        return ResponseEntity.ok(productService.createProduct(dto, userDpi, image));
    }

    /**
     * Es un endpoint para actualizar los datos de un producto.
     * @param productId es el identificador del producto que se modificará.
     * @param productName es el nombre del producto que se modificará.
     * @param description es la descripción del producto que se modificará.
     * @param price es el precio actualizado del producto.
     * @param stock es la nueva cantidad del producto.
     * @param condition indica si es nuevo o usado el producto.
     * @param categoryName indica la categoría a la que pertenece.
     * @param userDpi es el dpi del usuario que modifica el producto.
     * @param image es la imagen del producto.
     * @return una respuesta para el frontend.
     * @throws IOException en caso de que la imagen no pueda ser guardada.
     */
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("condition") boolean condition,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("userDpi") String userDpi,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        ProductUpdateDTO dto = new ProductUpdateDTO();
        dto.setProductName(productName);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setStock(stock);
        dto.setCondition(condition);
        dto.setCategory(categoryName);

        ProductResponseDTO updatedProduct = productService.updateProduct(productId, dto, image, userDpi);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Es un endpoint para obtener un producto según el usuario que lo creó.
     * @param userDpi es el dpi del usuario.
     * @return una respuesta para el frontend.
     */
    @GetMapping("/user/{userDpi}")
    public ResponseEntity<List<ProductResponseDTO>> getProductByUser(@PathVariable String userDpi) {
        return ResponseEntity.ok(productService.getProductsByUser(userDpi));
    }

    /**
     * Es un endpoint que contiene todos los productos aprobados y pueden ser vendidos.
     * @return un json con todos los productos con estado aprobados.
     */
    @GetMapping("/approved")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    /**
     * Es un endpoint para obtener un producto según su id.
     * @param id es el identificador del producto.
     * @return un json con los datos del producto
     */
    @GetMapping("/edit/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    /**
     * Es un endpoint que obtiene los productos exceptuando los que coinciden con el dpi ingresado.
     * @param userDpi es el dpi del usuario.
     * @return un json con los productos.
     */
    @GetMapping("/exclude/{userDpi}")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProductsExcludingUser(@PathVariable String userDpi) {
        return ResponseEntity.ok(productService.getAllActiveProductsExceptUser(userDpi));
    }

    /**
     * Es un endpoint para eliminar un producto y su stock.
     * @param id es el identificador del producto.
     * @param userDpi es el dpi del usuario que va a eliminar el producto.
     * @return un mensaje al frontend.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable Long id, @RequestParam("userDpi") String userDpi) {
        boolean deleted = productService.deleteProduct(id, userDpi);

        ProductResponseDTO response = new ProductResponseDTO();
        if (deleted) {
            response.setName("Producto eliminado");
        } else {
            response.setName("No tienes permiso para eliminar este producto o no existe");
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Es un endpoint para obtener los comentarios y raiting de un producto.
     * @param id es el identificador del producto del cual se obtendrán los comentarios y raiting.
     * @return un json con los datos.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        ProductResponseDTO productResponseDTO = productService.getProductWithRatings(id);
        return ResponseEntity.ok(productResponseDTO);
    }
}