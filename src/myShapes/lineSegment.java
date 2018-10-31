package myShapes;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.myShape;

public class lineSegment extends myShape{
	
	Point secondPoint = new Point();
	Map<String, Double> properties;
	
	public lineSegment (Point secondPoint) {
		// TODO Auto-generated constructor stub
		this.secondPoint = secondPoint;
	}
	
	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub
		canvas.drawLine(getPosition().x, getPosition().y, secondPoint.x, secondPoint.y);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		lineSegment copy = new lineSegment(secondPoint);
		copy.setColor(this.getColor());
		copy.setFillColor(this.getFillColor());
		copy.setPosition(this.getPosition());
		copy.setProperties(this.getProperties());
		return copy;
	}
	
}
