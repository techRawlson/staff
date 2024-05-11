package com.Staff.Service;

import com.Staff.Entities.Subject;
import com.Staff.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject createSubject(String name) {
        Subject subject = new Subject(name);
        return subjectRepository.save(subject);
    }
    public boolean isSubjectExists(String name) {
        // Check if a subject with the given name exists in the database
        return subjectRepository.existsByName(name);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }
}

