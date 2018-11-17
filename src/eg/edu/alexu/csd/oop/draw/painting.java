package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class painting extends JFrame{

	private JFrame frmPaint;
	private DrawEngine engine = new DrawEngine();
	private String selectedShape = new String("");
	private JComboBox<String> comboBox;
	private Canvas canvas;
	private Point position1, position2;
	private Shape shape;
	private ArrayList<Shape> shapes = new ArrayList<>();
	private String[] supportedShapesNames;
	private int[] indices;
	private List<Class<? extends Shape>> supportedShapes = new LinkedList<>();
	private JPanel frameColorPanel, fillColorPanel;
	private Color frameColor,fillColor; 
	private boolean isColorFill = false;
	private JList<Shape> list;
	private int movementSpeed = 6;
	int index = -1;
  
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
		frmPaint.setBounds(100, 100, 1138, 548);
		frmPaint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(230, 230, 250));
		frmPaint.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		list = new JList<Shape>();
		list.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Shapes", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 128)));
		list.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		list.setForeground(new Color(128, 0, 0));
		list.setVisibleRowCount(25);
		list.setBackground(SystemColor.inactiveCaption);
		list.setBounds(830, 38, 291, 471);
		panel.add(list);
		
		canvas = new Canvas();
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					Map<String, Double> properties = new HashMap<>();
					for (int i = 0 ; i < indices.length; i++) {
						if (engine.getShapes()[indices[i]].toString().contains("lineSegment")) {
							switch (e.getKeyCode()) {
							case 37:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1") - movementSpeed);
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1"));
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2") - movementSpeed);
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2"));
								break;
							case 38:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1"));
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1") - movementSpeed);
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2"));
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2") - movementSpeed);
								break;
							case 39:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1") + movementSpeed);
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1"));
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2") + movementSpeed);
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2"));
								break;
							case 40:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1"));
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1") + movementSpeed);
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2"));
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2") + movementSpeed);
								break;
							default:
								
								break;
							}
							try {
								shape = (Shape) engine.getShapes()[indices[i]].clone();
								shape.setProperties(properties);
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						} else if (engine.getShapes()[indices[i]].toString().contains("triangle")) {
							switch (e.getKeyCode()) {
							case 37:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1") - movementSpeed);
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1"));
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2") - movementSpeed);
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2"));
								properties.put("x3", engine.getShapes()[indices[i]].getProperties().get("x3") - movementSpeed);
								properties.put("y3", engine.getShapes()[indices[i]].getProperties().get("y3"));
								break;
							case 38:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1"));
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1") - movementSpeed);
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2"));
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2") - movementSpeed);
								properties.put("x3", engine.getShapes()[indices[i]].getProperties().get("x3"));
								properties.put("y3", engine.getShapes()[indices[i]].getProperties().get("y3") - movementSpeed);
								break;
							case 39:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1") + movementSpeed);
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1"));
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2") + movementSpeed);
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2"));
								properties.put("x3", engine.getShapes()[indices[i]].getProperties().get("x3") + movementSpeed);
								properties.put("y3", engine.getShapes()[indices[i]].getProperties().get("y3"));
								break;
							case 40:
								properties.put("x1", engine.getShapes()[indices[i]].getProperties().get("x1"));
								properties.put("y1", engine.getShapes()[indices[i]].getProperties().get("y1") + movementSpeed);
								properties.put("x2", engine.getShapes()[indices[i]].getProperties().get("x2"));
								properties.put("y2", engine.getShapes()[indices[i]].getProperties().get("y2") + movementSpeed);
								properties.put("x3", engine.getShapes()[indices[i]].getProperties().get("x3"));
								properties.put("y3", engine.getShapes()[indices[i]].getProperties().get("y3") + movementSpeed);
								break;
							default:
								
								break;
							}
							try {
								shape = (Shape) engine.getShapes()[indices[i]].clone();
								shape.setProperties(properties);
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						} else {
							try {
								shape = (Shape) engine.getShapes()[indices[i]].clone();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							switch (e.getKeyCode()) {
							case 37:
								shape.setPosition(new Point(engine.getShapes()
										[indices[i]].getPosition().x - movementSpeed, engine.getShapes()[indices[i]].getPosition().y));
								break;
							case 38:
								shape.setPosition(new Point(engine.getShapes()
										[indices[i]].getPosition().x, engine.getShapes()[indices[i]].getPosition().y - movementSpeed));
								break;
							case 39:
								shape.setPosition(new Point(engine.getShapes()
										[indices[i]].getPosition().x + movementSpeed, engine.getShapes()[indices[i]].getPosition().y));
								break;
							case 40:
								shape.setPosition(new Point(engine.getShapes()
										[indices[i]].getPosition().x, engine.getShapes()[indices[i]].getPosition().y + movementSpeed));
								break;
							default:
								break;
							}
						}
						engine.updateShape(engine.getShapes()[indices[i]], shape);
					}
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					engine.refresh(canvas.getGraphics());
				}
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (!selectedShape.equals("") && comboBox.getSelectedIndex() <= 6) {
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
					shapes.clear();
					/**
					 * check if the user only clicked the screen without dragging.
					 */
					if (!(position1.x == e.getPoint().x && position1.y == e.getPoint().y)) {
						engine.addShape(shape);
						list.setListData(engine.getShapes());
					}
				}
			}
		});
		canvas.setBounds(10, 38, 814, 471);
		canvas.setBackground(Color.WHITE);
		panel.add(canvas);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("Tool Bar");
		menuBar.setBackground(new Color(95, 158, 160));
		menuBar.setBounds(10, 11, 1049, 21);
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
				
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
//				int option = chooser.showOpenDialog(frmPaint);
			    chooser.setDialogTitle("Choose the file you want to load.");
			    FileNameExtensionFilter f = new FileNameExtensionFilter("Paint Saved Files", "txt", "text");
			    chooser.setFileFilter(f);
			    chooser.setApproveButtonText("Load");
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					engine.load(path);
					System.out.println(engine.getShapes()[0].getPosition());
					System.out.println(engine.getShapes()[0].getColor());
					System.out.println(engine.getShapes()[0].getFillColor());
					System.out.println(engine.getShapes()[0].getProperties());
					
				}
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.refresh(canvas.getGraphics());
				list.setListData(engine.getShapes());
				
			}
		});
		load.setBackground(new Color(70, 130, 180));
		load.setForeground(new Color(255, 255, 255));
		menuBar.add(load);
		load.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(70, 130, 180));
		btnDelete.setForeground(new Color(255, 255, 255));
		menuBar.add(btnDelete);
		btnDelete.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		
		comboBox = new JComboBox<String>();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(70, 130, 180));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShape = comboBox.getItemAt(comboBox.getSelectedIndex());
			}
		});
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					try {
						for (int i = 0; i < indices.length; i++) {
							shape = (Shape) engine.getShapes()[indices[i]].clone();
							engine.addShape(shape);
						}
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						engine.refresh(canvas.getGraphics());
						list.setListData(engine.getShapes());
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		menuBar.add(btnCopy);
		btnCopy.setForeground(Color.WHITE);
		btnCopy.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		btnCopy.setBackground(new Color(70, 130, 180));
		
		JButton btnResize = new JButton("Resize");
		btnResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() != -1) {
					ArrayList<String> propertiesOfShape = new ArrayList<>(
							engine.getShapes()[list.getSelectedIndex()].getProperties().keySet());
					
					final JFrame frame = new JFrame("");
				    frame.getContentPane().setLayout(new GridLayout(propertiesOfShape.size()+1, 2));

				    frame.setBounds(300, 200, 340, 65*propertiesOfShape.size());
				    SwingUtilities.invokeLater(new Runnable() {
				        @Override public void run() {
				            frame.setVisible(true);
				        }
				    });
					
					JLabel[] property = new JLabel[propertiesOfShape.size()];
					JTextArea[] inputs = new JTextArea[propertiesOfShape.size()];
					int i = 0;
					for ( String key : engine.getShapes()[list.getSelectedIndex()].getProperties().keySet() ) {
					    System.out.println( key );
						property[i] = new JLabel(key);
						property[i].setFont(new Font("Times New Roman", Font.ITALIC, 24));
						property[i].setBounds(10, 50, 80, 35);
						inputs[i] = new JTextArea();
						inputs[i].setBounds(50, 50, 50, 50);
						
						frame.getContentPane().add(property[i]);
						frame.getContentPane().add(inputs[i]);
						frame.validate();
	                    frame.repaint();
	                    i++;
					}
					
					JButton ok = new JButton("Ok");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int i = 0;
							for (String key : engine.getShapes()[list.getSelectedIndex()].getProperties().keySet()) {
								properties.put(key, Double.parseDouble(inputs[i].getText()));
								i++;
							}
							try {
								shape = (Shape) engine.getShapes()[list.getSelectedIndex()].clone();
								shape.setProperties(properties);
								engine.updateShape(engine.getShapes()[list.getSelectedIndex()], shape);
								canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
								engine.refresh(canvas.getGraphics());
								frame.removeAll();//or remove(JComponent)
								frame.dispose();
							} catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					ok.setForeground(Color.WHITE);
					ok.setFont(new Font("Times New Roman", Font.ITALIC, 12));
					ok.setBackground(new Color(70, 130, 180));
					frame.getContentPane().add(ok);
				}
			}
		});
		btnResize.setForeground(Color.WHITE);
		btnResize.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		btnResize.setBackground(new Color(70, 130, 180));
		menuBar.add(btnResize);
		menuBar.add(comboBox);
		comboBox.setFont(new Font("Times New Roman", Font.ITALIC, 12));
		comboBox.setMaximumRowCount(20);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Shape", "circle", "ellipse", "lineSegment", "square", "rectangle", "triangle"}));
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
				new JColorChooser();
				frameColor = JColorChooser.showDialog(null, "Select a color please." , Color.BLACK);
				frameColorPanel.setBackground(frameColor);
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					for (int i = 0; i < indices.length; i++) {
						engine.getShapes()[indices[i]].setColor(frameColor);
					}
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					engine.refresh(canvas.getGraphics());
				}
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
				new JColorChooser();
				isColorFill = true;
				fillColor = JColorChooser.showDialog(null, "Select a color please." , Color.WHITE);
				fillColorPanel.setBackground(fillColor);
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					for (int i = 0; i < indices.length; i++) {
						engine.getShapes()[indices[i]].setFillColor(fillColor);
					}
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					engine.refresh(canvas.getGraphics());
				}
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
				supportedShapes = engine.getSupportedShapes();
				supportedShapesNames  = new String[supportedShapes.size() + 1];
				supportedShapesNames[0] = "Select Shape";
				for (int i = 0; i < supportedShapes.size(); i++) {
					supportedShapesNames[i+1] = supportedShapes.get(i).getSimpleName();
				}
				comboBox.setModel(new DefaultComboBoxModel<String>(supportedShapesNames));
			}
		});
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.refresh(canvas.getGraphics());
				list.setListData(engine.getShapes());
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					int index = list.getSelectedIndex();
					shape = engine.getShapes()[index];
					engine.removeShape(shape);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					engine.refresh(canvas.getGraphics());
					list.setListData(engine.getShapes());
				}
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Choose the destination you want to save in.");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setApproveButtonText("Save");
			    chooser.setAcceptAllFileFilterUsed(false);
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
				list.setListData(engine.getShapes());
			}
		});
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				engine.undo();
				engine.refresh(canvas.getGraphics());
				list.setListData(engine.getShapes());
			}
		});
	 
	}
}
