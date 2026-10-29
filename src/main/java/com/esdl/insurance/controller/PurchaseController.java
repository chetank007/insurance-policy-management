package com.esdl.insurance.controller;

import com.esdl.insurance.model.Purchase;
import com.esdl.insurance.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService service;
    public PurchaseController(PurchaseService service) { this.service = service; }

    @PostMapping("/{policyId}")
    public Purchase purchase(@PathVariable Long policyId, Principal principal) {
        return service.purchase(policyId, principal.getName());
    }

    @GetMapping("/mine")
    public List<Purchase> myPurchases(Principal principal) {
        return service.myPurchases(principal.getName());
    }
}
