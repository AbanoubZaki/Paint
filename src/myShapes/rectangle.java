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
	private Map<String, Double> Properties = new HashMap<>();
	
	public rectangle(Point position, Double length, Double width, java.awt.Graphics canvas) {
		this.length = length;
		this.width = width;
		setProperties(Properties);
		setPosition(position);
		setCanvas(canvas);
		draw(canvas);
	}
	
	public void draw(java.awt.Graphics canvas) {
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(width));
	}
	
	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		Properties.put("length", length);
		Properties.put("width", width);
	}
	
	public java.util.Map<String, Double> getProperties() {
		return Properties;
	}
	
	/**
	 * makes a copy of the shape. implemented in each class extending shape
	 */
	public Object clone() throws CloneNotSupportedException {
		Shape newRectangle = new rectangle(getPosition(), length, width, getCanavas());
		newRectangle.setColor(getColor());
		newRectangle.setFillColor(getFillColor());
		return newRectangle;
	} // create a deep clone of the shape
}
