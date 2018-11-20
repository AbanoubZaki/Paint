package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveAndLoad {

	// singelton pattern.

	private static SaveAndLoad instance = new SaveAndLoad();

	private SaveAndLoad() {

	}

	public static SaveAndLoad getInstance() {
		return instance;
	}

	public void saveToXmlFile(String path, ArrayList<Shape> shapes) {
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
					property += "<" + keys.get(j) + ">" + shapes.get(i).getProperties().get(keys.get(j)) + "</"
							+ keys.get(j) + ">";
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
	}

	public void saveToJsOnFile(String path, ArrayList<Shape> shapes) {
		String startShapeArray = "{\"ShapeArray\" :";
		String startShape = "{ \"className\" :  \"";

		File saveXmL = new File(path);
		try {
			saveXmL.createNewFile();
		} catch (IOException e) {
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
					property += keys.get(j) + "\" : \"" + shapes.get(i).getProperties().get(keys.get(j)) + "\",";
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
			if (i == shapes.size() - 1) {
				pw.println("}");
			} else {
				pw.println("},");
			}
		}
		pw.println("]");
		pw.println("}");
		pw.close();

	}

	public ArrayList<Shape> loadFromXmlFile(String path, List<Class<? extends Shape>> mySupportedShapes) {
		String shapeStartPattern = ".shape id=\"(\\D+)..";
		String point = ".\\w.(-?\\d+)..\\w.";
		String mapItemsPattern = ".(\\w+).((\\d+\\.\\d+)|(null))..(\\w+).";
		String colorPattern = "<\\w+>(\\S+)</\\w+>";
		/**
		 * patterns contains: 0 -> class name 1 -> point x & y 2 -> map items 3 -> color
		 */
		ArrayList<String> patterns = new ArrayList<String>();
		patterns.add(shapeStartPattern);
		patterns.add(point);
		patterns.add(mapItemsPattern);
		patterns.add(colorPattern);
		Shape shape = null;
		String stringFromRegex;
		Double value = null;
		Color color;
		Pattern p;
		ArrayList<Shape> shapes = new ArrayList<>();
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
					Class<?> clazz = null;
					try {
						for (int i = 0; i < mySupportedShapes.size(); i++) {
							System.out.println(mySupportedShapes.get(i).getCanonicalName());
							if (stringFromRegex.equals(mySupportedShapes.get(i).getCanonicalName())) {
								clazz = mySupportedShapes.get(i);
							}
						}
						if (mySupportedShapes.contains(clazz)) {
							shape = (Shape) clazz.newInstance();
							Map<String, Double> theMap = new HashMap<String, Double>();
							Point thePoint = new Point();
							/**
							 * read point x.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(1));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								stringFromRegex = xml.group(1);
								thePoint.x = Integer.parseInt(stringFromRegex);
							}
							/**
							 * read point y.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(1));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								stringFromRegex = xml.group(1);
								thePoint.y = Integer.parseInt(stringFromRegex);
							}
							shape.setPosition(thePoint);
							/**
							 * read the map.
							 */
							fromFile = br.readLine();
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(2));
							xml = p.matcher(fromFile);
							while (true) {
								if (xml.find()) {
									stringFromRegex = xml.group(1);
									if (xml.group(2).equals("null")) {
										value = -1.0;
									} else {
										value = Double.parseDouble(xml.group(2));
									}
									theMap.put(stringFromRegex, value);
								} else {
									break;
								}
								fromFile = br.readLine();
								xml = p.matcher(fromFile);
							}
							shape.setProperties(theMap);
							/**
							 * read color.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(3));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								color = new Color(Integer.parseInt(xml.group(1)));
								shape.setColor(color);
							}
							/**
							 * read fill color.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(3));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								color = new Color(Integer.parseInt(xml.group(1)));
								shape.setFillColor(color);
							}
						}
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					} finally {
					}
					shapes.add(shape);
					fromFile = br.readLine();
				} else {
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shapes;
	}

	public ArrayList<Shape> loadFromJsonFile(String path, List<Class<? extends Shape>> mySupportedShapes) {
		String shapeStartPattern = ".\"className\" :  .(\\D+)..";
		String point = ".\\w. . .(-?\\d+)..";
		String mapItemsPattern = ".(\\w+). . .((\\d+\\.\\d+)|(null))..";
		String colorPattern01 = ".\\w+. . . (\\S+)..";
		String colorPattern02 = ".\\w+. . . (-\\d+)..?";
		/**
		 * patterns contains: 0 -> class name 1 -> point x & y 2 -> map items 3 -> color
		 */
		ArrayList<String> patterns = new ArrayList<String>();
		patterns.add(shapeStartPattern);
		patterns.add(point);
		patterns.add(mapItemsPattern);
		patterns.add(colorPattern01);
		patterns.add(colorPattern02);
		Shape shape = null;
		String stringFromRegex;
		Double value = null;
		Color color;
		Pattern p;
		ArrayList<Shape> shapes = new ArrayList<>();
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String fromFile;
			fromFile = br.readLine();
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
					Class<?> clazz = null;
					try {
						for (int i = 0; i < mySupportedShapes.size(); i++) {
							System.out.println(mySupportedShapes.get(i).getCanonicalName());
							if (stringFromRegex.equals(mySupportedShapes.get(i).getCanonicalName())) {
								clazz = mySupportedShapes.get(i);
							}
						}
						if (mySupportedShapes.contains(clazz)) {
							shape = (Shape) clazz.newInstance();
							Map<String, Double> theMap = new HashMap<>();
							Point thePoint = new Point();
							/**
							 * read point x.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(1));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								stringFromRegex = xml.group(1);

								thePoint.x = Integer.parseInt(stringFromRegex);
							}
							/**
							 * read point y.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(1));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								stringFromRegex = xml.group(1);

								thePoint.y = Integer.parseInt(stringFromRegex);
							}
							shape.setPosition(thePoint);
							/**
							 * read the map.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(2));
							xml = p.matcher(fromFile);
							while (!stringFromRegex.equals("color")) {
								if (xml.find()) {
									stringFromRegex = xml.group(1);

									if (xml.group(2).equals("null")) {
										value = -1.0;
									} else {
										value = Double.parseDouble(xml.group(2));
									}
									theMap.put(stringFromRegex, value);
								} else {
									break;
								}
								fromFile = br.readLine();
								xml = p.matcher(fromFile);
							}
							shape.setProperties(theMap);
							/**
							 * read color.
							 */
							p = Pattern.compile(patterns.get(3));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								System.out.println(xml.group(1));
								color = new Color(Integer.parseInt(xml.group(1)));
								shape.setColor(color);
							}
							/**
							 * read fill color.
							 */
							fromFile = br.readLine();
							p = Pattern.compile(patterns.get(4));
							xml = p.matcher(fromFile);
							if (xml.find()) {
								System.out.println(xml.group(1));
								color = new Color(Integer.parseInt(xml.group(1)));
								shape.setFillColor(color);
							}
						}
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					} finally {
					}
					shapes.add(shape);
					fromFile = br.readLine();
				} else {
					break;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return shapes;
	}
}
