package myShapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.myShape;

public class square extends myShape {

	private Double length = new Double(0);

	private Map<String, Double> Properties = new HashMap<>();
	
	public square(Point position, Double length, java.awt.Graphics canvas) {
		this.length = length;
		setProperties(Properties);
		setPosition(position);
		setCanvas(canvas);
		draw(canvas);
		
	}

	public void draw(java.awt.Graphics canvas) {
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(length));
	}

	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		Properties.put("length", length);
	}

	public java.util.Map<String, Double> getProperties() {
		return Properties;
	}

	/**
	 * makes a copy of the shape. implemented in each class extending shape
	 */
	public Object clone() throws CloneNotSupportedException {
		Shape newSquare = new square(getPosition(), length, getCanavas());
		newSquare.setColor(getColor());
		newSquare.setFillColor(getFillColor());
		return newSquare;
	} // create a deep clone of the shape

}
