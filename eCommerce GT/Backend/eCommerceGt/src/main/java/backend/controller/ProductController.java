package backend.controller;

import backend.dto.market.ProductCreateDTO;
import backend.dto.market.ProductResponseDTO;
import backend.dto.market.ProductUpdateDTO;
import backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create/{userDpi}")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreateDTO productCreateDTO, @PathVariable String userDpi) {
        return ResponseEntity.ok(productService.createProduct(productCreateDTO, userDpi));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateDTO productUpdateDTO) {
        return ResponseEntity.ok(productService.updateProduct(productId, productUpdateDTO));
    }

    @GetMapping("/user/{userDpi}")
    public ResponseEntity<List<ProductResponseDTO>> getProductByUser(@PathVariable String userDpi) {
        return ResponseEntity.ok(productService.getProductsByUser(userDpi));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductResponseDTO>> getActiveProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }
}