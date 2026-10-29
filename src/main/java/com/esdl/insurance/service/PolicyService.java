package com.esdl.insurance.service;

import com.esdl.insurance.dto.PolicyRequest;
import com.esdl.insurance.model.Policy;
import com.esdl.insurance.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PolicyService {
    private final PolicyRepository repository;
    public PolicyService(PolicyRepository repository) { this.repository = repository; }

    public List<Policy> browseActive() { return repository.findByActiveTrue(); }
    public Policy getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Policy not found: " + id));
    }
    public List<Policy> all() { return repository.findAll(); }

    @Transactional
    public Policy create(PolicyRequest request) {
        Policy p = new Policy(request.name(), request.description(), request.category(),
                request.premiumAmount(), request.coverageAmount());
        return repository.save(p);
    }
}
