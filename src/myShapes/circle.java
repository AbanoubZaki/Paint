package myShapes;

import java.awt.Graphics;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class circle extends myShape {

	int radius = 0;
	Map<String, Double> properties;
	
	public circle(int radius) {
		// TODO Auto-generated constructor stub
		this.radius = radius;
	}
	
	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.drawOval(getPosition().x, getPosition().y, radius, radius);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		circle copy = new circle(radius);
		copy.setColor(this.getColor());
		copy.setFillColor(this.getFillColor());
		copy.setPosition(this.getPosition());
		copy.setProperties(this.getProperties());
		return copy;
	}
}
