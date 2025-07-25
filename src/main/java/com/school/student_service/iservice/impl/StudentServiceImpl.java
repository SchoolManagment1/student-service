package com.school.student_service.iservice.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.school.student_service.dto.Attendence;
import com.school.student_service.dto.StudentRequest;
import com.school.student_service.dto.StudentResponse;
import com.school.student_service.entity.Student;
import com.school.student_service.iservice.StudentService;
import com.school.student_service.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;

    @Override
    public StudentResponse createStudent(StudentRequest request, String email) {
        Student student=new Student();
        BeanUtils.copyProperties(request,student);
        student.setCreatedByEmail(email);
        studentRepo.save(student);
        log.info("Student created for user {}", email);
        return mapToResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents(String email) {
        return studentRepo.findByCreatedByEmail(email).stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public StudentResponse getStudent(Long id, String email) {
        Student student = studentRepo.findById(id)
                .filter(s -> s.getCreatedByEmail().equals(email))
                .orElseThrow(() -> new AccessDeniedException("Not authorized"));
        return mapToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request, String email) {
        Student student = studentRepo.findById(id)
                .filter(s -> s.getCreatedByEmail().equals(email))
                .orElseThrow(() -> new AccessDeniedException("Not authorized"));
        BeanUtils.copyProperties(request, student);
        studentRepo.save(student);

        return mapToResponse(student);
    }

    @Override
    public void deleteStudent(Long id, String email) {
        Student student = studentRepo.findById(id)
                .filter(s -> s.getCreatedByEmail().equals(email))
                .orElseThrow(() -> new AccessDeniedException("Not authorized"));
        studentRepo.delete(student);
    }

    private StudentResponse mapToResponse(Student student) {
    	StudentResponse response=new StudentResponse();
    	BeanUtils.copyProperties(student, response);
        return response; 
    }

	@Override
	public List<StudentResponse> getAllStudents() {
		return studentRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
		
	}
}
