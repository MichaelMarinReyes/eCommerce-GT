package backend.controller;

import backend.dto.market.OrderResponseDTO;
import backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint para generar un pedido a partir del carrito
     *
     * @param cartId  id del carrito
     * @param userDpi dpi del usuario
     */
    @PostMapping("/checkout/{cartId}/{userDpi}")
    public ResponseEntity<OrderResponseDTO> checkout(
            @PathVariable Long cartId,
            @PathVariable String userDpi
    ) {
        return orderService.checkout(cartId, userDpi);
    }

    /**
     * Endpoint para listar los pedidos de un usuario
     *
     * @param userDpi dpi del usuario
     */
    @GetMapping("/user/{userDpi}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable String userDpi) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUser(userDpi);
        return ResponseEntity.ok(orders);
    }
}