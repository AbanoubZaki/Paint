package myShapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.myShape;

public class rectangle extends myShape {

	/**
	 * length of the rectangle.
	 */
	Double length = new Double(0);
	
	/**
	 * width of the rectangle
	 */
	Double width = new Double(0);
	
	/**
	 * the properties
	 */
	private Map<String, Double> properties = new HashMap<>();
	
	public rectangle(Point position, Double length, Double width) {
		this.length = length;
		this.width = width;
		setProperties(properties);
		setPosition(position);
	}
	
	public void draw(java.awt.Graphics canvas) {
		canvas.setColor(getFillColor());
		canvas.fillRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(width));
		canvas.setColor(getColor());
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(width));
	}
	
	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		properties.put("length", length);
		properties.put("width", width);
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
		Shape newRectangle = new rectangle(getPosition(), length, width);
		newRectangle.setColor(getColor());
		newRectangle.setFillColor(getFillColor());
		return newRectangle;
	} // create a deep clone of the shape
}
