package backend.controller;

import backend.dto.market.CartProductDTO;
import backend.dto.market.CategoryDTO;
import backend.dto.market.ProductCartDTO;
import backend.dto.market.ShoppingCartResponseDTO;
import backend.models.market.CartProduct;
import backend.models.market.Product;
import backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Es un endpoint que obtiene el carrito del usuario.
     *
     * @param userDpi es el dpi del usuario del cual se obtendrá el carrito.
     * @return un json de respuesta al frontend.
     */
    @GetMapping("/{userDpi}")
    public ResponseEntity<List<CartProductDTO>> getCart(@PathVariable String userDpi) {
        List<CartProduct> products = cartService.getCartByUser(userDpi);
        List<CartProductDTO> dtoList = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Añade un producto a un carrito.
     *
     * @param userDpi   es el dpi del usuario que tiene un carrito.
     * @param productId es el identificador del producto.
     * @param quantity  es la cantidad del producto.
     * @return un json con un mensaje al frontend.
     */
    @PostMapping("/add")
    public ResponseEntity<CartProductDTO> addToCart(
            @RequestParam String userDpi,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity) {

        CartProduct savedProduct = cartService.addProductToCart(userDpi, productId, quantity);
        CartProductDTO dto = convertToDTO(savedProduct);
        return ResponseEntity.ok(dto);
    }

    /**
     * Elimina un producto de un carrito.
     *
     * @param userDpi   es el dpi con el que se buscará el carrito.
     * @param productId es el identificador del producto que se eliminará.
     * @return un json con un mensaje al frontend.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeFromCart(@RequestParam String userDpi, @RequestParam Long productId) {
        cartService.removeProductFromCart(userDpi, productId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Producto eliminado del carrito");
        return ResponseEntity.ok(response);
    }

    /**
     * Vacía el carrito por completo.
     *
     * @param userDpi es el dpi del usuario con el que se buscará el carrito para vaciarlo.
     * @return un json con mensaje al frontend.
     */
    @DeleteMapping("/clear/{userDpi}")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable String userDpi) {
        cartService.clearCart(userDpi);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Carrito vaciado");
        return ResponseEntity.ok(response);
    }

    /**
     * Marca el carrito como pagado.
     *
     * @param userDpi es el dpi del producto que se guardará para reportes.
     * @return json con mensaje al frontend.
     */
    @PostMapping("/checkout/{userDpi}")
    public ResponseEntity<String> checkout(@PathVariable String userDpi) {
        cartService.checkout(userDpi);
        return ResponseEntity.ok("Compra realizada exitosamente");
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param userDpi   dpi del usuario
     * @param productId id del producto
     * @param quantity  nueva cantidad
     * @return json con el producto actualizado
     */
    @PutMapping("/update-quantity")
    public ResponseEntity<CartProductDTO> updateQuantity(@RequestParam String userDpi, @RequestParam Long productId, @RequestParam int quantity) {
        CartProduct updated = cartService.updateProductQuantity(userDpi, productId, quantity);
        CartProductDTO dto = convertToDTO(updated);
        return ResponseEntity.ok(dto);
    }


    /**
     * Convierte la entidad CartProduct a un DTO.
     *
     * @param cartProduct es el carrito de compras que se mapeará.
     * @return un CartResponseDTO mapeado.
     */
    private CartProductDTO convertToDTO(CartProduct cartProduct) {
        Product p = cartProduct.getProduct();

        ProductCartDTO productDTO = ProductCartDTO.builder()
                .id(p.getProductId())
                .name(p.getProductName())
                .description(p.getDescription())
                .image(p.getImage())
                .price(p.getPrice())
                .stock(p.getStock())
                .condition(p.isCondition())
                .status(p.getStatus().name())
                .category(CategoryDTO.builder()
                        .idCategory(p.getIdCategory().getIdCategory())
                        .categoryName(p.getIdCategory().getCategoryName())
                        .description(p.getIdCategory().getDescription())
                        .build())
                .userName(p.getUserDpi().getName())
                .build();

        return CartProductDTO.builder()
                .id(cartProduct.getIdCartProduct())
                .idCart(cartProduct.getCart().getIdCart())
                .product(productDTO)
                .quantity(cartProduct.getQuantity())
                .price(cartProduct.getPrice())
                .build();
    }

}