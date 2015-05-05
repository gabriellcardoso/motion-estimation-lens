package br.edu.ufpel.inf.utils;

public class Position extends Pair {
	
	public Position(int x, int y) {
		super(x, y);
	}
	
	public Position() {
		super(0, 0);
	}
	
	public void setPosition(int x, int y) {
		this.setPair(x, y);
	}
	
}
