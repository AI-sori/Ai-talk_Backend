package com.example.aitalk.domain.member;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Spring Security의 Principal 역할을 수행하기 위한 클래스
@AllArgsConstructor
@Getter
public class MemberDetails implements UserDetails {

	private final Member member;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 임시로 모든 사용자가 'ROLE_USER' 권한을 가진다고 가정
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}