package backend.service;

import backend.models.market.CartProduct;
import backend.models.market.Product;
import backend.models.market.ShoppingCart;
import backend.models.users.User;
import backend.repository.market.CartProductRepository;
import backend.repository.market.ProductRepository;
import backend.repository.market.ShoppingCartRepository;
import backend.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final ShoppingCartRepository shoppingCartRepo;
    private final CartProductRepository cartProductRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public CartService(ShoppingCartRepository shoppingCartRepo, CartProductRepository cartProductRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
        this.cartProductRepo = cartProductRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    /**
     * Sirve para añadir un producto al carrito de compras.
     *
     * @param userDpi   es el dpi del usuario que está comprando.
     * @param productId es el identificador del producto que va a comprar.
     * @param quantity  es la cantidad del producto que compra.
     */
    public void addProductToCart(String userDpi, Long productId, int quantity) {
        User user = userRepo.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ShoppingCart cart = shoppingCartRepo.findByUserDpi(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserDpi(user);
                    newCart.setStatus(true); // carrito activo
                    newCart.setCreatedAt(new Date());
                    newCart.setUpdateAt(new Date());
                    return shoppingCartRepo.save(newCart);
                });

        Optional<CartProduct> existing = cart.getProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuatity(existing.get().getQuatity() + quantity);
            cartProductRepo.save(existing.get());
        } else {
            CartProduct cp = new CartProduct();
            cp.setCart(cart);
            cp.setProduct(product);
            cp.setQuatity(quantity);
            cp.setPrice(product.getPrice());
            cartProductRepo.save(cp);
        }
    }

    /**
     * Obtiene el listado de productos que hay en el carrito de compras de un usuaario.
     *
     * @param userDpi es el dpi del usuario de quien se obtendrán los productos.
     * @return un listado con todos los productos en el carrito.
     */
    public List<CartProduct> getCartByUser(String userDpi) {
        User user = userRepo.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepo.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        return cart.getProducts();
    }

    /**
     * Elimina un producto de un carrito.
     *
     * @param userDpi   es el dpi del usuario que se buscará un carrito de compras.
     * @param productId es el identificador del producto.
     */
    public void removeProductFromCart(String userDpi, Long productId) {
        User user = userRepo.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepo.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        cart.getProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartProductRepo::delete);
    }

    /**
     * Vacía un carrito de compras seǵun el dpi ingresado.
     *
     * @param userDpi es el dpi del usuario del que se limpiará el carrito.
     */
    public void clearCart(String userDpi) {
        User user = userRepo.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepo.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        cartProductRepo.deleteAll(cart.getProducts());
    }

    /**
     * Srive para marcar el carrtio como pagado.
     * @param userDpi indica el dpi del usuario para pagar.
     */
    public void checkout(String userDpi) {
        User user = userRepo.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepo.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        cart.setStatus(false);
        cart.setUpdateAt(new Date());
        shoppingCartRepo.save(cart);
    }
}