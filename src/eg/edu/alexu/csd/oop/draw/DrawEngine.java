package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DrawEngine implements DrawingEngine {
	
	/**
	 * boolean for undoRedo methods
	 */
	boolean boolForUndoRedo = false;

	/**
	 * current shapes.
	 */
	public ArrayList<Shape> shapes = new ArrayList<>();

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
		if (redo.size() == 20) {
			undo.clear();
		}
		if (boolForUndoRedo) {
			return;
		}
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
		if (redo.size() == 20) {
			undo.clear();
		}
		if (boolForUndoRedo) {
			return;
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
		if (redo.size() == 20) {
			undo.clear();
		}
		if (boolForUndoRedo) {
			return;
		}
		operationDone op = new operationDone(oldShape, newShape);
		undo.add(op);
		redo.clear();
	}

	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
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
		boolForUndoRedo = true;
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
		boolForUndoRedo = false;
	}

	/**
	 * do the same last undo operation.
	 */
	@Override
	public void redo() {
		boolForUndoRedo = true;
		// TODO Auto-generated method stub
		if (redo.isEmpty()) {

		} else {
			undo.add(redo.peek());
			redo.pop();
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
		boolForUndoRedo = false;
	}

	/**
	 * open new json/xml file with custom name. the add in all the shapes.
	 */
	@Override
	public void save(String path) {
		// TODO Auto-generated method stub
		if (path.contains(".XmL")) {
			
			final String paintStart = "<paint>";
			final String paintEnd = "</paint>";
			final String shapeStart = "<shape id=\"";
			final String shapetEnd = "</shape>";
			final String mapStart = "<map>";
			final String mapEnd = "</map>";
			final String colorStart = "<color>";
			final String colorEnd = "</color>";
			final String fillcolorStart = "<fillcolor>";
			final String fillcolorEnd = "</fillcolor>";
			final String xStart = "<x>";
			final String xEnd = "</x>";
			final String yStart = "<y>";
			final String yEnd = "</y>";
			
			File saveXmL = new File(path);
			try {
				saveXmL.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			pw.println(paintStart);
			for (int i = 0; i < shapes.size(); i++) {
				pw.println(shapeStart + shapes.get(i).getClass().getCanonicalName() + "\">");
				if (shapes.get(i).getPosition() == null) {
					pw.println(xStart + -1 + xEnd);
					pw.println(yStart + -1 + yEnd);
				} else {
					pw.println(xStart + shapes.get(i).getPosition().x + xEnd);
					pw.println(yStart + shapes.get(i).getPosition().y + yEnd);
				}
				pw.println(mapStart);
				/**
				 * write properties of the shape from the map of properties.
				 */
				if (shapes.get(i).getProperties() == null) {
					pw.println("<key>-1</key>");
				} else {
					List<String> keys = new ArrayList<String>(shapes.get(i).getProperties().keySet());
					String property = "";
					for (int j = 0; j < shapes.get(i).getProperties().size(); j++) {
						property += "<" + keys.get(j) + ">" + 
						shapes.get(i).getProperties().get(keys.get(j)) + "</" + keys.get(j) + ">";
						pw.println(property);
						property = "";
					}	
				}
				pw.println(mapEnd);
				if (shapes.get(i).getColor() == null) {
					pw.println(colorStart + -1 + colorEnd);
				} else {
					pw.println(colorStart + shapes.get(i).getColor().getRGB() + colorEnd);
				}
				if (shapes.get(i).getFillColor() == null) {
					pw.println(fillcolorStart + -1 + fillcolorEnd);
				} else {
					pw.println(fillcolorStart + shapes.get(i).getFillColor().getRGB() + fillcolorEnd);
				}
				pw.println(shapetEnd);
			}
			pw.println(paintEnd);
			pw.close();
			
		} else if (path.contains(".JsOn")) {
			String startShapeArray = "{\"ShapeArray\" :";
			String startShape = "{ \"className\" :  \"";
			
			File saveXmL = new File(path);
			try {
				saveXmL.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			pw.println(startShapeArray);
			pw.println("[");
			for (int i = 0; i < shapes.size(); i++) {
				pw.println(startShape + shapes.get(i).getClass().getCanonicalName() + "\",");
				if (shapes.get(i).getPosition() == null) {
					pw.println("\"x\" : \"" + -1 + "\",");
					pw.println("\"y\" : \"" + -1 + "\",");
				} else {
					pw.println("\"x\" : \"" + shapes.get(i).getPosition().x + "\",");
					pw.println("\"y\" : \"" + shapes.get(i).getPosition().y + "\",");
				}
				/**
				 * write properties of the shape from the map of properties.
				 */
				if (shapes.get(i).getProperties() == null) {
					pw.println("\"key\" : \"-1\",");
				} else {
					List<String> keys = new ArrayList<String>(shapes.get(i).getProperties().keySet());
					String property = "\"";
					for (int j = 0; j < shapes.get(i).getProperties().size(); j++) {
						property += keys.get(j) + "\" : \"" + 
						shapes.get(i).getProperties().get(keys.get(j)) + "\",";
						pw.println(property);
						property = "\"";
					}	
				}
				
				if (shapes.get(i).getColor() == null) {
					pw.println("\"color\" : \" -1\",");
				} else {
					pw.println("\"color\" : \" " + shapes.get(i).getColor().getRGB() + "\",");
				}
				if (shapes.get(i).getFillColor() == null) {
					pw.println("\"fillcolor\" : \" -1\"");
				} else {
					pw.println("\"fillcolor\" : \" " + shapes.get(i).getFillColor().getRGB() + "\",");
				}
				if (i == shapes.size()-1) {
					pw.println("}");
				} else {
					pw.println("},");
				}
			}
			pw.println("]");
			pw.println("}");
			pw.close();
		}
	}

	/**
	 * read from the selected file and refresh the screen.
	 */
	@Override
	public void load(String path) {
		File f1 = new File(path);
		if ((f1.getName()).contains("XmL")) {
			loadFromXmlFile(path);
		} else if((f1.getName()).contains("JsOn")) {
			loadFromJsonFile(path);
		} else {
			return;
		}
	}

	private void loadFromXmlFile(String path) {

	}

	private void loadFromJsonFile(String path) {

	}

}
