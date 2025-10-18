package com.example.aitalk.domain.member;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.aitalk.api.exception.BusinessException;
import com.example.aitalk.api.exception.ErrorCode;
import com.example.aitalk.domain.member.dto.MemberJoinRequestDTO;
import com.example.aitalk.domain.member.dto.MemberLoginRequestDTO;
import com.example.aitalk.domain.member.dto.MemberProfileResponseDTO;
import com.example.aitalk.domain.member.dto.MemberProfileUpdateRequestDTO;
import com.example.aitalk.infra.s3.S3Uploader;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final S3Uploader s3Uploader;

	// 회원가입
	public void join(MemberJoinRequestDTO memberInfo) throws IOException {

		if (memberRepository.findMemberByEmail(memberInfo.getEmail()).isPresent()) {
			throw new BusinessException(ErrorCode.ALREADY_SIGNED_UP);
		}

		String imageUrl = null;
		try {
			if (memberInfo.getProfileImage() != null && !memberInfo.getProfileImage().isEmpty()) {
				imageUrl = s3Uploader.upload(memberInfo.getProfileImage(), "profile-images");
			}
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED);
		}

		Member member = Member.builder()
			.email(memberInfo.getEmail())
			.password(bCryptPasswordEncoder.encode(memberInfo.getPassword()))
			.nickname(memberInfo.getNickname())
			.profileImage(imageUrl)
			.createdAt(LocalDateTime.now())
			.build();

		memberRepository.save(member);
	}

	// 로그인
	public Member login(MemberLoginRequestDTO memberLoginRequestDTO) {

		Member member = memberRepository.findMemberByEmail(memberLoginRequestDTO.getEmail())
			.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

		if (!bCryptPasswordEncoder.matches(memberLoginRequestDTO.getPassword(), member.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}

		return member;
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
}
