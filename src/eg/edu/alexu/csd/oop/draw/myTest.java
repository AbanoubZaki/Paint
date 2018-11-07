package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;

import eg.edu.alexu.csd.oop.test.DummyShape;

public class myTest {

	public static void main(String[] args) {
		DrawEngine d = new DrawEngine();
		Shape triangle = new triangle(
				new Double[] {10.0 , 250.0, 20.0}, new Double[] {10.0 , 50.0, 100.0});
		Shape square = new square(new Point(75, 250), 40.0);
		Shape circle = new circle(new Point(20,50), 20.0);
		Shape ellipse = new ellipse(new Point(20,50), 60.0, 30.0);
		Shape line = new lineSegment(new Point(20,50), new Point(20,50));
		Shape rect = new rectangle(new Point(20,50), 42.0, 60.0);
		
		Shape shape1 = new DummyShape();
        shape1.setColor(Color.RED);
        d.addShape(square);
		d.addShape(shape1);
		d.addShape(rect);
		d.addShape(ellipse);
		d.addShape(line);
		d.addShape(triangle);
		d.addShape(circle);
		d.load("C:\\Users\\Abanoub Ashraf\\Desktop\\test save\\saved.XmL.txt");
	}
}
