package com.nixi.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nixi.model.Role;
import com.nixi.model.UserInfo;
import com.nixi.repository.RoleRepository;
import com.nixi.repository.UserInfoRepository;
import com.nixi.utilis.Constants;

@Service
public class UserInfoService implements UserDetailsService {
	
	@Autowired
     UserInfoRepository userInfoRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo data = userInfoRepository.findByEmail(username);
		return UserInfoDetailsImpl.buildUserWithAuth(data);
	}

	public Object signupUser(UserInfo userInfo) {
		Role role = roleRepository.findByRoleName("USER");
		if (role != null) {
			userInfo.setRoleId(role);
			userInfo.setUserName(userInfo.getEmail());
			userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
			userInfo.setCreatedAt(Constants.getDateAndTime());
			userInfo.setUpdatedAt(Constants.getDateAndTime());
			userInfo.setStatus(true);// It will be change & verified by Admin
			UserInfo userData = userInfoRepository.save(userInfo);
			userData.setUuid(Constants.USER + userData.getId());
			userInfoRepository.save(userData);
			HashMap<String, Object> Data = new HashMap<>();
			Data.put("username", userData.getUserName());
			Data.put("Password", userData.getPassword());
			return userData;
		} else {
			throw new RuntimeException("User Role is not exist");
		}
	}

	public Object forgotPassword(UserInfo user) {
		String otp = Constants.genrateOTP();
		user.setOtp(otp);
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("userId", user.getUuid());
		hashMap.put("otp", otp);
		userInfoRepository.save(user);
		return hashMap;
	}

	public void changePassword(UserInfo user, String newPassword) {
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		user.setConfirmPassword(bCryptPasswordEncoder.encode(newPassword));
		userInfoRepository.save(user);
	}

}
