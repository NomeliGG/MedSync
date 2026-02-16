package com.medtech.platform.outbox;

import com.medtech.platform.persistence.projection.LongId;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface OutboxRepository extends JpaRepository<OutboxEntity, Long> {

    List<LongId> findIdByStatusAndAttemptsLessThanEqualOrderByCreatedAt(
            OutboxStatus status,
            int attempts,
            Limit limit
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<OutboxEntity> findById(Long id);

}
