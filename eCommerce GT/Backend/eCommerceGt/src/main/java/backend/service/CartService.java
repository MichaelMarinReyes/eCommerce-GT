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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(ShoppingCartRepository shoppingCartRepo, CartProductRepository cartProductRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.shoppingCartRepository = shoppingCartRepo;
        this.cartProductRepository = cartProductRepo;
        this.productRepository = productRepo;
        this.userRepository = userRepo;
    }

    /**
     * Sirve para añadir un producto al carrito de compras.
     *
     * @param userDpi   es el dpi del usuario que está comprando.
     * @param productId es el identificador del producto que va a comprar.
     * @param quantity  es la cantidad del producto que compra.
     */
    public CartProduct addProductToCart(String userDpi, Long productId, int quantity) {
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ShoppingCart cart = shoppingCartRepository.findByUserDpi(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserDpi(user);
                    newCart.setStatus(true);
                    newCart.setCreatedAt(new Date());
                    newCart.setUpdateAt(new Date());
                    return shoppingCartRepository.save(newCart);
                });

        cart.setUpdateAt(new Date());
        shoppingCartRepository.save(cart);

        Optional<CartProduct> existing = cart.getProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            CartProduct cp = existing.get();
            cp.setQuantity(cp.getQuantity() + quantity);
            return cartProductRepository.save(cp);
        } else {
            CartProduct cp = new CartProduct();
            cp.setCart(cart);
            cp.setProduct(product);
            cp.setQuantity(quantity);
            cp.setPrice(product.getPrice());
            return cartProductRepository.save(cp);
        }
    }

    /**
     * Obtiene el listado de productos que hay en el carrito de compras de un usuaario.
     *
     * @param userDpi es el dpi del usuario de quien se obtendrán los productos.
     * @return un listado con todos los productos en el carrito.
     */
    public List<CartProduct> getCartByUser(String userDpi) {
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepository.findByUserDpi(user)
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
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepository.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        cart.getProducts().stream()
                .filter(cp -> cp.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartProductRepository::delete);
    }

    /**
     * Vacía un carrito de compras seǵun el dpi ingresado.
     *
     * @param userDpi es el dpi del usuario del que se limpiará el carrito.
     */
    public void clearCart(String userDpi) {
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepository.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        cartProductRepository.deleteAll(cart.getProducts());
    }

    /**
     * Srive para marcar el carrtio como pagado.
     *
     * @param userDpi indica el dpi del usuario para pagar.
     */
    public void checkout(String userDpi) {
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepository.findByUserDpi(user)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        cart.setStatus(false);
        cart.setUpdateAt(new Date());
        shoppingCartRepository.save(cart);
    }

    /**
     * Sirve para actualizar la cantidad de un mismo producto.
     *
     * @param userDpi   es el dpi del usuario que compra.
     * @param productId es el identificador del producto.
     * @param quantity  es la cantidad del producto
     * @return un carrito de compras actualizado.
     */
    @Transactional
    public CartProduct updateProductQuantity(String userDpi, Long productId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findByUserDpi_Dpi(userDpi)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CartProduct cartProduct = cartProductRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        cartProduct.setQuantity(quantity);
        cartProduct.setPrice(product.getPrice() * quantity);

        return cartProductRepository.save(cartProduct);
    }

    /**
     * Obtiene los productos de un carrito específico asociado a un usuario.
     *
     * @param cartId  identificador del carrito.
     * @param userDpi dpi del usuario propietario del carrito.
     * @return lista de productos dentro del carrito.
     */
    public List<CartProduct> getCartByIdAndUser(Long cartId, String userDpi) {
        User user = userRepository.findById(userDpi)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (!cart.getUserDpi().equals(user)) {
            throw new RuntimeException("El carrito no pertenece al usuario indicado");
        }

        return cart.getProducts();
    }
}