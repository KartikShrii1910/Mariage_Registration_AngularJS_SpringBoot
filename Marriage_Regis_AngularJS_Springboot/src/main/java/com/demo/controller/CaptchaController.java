package com.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {


	 private static final int CAPTCHA_LENGTH = 6;
	    private static final String CAPTCHA_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    private static Map<String, String> captchaMap = new HashMap<>();

	    @GetMapping("/captcha")
	    public Map<String, String> getCaptcha() {
	        String captcha = generateRandomCaptcha();
	        String captchaId = generateCaptchaId();
	        captchaMap.put(captchaId, captcha);

	        Map<String, String> response = new HashMap<>();
	        response.put("captchaId", captchaId);
	        response.put("captcha", captcha);
	        return response;
	    }

	    @PostMapping("/validate-captcha")
	    public Map<String, Object> validateCaptcha(@RequestBody Map<String, String> request) {
	        Map<String, Object> response = new HashMap<>();
	        String captchaId = request.get("captchaId");
	        String userCaptcha = request.get("userCaptcha");

	        String storedCaptcha = captchaMap.get(captchaId);
	        boolean isCaptchaValid = storedCaptcha != null && storedCaptcha.equals(userCaptcha);

	        response.put("success", isCaptchaValid);

	        if (!isCaptchaValid) {
	            // If the captcha is incorrect, generate a new one
	            String newCaptcha = generateRandomCaptcha();
	            captchaMap.put(captchaId, newCaptcha);
	            response.put("newCaptcha", newCaptcha);
	        }

	        return response;
	    }

	    private String generateRandomCaptcha() {
	        Random random = new Random();
	        StringBuilder captcha = new StringBuilder();
	        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
	            int index = random.nextInt(CAPTCHA_CHARACTERS.length());
	            captcha.append(CAPTCHA_CHARACTERS.charAt(index));
	        }
	        return captcha.toString();
	    }

	    private String generateCaptchaId() {
	        return String.valueOf(System.currentTimeMillis());
	    }
	}