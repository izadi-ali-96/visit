package com.project.visit.service.impl;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.exception.UserException;
import com.project.visit.model.Comment;
import com.project.visit.repository.CommentRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.service.CommentService;
import com.project.visit.service.model.CommentServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    @Override
    public List<CommentServiceModel> getComments(String doctorId) {
        var converter = new DateConverter();
        var comments = repository.findAllByDoctorId(doctorId);
        return comments.stream().map(c -> {
            var user = userRepository.findByUserId(c.getUserId()).orElseThrow(() -> new UserException(ResponseResult.USER_NOT_FOUND));
            var time = LocalDateTime.ofInstant(Instant.ofEpochSecond(c.getCreationDate()), ZoneId.of(ZoneOffset.UTC.getId()));

            var jalaliDate = converter.gregorianToJalali(time.getYear(), time.getMonth(), time.getDayOfMonth());
            var persianTime = jalaliDate.format(new JalaliDateFormatter("yyyy M dd", JalaliDateFormatter.FORMAT_IN_PERSIAN));

            return new CommentServiceModel(user.getFullName(), c.getComment(), persianTime);
        }).toList();
    }

    @Override
    public void addComment(String description, String userId, String doctorId) {
        var doctor = doctorRepository.findByDoctorId(doctorId).orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        var user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(ResponseResult.DOCTOR_NOT_FOUND));

        if (doctor.getUserId().equals(userId)) {
            throw new DoctorException(ResponseResult.INVALID_COMMENT_OWNER);
        }

        var comment = new Comment();
        comment.setComment(description);
        comment.setDoctorId(doctor.getDoctorId());
        comment.setUserId(user.getUserId());
        comment.setCreationDate(Instant.now().getEpochSecond());
        repository.save(comment);
    }
}
