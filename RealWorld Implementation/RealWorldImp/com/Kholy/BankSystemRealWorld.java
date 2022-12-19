package com.kholy;

import java.util.ArrayList;

public class BankSystemRealWorld {
	private BankSystem diningTable;
	private ArrayList<Key> forkList;
	private ArrayList<Accounts> philosophers;
	
//	public static void main(String[] args) {
//		new DiningApp().start();
//	}

	public void start() {
		diningTable = new BankSystem();
		forkList = diningTable.getForks();
		philosophers = diningTable.getDiners();
		addForks();
		System.out.println(forkList);
		addDiners();
		System.out.println(philosophers);
		startDining();
	}

	private void startDining() {
		Thread diner1 = new Thread(philosophers.get(0));
		Thread diner2 = new Thread(philosophers.get(1));
		Thread diner3 = new Thread(philosophers.get(2));
		Thread diner4 = new Thread(philosophers.get(3));
		Thread diner5 = new Thread(philosophers.get(4));
		diner1.start();
		diner2.start();
		diner3.start();
		diner4.start();
		diner5.start();
	}

	private void addDiners() {
		int rightFork = 4;
		for(int i = 0; i < 5; ++i){
			if(i == 1){
				rightFork = 0;
			}else if (i == 2) {
				rightFork = 1;
			}else if (i == 3) {
				rightFork = 2;
			}else if (i == 4) {
				rightFork = 3;
			}
			philosophers.add(new Accounts(i + 1, forkList.get(i), forkList.get(rightFork)));
		}
	}

	private void addForks() {
		for(int i = 0; i < 5; ++i){
			forkList.add(new Key(i + 1));
		}
	}

	public ArrayList<Accounts> getPhilosophers() {
		return philosophers;
	}

	public void setPhilosophers(ArrayList<Accounts> philosophers) {
		this.philosophers = philosophers;
	}

	public ArrayList<Key> getForkList() {
		return forkList;
	}

	public void setForkList(ArrayList<Key> forkList) {
		this.forkList = forkList;
	}
	
	
}
