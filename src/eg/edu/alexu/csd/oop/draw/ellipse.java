package eg.edu.alexu.csd.oop.draw;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class ellipse extends myShape {

	private Double horizontalRadius = 0.0 ;
	
	private Double verticalRadius = 0.0 ;
	
	/**
     * properties of the shape.
     */
    private Map<String, Double> properties = new HashMap<>();
    
    /**
     * no argument constructor.
     */
    public ellipse() {
    	this.properties.put("horizontalRadius", null);
		this.properties.put("verticalRadius", null);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		if (getFillColor() != null) {
			canvas.setColor(getFillColor());
			canvas.fillOval(getPosition().x, getPosition().y, (int) Math.round(horizontalRadius), (int) Math.round(verticalRadius));
		}
		canvas.setColor(getColor());
		canvas.drawOval(getPosition().x, getPosition().y, (int) Math.round(horizontalRadius), (int) Math.round(verticalRadius));
	}
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
		horizontalRadius = properties.get("horizontalRadius");
		verticalRadius = properties.get("verticalRadius");
		
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
		ellipse copy = new ellipse();
		copy.setPosition(new Point(getPosition().x, getPosition().y));
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		Map <String, Double> propertiesCopy = new HashMap<String, Double>();
		propertiesCopy.putAll(getProperties());
		copy.setProperties(propertiesCopy);
		return copy;
	}
}
