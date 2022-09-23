package com.nixi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nixi.model.UserInfo;

public class UserInfoDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String uuid;
	private String userName;
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean loginStatus;
	
	private UserInfoDetailsImpl(Long id, String uuid, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities, boolean loginStatus) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.loginStatus = loginStatus;
	}
	
	
	public static UserInfoDetailsImpl buildUserWithAuth(UserInfo user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRoleId().getRoleName()));
		return new UserInfoDetailsImpl(user.getId(), user.getUuid(), user.getUserName(), user.getEmail(),
				user.getPassword(), authorities, user.isLoginStatus());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public String getEmail() {
		return email;
	}
	
	public boolean isLoginStatus() {
		return loginStatus;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserInfoDetailsImpl user = (UserInfoDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

}
