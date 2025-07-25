package com.school.student_service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("studentClass")
    private String studentClass;
    
    @JsonProperty("phone")
    private String phone;
    

    @JsonProperty("rollNumber")
    private Integer rollNumber;

    @JsonProperty("status")
    private Boolean status;
    
    private String createdByEmail; 
}
