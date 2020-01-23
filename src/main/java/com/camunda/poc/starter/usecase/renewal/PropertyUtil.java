package com.camunda.poc.starter.usecase.renewal;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyUtil {

	public static final String[] stateAbbrvArray = {"AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"};
	public static final String[] statesArray = {"Alaska","Alabama","Arkansas","American Samoa","Arizona","California","Colorado","Connecticut","District of Columbia","Delaware","Florida","Georgia","Guam","Hawaii","Iowa","Idaho","Illinois","Indiana","Kansas","Kentucky","Louisiana","Massachusetts","Maryland","Maine","Michigan","Minnesota","Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska","New Hampshire","New Jersey","New Mexico","Nevada","New York","Ohio","Oklahoma","Oregon","Pennsylvania","Puerto Rico","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Virginia","Virgin Islands","Vermont","Washington","Wisconsin","West Virginia","Wyoming"};

	public static final List<String> stateAbbrvList = Arrays.asList(stateAbbrvArray);
	public static final List<String> stateList = Arrays.asList(statesArray);;

	public static String getStreet(String slug) throws Exception{
		String[] addr = slug.split(" ");
		if(addr[0].isEmpty() || addr[1].isEmpty())
			throw new Exception("Could not find street : incomplete address");
		
		return addr[0]+" "+addr[1];
	}
	
	public static String getDesignation(String slug) throws Exception{
		String[] addr = slug.split(" ");
		if(addr[2].isEmpty())
			throw new Exception("Could not find street designation e.g. Ave : incomplete address");
		
		return addr[2];	
	}

	public static Boolean isCity(String val){
		String regex = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(val);
		return matcher.matches();
	}
	
	public static String getUnit(String slug) throws Exception{
		String[] addr = slug.split(" ");
		if(addr[3].isEmpty())
			throw new Exception("Could not find unit : incomplete address");
		
		if (!isZip(addr[3]) && !isCity(addr[3]))
			return addr[3];
		
		return "";
	}

	public static Boolean isZip(String slug) throws Exception{
		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(slug);
		return matcher.matches();
	}

	public static String getZip(String slug) throws Exception{
		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(slug);
		String zip = matcher.group();
		if (zip.isEmpty())
			throw new Exception("Could not find zip : incomplete address");

		return zip;
	}
	
	public static String getCity(String slug) throws Exception{
		String[] addr = slug.split(" ");
		if(addr[3].isEmpty())
			throw new Exception("Could not find city : incomplete address");

		if (addr.length > 4){
			if (!isZip(addr[3]) && isCity(addr[3]))
				return addr[3];
			else if(isCity(addr[4]) && !isState(addr[4]))
				return addr[4];
		}
		
		return "";
	}
	
	public static Boolean isState(String val){
		return stateList.contains(val);
	}
	
	public static Boolean isStateAbbrv(String val){
		return stateAbbrvList.contains(val);
	}
	
	public static String getState(String slug) throws Exception{
		String[] addr = slug.split(" ");
		if(addr[3].isEmpty())
			throw new Exception("Could not find city : incomplete address");

		if (addr.length > 5){
			if (isState(addr[4]) && !isState(addr[5]))
				return addr[4];
			else if(isState(addr[5]) || !isStateAbbrv(addr[5]))
				return addr[5];
		}
		
		return "";
	}
	
	
	public static String getStateAbbrv(String slug) throws Exception{
		String[] addr = slug.split(" ");
		if(addr[3].isEmpty())
			throw new Exception("Could not find state : incomplete address");

		if (addr.length > 6){
			if (isState(addr[5]) && !isState(addr[6]))
				return addr[5];
			else if(isState(addr[6]) || !isZip(addr[6]))
				return addr[6];
		}
		
		return "";
	}
}
