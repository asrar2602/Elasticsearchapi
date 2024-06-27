package com.asrarcode.elasticsearchapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class USEmployee implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zip;
    private String phone;
    private String phone2;
    private String email;
    private String web;

}
