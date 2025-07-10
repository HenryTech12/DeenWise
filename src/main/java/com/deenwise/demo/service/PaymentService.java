package com.deenwise.demo.service;
import com.paypal.api.payments.*;
import com.deenwise.demo.payment.*;
import java.util.*;
import com.paypal.base.rest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService
{
	@Autowired
	private APIContext context;
	public Payment createPayment(PaypalPayment paypalPayment) {
	   try {
        Amount amount = new Amount();
		amount.setTotal(""+paypalPayment.getAmount());
		amount.setCurrency(paypalPayment.getCurrency());
		
		List<Transaction> listTx = new ArrayList<>();
		Transaction tx = new Transaction();	
		listTx.add(tx);
		tx.setDescription(paypalPayment.getDescription());
		
		Payer payer = new Payer();
		payer.setPaymentMethod(paypalPayment.getMethod());
	    
		
		RedirectUrls urls = new RedirectUrls();
		urls.setReturnUrl(paypalPayment.getSuccessUrl());
		urls.setCancelUrl(paypalPayment.getCancelUrl());
		
		Payment payment = new Payment();
		payment.setIntent(paypalPayment.getIntent());
		payment.setTransactions(listTx);
		payment.setRedirectUrls(urls);
		payment.setPayer(payer);
		
		return payment.create(context);
	  }
	  catch(PayPalRESTException e) {
		 e.printStackTrace();
	  }
	  return null;
	}
	
	public Payment executePayment(ExecutePayment ePayment) {
	  try {
		PaymentExecution execute = new PaymentExecution();
		execute.setPayerId(ePayment.getPayerID());
		Payment payment = new Payment();
		payment.setId(ePayment.getPaymentId());
		
		return payment.execute(context,execute);
	   }
	  catch(PayPalRESTException e) {
		e.printStackTrace();
	  }
	  return null;
	}
}
