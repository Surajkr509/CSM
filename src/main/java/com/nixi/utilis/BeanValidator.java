package com.nixi.utilis;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import com.nixi.model.UserInfo;


@Component
public class BeanValidator {

	public Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	public ArrayList<String> userSignupValidate(UserInfo reqData) {
		ArrayList<String> arrayList = new ArrayList<>();
		Set<ConstraintViolation<UserInfo>> constraintViolations = getValidator().validate(reqData);
		for (ConstraintViolation<UserInfo> constraintViolation : constraintViolations) {
			if (constraintViolation.getPropertyPath().toString().equals("firstName")) {
				arrayList.add(constraintViolation.getMessage());
			}
			if (constraintViolation.getPropertyPath().toString().equals("email")) {
				arrayList.add(constraintViolation.getMessage());
			}
			if (constraintViolation.getPropertyPath().toString().equals("password")) {
				arrayList.add(constraintViolation.getMessage());
			}
		}
		return arrayList;
	}
	
	
	
	
	
}