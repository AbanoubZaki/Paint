package eg.edu.alexu.csd.oop.draw;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class lineSegment extends myShape {

	private Point firstPoint = new Point(0,0);
	
	private Point secondPoint = new Point(0,0);
	
	/**
     * properties of the shape.
     */
    private Map<String, Double> properties = new HashMap<>();
    
    /**
     * no argument constructor.
     */
    public lineSegment() {
    	this.properties.put("x1", null);
		this.properties.put("y1", null);
		this.properties.put("x2", null);
		this.properties.put("y2", null);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.setColor(getColor());
		canvas.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		firstPoint.x = (int) Math.round(properties.get("x1"));
		firstPoint.y = (int) Math.round(properties.get("y1"));
		secondPoint.x = (int) Math.round(properties.get("x2"));
		secondPoint.y = (int) Math.round(properties.get("y2"));
		
		this.properties = properties;
	}
	
	public java.util.Map<String, Double> getProperties() {
		if (properties.equals(null)) {
			return null;
		}
		return properties;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		lineSegment copy = new lineSegment();
		copy.setPosition(new Point(getPosition().x, getPosition().y));
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		Map <String, Double> propertiesCopy = new HashMap<String, Double>();
		propertiesCopy.putAll(getProperties());
		copy.setProperties(propertiesCopy);
		return copy;
	}
}
