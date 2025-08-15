package com.seniclass.server.domain.lecture.controller;

import com.seniclass.server.domain.auth.domain.RequireAuth;
import com.seniclass.server.domain.auth.enums.UserRole;
import com.seniclass.server.domain.lecture.dto.request.LectureCreateRequest;
import com.seniclass.server.domain.lecture.dto.request.LectureUpdateRequest;
import com.seniclass.server.domain.lecture.dto.response.LectureBannerResponse;
import com.seniclass.server.domain.lecture.dto.response.LectureInfoWidgetResponse;
import com.seniclass.server.domain.lecture.dto.response.LectureResponse;
import com.seniclass.server.domain.lecture.dto.response.MyLectureStatusWidgetResponse;
import com.seniclass.server.domain.lecture.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Lecture", description = "강의 관리 API")
@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @Operation(
            summary = "강의 생성 (강사)",
            description = "강사가 새로운 강의를 생성합니다. TEACHER 권한 필요.",
            requestBody =
                    @RequestBody(
                            content =
                                    @Content(
                                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                            encoding = {
                                                @Encoding(
                                                        name = "request",
                                                        contentType =
                                                                MediaType.APPLICATION_JSON_VALUE),
                                                @Encoding(name = "file", contentType = "image/*")
                                            })))
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequireAuth(roles = {UserRole.TEACHER})
    public LectureResponse createLecture(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "강좌 생성에 필요한 정보") @Valid @RequestPart("request")
                    LectureCreateRequest request,
            @Parameter(
                            description = "강좌 썸네일 이미지 파일 (JPG, PNG, GIF 등)",
                            schema =
                                    @Schema(
                                            type = "string",
                                            format = "binary",
                                            description = "이미지 파일"))
                    @RequestPart("file")
                    MultipartFile file) {
        return lectureService.createLecture(userId, request, file);
    }

    @Operation(summary = "강의 단일 조회 (강사, 수강생)", description = "강의 ID로 특정 강의의 상세 정보를 조회합니다.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "강의 조회 성공"),
                @ApiResponse(responseCode = "401", description = "인증 실패 - 토큰이 유효하지 않음"),
                @ApiResponse(
                        responseCode = "403",
                        description = "권한 없음 - TEACHER 또는 STUDENT 권한 필요"),
                @ApiResponse(responseCode = "404", description = "강의를 찾을 수 없음 (LECTURE_NOT_FOUND)"),
                @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            })
    @GetMapping("/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER, UserRole.STUDENT})
    public LectureResponse getLecture(
            @Parameter(description = "조회할 강의 ID", example = "1") @PathVariable Long lectureId) {
        return lectureService.getLecture(lectureId);
    }

    @Operation(
            summary = "강의 수정 (강사)",
            description = "강사가 본인의 강의 정보를 수정합니다. TEACHER 권한 필요.",
            requestBody =
                    @RequestBody(
                            content =
                                    @Content(
                                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                            encoding = {
                                                @Encoding(
                                                        name = "request",
                                                        contentType =
                                                                MediaType.APPLICATION_JSON_VALUE),
                                                @Encoding(name = "file", contentType = "image/*")
                                            })))
    @PutMapping(value = "/{lectureId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequireAuth(roles = {UserRole.TEACHER})
    public LectureResponse updateLecture(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "수정할 강의 ID") @PathVariable Long lectureId,
            @Parameter(description = "수정할 강의 정보") @Valid @RequestPart("request")
                    LectureUpdateRequest request,
            @Parameter(
                            description = "강좌 썸네일 이미지 파일 (JPG, PNG, GIF 등) - 선택사항",
                            schema =
                                    @Schema(
                                            type = "string",
                                            format = "binary",
                                            description = "이미지 파일"))
                    @RequestPart(value = "file", required = false)
                    MultipartFile file) {
        return lectureService.updateLecture(userId, lectureId, request, file);
    }

    @Operation(summary = "강의 삭제 (강사)", description = "강사가 본인의 강의를 삭제합니다. TEACHER 권한 필요.")
    @DeleteMapping("/{lectureId}")
    @RequireAuth(roles = {UserRole.TEACHER})
    public ResponseEntity<Void> deleteLecture(
            @Parameter(description = "삭제할 강의 ID") @PathVariable Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "강좌 상세 화면의 강좌 정보 사이드바 위젯 (학생)", description = "강좌의 강좌 정보 위젯 데이터를 받습니다.")
    @GetMapping("/{lectureId}/side-widget/lecture-info")
    public LectureInfoWidgetResponse getLectureInfoWidget(
            @Parameter(description = "조회할 강좌 ID") @PathVariable Long lectureId) {
        return lectureService.getLectureInfoWidget(lectureId);
    }

    @Operation(
            summary = "강좌 상세 화면의 나의 강의 현황 사이드바 위젯 (학생)",
            description = "수강중인 강좌의 나의 강의 현황 데이터를 받습니다.")
    @GetMapping("/{lectureId}/side-widget/my-lecture-status")
    @RequireAuth(roles = {UserRole.STUDENT})
    public MyLectureStatusWidgetResponse getMyLectureStatusWidget(
            @Parameter(hidden = true) @RequestAttribute("userId") Long userId,
            @Parameter(description = "조회할 강좌 ID") @PathVariable Long lectureId) {
        return lectureService.getMyLectureStatusWidget(userId, lectureId);
    }

    @Operation(summary = "강좌의 배너", description = "강좌의 대략적인 정보를 포함하여 이미지 링크를 포함하는 배너 정보를 받습니다.")
    @GetMapping("/{lectureId}/banner")
    public LectureBannerResponse getLectureBanner(
            @Parameter(description = "조회할 강좌 ID") @PathVariable Long lectureId) {
        return lectureService.getLectureBanner(lectureId);
    }
}
