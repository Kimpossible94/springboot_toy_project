package com.kh.spring.common.code;

public enum Config {
	
	//DOMAIN("https://pclass.ga"),
	DOMAIN("http://localhost:9090"),
	COMPANY_EMAIL("dudqja115@gmail.com"),
	SMTP_AUTHENTICATION_ID("dudqja115@gmail.com"),
	SMTP_AUTHENTICATION_PASSWORD("1234"),
	UPLOAD_PATH("C:\\CODE\\upload\\");

	public final String DESC;
	
	Config(String desc) {
		this.DESC = desc;
	}

	
	
}
