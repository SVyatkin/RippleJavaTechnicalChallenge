package com.trustline.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
    
	private static final HttpHeaders requestHeaders = new HttpHeaders();
	
	static {
		requestHeaders.set("Content-Type", "application/json;charset=UTF-8");
	}
	
	private RestTemplate rest = new RestTemplate();
	
	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	public ResponseEntity<String> call(HttpMethod method, String url, String request) {
        ResponseEntity<String> responseEntity = rest.exchange(url, method, new HttpEntity<>(request, requestHeaders), String.class);
        return responseEntity;
	}

	public String get(String url) {
		return callGet(url).getBody();
	}
	
	public String post(String url, String request) {
		return callPost(url, request).getBody();
	}

	public ResponseEntity<String> callGet(String url) {
		return callAndTrace(HttpMethod.GET, url, null);
	}
	
	public ResponseEntity<String> callPost(String url, String request) {
		return callAndTrace(HttpMethod.POST, url, request);
	}

	private ResponseEntity<String> callAndTrace(HttpMethod method, String url, String request) {
		printRequest(url, method, request);
        ResponseEntity<String> responseEntity = call(method, url, request);
        log.info("Response Header:\n" + responseEntity.getHeaders());
        log.info("Response status code: " + responseEntity.getStatusCode());
		log.info("Response body(JSON):  " + responseEntity.getBody());
		return responseEntity;
	}

	private void printRequest(String url, HttpMethod method, String request) {
		log.info("----------------------------------------------------------------------------------");
		log.info("\nURL: " + url);
		log.info("HTTP method: " + method);
		log.info("Request body(JSON):  " + request);
	}

}
