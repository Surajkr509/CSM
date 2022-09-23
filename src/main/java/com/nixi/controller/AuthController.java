package com.nixi.controller;




import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nixi.bean.ChangePassword;
import com.nixi.bean.LoginRequest;
import com.nixi.bean.LoginResponse;
import com.nixi.bean.ResultDTO;
import com.nixi.model.UserInfo;
import com.nixi.service.UserInfoDetailsImpl;
import com.nixi.utilis.Constants;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends BaseController {
	
	@PostMapping("/signupUser")
	public ResponseEntity<?> signupUser(@ModelAttribute UserInfo userData) {
		System.err.println(" ::: AuthController.signupUser ::: ");
		ResultDTO<?> responsePacket = null;
		try {
			ArrayList<String> errorList = beanValidator.userSignupValidate(userData);
			if (errorList.size() != 0) {
				ResultDTO<ArrayList<String>> errorPacket = new ResultDTO<>(false, errorList, Constants.invalidData);
				return new ResponseEntity<>(errorPacket, HttpStatus.BAD_REQUEST);
			}
			if (userData.getPassword().equals(userData.getConfirmPassword())) {
			 
			if (userInfoRepository.existsByEmail(userData.getEmail())) {
				responsePacket = new ResultDTO<>(false, null, "User already exist with email");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				
				
			} else {
				responsePacket = new ResultDTO<>(true, userInfoService.signupUser(userData),Constants.requestSuccess);
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			}
			}else {
				responsePacket = new ResultDTO<>(false, "Password & Confirm Password must be same ");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket = new ResultDTO<>(false, null, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@ModelAttribute LoginRequest loginRequest) {
		System.err.println(" ::: AuthController.loginUser ::: ");
		ResultDTO<?> responsePacket = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserInfoDetailsImpl userDetails = (UserInfoDetailsImpl) authentication.getPrincipal();
			System.err.println(" ::: AuthController.loginUser2 ::: ");
			String jwt = jwtUtils.generateJwtToken(authentication);
			System.err.println(" ::: AuthController.loginUser3 ::: ");
			UserInfo user = userInfoRepository.findById(userDetails.getId()).get();
			System.err.println(" ::: AuthController.loginUser4 ::: ");
			if (user.getStatus()) {
				System.err.println("::::::::::::::");
				LoginResponse loginResponse = new LoginResponse(userDetails.getUuid(), userDetails.getUsername(),
						user.getFirstName(), userDetails.getEmail(),jwt);
				loginResponse.setProfileImageUrl(Constants.BASE_IP + Constants.PROFILE_IMAGES_LOCATION);
				user.setLoginStatus(true);
				user.setLoginTime(Constants.getDateAndTime());
				user.setToken(jwt);
				userInfoRepository.save(user);
				responsePacket = new ResultDTO<>(true, loginResponse, Constants.loginSuccess);
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, null, Constants.accountNotVerified);
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket = new ResultDTO<>(false, null, Constants.unathorized);
			return new ResponseEntity<>(responsePacket, HttpStatus.UNAUTHORIZED);
		}
	}
	@PostMapping("/forgotPassword/{userName}")
	public ResponseEntity<?> forgotPassword(@PathVariable("userName") String userName) {
		System.err.println("::: AuthController.forgotPassword :::");
		ResultDTO<?> responsePacket = null;
		try {
			UserInfo user = userInfoRepository.findByEmail(userName);
			if (user != null) {
				responsePacket = new ResultDTO<>(true, userInfoService.forgotPassword(user), "OTP Sent Successfully");
				return new ResponseEntity<>(responsePacket, HttpStatus.OK);
			} else {
				responsePacket = new ResultDTO<>(false, "User not exists");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword reqData) {
		System.err.println("::: AuthController.changePassword :::");
		ResultDTO<?> responsePacket = null;
		try {
			UserInfo user = userInfoRepository.findById(reqData.getUserId()).orElse(null);
			if (user != null) {
				if (user.getStatus()) {
					if (reqData.getOtp().equals(user.getOtp())) {
						if (reqData.getNewPassword().equals(reqData.getConfirmPassword())) {
							userInfoService.changePassword(user, reqData.getNewPassword());
						} else {
							responsePacket = new ResultDTO<>(false, "Password & Confirm Password must be same ");
							return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
						}
					} else {
						responsePacket = new ResultDTO<>(false, "Invalid OTP");
						return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
					}
				} else {
					responsePacket = new ResultDTO<>(false, "Player account not verified");
					return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
				}
			} else {
				responsePacket = new ResultDTO<>(false, "Player not exists");
				return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
			}
			responsePacket = new ResultDTO<>(true, "Password Updated Succesfully!!");
			return new ResponseEntity<>(responsePacket, HttpStatus.OK);
		} catch (Exception e) {
			responsePacket = new ResultDTO<>(false, e.getMessage());
			return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
		}
	}

}
