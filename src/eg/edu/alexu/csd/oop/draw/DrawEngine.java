package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		myShape shape = null;
		String pointX = ".\\w.(-?\\d+)..\\w.";
		String shapeStartPattern = ".shape id=\"(\\D+)..";
		String mapItemsPattern = ".(\\w+).(\\d+\\.\\d+)..(\\w+).";
		String colorPattern = "<\\w+>(\\S+)</\\w+>";
		String stringFromRegex;
		double value;
		Color color;
		/**
		 * patterns contains: 0 -> class name 1 -> point x & y
		 */
		ArrayList<String> patterns = new ArrayList<String>();
		patterns.add(shapeStartPattern);
		patterns.add(pointX);
		patterns.add(mapItemsPattern);
		Pattern p;

		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String fromFile;
			fromFile = br.readLine();

			while (true) {
				/**
				 * read class name.
				 */
				fromFile = br.readLine();
				p = Pattern.compile(patterns.get(0));
				Matcher xml = p.matcher(fromFile);
				if (xml.find()) {
					stringFromRegex = xml.group(1);
					System.out.println(stringFromRegex);
					Class<?> clazz;
					try {
						clazz = Class.forName(stringFromRegex);
						shape = (myShape) clazz.newInstance();
						Map<String, Double> theMap = shape.getProperties();

						Point thePoint = new Point();

						/**
						 * read point x.
						 */
						fromFile = br.readLine();
						p = Pattern.compile(pointX);
						xml = p.matcher(fromFile);
						if (xml.find()) {
							stringFromRegex = xml.group(1);
							System.out.println(stringFromRegex);
							thePoint.x = Integer.parseInt(stringFromRegex);
						}

						/**
						 * read point y.
						 */
						fromFile = br.readLine();
						p = Pattern.compile(pointX);
						xml = p.matcher(fromFile);
						if (xml.find()) {
							stringFromRegex = xml.group(1);
							System.out.println(stringFromRegex);
							thePoint.y = Integer.parseInt(stringFromRegex);
						}
						shape.setPosition(thePoint);

						/**
						 * read the map.
						 */
						fromFile = br.readLine();

						fromFile = br.readLine();
						p = Pattern.compile(mapItemsPattern);
						xml = p.matcher(fromFile);
						while (true) {
							if (xml.find()) {
								stringFromRegex = xml.group(1);
								System.out.println(stringFromRegex);
								value = Double.parseDouble(xml.group(2));
								System.out.println(value);
								theMap.put(stringFromRegex, value);
							} else {
								break;
							}
							fromFile = br.readLine();
							xml = p.matcher(fromFile);
						}

						/**
						 * read color.
						 */
						fromFile = br.readLine();
						p = Pattern.compile(colorPattern);
						xml = p.matcher(fromFile);
						if (xml.find()) {
							color = new Color(Integer.parseInt(xml.group(1)));
							shape.setColor(color);
							System.out.println(color);
						}

						/**
						 * read fill color.
						 */
						fromFile = br.readLine();
						p = Pattern.compile(colorPattern);
						xml = p.matcher(fromFile);
						if (xml.find()) {
							color = new Color(Integer.parseInt(xml.group(1)));
							shape.setFillColor(color);
							System.out.println(color);
						}

					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
					}
					shapes.add(shape);
					fromFile = br.readLine();
				} else {
					break;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFromJsonFile(String path) {

	}

}
