package myShapes;

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
	}
    
	public lineSegment (Point firstPoint, Point secondPoint) {
		// TODO Auto-generated constructor stub
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
		setPosition(firstPoint);
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
		
		this.properties.put("x1", (double) firstPoint.x);
		this.properties.put("y1", (double) firstPoint.y);
		this.properties.put("x2", (double) secondPoint.x);
		this.properties.put("y2", (double) secondPoint.y);
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
		lineSegment copy = new lineSegment(firstPoint, secondPoint);
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		return copy;
	}
}
