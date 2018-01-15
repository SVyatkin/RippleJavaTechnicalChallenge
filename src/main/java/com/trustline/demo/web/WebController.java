package com.trustline.demo.web;

import java.io.IOException;
import java.util.Date;
import java.util.Stack;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trustline.demo.config.Configuration;
import com.trustline.demo.dto.MoneyTransfer;
import com.trustline.demo.dto.Payment;
import com.trustline.demo.service.RestClient;

@RestController
@CrossOrigin()
public class WebController {

	@Autowired
	Configuration configuration;
	
	@Autowired
	RestClient restClient;
	
	private static final Logger log = LoggerFactory.getLogger(WebController.class);

	public static final String PAYMENT = "/pay/{payment}";
	private static final String BALANCE = "/balance";
	private static final String HISTORY = "/history";
	private static final String DEPOSIT = "/deposit";
	private static final String NAME = "/name";

	private Stack<Payment> ledger = new Stack<Payment>();

	private static String unprocessedPayment = ": payment is not processed";
	
	ObjectMapper mapper = new ObjectMapper();
	
	@PostConstruct
	private void init() {
		log.info("Welcome to the Trustline");
		log.info(configuration.getMyName() + ", your Trustline balance is: " + getLastBalance());
	}

	/**
	 * Transfer payment to partner
	 */

	@RequestMapping(value = PAYMENT, method = RequestMethod.GET)
	public String payment(@PathVariable double payment) throws JsonParseException, JsonMappingException, IOException {
		UUID confirmationNumber = UUID.randomUUID();

		// deposit payment to partner

		MoneyTransfer moneyTransfer = new MoneyTransfer(payment, confirmationNumber.toString(), new Date());
		log.info("Paying " + payment +   " to " + configuration.getPartnerName());
		ResponseEntity<String> response = restClient.callPost(configuration.getPartnerUrl() + DEPOSIT, mapper.writeValueAsString(moneyTransfer));
		String responseMessage;
		if (response.getStatusCode() == HttpStatus.OK) {
			addPaymentToLedger(-payment, confirmationNumber.toString(), response.getBody());
			log.info("Paid");
			responseMessage = confirmationNumber.toString();
			log.info(configuration.getMyName() + ", your Trustline balance is: " + getLastBalance());
		} else
			responseMessage = response.getStatusCode() + unprocessedPayment;

		return responseMessage;
	}

	private void addPaymentToLedger(double payment, String confirmationNumber, String confirmationNumberBeneficiar) {
		ledger.add(new Payment(confirmationNumber, confirmationNumberBeneficiar, payment, getLastBalance() + payment, new Date()));
	}

	private double getLastBalance() {
		double balance = ledger.isEmpty() ? 0.0 : ledger.peek().getBalance();
		return balance;
	}

	/**
	 * Deposit payment from partner
	 */

	@RequestMapping(value = DEPOSIT, method = RequestMethod.POST)
	public String deposit(@RequestBody MoneyTransfer moneyTransfer) throws JsonParseException, JsonMappingException, IOException {
		UUID confirmationNumber = UUID.randomUUID();
		addPaymentToLedger(moneyTransfer.getPayment(), confirmationNumber.toString(), moneyTransfer.getConfirmationNumber());
		log.info(configuration.getMyName() + ", you were paid  " + moneyTransfer.getPayment());
		log.info(configuration.getMyName() + ", your Trustline balance is: " + getLastBalance());
		return confirmationNumber.toString();
	}

	/**
	 * your balance
	 */
	
	@RequestMapping(value = BALANCE, method = RequestMethod.GET)
	public double balance() throws JsonParseException, JsonMappingException, IOException {
		return getLastBalance();
	}
	
	/**
	 * ledger all payment
	 */
	
	@RequestMapping(value = HISTORY, method = RequestMethod.GET)
	public Stack<Payment> statment() throws JsonParseException, JsonMappingException, IOException {
		return ledger;
	}
	
	/**
	 * provide my name 
	 */
	
	@RequestMapping(value = NAME, method = RequestMethod.GET)
	public String yourName() throws JsonParseException, JsonMappingException, IOException {
		String s = configuration.getMyName() ;
		return "My name is " + s;
	}
	
}