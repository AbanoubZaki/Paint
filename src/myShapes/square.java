package myShapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.myShape;

public class square extends myShape {

	private Double length = new Double(0);

	private Map<String, Double> properties = new HashMap<>();
	
	public square(Point position, Double length) {
		this.length = length;
		setProperties(properties);
		setPosition(position);
	}

	public void draw(java.awt.Graphics canvas) {
		canvas.setColor(getFillColor());
		canvas.fillRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(length));
		canvas.setColor(getColor());
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(length));
	}

	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		properties.put("length", length);
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
		Shape newSquare = new square(getPosition(), length);
		newSquare.setColor(getColor());
		newSquare.setFillColor(getFillColor());
		return newSquare;
	} // create a deep clone of the shape

}
