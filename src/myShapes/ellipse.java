package myShapes;

import java.awt.Graphics;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class ellipse extends myShape {

	int width = 0,height = 0;
	Map<String, Double> properties;
	
	public ellipse (int width, int height) {
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.drawOval(getPosition().x, getPosition().y, width, height);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ellipse copy = new ellipse(width, height);
		copy.setColor(this.getColor());
		copy.setFillColor(this.getFillColor());
		copy.setPosition(this.getPosition());
		copy.setProperties(this.getProperties());
		return copy;
	}
}
