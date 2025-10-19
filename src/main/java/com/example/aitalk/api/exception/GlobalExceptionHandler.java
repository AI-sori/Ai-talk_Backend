package com.example.aitalk.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.aitalk.api.dto.CommonResponse;
import com.example.aitalk.global.util.ResponseUtil;

@RestControllerAdvice
class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// [비즈니스 로직 오류 처리] 개발자가 의도적으로 던진 비즈니스 규칙 위반(예: 없는 회원 접근, 재고 부족)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<CommonResponse<Void>> handleBusinessException(BusinessException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		logger.error("BusinessException: {}", errorCode.getMsg(), ex);
		return ResponseUtil.fail(errorCode.getHttpStatus(), errorCode.getMsg());
	}

	// [유효성 검사 오류 처리] @Valid DTO 필드 검증 실패 (400 Bad Request)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CommonResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {

		String errorMsg;

		FieldError fieldError = ex.getBindingResult().getFieldError();

		if (fieldError != null) {
			if ("profileImage".equals(fieldError.getField()) && "typeMismatch".equals(fieldError.getCode())) {
				errorMsg = ErrorCode.INVALID_MULTIPART_REQUEST.getMsg();
				return ResponseUtil.fail(HttpStatus.BAD_REQUEST, errorMsg);
			}
			errorMsg = fieldError.getDefaultMessage();
		} else {
			errorMsg = ErrorCode.BAD_REQUEST.getMsg();
		}

		logger.error("ValidationException: {}", errorMsg, ex);
		return ResponseUtil.fail(HttpStatus.BAD_REQUEST, errorMsg);
	}

	// [요청 본문 파싱 오류 처리] JSON 문법 오류 또는 타입 불일치 (400 Bad Request)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<CommonResponse<Void>> handleHttpMessageNotReadableException(
		HttpMessageNotReadableException ex) {
		logger.error("HttpMessageNotReadableException: {}", ex.getMessage(), ex);
		return ResponseUtil.fail(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getMsg());
	}

	// [인자 타입 불일치 오류 처리] URL/쿼리 파라미터 타입이 메서드 인자와 불일치 (400 Bad Request)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<CommonResponse<Void>> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		logger.error("MethodArgumentTypeMismatchException: {}", ex.getMessage(), ex);
		return ResponseUtil.fail(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST.getMsg());
	}

	// 존재하지 않는 URL 경로 요청 (404 Not Found)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<CommonResponse<Void>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		logger.error("NoHandlerFoundException: {}", ex.getMessage(), ex);
		return ResponseUtil.fail(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.getMsg());
	}

	// [HTTP 메서드 오류 처리] 정의되지 않은 HTTP 메서드 사용 (405 Method Not Allowed)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<CommonResponse<Void>> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		logger.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage(), ex);
		return ResponseUtil.fail(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.METHOD_NOT_ALLOWED.getMsg());
	}

	// [최후의 오류 처리] 위에서 처리되지 않은 예상치 못한 모든 런타임 예외 (500 Internal Server Error)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<Void>> handleUnexpectedException(Exception ex) {
		logger.error("UnexpectedException occurred: {}", ex.getMessage(), ex);
		return ResponseUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMsg());
	}
}
