package myShapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.myShape;

public class triangle extends myShape {

	/**
	 * 1st point
	 */
	Point firstPoint = new Point();

	/**
	 * 2nd point
	 */
	Point secondPoint = new Point();

	/**
	 * 3rd point
	 */
	Point thirdPoint = new Point();
	/**
	 * properties hashMap.
	 */
	private Map<String, Double> Properties = new HashMap<>();

	public triangle(Point position, Double[] xPoints, Double[] yPoints, java.awt.Graphics canvas) {
		firstPoint.x = (int) Math.round(xPoints[0]);
		firstPoint.x = (int) Math.round(xPoints[0]);
		secondPoint.x = (int) Math.round(xPoints[1]);
		secondPoint.x = (int) Math.round(xPoints[1]);
		thirdPoint.x = (int) Math.round(xPoints[2]);
		thirdPoint.x = (int) Math.round(xPoints[2]);
		setProperties(Properties);
		setPosition(position);
		setCanvas(canvas);
		draw(canvas);
	}

	public void draw(java.awt.Graphics canvas) {
		canvas.drawPolygon(new int[] { firstPoint.x, secondPoint.x, thirdPoint.x },
				new int[] { firstPoint.y, secondPoint.y, thirdPoint.y }, 3);
	}
	
	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		Properties.put("firstPointX", (double) firstPoint.x);
		Properties.put("firstPointY", (double) firstPoint.y);
		Properties.put("secondPointX", (double) secondPoint.x);
		Properties.put("secondPointY", (double) secondPoint.y);
		Properties.put("thirdPointX", (double) thirdPoint.x);
		Properties.put("thirdPointY", (double) thirdPoint.y);
	}
	
	public java.util.Map<String, Double> getProperties() {
		return Properties;
	}
	
	/**
	 * makes a copy of the shape. implemented in each class extending shape
	 */
	public Object clone() throws CloneNotSupportedException {
		Shape newTriangle = new triangle(getPosition(),
				new Double[] { (double) firstPoint.x, (double) secondPoint.x, (double) thirdPoint.x },
				new Double[] { (double) firstPoint.y, (double) secondPoint.y, (double) thirdPoint.y }, getCanavas());
		newTriangle.setColor(getColor());
		newTriangle.setFillColor(getFillColor());
		return newTriangle;
	} // create a deep clone of the shape
}
