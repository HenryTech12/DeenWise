package com.deenwise.demo.payment;

public class PaypalPayment
{
	private Double amount;
	private String intent;
	private String method;
	private String currency;
	private String cancelUrl;
	private String successUrl;
	private String description;

	public void setDescription(String description)
	{
	this.description = description;
	}

	public String getDescription()
	{
	return description;
	}


	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	public Double getAmount()
	{
		return amount;
	}

	public void setIntent(String intent)
	{
		this.intent = intent;
	}

	public String getIntent()
	{
		return intent;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public String getMethod()
	{
		return method;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCancelUrl(String cancelUrl)
	{
		this.cancelUrl = cancelUrl;
	}

	public String getCancelUrl()
	{
		return cancelUrl;
	}

	public void setSuccessUrl(String successUrl)
	{
		this.successUrl = successUrl;
	}

	public String getSuccessUrl()
	{
		return successUrl;
	}
	
	}
