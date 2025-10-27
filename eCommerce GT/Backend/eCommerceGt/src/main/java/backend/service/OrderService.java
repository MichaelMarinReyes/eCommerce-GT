package backend.service;

import backend.dto.market.OrderProductDTO;
import backend.dto.market.OrderResponseDTO;
import backend.models.market.Order;
import backend.models.market.OrderProduct;
import backend.models.market.ShoppingCart;
import backend.models.users.User;
import backend.repository.market.CartProductRepository;
import backend.repository.market.OrderProductRepository;
import backend.repository.market.OrderRepository;
import backend.repository.market.ShoppingCartRepository;
import backend.repository.users.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderService(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, CartProductRepository cartProductRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartProductRepository = cartProductRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    /**
     * Crea un pedido a partir del carrito del usuario.
     */
    @Transactional
    public ResponseEntity<OrderResponseDTO> checkout(Long cartId, String userDpi) {
        User user = userRepository.findById(userDpi).orElseThrow(() ->
                new RuntimeException("Usuario no encontrado")
        );
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElseThrow(() ->
                new RuntimeException("Carrito no encontrado")
        );

        Order order = Order.builder()
                .user(user)
                .shoppingCart(cart)
                .totalAmount(cart.getProducts().stream()
                        .mapToDouble(cp -> cp.getPrice() * cp.getQuantity())
                        .sum())
                .delivered(true) // en curso
                .createdAt(new Date())
                .deliveryDate(new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000)) // 5 días para entrega
                .build();
        orderRepository.save(order);

        List<OrderProduct> orderProducts = cart.getProducts().stream().map(cp -> {
            OrderProduct op = OrderProduct.builder()
                    .idOrder(order)
                    .product(cp.getProduct())
                    .quantity(cp.getQuantity())
                    .price(cp.getPrice())
                    .build();
            return orderProductRepository.save(op);
        }).collect(Collectors.toList());

        List<OrderProductDTO> productsDTO = orderProducts.stream().map(op ->
                OrderProductDTO.builder()
                        .name(op.getProduct().getProductName())
                        .price(op.getPrice())
                        .quantity(op.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .userDpi(user.getDpi())
                .products(productsDTO)
                .totalAmount(order.getTotalAmount())
                .delivered(order.isDelivered() ? "En curso" : "Entregado")
                .createdAt(order.getCreatedAt())
                .deliveryDate(order.getDeliveryDate())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Listar pedidos de un usuario
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUser(String userDpi) {
        User user = userRepository.findById(userDpi).orElseThrow(() ->
                new RuntimeException("Usuario no encontrado")
        );

        List<Order> orders = orderRepository.findByUserDpi(user.getDpi());

        return orders.stream().map(order -> {
            List<OrderProductDTO> productsDTO = order.getShoppingCart().getProducts().stream().map(cp ->
                    OrderProductDTO.builder()
                            .name(cp.getProduct().getProductName())
                            .price(cp.getPrice())
                            .quantity(cp.getQuantity())
                            .build()
            ).collect(Collectors.toList());

            return OrderResponseDTO.builder()
                    .orderId(order.getOrderId())
                    .userDpi(user.getDpi())
                    .products(productsDTO)
                    .totalAmount(order.getTotalAmount())
                    .delivered(order.isDelivered() ? "En curso" : "Entregado")
                    .createdAt(order.getCreatedAt())
                    .deliveryDate(order.getDeliveryDate())
                    .build();
        }).collect(Collectors.toList());
    }
}