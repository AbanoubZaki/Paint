package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DrawEngine implements DrawingEngine {

//	 singelton pattern.
	
	private static DrawEngine instance = new DrawEngine(); 
	
	private DrawEngine() {
	}
	
	public static DrawEngine getInstance() {
		return instance;
	}
	/**
	 * boolean for undoRedo methods
	 */
	private boolean boolForUndoRedo = false;

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
	
	public String JarPath = "";
	
	@Override
	public void refresh(Graphics canvas) {
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(canvas);
		}
	}

	@Override
	public void addShape(Shape shape) {
		
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
		
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i).equals(oldShape)) {
				shapes.remove(i);
				shapes.add(i, newShape);
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
		
		Shape[] ArrayOfShapes = new Shape[shapes.size()];
		for (int i = 0; i < shapes.size(); i++) {
			ArrayOfShapes[i] = shapes.get(i);
		}
		return ArrayOfShapes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> inheritedclasses = new LinkedList<>();
		String folder = "eg/edu/alexu/csd/oop/draw";
		ClassLoader loader = ClassLoader.getSystemClassLoader();// this.getClass().getClassLoader();//Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(folder);
		String path = url.getPath();
		File[] files = new File(path).listFiles();
		for (File f : files) {
			String string = folder + "/" + f.getName().substring(0, f.getName().length() - 6);
			Class<?> check = null;
			boolean isshape = false;
			try {
				check = Class.forName(string.replace('/', '.'));
				isshape = myShape.class.isAssignableFrom(check);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (isshape && !f.getName().contains("myShape")) {
				inheritedclasses.add((Class<? extends Shape>) check);
			}
		}
		if (JarPath.equals("") || !JarPath.contains(".jar")) {
			inheritedclasses.add((Class<? extends Shape>) externalJar.getInstance().installPluginShape("RoundRectangle.jar"));
		} else {
			inheritedclasses.add((Class<? extends Shape>) externalJar.getInstance().installPluginShape(JarPath));
			path = null;
		}
		
//		/**
//		 * get external jar classes that inherits from shape interface.
//		 */
//		List<Class<? extends Shape>> externalInheritedclasses = new LinkedList<>();
//		try {
//			externalInheritedclasses = externalJar.getInstance().supportedShape();
//			for (int i = 0; i < externalInheritedclasses.size(); i++) {
//				inheritedclasses.add(externalInheritedclasses.get(i));
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String folder = "eg/edu/alexu/csd/oop/draw";
//		ClassLoader loader = ClassLoader.getSystemClassLoader();// this.getClass().getClassLoader();//Thread.currentThread().getContextClassLoader();
//		URL url = loader.getResource(folder);
//		String path = url.getPath();
//		File[] files = new File(path).listFiles();
//		for (File f : files) {
//			String string = folder + "/" + f.getName().substring(0, f.getName().length() - 6);
//			Class<?> check = null;
//			boolean isshape = false;
//			try {
//				check = Class.forName(string.replace('/', '.'));
//				isshape = myShape.class.isAssignableFrom(check);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//
//			if (isshape && !f.getName().contains("myShape")) {
//				inheritedclasses.add((Class<? extends Shape>) check);
//			}
//		}
//		try {
//			inheritedclasses.add(classloading(new File("RoundRectangle.jar"), folder.replace('/', '.')));
//			// inheritedclasses.add(classloading(new File("DummyShape.jar"),
//			// folder.replace('/', '.')));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}

		return inheritedclasses;
	}

	/**
	 * do the opposite of last operation.
	 */
	@Override
	public void undo() {
		boolForUndoRedo = true;
		
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
		if (path.contains(".XmL") || path.contains(".Xml")) {
			SaveAndLoad.getInstance().saveToXmlFile(path, shapes);
		} else if (path.contains(".JsOn")) {
			SaveAndLoad.getInstance().saveToJsOnFile(path, shapes);
		}
	}

	/**
	 * read from the selected file and refresh the screen.
	 */
	@Override
	public void load(String path) {
		shapes.clear();
		undo.clear();
		redo.clear();
		List<Class<? extends Shape>> mySupportedShapes = getSupportedShapes();
		File f1 = new File(path);
		if ((f1.getName()).contains("XmL") || (f1.getName()).contains("Xml")) {
			shapes = SaveAndLoad.getInstance().loadFromXmlFile(path, mySupportedShapes);
		} else if ((f1.getName()).contains("JsOn")) {
			shapes = SaveAndLoad.getInstance().loadFromJsonFile(path, mySupportedShapes);
		} else {
			return;
		}
	}

}