package com.ftm.main.mock.features.jpa.annotations.compositeKeys;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class OrderItemId {
    private Long orderId;
    private Long productId;

    @Override
    public int hashCode() {
        return Objects.hash(orderId,productId);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof OrderItemId that))
            return false;
        return (Objects.equals(orderId,that.orderId)
            && Objects.equals(productId,that.productId));
    }
}
