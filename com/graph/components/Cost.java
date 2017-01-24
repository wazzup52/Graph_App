package com.graph.components;

public class Cost {
	private int x;
	private int y;
	private int cost;

	public Cost(int xx, int yy, int cst) {
		this.x = xx;
		this.y = yy;
		this.cost = cst;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCost() {
		return cost;
	}

	public void setX(int xx) {
		this.x = xx;
	}

	public void setY(int yy) {

		this.y = yy;
	}

	public void setCost(int cst) {
		this.cost = cst;
	}

	public String toString() {
		return "[" + x + "," + y + "," + cost + " ]";
	}

}
