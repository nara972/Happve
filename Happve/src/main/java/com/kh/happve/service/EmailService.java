package com.kh.happve.service;

import com.kh.happve.dto.EmailMessage;

public interface EmailService {
	
	void sendEmail(EmailMessage emailMessage);

}
