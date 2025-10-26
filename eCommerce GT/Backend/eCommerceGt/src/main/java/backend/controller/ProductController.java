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

    @PostMapping("/create/{userDpi}")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @PathVariable String userDpi,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("condition")  boolean condition,
            @RequestParam("categoryName") String categoryName,
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

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("condition") boolean condition,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("userDpi") String userDpi
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

    @GetMapping("/user/{userDpi}")
    public ResponseEntity<List<ProductResponseDTO>> getProductByUser(@PathVariable String userDpi) {
        return ResponseEntity.ok(productService.getProductsByUser(userDpi));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/exclude/{userDpi}")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProductsExludingUser(@PathVariable String userDpi) {
        return ResponseEntity.ok(productService.getAllActiveProductsExceptUser(userDpi));
    }

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

}