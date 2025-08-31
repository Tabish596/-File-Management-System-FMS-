package com.ftm.main.mock.features.jpa.annotations.compositeKeys;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class AuditInfo {
    private String createdBy;
    private LocalDateTime createdDate;
}
