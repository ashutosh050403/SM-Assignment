package com.ashutosh.smassignment.repository;

import com.ashutosh.smassignment.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, String> {
    List<Allocation> findByUserId(String userId);
    List<Allocation> findByOrderId(String orderId);
}
