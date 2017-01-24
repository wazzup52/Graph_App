package com.graph.components;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.graph.Main;
import com.graph.Utilities;

public class Arc {
	private Point start;
	private Point end;

	public Arc(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public String toString() {
		int[] nodes = Utilities.arcNodes(this);
		return nodes[0] + " " + nodes[1];
	}

	public boolean existsAlready(Arc a) {
		if(Main.current == 0){
			if (a.getStart().equals(Utilities.nodeCoord(this.start)) && a.getEnd().equals(Utilities.nodeCoord(this.end))
					|| a.getStart().equals(Utilities.nodeCoord(this.end))
							&& a.getEnd().equals(Utilities.nodeCoord(this.start)))
				return true;
		}else{
			if (a.getStart().equals(Utilities.nodeCoord(this.start)) && a.getEnd().equals(Utilities.nodeCoord(this.end)))
				return true;
		}
		return false;
	}


	public void drawArc(Graphics g) {
		if (start != null)
		if(Main.current == 0){
			g.setColor(Color.BLACK);
			g.drawLine(start.x, start.y, end.x, end.y);
			if (Main.enableCost) {

				Point p1 = Utilities.nodeCoord(start);
				Point p2 = Utilities.nodeCoord(end);
				int x = (p1.x + p2.x) / 2;
				int y = (p1.y + p2.y) / 2;

				int[] nodes = Utilities.arcNodes(this);
				g.drawString(Integer.toString(Utilities.getCost(nodes[0], nodes[1])), x, y);
			}
		}else{
			drawArrow((Graphics2D)g, this.start.x,this.start.y,this.end.x,this.end.y);
		}
	}

	public void drawArrow(Graphics2D g, int x, int y, int xx, int yy) {

		float mx = x - xx;
		float my = y - yy;
		if (mx < 0) {
			xx -= Main.nodeSize / 3;
		} else if (mx > 0) {
			xx += Main.nodeSize / 3;
		}
		if (my < 0) {
			yy -= Main.nodeSize / 3;
		} else if (my > 0) {
			yy += Main.nodeSize / 3;
		}

		float arrowWidth = 10.0f;
		float theta = 0.423f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength;
		float th;
		float ta;
		float baseX, baseY;

		xPoints[0] = xx;
		yPoints[0] = yy;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - x;
		vecLine[1] = (float) yPoints[0] - y;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = ((float) xPoints[0] - ta * vecLine[0]);
		baseY = ((float) yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		g.drawLine(x, y, (int) baseX, (int) baseY);
		g.fillPolygon(xPoints, yPoints, 3);
	}
}

