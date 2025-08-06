package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;
import com.seniclass.server.domain.lecture.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping
    @RequireAuth(roles = {UserRole.TEACHER})
    public ResponseEntity<Void> createLecture(@Valid @RequestBody LectureCreateRequest request) {
        request.validateDateOrder();
        lectureService.createLecture(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{lectureId}")
    public LectureResponse getLecture(@PathVariable Long lectureId) {
        return LectureResponse.from(lectureService.getLecture(lectureId));
    }

    @PutMapping("/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public void updateLecture(@PathVariable Long lectureId, @RequestBody LectureUpdateRequest request) {
        lectureService.updateLecture(lectureId, request);
    }

    @DeleteMapping("/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public ResponseEntity<Void> deleteLecture(@PathVariable Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return ResponseEntity.ok().build();
    }
}
