package com.medtech.platform.persistence;

import com.medtech.platform.util.time.UtcClock;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditableEntity {

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        updatedAt = UtcClock.nowLocal();
    }

}
