package com.school.student_service.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phone")
    private String phone;
    

    @JsonProperty("studentClass")
    private String studentClass;

    @JsonProperty("rollNumber")
    private Integer rollNumber;

    @JsonProperty("status")
    private Boolean status;
}
