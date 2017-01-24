package com.graph.components;

import java.awt.Color;
import java.awt.Graphics;

public class Node {
	private int coordX;
	private int coordY;
	private int number;
	private int nodeSize;

	public Node(int coordX, int coordY, int number, int nodeSize) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.number = number;
		this.nodeSize = nodeSize;
	}

	public int getSize() {
		return nodeSize;
	}

	public int getCoordX() {
		return this.coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return this.coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void drawNode(Graphics g, int nodeSize) {
		int r = nodeSize / 2;
		g.setColor(Color.BLACK);
		g.fillOval(coordX - r, coordY - r, nodeSize, nodeSize);
		g.setColor(Color.WHITE);
		g.drawOval(coordX - r, coordY - r, nodeSize, nodeSize);
		if (number < 10)
			g.drawString(((Integer) number).toString(), coordX - r + 13, coordY - r + 20);
		else
			g.drawString(((Integer) number).toString(), coordX - r + 8, coordY - r + 20);
	}

	private boolean Intersects(Node node) {
		float distanceX = coordX - node.getCoordX();
		float distanceY = coordY - node.getCoordY();
		float radiusSum = nodeSize;
		return distanceX * distanceX + distanceY * distanceY <= radiusSum * radiusSum;
	}

	private boolean Contains(Node node) {
		float distanceX = coordX - node.getCoordX();
		float distanceY = coordY - node.getCoordY();
		float radiusD = 0;
		return distanceX * distanceX + distanceY * distanceY <= radiusD * radiusD;
	}

	public boolean collides(Node node) {
		if (!Intersects(node) && !Contains(node))
			return false;
		return true;
	}
}
