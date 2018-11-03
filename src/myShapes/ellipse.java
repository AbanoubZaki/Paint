package myShapes;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class ellipse extends myShape {

	private Double horizontalRadius = 0.0 ;
	
	private Double verticalRadius = 0.0 ;
	
	/**
     * properties of the shape.
     */
    private Map<String, Double> properties = new HashMap<>();
    
	public ellipse(Point position, Double horizontalRadius, Double verticalRadius, java.awt.Graphics canvas) {
		// TODO Auto-generated constructor stub
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
		setPosition(position);
		setProperties(properties);
		setCanvas(canvas);
		draw(canvas);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.drawOval(getPosition().x, getPosition().y, (int) Math.round(horizontalRadius), (int) Math.round(verticalRadius));
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		this.properties.put("horizontalRadius", horizontalRadius);
		this.properties.put("verticalRadius", verticalRadius);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ellipse copy = new ellipse(getPosition(), horizontalRadius, verticalRadius, getCanavas());
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		return copy;
	}
}
