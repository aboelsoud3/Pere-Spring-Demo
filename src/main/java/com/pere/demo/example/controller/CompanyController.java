package com.pere.demo.example.controller;

import com.pere.demo.example.exceptions.CompanyNotFoundException;
import com.pere.demo.example.model.Company;
import com.pere.demo.example.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyRepository repository;

    public CompanyController(CompanyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        try {
            List<Company> companies;
            Pageable paging = PageRequest.of(page, size);

            Page<Company> compPage;
            if (name != null) {
                compPage = repository.findByNameContainsIgnoreCase(name, paging);
            } else if (email != null) {
                compPage = repository.findByEmailContainsIgnoreCase(email, paging);
            } else {
                Pageable pagingSort = getPagingSort(sortBy, sortDirection, page, size);
                compPage = repository.findAll(pagingSort);
            }


            companies = compPage.getContent();

            if (companies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("companies", companies);
            response.put("currentPage", compPage.getNumber());
            response.put("totalItems", compPage.getTotalElements());
            response.put("totalPages", compPage.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/")
    ResponseEntity<Object> addNewCompany(@RequestBody Company newCompany) {
        try {
            newCompany = repository.save(newCompany);
            return new ResponseEntity<Object>(newCompany, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    Company getCompanyDetails(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @PutMapping("/{id}")
    Company updateCompany(@RequestBody Company newCompany, @PathVariable Long id) {

        return repository.findById(id)
                .map(Company -> {
                    Company.setName(newCompany.getName());
                    Company.setEmail(newCompany.getEmail());
                    Company.setAddress(newCompany.getAddress());
                    return repository.save(Company);
                })
                .orElseGet(() -> {
                    newCompany.setId(id);
                    return repository.save(newCompany);
                });
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteCompany(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Pageable getPagingSort(String sortBy, String direction, int page, int size) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(getSortDirection(direction), sortBy));
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        return pagingSort;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }


    }


}
