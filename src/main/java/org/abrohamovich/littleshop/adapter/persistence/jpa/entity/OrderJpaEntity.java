package org.abrohamovich.littleshop.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.abrohamovich.littleshop.domain.model.Customer;
import org.abrohamovich.littleshop.domain.model.OrderItem;
import org.abrohamovich.littleshop.domain.model.OrderStatus;
import org.abrohamovich.littleshop.domain.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerJpaEntity customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity user;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    public OrderJpaEntity(Long id, CustomerJpaEntity customer, UserJpaEntity user, OrderStatus status,
                          List<OrderItemJpaEntity> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.status = status;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addOrderItem(OrderItemJpaEntity item) {
        this.items.add(item);
        item.setOrder(this);
    }

    public void removeOrderItem(OrderItemJpaEntity item) {
        this.items.remove(item);
        item.setOrder(null);
    }
}
