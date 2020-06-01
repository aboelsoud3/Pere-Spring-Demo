package com.pere.demo.example.repository;

import com.pere.demo.example.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Page<Company> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Company> findByEmailContainsIgnoreCase(String email, Pageable pageable);

}