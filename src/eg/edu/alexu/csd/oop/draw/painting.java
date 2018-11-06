package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JFrame;
import javax.swing.JPanel;

import myShapes.circle;
import myShapes.ellipse;
import myShapes.lineSegment;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.Window.Type;

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
		frmPaint.setBounds(100, 100, 607, 466);
		frmPaint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmPaint.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		comboBox.setMaximumRowCount(20);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Circle", "Ellipse", "LineSegment", "Square", "Rectangle", "Triangle"}));
		comboBox.setToolTipText("Draw");
		comboBox.setBounds(338, 11, 126, 28);
		panel.add(comboBox);
		
		JButton redo = new JButton("Redo");
		redo.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, 454, 306);
				engine.redo();
				engine.refresh(canvas.getGraphics());
			}
		});
		redo.setBounds(89, 11, 69, 28);
		panel.add(redo);
		
		JButton undo = new JButton("Undo");
		undo.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.getGraphics().clearRect(0, 0, 454, 306);
				engine.undo();
				engine.refresh(canvas.getGraphics());
			}
		});
		undo.setBounds(10, 11, 69, 28);
		panel.add(undo);
		
		JButton refresh = new JButton("Refresh");
		refresh.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, 454, 306);
				engine.refresh(canvas.getGraphics());
			}
		});
		refresh.setBounds(474, 11, 118, 28);
		panel.add(refresh);
		
		JButton delete = new JButton("Delete");
		delete.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		delete.setBounds(247, 11, 81, 28);
		panel.add(delete);
		
		JButton button = new JButton("Import Shapes");
		button.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getSupportedShapes();
			}
		});
		button.setBounds(474, 53, 118, 28);
		panel.add(button);
		
		JButton save = new JButton("Save");
		save.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		save.setBounds(168, 11, 69, 28);
		panel.add(save);
		
		JButton load = new JButton("Load");
		load.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		load.setBounds(474, 331, 118, 28);
		panel.add(load);
		
		JButton circle = new JButton("Circle");
		circle.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = "circle";
			}
		});
		circle.setBounds(474, 131, 118, 23);
		panel.add(circle);
		
		JButton ellipse = new JButton("Ellipse");
		ellipse.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		ellipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = "ellipse";
			}
		});
		ellipse.setBounds(474, 165, 118, 23);
		panel.add(ellipse);
		
		JButton linesegment = new JButton("Line Segment");
		linesegment.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		linesegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = "lineSegment";
			}
		});
		linesegment.setBounds(474, 199, 118, 23);
		panel.add(linesegment);
		
		JButton square = new JButton("Square");
		square.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = "square";
			}
		});
		square.setBounds(474, 233, 118, 23);
		panel.add(square);
		
		JButton rectangle = new JButton("Rectangle");
		rectangle.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = "rectangle";
			}
		});
		rectangle.setBounds(474, 267, 118, 23);
		panel.add(rectangle);
		
		JButton triangle = new JButton("Triangle");
		triangle.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = "triangle";
			}
		});
		triangle.setBounds(474, 297, 118, 23);
		panel.add(triangle);
		
		canvas = new Canvas();
		
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (!selectedShape.equals("")) {
					if(e.getPoint().x > 454 || e.getPoint().x < 0 || e.getPoint().y > 306 || e.getPoint().y < 0) {
						
					} else {
						position2 = e.getPoint();
					}
					int minX = Math.min(position1.x, position2.x);
					int maxX = Math.max(position1.x, position2.x);
					int minY = Math.min(position1.y, position2.y);
					int maxY = Math.max(position1.y, position2.y);
					
					canvas.getGraphics().clearRect(0, 0, 454, 306);
					engine.refresh(canvas.getGraphics());
					switch (selectedShape) {
					case "circle":
						properties.put("radius", position1.distance(position2));
						break;
					case "ellipse":
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
						shapes.add(new myShapes.square());
						break;
					case "rectangle":
						shapes.add(new myShapes.rectangle());
						break;
					case "triangle":
						shapes.add(new myShapes.triangle());
						break;
					default:
						break;
					}
					shape = shapes.get(shapes.size()-1);
					shape.setPosition(position1);
					shape.setColor(frameColor);
					shape.setFillColor(fillColor);
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!selectedShape.equals("")) {					
					engine.addShape(shape);
				}
			}
		});
		canvas.setBounds(10, 53, 454, 306);
		canvas.setBackground(Color.WHITE);
		panel.add(canvas);
		
		frameColorPanel = new JPanel();
		frameColorPanel.setForeground(new Color(0, 0, 0));
		frameColorPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		frameColorPanel.setBackground(new Color(0, 0, 0));
		frameColorPanel.setBounds(23, 365, 28, 28);
		panel.add(frameColorPanel);
		
		fillColorPanel = new JPanel();
		fillColorPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		fillColorPanel.setBackground(new Color(255, 255, 255));
		fillColorPanel.setBounds(23, 404, 28, 28);
		panel.add(fillColorPanel);
		
		JButton btnFrameColor = new JButton("Frame Color");
		btnFrameColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				frameColor = JColorChooser.showDialog(null, "Select a color please." , Color.BLACK);
				frameColorPanel.setBackground(frameColor);
			}
		});
		btnFrameColor.setBounds(61, 365, 114, 28);
		panel.add(btnFrameColor);
		
		JButton btnFillColor = new JButton("Fill Color");
		btnFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				fillColor = JColorChooser.showDialog(null, "Select a color please." , Color.WHITE);
				fillColorPanel.setBackground(fillColor);
			}
		});
		btnFillColor.setBounds(61, 404, 114, 28);
		panel.add(btnFillColor);
		
		frameColor = frameColorPanel.getBackground();
		fillColor = fillColorPanel.getBackground();
	 
	}
}
