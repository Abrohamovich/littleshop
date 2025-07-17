package org.abrohamovich.littleshop.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.abrohamovich.littleshop.domain.model.OfferType;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferType type;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryJpaEntity category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierJpaEntity supplier;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
