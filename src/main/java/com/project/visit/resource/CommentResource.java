package com.project.visit.resource;

import com.project.visit.resource.filter.RequestContextInterceptor;
import com.project.visit.resource.mapper.CommentResourceMapper;
import com.project.visit.resource.request.CreateCommentRequest;
import com.project.visit.resource.response.CommentResponse;
import com.project.visit.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentResource {

    private final CommentService service;

    private final CommentResourceMapper mapper;

    @GetMapping("/{doctorId}")
    ResponseEntity<CommentResponse> getComment(@PathVariable("doctorId") String doctorId) {
        return ResponseEntity.ok(mapper.toCommentResponse(service.getComments(doctorId)));
    }

    @PostMapping("/add")
    ResponseEntity<Void> setComment(@RequestBody CreateCommentRequest request) {
        var context = RequestContextInterceptor.getCurrentContext();
        service.addComment(request.getDescription(), context.getUserId(), request.getDoctorId());
        return ResponseEntity.ok().build();
    }


}
