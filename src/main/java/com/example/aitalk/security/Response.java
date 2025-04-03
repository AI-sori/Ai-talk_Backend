package com.example.aitalk.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// 서버에서 클라이언트로 http 응답을 보낼때 필요한 상태 코드들을 Response라는 Generic 클래스로 정의해둔 것
// 없어도 기능 구현에 문제는 X
// 상태 코드는 여러 코드에서 쓰이는 경우가 많아 이를 하나의 클래스로 묶어서 편하게 관리하면 좋음
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> error(String resultCode){
        return new Response(resultCode, null);
    }

    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }
}
