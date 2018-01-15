package com.trustline.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan("com.trustline.demo")
@Component
public class Configuration {
	@Value("${com.trustline.partner.url}")
	public String partnerUrl;	
	
	@Value("${com.trustline.partner.name}")
	public String partnerName;	
	
	@Value("${com.trustline.my.name}")
	public String myName;	
	
	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	
}
