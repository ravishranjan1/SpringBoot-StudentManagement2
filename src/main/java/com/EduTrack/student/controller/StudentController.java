package com.EduTrack.student.controller;

import com.EduTrack.student.dto.AddStudentDto;
import com.EduTrack.student.dto.StudentDto;
import com.EduTrack.student.exception.StudentNotFoundException;
import com.EduTrack.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody AddStudentDto student){
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id){
        try {
            StudentDto student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDto> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        try {
           StudentDto student = studentService.partialUpdate(id, updates);
           return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> fullUpdate(@PathVariable Long id, @RequestBody AddStudentDto updates){
        try {
            StudentDto student = studentService.fullUpdate(id, updates);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
