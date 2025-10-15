package com.example.aitalk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder // 빌더 패턴으로 객체 생성 간편화 추천
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

	private int code;
	private String msg;
	private T data;

	// 실패 응답용 생성자 (data 없음)
	public CommonResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
