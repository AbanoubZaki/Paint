package myShapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class circle extends myShape {

	private Double radius = 0.0 ;
	
	/**
     * properties of the shape.
     */
    private Map<String, Double> properties = new HashMap<>();
    
	public circle(Point position, Double radius, java.awt.Graphics canvas) {
		// TODO Auto-generated constructor stub
		this.radius = radius;
		setPosition(position);
		setProperties(properties);
		setCanvas(canvas);
		draw(canvas);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.drawOval(getPosition().x, getPosition().y, (int) Math.round(radius), (int) Math.round(radius));
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		this.properties.put("radius", radius);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		circle copy = new circle(getPosition(), radius, getCanavas());
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		copy.setPosition(getPosition());
		copy.setProperties(getProperties());
		return copy;
	}
}
