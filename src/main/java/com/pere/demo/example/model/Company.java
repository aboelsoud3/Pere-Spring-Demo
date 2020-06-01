package com.pere.demo.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @Column(name = "hide_me")
    private Boolean hideMe;

    public Company(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
