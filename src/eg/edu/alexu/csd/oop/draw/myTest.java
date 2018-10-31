package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.test.DummyShape;

public class myTest {

	public static void main(String[] args) {
		DrawEngine d = new DrawEngine();
		Shape sh1= new DummyShape();
		d.addShape(sh1);
		Shape sh2= new DummyShape();
		d.addShape(sh2);
		Shape sh3= new DummyShape();
		d.updateShape(sh1, sh3);
		d.removeShape(sh2);
		d.undo();
		d.undo();
		d.undo();
		d.undo();
		System.out.println(d.getShapes().length);
		
	}
}
