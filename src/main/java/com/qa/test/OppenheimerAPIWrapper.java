package com.qa.test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class OppenheimerAPIWrapper {

	private static final DecimalFormat twoDecimalPointRounding = new DecimalFormat("0.00");

	public static boolean saveSingleRecord(Hero hero) {

		Gson gson = new Gson();
		String json = gson.toJson(hero);
		System.out.println(json);
		// todo - call actual api to save hero object
		return false;
	}

	public static boolean saveMultipleRecord(ArrayList<Hero> heroList) {

		// todo - call actual api to save hero object
		Gson gson = new Gson();
		String json = gson.toJson(heroList);
		System.out.println(json);
		return false;
	}

	public static boolean verifyNatIDFormat(String natID) {
		boolean result = false;
		if (!natID.isEmpty()) {
		    String substr = natID.substring(4, natID.length());
			System.out.println(substr);
		} else {
			System.out.println("No value present in natID");
		}
		//need to add verification
		return result;
	}

//AC3
	public static boolean verifyTaxRelief(double salary, double taxPaid, String dob, String gender)
			throws ParseException {
		boolean result = false;
		double genderBonus = 0;
		double variable = 0;
		int userAge = getUserAge(dob);
		if (gender == "F") {
			genderBonus = 500;
		}
		if (userAge <= 18) {
			variable = 1;
		} else if (userAge <= 35) {
			variable = 0.8;
		} else if (userAge <= 50) {
			variable = 0.5;
		} else if (userAge <= 50) {
			variable = 0.89;
		} else if (userAge >= 80) {
			variable = 0.90;
		}

		// need to write later
		double taxReliefCal = ((salary - taxPaid) * (variable) + genderBonus);
		double finalAmt = setFinalTaxReliefAmt(taxReliefCal);
		//double finalAmtR = taxReliefAmtTwoDecimalPoint(finalAmt);
		
		return result;

	}


//AC5
	public static double setFinalTaxReliefAmt(double taxRelief) {
		double result;
		result = Math.round(taxRelief);
		if (result <= 0)
		{
			result = 0.00;
		}
		else if(result >= 50) {
			result = 50.00;
		}
		return result;

	}
// AC 6

	public static String taxReliefAmtTwoDecimalPoint(double taxRelief) {

		twoDecimalPointRounding.setRoundingMode(RoundingMode.UP);
		return twoDecimalPointRounding.format(taxRelief);

	}

	// Method to return the date
	public static Date StringToDate(String dob) throws ParseException {
		// Instantiating the SimpleDateFormat class
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// Parsing the given String to Date object
		Date date = formatter.parse(dob);
		System.out.println("Date object value: " + date);
		return date;
	}

//return age
	public static int getUserAge(String dob) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = formatter.parse(dob);
		// Converting obtained Date object to LocalDate object
		Instant instant = date.toInstant();
		ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
		LocalDate givenDate = zone.toLocalDate();
		// Calculating the difference between given date to current date.
		Period period = Period.between(givenDate, LocalDate.now());
		System.out.print(period.getYears() + " years " + period.getMonths() + " and " + period.getDays() + " days");
		int agevalue = period.getYears();
		return agevalue;
	}
}
