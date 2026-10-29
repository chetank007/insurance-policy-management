package com.esdl.insurance.config;

import com.esdl.insurance.model.Policy;
import com.esdl.insurance.repository.PolicyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedPolicies(PolicyRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Policy("SecureLife Basic", "Term life cover for family financial protection.", "LIFE",
                    new BigDecimal("650.00"), new BigDecimal("1000000.00")));
                repository.save(new Policy("HealthGuard Plus", "Hospitalisation and eligible medical expense cover.", "HEALTH",
                    new BigDecimal("1200.00"), new BigDecimal("500000.00")));
                repository.save(new Policy("DriveSafe Motor", "Protection for eligible accidental vehicle damage.", "MOTOR",
                    new BigDecimal("900.00"), new BigDecimal("300000.00")));
            }
        };
    }
}
