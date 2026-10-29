package com.esdl.insurance.repository;

import com.esdl.insurance.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByActiveTrue();
}
