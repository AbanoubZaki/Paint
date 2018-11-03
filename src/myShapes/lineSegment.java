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
    
	public lineSegment (Point firstPoint, Point secondPoint, java.awt.Graphics canvas) {
		// TODO Auto-generated constructor stub
		this.firstPoint = firstPoint;
		this.secondPoint = secondPoint;
		setPosition(firstPoint);
		setProperties(properties);
		setCanvas(canvas);
		draw(canvas);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		this.properties.put("x1", (double) firstPoint.x);
		this.properties.put("y1", (double) firstPoint.y);
		this.properties.put("x2", (double) secondPoint.x);
		this.properties.put("y2", (double) secondPoint.y);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		lineSegment copy = new lineSegment(firstPoint, secondPoint, getCanavas());
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		return copy;
	}
}
