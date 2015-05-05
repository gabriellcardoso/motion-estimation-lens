package br.edu.ufpel.inf.utils;

public class Vector extends Pair {

	public Vector(int x, int y) {
		super(x, y);
	}
	
	public Vector() {
		super(0, 0);
	}
	
	public void setVector(int x, int y) {
		this.setPair(x, y);
	}
	
}
