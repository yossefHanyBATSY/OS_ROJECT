package com.kholy;

import java.util.ArrayList;

public class BankSystem {
	private ArrayList<Accounts> diners = new ArrayList<>();
	private ArrayList<Key> forks = new ArrayList<>();
	
	public ArrayList<Accounts> getDiners() {
		return diners;
	}
	public void setDiners(ArrayList<Accounts> diners) {
		this.diners = diners;
	}
	public ArrayList<Key> getForks() {
		return forks;
	}
	public void setForks(ArrayList<Key> forks) {
		this.forks = forks;
	}
	
	
}
