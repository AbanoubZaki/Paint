package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;

public class myShape implements Shape{

	/**
	 * center of the shape.
	 */
	Point position = new Point(0,0);
	/**
	 * frame color of the shape.
	 */
	Color frameColor = new Color(0);
	/**
	 * fill color of the shape.
	 */
	Color fillColor = new Color(0);
	
	public void setPosition(java.awt.Point position) {
		this.position = position;
	}
	
    public java.awt.Point getPosition() {
		return position;
	}

    /**
     * contains each main property of the shape.
     * implemented in each class extending shape
     */
    // update shape specific properties (e.g., radius)
    public void setProperties(java.util.Map<String, Double> properties) {
    	
	}
    public java.util.Map<String, Double> getProperties() {
		return null;
	}

    public void setColor(java.awt.Color color) {
    	this.frameColor = color;
	}
    public java.awt.Color getColor() {
		return frameColor;
	}

    public void setFillColor(java.awt.Color color) {
    	this.fillColor = color;
	}
    
    public java.awt.Color getFillColor() {
		return fillColor;
	}

    /**
     * the method which draws the shape on the screen.
     * implemented in each class extending shape.
     */
    public void draw(java.awt.Graphics canvas) {
	} // redraw the shape on the canvas

    /**
     * makes a copy of the shape.
     * implemented in each class extending shape
     */
    public Object clone() throws CloneNotSupportedException {
		return null;
	} // create a deep clone of the shape
    
}
