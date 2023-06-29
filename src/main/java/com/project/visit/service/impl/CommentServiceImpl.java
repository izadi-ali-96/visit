package com.project.visit.service.impl;

import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.exception.UserException;
import com.project.visit.model.Comment;
import com.project.visit.repository.CommentRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    @Override
    public List<Comment> getComments(String doctorId) {
        return repository.findAllByDoctorId(doctorId);
    }

    @Override
    public void addComment(String description, String userId, String doctorId) {
        var doctor = doctorRepository.findByDoctorId(doctorId).orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        var user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(ResponseResult.DOCTOR_NOT_FOUND));

        var comment = new Comment();
        comment.setComment(description);
        comment.setDoctorId(doctor.getDoctorId());
        comment.setUserId(user.getUserId());
        repository.save(comment);
    }
}
