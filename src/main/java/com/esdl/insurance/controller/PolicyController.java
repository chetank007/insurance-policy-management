package com.esdl.insurance.controller;

import com.esdl.insurance.dto.PolicyRequest;
import com.esdl.insurance.model.Policy;
import com.esdl.insurance.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    private final PolicyService service;
    public PolicyController(PolicyService service) { this.service = service; }

    @GetMapping
    public List<Policy> browsePolicies() { return service.browseActive(); }

    @GetMapping("/{id}")
    public Policy getPolicy(@PathVariable Long id) { return service.getById(id); }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Policy> allPoliciesForAdmin() { return service.all(); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Policy createPolicy(@Valid @RequestBody PolicyRequest request) { return service.create(request); }
}
