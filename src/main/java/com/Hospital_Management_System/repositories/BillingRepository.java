package com.Hospital_Management_System.repositories;

import com.Hospital_Management_System.entities.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
}
