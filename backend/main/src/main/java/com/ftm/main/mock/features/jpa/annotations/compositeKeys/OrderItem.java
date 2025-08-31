package com.ftm.main.mock.features.jpa.annotations.compositeKeys;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @EmbeddedId
    private OrderItemId OrderItemId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    private Product product;

    @Embedded
    private AuditInfo auditInfo;

    private int quantity;
}
