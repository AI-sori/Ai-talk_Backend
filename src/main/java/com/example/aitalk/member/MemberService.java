package com.example.aitalk.member;

import com.example.aitalk.member.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 자동 생성 (의존성 주입)
@Transactional // 트랜잭션 처리 (데이터 일관성 보장)
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /* 회원가입 */
    public MemberJoinResponseDTO join(MemberJoinRequestDTO memberJoinRequestDTO){

        // 빌더 패턴을 사용하여 응답 객체 초기화
        MemberJoinResponseDTO.MemberJoinResponseDTOBuilder responseBuilder = MemberJoinResponseDTO.builder();

        if(memberRepository.findMemberByEmail(memberJoinRequestDTO.getEmail()).isPresent()){
            return responseBuilder
                    .statusCode(401)
                    .message("이미 가입된 회원입니다.")
                    .build();
        }

        // 새로운 회원 정보 생성 및 저장
        Member member = Member.builder()
                .email(memberJoinRequestDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(memberJoinRequestDTO.getPassword()))
                .nickname(memberJoinRequestDTO.getNickname())
                .profileImage(memberJoinRequestDTO.getProfileImage())
                .createdAt(LocalDateTime.now())
                .build();

        memberRepository.save(member); // DB에 회원 정보 저장

        // 회원가입 성공 응답 반환
        return responseBuilder
                .statusCode(200)
                .email(member.getEmail())
                .message("회원가입 성공")
                .build();
    }

    /* 로그인 */
    public MemberLoginResponseDTO login(MemberLoginRequestDTO memberLoginRequestDTO) {
        // 빌더 패턴을 사용하여 응답 객체 초기화
        MemberLoginResponseDTO.MemberLoginResponseDTOBuilder responseBuilder = MemberLoginResponseDTO.builder();

        // 이메일로 회원 정보 조회
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(memberLoginRequestDTO.getEmail());
        if(optionalMember.isEmpty()){
            return responseBuilder
                    .statusCode(401)
                    .message("회원 정보를 찾을 수 없습니다.")
                    .build();
        }

        Member member = (Member) optionalMember.get(); // Optional에서 Member 객체 추출

        if(!bCryptPasswordEncoder.matches(memberLoginRequestDTO.getPassword(), member.getPassword())){
            return responseBuilder
                    .statusCode(401)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }

        return responseBuilder
                .statusCode(200)
                .message("로그인 성공")
                .email(member.getEmail())
                .build();
    }
}
