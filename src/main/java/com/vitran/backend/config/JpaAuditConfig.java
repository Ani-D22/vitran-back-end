package com.vitran.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *    Added this Config to enable automatic handling of the following fields
 *    that exist in almost all the model entities:
 *      1. createdDate
 *      2. updatedDate
 *    Which means that we are not supposed to handle the above fields.
 *    They will automatically be handled by the Spring Boot itself.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {
}