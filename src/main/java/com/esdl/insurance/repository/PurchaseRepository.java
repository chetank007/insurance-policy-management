package com.esdl.insurance.repository;

import com.esdl.insurance.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUsernameOrderByPurchasedAtDesc(String username);
    Optional<Purchase> findByIdAndUsername(Long id, String username);
}
