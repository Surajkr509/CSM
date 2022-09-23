package com.nixi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;


@Entity
public class UserInfo implements Serializable { 
	

		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;

		private String uuid;

		@Column(name = "first_name")
		@NotEmpty(message = "*Please provide your first name")
		private String firstName;

		@NotEmpty(message = "*Please provide your email")
		private String email;

		private String password;
		
		private String confirmPassword;

		@OneToOne
		@JoinColumn(name = "role_id")
		private Role roleId;

		private String userName;
		private boolean status;
		private String loginTime;
		private boolean loginStatus;
		private String token;
		private String otp;
		private String createdAt;
		private String updatedAt;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getConfirmPassword() {
			return confirmPassword;
		}
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public boolean getStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public String getLoginTime() {
			return loginTime;
		}
		public void setLoginTime(String loginTime) {
			this.loginTime = loginTime;
		}
		public boolean isLoginStatus() {
			return loginStatus;
		}
		public void setLoginStatus(boolean loginStatus) {
			this.loginStatus = loginStatus;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getOtp() {
			return otp;
		}
		public void setOtp(String otp) {
			this.otp = otp;
		}
		public String getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}
		public String getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}
		public Role getRoleId() {
			return roleId;
		}
		public void setRoleId(Role roleId) {
			this.roleId = roleId;
		}
		
		
	}


