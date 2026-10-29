package com.esdl.insurance.repository;

import com.esdl.insurance.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByUsernameOrderBySubmittedAtDesc(String username);
    List<Claim> findAllByOrderBySubmittedAtDesc();
}
