package com.esdl.insurance.controller;

import com.esdl.insurance.dto.ReviewRequest;
import com.esdl.insurance.model.Claim;
import com.esdl.insurance.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    private final ClaimService service;
    public ClaimController(ClaimService service) { this.service = service; }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Claim submitClaim(@RequestParam Long purchaseId,
                             @RequestParam String incidentDetails,
                             @RequestParam BigDecimal claimAmount,
                             @RequestPart("document") MultipartFile document,
                             Principal principal) {
        return service.submit(purchaseId, principal.getName(), incidentDetails, claimAmount, document);
    }

    @GetMapping("/mine")
    public List<Claim> myClaims(Principal principal) { return service.myClaims(principal.getName()); }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Claim> allClaimsForAdmin() { return service.allClaims(); }

    @PatchMapping("/{id}/review")
    @PreAuthorize("hasRole('ADMIN')")
    public Claim reviewClaim(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        return service.review(id, request);
    }
}
