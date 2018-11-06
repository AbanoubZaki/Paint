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
	private Map<String, Double> properties = new HashMap<>();

	/**
     * no argument constructor.
     */
    public triangle() {
	}
	
	public triangle(Double[] xPoints, Double[] yPoints) {
		firstPoint.x = (int) Math.round(xPoints[0]);
		firstPoint.y = (int) Math.round(yPoints[0]);
		secondPoint.x = (int) Math.round(xPoints[1]);
		secondPoint.y = (int) Math.round(yPoints[1]);
		thirdPoint.x = (int) Math.round(xPoints[2]);
		thirdPoint.y = (int) Math.round(yPoints[2]);
		setPosition(firstPoint);
	}

	public void draw(java.awt.Graphics canvas) {
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillPolygon(new int[] { firstPoint.x, secondPoint.x, thirdPoint.x },
					new int[] { firstPoint.y, secondPoint.y, thirdPoint.y }, 3);
		}
		canvas.setColor(getColor());
		canvas.drawPolygon(new int[] { firstPoint.x, secondPoint.x, thirdPoint.x },
				new int[] { firstPoint.y, secondPoint.y, thirdPoint.y }, 3);
	}
	
	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		firstPoint.x = (int) Math.round(properties.get("x1"));
		firstPoint.y = (int) Math.round(properties.get("y1"));
		secondPoint.x = (int) Math.round(properties.get("x2"));
		secondPoint.y = (int) Math.round(properties.get("y2"));
		thirdPoint.x = (int) Math.round(properties.get("x3"));
		thirdPoint.y = (int) Math.round(properties.get("y3"));
		
		properties.put("x1", (double) firstPoint.x);
		properties.put("y1", (double) firstPoint.y);
		properties.put("x2", (double) secondPoint.x);
		properties.put("y2", (double) secondPoint.y);
		properties.put("x3", (double) thirdPoint.x);
		properties.put("y3", (double) thirdPoint.y);
	}
	
	public java.util.Map<String, Double> getProperties() {
		if (properties.equals(null)) {
			return null;
		}
		return properties;
	}
	
	/**
	 * makes a copy of the shape. implemented in each class extending shape
	 */
	public Object clone() throws CloneNotSupportedException {
		Shape newTriangle = new triangle(
				new Double[] { (double) firstPoint.x, (double) secondPoint.x, (double) thirdPoint.x },
				new Double[] { (double) firstPoint.y, (double) secondPoint.y, (double) thirdPoint.y });
		newTriangle.setColor(getColor());
		newTriangle.setFillColor(getFillColor());
		return newTriangle;
	} // create a deep clone of the shape
}
