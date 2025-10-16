package com.example.aitalk.api.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400 Bad Request 계열 (일반적인 오류)
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON-400", "잘못된 요청입니다. 요청 형식을 확인해주세요."),

	// 401 Unauthorized 계열 (인증 오류)
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH-401", "인증 정보가 유효하지 않습니다. 로그인이 필요합니다."),

	// 403 Forbidden 계열 (권한/인가 오류)
	NO_PERMISSION(HttpStatus.FORBIDDEN, "CMT-403", "해당 작업을 수행할 권한이 없습니다."),

	// 404 Not Found 계열 (자원 없음)
	NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-404", "지원하지 않는 URL입니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MBR-404", "존재하지 않는 사용자 정보입니다."),
	NOT_FOUND_POST(HttpStatus.NOT_FOUND, "PST-404", "존재하지 않는 게시글입니다."),
	NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "CMT-404", "존재하지 않는 댓글입니다."),

	// 500 Internal Server Error 계열
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON-405", "잘못된 HTTP method 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "서버 내부 오류입니다."),

	/* 도메인별 */
	// Like
	ALREADY_LIKED(HttpStatus.BAD_REQUEST, "LIKE-400", "이미 좋아요를 누른 게시글입니다."),
	NOT_LIKED(HttpStatus.BAD_REQUEST, "LIKE-401", "좋아요를 누르지 않은 게시글입니다."),

	// Member
	ALREADY_SIGNED_UP(HttpStatus.CONFLICT, "MBR-409", "이미 등록된 이메일 주소입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH-401", "비밀번호가 일치하지 않습니다."),
	IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "INF-500", "프로필 이미지 업로드 중 오류가 발생했습니다."),

	// QnA
	NOT_FOUND_QNA(HttpStatus.NOT_FOUND, "QNA-404", "해당 문의사항을 찾을 수 없습니다."),
	ALREADY_REPLIED(HttpStatus.BAD_REQUEST, "QNA-400", "이미 답변이 완료된 문의사항은 수정/삭제할 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String msg;

}
