package com.asrarcode.elasticsearchapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "users")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_Id")
    private String customer_id;

    @Transient
    private Long id;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Transient
    private String gender;

    @Column(name = "Company")
    private String jobTitle;

    @Column(name = "Phone_1")
    private String phone;

    @Transient
    private Integer size;
}
