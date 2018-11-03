package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DrawEngine implements DrawingEngine{

	/**
	 * current shapes.
	 */
	private ArrayList<Shape> shapes = new ArrayList<>(); 
	
	/**
	 * 2 stacks for undo and redo.
	 */
	private Stack<operationDone> undo = new Stack<>();
	/**
	 * this stack is cleared 
	 */
	private Stack<operationDone> redo = new Stack<>();
	
	@Override
	public void refresh(Graphics canvas) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		// TODO Auto-generated method stub
		shapes.add(shape);
		operationDone op = new operationDone(shape, 1);
		undo.add(op);
		redo.clear();
	}

	@Override
	public void removeShape(Shape shape) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i).equals(shape)) {
				shapes.remove(i);
			}
		}
		operationDone op = new operationDone(shape, 2);
		undo.add(op);
		redo.clear();
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		// TODO Auto-generated method stub
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i).equals(oldShape)) {
				shapes.remove(i);
				shapes.add(newShape);
				break;
			}
		}
		operationDone op = new operationDone(oldShape, newShape);
		undo.add(op);
		redo.clear();
	}

	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
		if (shapes.isEmpty()) {
			return null;
		}
		Shape[] ArrayOfShapes = new Shape[shapes.size()];
		for (int i = 0; i < shapes.size(); i++) {
			ArrayOfShapes[i] = shapes.get(i);
		}
		return ArrayOfShapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * do the opposite of last operation.
	 */
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		if (undo.isEmpty() || redo.size() == 20) {
			
		} else {
			redo.add(undo.peek());
			undo.pop();
			switch (redo.peek().operation) {
			case 1:
				removeShape(redo.peek().shape1);
				break;
			case 2:
				addShape(redo.peek().shape1);
				break;
			case 3:
				updateShape(redo.peek().shape2, redo.peek().shape1);
				break;
			}
		}
	}
	
	/**
	 * do the same last undo operation.
	 */
	@Override
	public void redo() {
		// TODO Auto-generated method stub
		if (redo.isEmpty()) {
			
		} else {
			undo.add(redo.pop());
			switch (undo.peek().operation) {
			case 1:
				addShape(undo.peek().shape1);
				break;
			case 2:
				removeShape(undo.peek().shape1);
				break;
			case 3:
				updateShape(undo.peek().shape1, undo.peek().shape2);
				break;
			}
		}
	}

	/**
	 * open new json/xml file with custom name.
	 * the add in all the shapes.
	 */
	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		
	}

	public void saveXml() {
		// TODO Auto-generated method stub
		
	}
	
	public void saveJson() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * read from the selected file and refresh the screen.
	 */
	@Override
	public void load(String path) {
		// TODO Auto-generated method stub
		
	}

}
