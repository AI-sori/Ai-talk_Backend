package com.example.aitalk.domain.member;

import com.example.aitalk.domain.member.dto.join.MemberJoinRequestDTO;
import com.example.aitalk.domain.member.dto.join.MemberJoinResponseDTO;
import com.example.aitalk.domain.member.dto.login.MemberLoginRequestDTO;
import com.example.aitalk.domain.member.dto.login.MemberLoginResponseDTO;
import com.example.aitalk.domain.member.dto.profile.MemberProfileResponseDTO;
import com.example.aitalk.domain.member.dto.profile.MemberProfileUpdateRequestDTO;
import com.example.aitalk.infra.s3.S3Uploader;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3Uploader s3Uploader;

    // 회원가입
    public MemberJoinResponseDTO join(MemberJoinRequestDTO memberJoinRequestDTO) throws IOException {

        if (memberRepository.findMemberByEmail(memberJoinRequestDTO.getEmail()).isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_SIGNED_UP);
        }

        String imageUrl = null;
        try {
            if (memberJoinRequestDTO.getProfileImage() != null && !memberJoinRequestDTO.getProfileImage().isEmpty()) {
                imageUrl = s3Uploader.upload(memberJoinRequestDTO.getProfileImage(), "profile-images");
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        Member member = Member.builder()
            .email(memberJoinRequestDTO.getEmail())
            .password(bCryptPasswordEncoder.encode(memberJoinRequestDTO.getPassword()))
            .nickname(memberJoinRequestDTO.getNickname())
            .profileImage(imageUrl)
            .createdAt(LocalDateTime.now())
            .build();

        memberRepository.save(member);

        return MemberJoinResponseDTO.builder()
            .email(member.getEmail())
            .message("회원가입 성공")
            .build();
    }

    // 로그인
    public MemberLoginResponseDTO login(MemberLoginRequestDTO memberLoginRequestDTO) {

        Member member = getMemberByEmailOrThrow(memberLoginRequestDTO.getEmail());

        if(!bCryptPasswordEncoder.matches(memberLoginRequestDTO.getPassword(), member.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return MemberLoginResponseDTO.builder()
            .message("로그인 성공")
            .email(member.getEmail())
            .userId(member.getId())
            .build();
    }

    public Member findByEmail(String email) {
        return getMemberByEmailOrThrow(email);
    }

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
            try {
                String imageUrl = s3Uploader.upload(imageFile, "profile-images");
                member.setProfileImage(imageUrl);
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED);
            }
        }
    }

    // 회원 탈퇴
    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    // Email로 Member 객체를 찾거나 예외를 던지기
    private Member getMemberByEmailOrThrow(String email) {
        return memberRepository.findMemberByEmail(email)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
