package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class painting extends JFrame{

	private JFrame frmPaint;
	private DrawEngine engine = new DrawEngine();
	private String selectedShape = new String("");
	JComboBox<String> comboBox;
	Canvas canvas;
	Point position1, position2;
	Shape shape;
	ArrayList<Shape> shapes = new ArrayList<>();
	JPanel frameColorPanel, fillColorPanel;
	Color frameColor,fillColor;
	boolean isColorFill = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					painting window = new painting();
					window.frmPaint.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public painting() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/**
		 * a map to set properties of each shape;
		 */
		Map<String, Double> properties = new HashMap<>();
		
		frmPaint = new JFrame();
		frmPaint.setType(Type.POPUP);
		frmPaint.setTitle("Paint");
		frmPaint.setResizable(false);
		frmPaint.setBounds(100, 100, 870, 494);
		frmPaint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 250));
		frmPaint.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		canvas = new Canvas();
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (!selectedShape.equals("")) {
					position2 = e.getPoint();
					int minX = Math.min(position1.x, position2.x);
					int maxX = Math.max(position1.x, position2.x);
					int minY = Math.min(position1.y, position2.y);
					int maxY = Math.max(position1.y, position2.y);
					
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					engine.refresh(canvas.getGraphics());
					switch (selectedShape) {
					case "circle":
						properties.put("radius", position1.distance(position2));
						break;
					case "ellipse":
						if (position2.x > position1.x && position2.y < position1.y) {
							//1st quad
							shape.setPosition(new Point(position1.x, position2.y));
							
						} else if (position2.x < position1.x && position2.y > position1.y) {
							//3rd quad
							shape.setPosition(new Point(position2.x, position1.y));

						} else if (position2.x < position1.x && position2.y < position1.y) {
							//2nd quad
							shape.setPosition(new Point(position2.x, position2.y));
						}
						
						properties.put("horizontalRadius", (double) (maxX-minX));
						properties.put("verticalRadius", (double) (maxY-minY));		
						break;
					case "lineSegment":
						properties.put("x1", (double) position1.x);
						properties.put("y1", (double) position1.y);
						properties.put("x2", (double) position2.x);
						properties.put("y2", (double) position2.y);
						break;
					case "square":
						if ((maxX-minX) > (maxY-minY)) {
							properties.put("length", (double) (maxX-minX));
						} else {
							properties.put("length", (double) (maxY-minY));
						}
						break;
					case "rectangle":
						if (position2.x > position1.x && position2.y < position1.y) {
							//1st quad
							shape.setPosition(new Point(position1.x, position2.y));
							
						} else if (position2.x < position1.x && position2.y > position1.y) {
							//3rd quad
							shape.setPosition(new Point(position2.x, position1.y));

						} else if (position2.x < position1.x && position2.y < position1.y) {
							//2nd quad
							shape.setPosition(new Point(position2.x, position2.y));
						}
						
						properties.put("length", (double) (maxX-minX));
						properties.put("width", (double) (maxY-minY));
						break;
					case "triangle":
						properties.put("x1", (double) position1.x);
						properties.put("y1", (double) position1.y);
						properties.put("x2", (double) Math.abs(maxX-minX));
						if (position2.y > position1.y) {
							properties.put("y2", (double) Math.abs(maxY));
						} else {
							properties.put("y2", (double) Math.abs(minY));
						}
						properties.put("x3", (double) position2.x);
						properties.put("y3", (double) position1.y);
						break;
					default:
						break;
					}
					
					shape.setProperties(properties);
					shape.draw(canvas.getGraphics());
				}
			}
		});
		
		canvas.addMouseListener(new MouseAdapter() {	
			@Override
			public void mousePressed(MouseEvent e) {
				if (!selectedShape.equals("")) {
					position1 = e.getPoint();
					switch (selectedShape) {
					case "circle":
						shapes.add(new circle());
						break;
					case "ellipse":
						shapes.add(new ellipse());	
						break;
					case "lineSegment":
						shapes.add(new lineSegment());
						break;
					case "square":
						shapes.add(new square());
						break;
					case "rectangle":
						shapes.add(new rectangle());
						break;
					case "triangle":
						shapes.add(new triangle());
						break;
					default:
						break;
					}
					shape = shapes.get(shapes.size()-1);
					shape.setPosition(position1);
					shape.setColor(frameColor);
					if (isColorFill) {
						shape.setFillColor(fillColor);
					}
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!selectedShape.equals("")) {
					/**
					 * check if the user only clicked the screen without dragging.
					 */
					if (!(position1.x == e.getPoint().x && position1.y == e.getPoint().y)) {
						engine.addShape(shape);
					}
				}
			}
		});
		canvas.setBounds(10, 38, 844, 417);
		canvas.setBackground(Color.WHITE);
		panel.add(canvas);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("Tool Bar");
		menuBar.setBackground(new Color(95, 158, 160));
		menuBar.setBounds(10, 11, 844, 21);
		panel.add(menuBar);
		
		JButton undo = new JButton("Undo");
		undo.setBackground(new Color(70, 130, 180));
		undo.setForeground(new Color(255, 255, 255));
		menuBar.add(undo);
		undo.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		JButton redo = new JButton("Redo");
		redo.setBackground(new Color(70, 130, 180));
		redo.setForeground(new Color(255, 255, 255));
		menuBar.add(redo);
		redo.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		JButton save = new JButton("Save");
		save.setBackground(new Color(70, 130, 180));
		save.setForeground(new Color(255, 255, 255));
		menuBar.add(save);
		save.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				engine.load("C:\\Users\\Abanoub Ashraf\\Desktop\\paint project\\test save\\saved.XmL.txt");
				engine.refresh(canvas.getGraphics());
			}
		});
		load.setBackground(new Color(70, 130, 180));
		load.setForeground(new Color(255, 255, 255));
		menuBar.add(load);
		load.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		JButton delete = new JButton("Delete");
		delete.setBackground(new Color(70, 130, 180));
		delete.setForeground(new Color(255, 255, 255));
		menuBar.add(delete);
		delete.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		comboBox = new JComboBox<String>();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(70, 130, 180));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (comboBox.getSelectedIndex()) {
				case 0:
					
					break;
				case 1:
					selectedShape = "circle";
					break;
				case 2:
					selectedShape = "ellipse";
					break;
				case 3:
					selectedShape = "lineSegment";
					break;
				case 4:
					selectedShape = "square";
					break;
				case 5:
					selectedShape = "rectangle";
					break;
				case 6:
					selectedShape = "triangle";
					break;
				default:
					break;
				}
			}
		});
		
		JButton clone = new JButton("Clone");
		menuBar.add(clone);
		clone.setForeground(Color.WHITE);
		clone.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		clone.setBackground(new Color(70, 130, 180));
		menuBar.add(comboBox);
		comboBox.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		comboBox.setMaximumRowCount(20);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Shape", "Circle", "Ellipse", "LineSegment", "Square", "Rectangle", "Triangle"}));
		comboBox.setToolTipText("Draw");
		
		frameColorPanel = new JPanel();
		menuBar.add(frameColorPanel);
		frameColorPanel.setForeground(new Color(0, 0, 0));
		frameColorPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		frameColorPanel.setBackground(new Color(0, 0, 0));
		
		frameColor = frameColorPanel.getBackground();
		
		JButton btnFrameColor = new JButton("Frame Color");
		btnFrameColor.setBackground(new Color(70, 130, 180));
		btnFrameColor.setForeground(new Color(255, 255, 255));
		menuBar.add(btnFrameColor);
		btnFrameColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				frameColor = JColorChooser.showDialog(null, "Select a color please." , Color.BLACK);
				frameColorPanel.setBackground(frameColor);
			}
		});
		
		fillColorPanel = new JPanel();
		menuBar.add(fillColorPanel);
		fillColorPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		fillColorPanel.setBackground(new Color(255, 255, 255));
		fillColor = fillColorPanel.getBackground();
		
		JButton btnFillColor = new JButton("Fill Color");
		btnFillColor.setBackground(new Color(70, 130, 180));
		btnFillColor.setForeground(new Color(255, 255, 255));
		menuBar.add(btnFillColor);
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				isColorFill = true;
				fillColor = JColorChooser.showDialog(null, "Select a color please." , Color.WHITE);
				fillColorPanel.setBackground(fillColor);
			}
		});
		
		JButton refresh = new JButton("Refresh");
		refresh.setBackground(new Color(70, 130, 180));
		refresh.setForeground(new Color(255, 255, 255));
		menuBar.add(refresh);
		refresh.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		JButton button = new JButton("Import Shapes");
		menuBar.add(button);
		button.setBackground(new Color(70, 130, 180));
		button.setForeground(new Color(255, 255, 255));
		button.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getSupportedShapes();
			}
		});
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.refresh(canvas.getGraphics());
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Choose the destination you want to save in.");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
//			    FileNameExtensionFilter f = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
//			    chooser.setFileFilter(f);
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			      engine.save(chooser.getSelectedFile() + "\\saved.XmL.txt");
			      engine.save(chooser.getSelectedFile() + "\\saved.JsOn.txt");
			      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			    } else {
			      System.out.println("No Selection ");
			    }
			    
			}
		});
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.redo();
				engine.refresh(canvas.getGraphics());
			}
		});
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.undo();
				engine.refresh(canvas.getGraphics());
			}
		});
	 
	}
}
