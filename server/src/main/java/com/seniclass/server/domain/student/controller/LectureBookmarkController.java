package com.seniclass.server.domain.student.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.student.dto.LectureBookmarkRequest;
import com.seniclass.server.domain.student.dto.LectureBookmarkResponse;
import com.seniclass.server.domain.student.service.LectureBookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Lecture Bookmark", description = "학생 강의 북마크 관리 API")
@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class LectureBookmarkController {

    private final LectureBookmarkService lectureBookmarkService;

    @Operation(summary = "내 북마크한 강의 목록 조회 (수강생)", description = "현재 로그인한 학생의 북마크한 강의 목록을 조회합니다.")
    @GetMapping
    @RequireAuth(roles = {UserRole.STUDENT})
    public Page<LectureBookmarkResponse> getMyBookmarks(
            @PageableDefault(size = 20, sort = "createdDt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return lectureBookmarkService.getCurrentStudentBookmarks(pageable);
    }

    @Operation(summary = "강의 북마크 추가 (수강생)", description = "강의를 북마크에 추가합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequireAuth(roles = {UserRole.STUDENT})
    public LectureBookmarkResponse addBookmark(@Valid @RequestBody LectureBookmarkRequest request) {
        return lectureBookmarkService.addBookmark(request);
    }

    @Operation(summary = "강의 북마크 삭제 (수강생)", description = "북마크를 삭제합니다.")
    @DeleteMapping("/{bookmarkId}")
    @ResponseStatus(HttpStatus.OK)
    @RequireAuth(roles = {UserRole.STUDENT})
    public void removeBookmark(@Parameter(description = "북마크 ID") @PathVariable Long bookmarkId) {
        lectureBookmarkService.removeBookmark(bookmarkId);
    }

    @Operation(summary = "강의로 북마크 삭제 (수강생)", description = "강의 ID로 북마크를 삭제합니다.")
    @DeleteMapping("/lecture/{lectureId}")
    @ResponseStatus(HttpStatus.OK)
    @RequireAuth(roles = {UserRole.STUDENT})
    public void removeBookmarkByLecture(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        lectureBookmarkService.removeBookmarkByLectureId(lectureId);
    }

    @Operation(summary = "강의 북마크 여부 확인 (수강생)", description = "특정 강의가 북마크되어 있는지 확인합니다.")
    @GetMapping("/check/{lectureId}")
    @RequireAuth(roles = {UserRole.STUDENT})
    public Map<String, Boolean> checkBookmark(
            @Parameter(description = "강의 ID") @PathVariable Long lectureId) {
        return Map.of("isBookmarked", lectureBookmarkService.isBookmarked(lectureId));
    }
}
