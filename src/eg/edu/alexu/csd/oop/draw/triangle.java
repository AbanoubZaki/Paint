package eg.edu.alexu.csd.oop.draw;

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
    	properties.put("x1", null);
		properties.put("y1", null);
		properties.put("x2", null);
		properties.put("y2", null);
		properties.put("x3", null);
		properties.put("y3", null);
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
		triangle copy = new triangle();
		copy.setPosition(new Point(getPosition().x, getPosition().y));
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		Map <String, Double> propertiesCopy = new HashMap<String, Double>();
		propertiesCopy.putAll(getProperties());
		copy.setProperties(propertiesCopy);
		return copy;
	} // create a deep clone of the shape
}
