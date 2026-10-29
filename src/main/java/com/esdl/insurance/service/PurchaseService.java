package com.esdl.insurance.service;

import com.esdl.insurance.model.Policy;
import com.esdl.insurance.model.Purchase;
import com.esdl.insurance.repository.PolicyRepository;
import com.esdl.insurance.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PurchaseService {
    private final PolicyRepository policies;
    private final PurchaseRepository purchases;
    public PurchaseService(PolicyRepository policies, PurchaseRepository purchases) {
        this.policies = policies; this.purchases = purchases;
    }
    @Transactional
    public Purchase purchase(Long policyId, String username) {
        Policy policy = policies.findById(policyId)
            .filter(Policy::isActive)
            .orElseThrow(() -> new ResourceNotFoundException("Active policy not found: " + policyId));
        return purchases.save(new Purchase(policy, username));
    }
    public List<Purchase> myPurchases(String username) {
        return purchases.findByUsernameOrderByPurchasedAtDesc(username);
    }
    public Purchase ownedPurchase(Long id, String username) {
        return purchases.findByIdAndUsername(id, username)
            .orElseThrow(() -> new ResourceNotFoundException("Purchased policy not found for this user: " + id));
    }
}
