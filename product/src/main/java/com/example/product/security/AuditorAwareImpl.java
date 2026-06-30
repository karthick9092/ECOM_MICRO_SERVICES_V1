package com.example.product.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public java.util.Optional<String> getCurrentAuditor() {
        // Implement logic to retrieve the current auditor (e.g., from security context)
        return java.util.Optional.of("system"); // Placeholder for demonstration
    }
}
