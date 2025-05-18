package com.example.aitalk.member;

import com.example.aitalk.s3.S3Uploader;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 자동 생성 (의존성 주입)
@Transactional // 트랜잭션 처리 (데이터 일관성 보장)
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Uploader s3Uploader;

    /* 회원가입 */
    public MemberJoinResponseDTO join(MemberJoinRequestDTO memberJoinRequestDTO) throws IOException {

        MemberJoinResponseDTO.MemberJoinResponseDTOBuilder responseBuilder = MemberJoinResponseDTO.builder();

        if (memberRepository.findMemberByEmail(memberJoinRequestDTO.getEmail()).isPresent()) {
            return responseBuilder
                    .statusCode(401)
                    .message("이미 가입된 회원입니다.")
                    .build();
        }

        String imageUrl = null;
        if (memberJoinRequestDTO.getProfileImage() != null && !memberJoinRequestDTO.getProfileImage().isEmpty()) {
            imageUrl = s3Uploader.upload(memberJoinRequestDTO.getProfileImage(), "profile-images");
        }

        Member member = Member.builder()
                .email(memberJoinRequestDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(memberJoinRequestDTO.getPassword()))
                .nickname(memberJoinRequestDTO.getNickname())
                .profileImage(imageUrl)  // 업로드된 이미지 URL 저장
                .createdAt(LocalDateTime.now())
                .build();

        memberRepository.save(member);

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

        // 넘겨받은 MemberLoginRequestDTO의 Email parameter를 이용하여 Member 정보를 가져온다.
        // 이메일로 회원 정보 조회
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(memberLoginRequestDTO.getEmail());
        if(optionalMember.isEmpty()){ // Member 정보가 없으면
            return responseBuilder
                    .statusCode(401)
                    .message("회원 정보를 찾을 수 없습니다.")
                    .build();
        }

        Member member = (Member) optionalMember.get(); // Optional에서 Member 객체 추출

        // 조건문을 통해 가져온 Member 정보와 RequestDTO에 담긴 Member의 email, password와 일치하는지 확인
        if(!bCryptPasswordEncoder.matches(memberLoginRequestDTO.getPassword(), member.getPassword())){
            return responseBuilder
                    .statusCode(401)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();
        }
        // 이때 password는 RequestDTO에 가져온 비밀번호를 SecurityConfig 클래스에 Bean 객체로 등록되어
        // 주입된 BCryptPasswordEncoder 클래스의 matches() 메서드를 사용해서 일치하는지 여부를 확인

        return responseBuilder
                .statusCode(200)
                .message("로그인 성공")
                .email(member.getEmail())
                .build();
    }

    public Member findByEmail(String email) {
        return (Member) memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + email));
    }
    // 성공을 의미하는 http 상태코드 200, 로그인 성공 메시지, 로그인 id를 MemberResponse 객체에 담아 
    // MemberRestController에 돌려줌


    // 프로필 조회
    public MemberProfileResponseDTO getProfile(Member member) {
        return MemberProfileResponseDTO.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }

    // 프로필 수정 (MultipartFile 버전)
    @Transactional
    public void updateProfile(Member member, MemberProfileUpdateRequestDTO requestDTO) throws IOException {
        member.setNickname(requestDTO.getNickname());

        MultipartFile imageFile = requestDTO.getProfileImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3Uploader.upload(imageFile, "profile-images");
            member.setProfileImage(imageUrl);
        }
    }
}
