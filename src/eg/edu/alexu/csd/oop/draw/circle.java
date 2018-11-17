package eg.edu.alexu.csd.oop.draw;

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
    	this.properties.put("radius", null);
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
		
		this.properties = properties;
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
		circle copy = new circle();
		copy.setPosition(new Point(getPosition().x + 10, getPosition().y + 10));
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		Map <String, Double> propertiesCopy = new HashMap<String, Double>();
		propertiesCopy.putAll(getProperties());
		copy.setProperties(propertiesCopy);
		return copy;
	}
}
