package eg.edu.alexu.csd.oop.draw;

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
	
	/**
     * no argument constructor.
     */
    public rectangle() {
    	properties.put("length", null);
		properties.put("width", null);
	}
	
	public void draw(java.awt.Graphics canvas) {
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(width));
		}
		canvas.setColor(getColor());
		canvas.drawRect(getPosition().x, getPosition().y, (int) Math.round(length), (int) Math.round(width));
	}
	
	/**
	 * contains each main property of the shape. implemented in each class extending
	 * shape
	 */
	// update shape specific properties (e.g., radius)
	public void setProperties(java.util.Map<String, Double> properties) {
		length = properties.get("length");
		width = properties.get("width");
		
		this.properties = properties;
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
		rectangle copy = new rectangle();
		copy.setPosition(new Point(getPosition().x, getPosition().y));
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		Map <String, Double> propertiesCopy = new HashMap<String, Double>();
		propertiesCopy.putAll(getProperties());
		copy.setProperties(propertiesCopy);
		return copy;
	} // create a deep clone of the shape
}
