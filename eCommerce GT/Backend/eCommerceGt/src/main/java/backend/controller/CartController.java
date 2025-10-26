package backend.controller;

import backend.models.market.CartProduct;
import backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return
     */
    @GetMapping("/{userDpi}")
    public ResponseEntity<List<CartProduct>> getCart(@PathVariable String userDpi) {
        List<CartProduct> products = cartService.getCartByUser(userDpi);
        return ResponseEntity.ok(products);
    }

    /**
     * Añade un producto a un carrito.
     *
     * @param userDpi   es el dpi del usuario que tiene un carrito.
     * @param productId es el identificador del producto.
     * @param quantity  es la cantidad del producto.
     * @return un json con un un mensaje al frontend.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam String userDpi, @RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity) {
        cartService.addProductToCart(userDpi, productId, quantity);
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    /**
     * Elimina un producto de un carrito.
     *
     * @param userDpi   es el dpi con el que se buscará el carrito.
     * @param productId es el identificador del producto que se eliminará.
     * @return un json con un mensaje al frontend.
     */
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam String userDpi, @RequestParam Long productId) {
        cartService.removeProductFromCart(userDpi, productId);
        return ResponseEntity.ok("Producto eliminado del carrito");
    }

    /**
     * Vacía el carrito por completo.
     *
     * @param userDpi es el dpi del usuario con el que se buscará el carrito para vaciarlo.
     * @return un json con mensaje al frontend.
     */
    @DeleteMapping("/clear/{userDpi}")
    public ResponseEntity<String> clearCart(@PathVariable String userDpi) {
        cartService.clearCart(userDpi);
        return ResponseEntity.ok("Carrito vaciado");
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
}