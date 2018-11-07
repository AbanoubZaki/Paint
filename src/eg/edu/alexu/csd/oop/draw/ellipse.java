package eg.edu.alexu.csd.oop.draw;

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
    
    /**
     * no argument constructor.
     */
    public ellipse() {
	}
    
	public ellipse(Point position, Double horizontalRadius, Double verticalRadius) {
		// TODO Auto-generated constructor stub
		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
		setPosition(position);
	}
	
	@Override
	public void draw(java.awt.Graphics canvas) {
		// TODO Auto-generated method stub
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
		
		this.properties.put("horizontalRadius", horizontalRadius);
		this.properties.put("verticalRadius", verticalRadius);
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
		ellipse copy = new ellipse(getPosition(), horizontalRadius, verticalRadius);
		copy.setColor(getColor());
		copy.setFillColor(getFillColor());
		return copy;
	}
}
