package com.example.aitalk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;  // 상태 코드 (예: "SUCCESS", "ERROR")
    private T result;  // 결과 데이터

    // ✅ 기존 에러 응답 (String만 허용) → 객체도 허용하도록 수정
    public static <T> Response<T> error(T result){
        return new Response<>("ERROR", result);
    }

    // ✅ 성공 응답
    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }
}
