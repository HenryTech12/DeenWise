package com.deenwise.demo.controller;
import com.deenwise.demo.payment.*;
import com.deenwise.demo.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/payment")
@Controller
public class PaymentController
{
	@Autowired
	private PaymentService paymentService;


	@GetMapping("/details")
	public String paymentDetails(Model model) {
		model.addAttribute("details", new PaypalPayment());
		return "";
	}
	@PostMapping("/create")
	public RedirectView createPayment(@ModelAttribute PaypalPayment details) {
		String successUrl = "http://localhost:8080/payment/success";
		String cancelUrl = "http://localhost:8080/payment/error";

		details.setCancelUrl(cancelUrl);
		details.setSuccessUrl(successUrl);
		Payment payment = paymentService.createPayment(details);
		System.out.println(payment.getState());
		for(Links links : payment.getLinks()) {
			if(links.getRel().equals("approval_url"))
				return new RedirectView(links.getHref());
			else
				return new RedirectView("/payment/error");
		}
		return new RedirectView("/payment/error");
	}
	
	@GetMapping("/execute")
	public RedirectView executePayment(@ModelAttribute ExecutePayment ePayment) {
		Payment payment	= paymentService.executePayment(ePayment);
		if(payment.getState().equals("approved"))
			return new RedirectView("/payment/success");
		else
			return new RedirectView("/payment/error");
	}
	
	@PostMapping("/error")
	public String paymentError() {	
		return "error.html";
	}
	
	@PostMapping("/success")
	public String paymentSuccess() {	
		return "success.html";
	}
}
