package com.kholy;

public class Key {
	private boolean isAvailable = true;
	private int id;

	public Key(int id) {
		super();
		this.id = id;
	}

	public synchronized boolean isAvailable() {
		return isAvailable;
	}

	public synchronized void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public String toString(){
		return id + "";
	}
}
