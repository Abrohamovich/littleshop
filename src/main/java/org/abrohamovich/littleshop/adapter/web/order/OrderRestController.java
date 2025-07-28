package org.abrohamovich.littleshop.adapter.web.order;

import jakarta.validation.Valid;
import lombok.*;
import org.abrohamovich.littleshop.adapter.web.offer.OfferWebResponse;
import org.abrohamovich.littleshop.application.dto.PageResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderCreateCommand;
import org.abrohamovich.littleshop.application.dto.order.OrderResponse;
import org.abrohamovich.littleshop.application.dto.order.OrderUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.order.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {
    private final AddOrderItemToOrderUseCase addOrderItemToOrderUseCase;
    private final ChangeOrderStatusUseCase changeOrderStatusUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final RemoveOrderItemFromOrderUseCase removeOrderItemFromOrderUseCase;
    private final UpdateOrderItemQuantityUseCase updateOrderItemQuantityUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    @PostMapping
    public ResponseEntity<OrderWebResponse> create(@Valid @RequestBody OrderCreateWebRequest orderCreateWebRequest) {
        OrderCreateCommand orderCreateCommand = orderWebMapper.toCreateCommand(orderCreateWebRequest);
        OrderResponse orderResponse = createOrderUseCase.save(orderCreateCommand);
        return new ResponseEntity<>(orderWebMapper.toWebResponse(orderResponse), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderWebResponse> findById(@PathVariable Long id) {
        OrderResponse orderResponse = getOrderUseCase.findById(id);
        return new ResponseEntity<>(orderWebMapper.toWebResponse(orderResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderWebResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long userId
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponse> ordersPage;

        if(customerId != null && customerId > 0) {
            ordersPage = getOrderUseCase.findByCustomerId(customerId, pageable);
        } else if(userId != null && userId > 0) {
            ordersPage = getOrderUseCase.findByUserId(userId, pageable);
        } else {
            ordersPage = getOrderUseCase.findAll(pageable);
        }

        Page<OrderWebResponse> webResponsePage = ordersPage.map(orderWebMapper::toWebResponse);
        return new ResponseEntity<>(PageResponse.fromSpringPage(webResponsePage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderWebResponse> update(@PathVariable Long id, @Valid @RequestBody OrderUpdateWebRequest orderUpdateWebRequest) {
        OrderUpdateCommand orderUpdateCommand = orderWebMapper.toUpdateCommand(orderUpdateWebRequest);
        OrderResponse orderResponse = updateOrderUseCase.update(id, orderUpdateCommand);
        return new ResponseEntity<>(orderWebMapper.toWebResponse(orderResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteOrderUseCase.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
