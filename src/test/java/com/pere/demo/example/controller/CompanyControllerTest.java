package com.pere.demo.example.controller;

import com.pere.demo.example.model.Company;
import com.pere.demo.example.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyControllerTest {
    @InjectMocks
    CompanyController companyController;

    @Mock
    CompanyRepository companyRepository;

    @BeforeEach
    private void initialize() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testGetAll() {
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company("name1", "email1@gmail.com", "address 1"));
        companyList.add(new Company("name2", "email2@gmail.com", "address 2"));
        companyList.add(new Company("name3", "email3@gmail.com", "address 3"));

        Page<Company> pagedCompanies = new PageImpl(companyList);

        when(companyRepository.findAll(any(Pageable.class))).thenReturn(pagedCompanies);
        ResponseEntity<Map<String, Object>> responseEntity = companyController.getAll(null, null, 0, 20, "id", "desc");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(4);

    }

    @Test
    public void testAddNewCompany() {

        Company newCompany = new Company("company name", "email@domain.com", "new company address");
        when(companyRepository.save(any(Company.class))).thenReturn(newCompany);
        ResponseEntity<Object> responseEntity = companyController.addNewCompany(newCompany);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(((Company) responseEntity.getBody()).getName()).isEqualTo(newCompany.getName());
    }

    @Test
    public void testGetCompanyDetails() {
        Company aCompany = new Company("company name", "email@domain.com", "new company address");
        aCompany.setId(1l);

        when(companyRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(aCompany));
        Company returnedCompany = companyController.getCompanyDetails(1l);
        assertThat((returnedCompany.getId()).equals(1l));
    }

    @Test
    public void testUpdateCompany() {
        Company aCompany = new Company("company name", "email@domain.com", "new company address");
        aCompany.setId(1l);

        Company newCompany = new Company("new company name", "newEmail@domain.com", "new company address");

        when(companyRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(aCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(newCompany);
        Company updatedCompany = companyController.updateCompany(newCompany, 1l);

        assertThat((updatedCompany.getName()).equals(newCompany.getName()));
        assertThat((updatedCompany.getEmail()).equals(newCompany.getEmail()));
    }

    @Test
    public void testDeleteCompany() {
        ResponseEntity<Object> responseEntity = companyController.deleteCompany(1l);
        verify(companyRepository).deleteById(any());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

}
