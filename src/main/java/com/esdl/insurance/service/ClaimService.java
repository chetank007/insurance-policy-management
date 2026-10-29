package com.esdl.insurance.service;

import com.esdl.insurance.dto.ReviewRequest;
import com.esdl.insurance.model.*;
import com.esdl.insurance.repository.ClaimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ClaimService {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED_TYPES = Set.of("application/pdf", "image/jpeg", "image/png");
    private final ClaimRepository claims;
    private final PurchaseService purchaseService;
    private final Path uploadDir = Paths.get("uploads", "claims").toAbsolutePath().normalize();

    public ClaimService(ClaimRepository claims, PurchaseService purchaseService) {
        this.claims = claims; this.purchaseService = purchaseService;
    }

    @Transactional
    public Claim submit(Long purchaseId, String username, String details, BigDecimal amount, MultipartFile document) {
        if (details == null || details.isBlank()) throw new IllegalArgumentException("Incident details are required.");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Claim amount must be greater than zero.");
        if (document == null || document.isEmpty()) throw new IllegalArgumentException("A supporting document is required.");
        if (document.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("Document must be 5 MB or smaller.");
        if (document.getContentType() == null || !ALLOWED_TYPES.contains(document.getContentType())) throw new IllegalArgumentException("Only PDF, JPG, and PNG documents are allowed.");

        Purchase purchase = purchaseService.ownedPurchase(purchaseId, username);
        if (amount.compareTo(purchase.getPolicy().getCoverageAmount()) > 0)
            throw new IllegalArgumentException("Claim amount cannot exceed policy coverage.");

        try {
            Files.createDirectories(uploadDir);
            String original = document.getOriginalFilename() == null ? "document" : Paths.get(document.getOriginalFilename()).getFileName().toString();
            String storedName = UUID.randomUUID() + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path destination = uploadDir.resolve(storedName).normalize();
            if (!destination.startsWith(uploadDir)) throw new IllegalArgumentException("Invalid document filename.");
            Files.copy(document.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return claims.save(new Claim(purchase, username, details.trim(), amount, destination.toString()));
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save claim document.", ex);
        }
    }

    public List<Claim> myClaims(String username) { return claims.findByUsernameOrderBySubmittedAtDesc(username); }
    public List<Claim> allClaims() { return claims.findAllByOrderBySubmittedAtDesc(); }

    @Transactional
    public Claim review(Long id, ReviewRequest request) {
        Claim claim = claims.findById(id).orElseThrow(() -> new ResourceNotFoundException("Claim not found: " + id));
        if (claim.getStatus() == ClaimStatus.APPROVED || claim.getStatus() == ClaimStatus.REJECTED)
            throw new IllegalStateException("This claim has already been finalized.");
        String decision = request.decision().trim().toUpperCase();
        if (!decision.equals("APPROVED") && !decision.equals("REJECTED"))
            throw new IllegalArgumentException("Decision must be APPROVED or REJECTED.");
        claim.setStatus(ClaimStatus.valueOf(decision));
        claim.setAdminRemarks(request.remarks() == null ? "" : request.remarks().trim());
        claim.setReviewedAt(LocalDateTime.now());
        return claims.save(claim);
    }
}
