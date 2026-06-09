package com.medtech.platform.messaging.config;

import com.medtech.platform.web.micro.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties defining service-specific messaging settings.
 *
 * <p>Encapsulates metadata required for Kafka message production and consumption
 * within a particular service context.</p>
 *
 * <h3>Properties</h3>
 * <ul>
 *     <li><b>currentService</b> — The logical service identity (see {@link Service}).
 *     Used for message headers and service-aware communication.</li>
 *
 *     <li><b>consumerGroupId</b> — Kafka consumer group identifier used
 *     for message consumption.</li>
 *
 *     <li><b>trustedConsumerPackage</b> — Base package pattern used for
 *     deserialization trust configuration (e.g., JSON type mapping).</li>
 * </ul>
 *
 * <p>All fields are validated to ensure proper configuration at startup.</p>
 *
 * <h3>Factory Method</h3>
 *
 * <p>The {@link #of(Service, String, Class)} method simplifies creation by
 * automatically deriving the trusted package from the provided topic class.</p>
 */
@Validated
@AllArgsConstructor
@Getter
@Setter
public class ServiceMessagingProperties {

    @NotNull
    private Service currentService;

    @NotBlank
    private String consumerGroupId;

    @NotBlank
    private String trustedConsumerPackage;

    public static ServiceMessagingProperties of(Service currentService, String consumerGroupId, Class<?> serviceTopicClass) {
        return new ServiceMessagingProperties(
                currentService,
                consumerGroupId,
                serviceTopicClass.getPackage().getName() + ".*"
        );
    }

}
