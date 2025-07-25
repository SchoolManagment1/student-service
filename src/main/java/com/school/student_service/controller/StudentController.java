package com.school.student_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.student_service.dto.Attendence;
import com.school.student_service.dto.StudentRequest;
import com.school.student_service.dto.StudentResponse;
import com.school.student_service.entity.Student;
import com.school.student_service.iservice.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student API", description = "Operations for managing students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<List<StudentResponse>> getStudents(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(studentService.getAllStudents(email));
    }

    @PostMapping
    @Operation(summary = "Create a student")
    public ResponseEntity<?> createStudent(@RequestBody @Valid StudentRequest request, Authentication auth) {
        String email = auth.getName();
        return new ResponseEntity<>(studentService.createStudent(request, email), HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getallStudent(){
    	
    	return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(studentService.getStudent(id, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody StudentRequest request, Authentication auth) {
        return ResponseEntity.ok(studentService.updateStudent(id, request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id, Authentication auth) {
        studentService.deleteStudent(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
