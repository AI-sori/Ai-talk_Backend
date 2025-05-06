package com.example.aitalk.security;

import com.example.aitalk.mypage.QnaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
// 서버에서 클라이언트로 http 응답을 보낼때 필요한 상태 코드들을 Response라는 Generic 클래스로 정의해둔 것
// 없어도 기능 구현에 문제는 X
// 상태 코드는 여러 코드에서 쓰이는 경우가 많아 이를 하나의 클래스로 묶어서 편하게 관리하면 좋음
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
