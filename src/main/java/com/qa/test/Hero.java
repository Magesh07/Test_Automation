package com.qa.test;

public class Hero {

	String natid;
	String Name;
	String Gender;
	String Birthday;
	Double Salary;
	Double Tax_paid;

	public Hero() {
		
	}
	public Hero(String natural_ID, String name, String gender, String birthday, Double salary, Double taxPaid) {

		this.natid = natural_ID;
		this.Name = name;
		this.Gender = gender;
		this.Birthday = birthday;
		this.Salary = salary;
		this.Tax_paid = taxPaid;

	}

	public int getAge() {
		return 0;
	}

}
