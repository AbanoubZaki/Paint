package myShapes;

import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class circle extends myShape {

	Double radius = new Double(0);
	
	public circle(Point center, Double radius) {
		// TODO Auto-generated constructor stub
		this.radius = radius;
		setPosition(center);
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		super.setProperties(properties);
	}
}
