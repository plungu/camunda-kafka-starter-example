package com.camunda.poc.starter.usecase.renewal.csv;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.opencsv.bean.CsvBindByName;

public class Renewal {

    @CsvBindByName
	private String start;
    
    @CsvBindByName(required = true)
	private String end;
    
    @CsvBindByName(required = true)
	private String propertySlug;
	
	@CsvBindByName
    private Float currentRent = new Float(0);
	
	@CsvBindByName
    private Float oneYearOffer = new Float(0);
	
	@CsvBindByName
    private Float twoYearOffer = new Float(0);
		
//	@CsvBindByName(required = true)
//    private String tenants;
    
	public Date getStart() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date date = null;
		if(this.start == null){
			date = format.parse(this.end);
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -365);
			return calendar.getTime();
		}else{
			date = format.parse(this.start);
		}
		return date;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public Date getEnd() throws ParseException {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date date = format.parse(this.end);
		return date;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getPropertySlug() {
		return propertySlug;
	}
	public void setPropertySlug(String propertySlug) {
		this.propertySlug = propertySlug;
	}
	public Float getCurrentRent() {
		return currentRent;
	}
	public void setCurrentRent(Float currentRent) {
		this.currentRent = currentRent;
	}
	public Float getOneYearOffer() {
		return oneYearOffer;
	}
	public void setOneYearOffer(Float oneYearOffer) {
		this.oneYearOffer = oneYearOffer;
	}
	public Float getTwoYearOffer() {
		return twoYearOffer;
	}
	public void setTwoYearOffer(Float twoYearOffer) {
		this.twoYearOffer = twoYearOffer;
	}
//	public List<String> getTenants() {
//		String[] tenants = this.tenants.replace("[", "").replace("]", "").split(",");
//		return Arrays.asList(tenants);
//	}
//	public void setTenants(String tenants) {
//		this.tenants = tenants;
//	}
    
    
	
}
