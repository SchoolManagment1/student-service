package com.school.student_service.iservice;

import java.util.List;

import com.school.student_service.dto.StudentRequest;
import com.school.student_service.dto.StudentResponse;

public interface StudentService {
    StudentResponse createStudent(StudentRequest request, String email);
    List<StudentResponse> getAllStudents(String email);
    List<StudentResponse> getAllStudents();
    StudentResponse getStudent(Long id, String email);
    StudentResponse updateStudent(Long id, StudentRequest request, String email);
    void deleteStudent(Long id, String email);
}
