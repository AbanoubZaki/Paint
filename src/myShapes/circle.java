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
    
    /**
     * no argument constructor.
     */
    public circle() {
	}
    
	public circle(Point position, Double radius) {
		// TODO Auto-generated constructor stub
		this.radius = radius;
		setPosition(position);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		// TODO Auto-generated method stub
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillOval(getPosition().x, getPosition().y, (int) Math.round(radius), (int) Math.round(radius));
		}
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, (int) Math.round(radius), (int) Math.round(radius));
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		radius = properties.get("radius");
		
		this.properties.put("radius", radius);
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
		circle copy = new circle(getPosition(), radius);
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		return copy;
	}
}
