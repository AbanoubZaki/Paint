package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class painting extends JFrame {

	private JFrame frmPaint;
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
	private Color frameColor, fillColor;
	private boolean isColorFill = false;
	private boolean resizePressed = false;
	private JList<Shape> list;
	private int movementSpeed = 6;
	private ArrayList<String> propertiesOfShape = null;
	private Map<String, Double> properties = new HashMap<>();

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
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
		});
		list.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Shapes",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 128)));
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
				shape = new myShape();
				if (list.getSelectedIndex() != -1) {
					Map<String, Double> properties = new HashMap<>();
					if (DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].toString().contains("lineSegment")) {
						switch (e.getKeyCode()) {
						case 37:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1")
									- movementSpeed);
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1"));
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2")
									- movementSpeed);
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2"));
							break;
						case 38:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1"));
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1")
									- movementSpeed);
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2"));
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2")
									- movementSpeed);
							break;
						case 39:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1")
									+ movementSpeed);
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1"));
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2")
									+ movementSpeed);
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2"));
							break;
						case 40:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1"));
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1")
									+ movementSpeed);
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2"));
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2")
									+ movementSpeed);
							break;
						default:

							break;
						}
						try {
							shape = (Shape) DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].clone();
							shape.setProperties(properties);
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}

					} else if (DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].toString().contains("triangle")) {
						switch (e.getKeyCode()) {
						case 37:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1")
									- movementSpeed);
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1"));
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2")
									- movementSpeed);
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2"));
							properties.put("x3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x3")
									- movementSpeed);
							properties.put("y3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y3"));
							break;
						case 38:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1"));
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1")
									- movementSpeed);
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2"));
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2")
									- movementSpeed);
							properties.put("x3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x3"));
							properties.put("y3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y3")
									- movementSpeed);
							break;
						case 39:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1")
									+ movementSpeed);
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1"));
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2")
									+ movementSpeed);
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2"));
							properties.put("x3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x3")
									+ movementSpeed);
							properties.put("y3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y3"));
							break;
						case 40:
							properties.put("x1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x1"));
							properties.put("y1", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y1")
									+ movementSpeed);
							properties.put("x2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x2"));
							properties.put("y2", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y2")
									+ movementSpeed);
							properties.put("x3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("x3"));
							properties.put("y3", DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getProperties().get("y3")
									+ movementSpeed);
							break;
						default:

							break;
						}
						try {
							shape = (Shape) DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].clone();
							shape.setProperties(properties);
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}

					} else {
						try {
							shape = (Shape) DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].clone();
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						switch (e.getKeyCode()) {
						case 37:
							shape.setPosition(new Point(
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().x - movementSpeed,
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().y));
							break;
						case 38:
							shape.setPosition(new Point(DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().x,
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().y - movementSpeed));
							break;
						case 39:
							shape.setPosition(new Point(
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().x + movementSpeed,
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().y));
							break;
						case 40:
							shape.setPosition(new Point(DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().x,
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().y + movementSpeed));
							break;
						default:
							break;
						}
					}

					DrawEngine.getInstance().updateShape(DrawEngine.getInstance().getShapes()[list.getSelectedIndex()], shape);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					DrawEngine.getInstance().refresh(canvas.getGraphics());

				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

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
					DrawEngine.getInstance().refresh(canvas.getGraphics());
					switch (selectedShape) {
					case "circle":
						if (resizePressed) {
							properties.put("radius",
									DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].getPosition().distance(position2));
						} else {
							properties.put("radius", position1.distance(position2));
						}
						break;
					case "ellipse":
						if (position2.x > position1.x && position2.y < position1.y) {
							// 1st quad
							shape.setPosition(new Point(position1.x, position2.y));

						} else if (position2.x < position1.x && position2.y > position1.y) {
							// 3rd quad
							shape.setPosition(new Point(position2.x, position1.y));

						} else if (position2.x < position1.x && position2.y < position1.y) {
							// 2nd quad
							shape.setPosition(new Point(position2.x, position2.y));
						}

						properties.put("horizontalRadius", (double) (maxX - minX));
						properties.put("verticalRadius", (double) (maxY - minY));
						break;
					case "lineSegment":
						properties.put("x1", (double) position1.x);
						properties.put("y1", (double) position1.y);
						properties.put("x2", (double) position2.x);
						properties.put("y2", (double) position2.y);
						break;
					case "square":
						if ((maxX - minX) > (maxY - minY)) {
							properties.put("length", (double) (maxX - minX));
						} else {
							properties.put("length", (double) (maxY - minY));
						}
						break;
					case "rectangle":
						if (position2.x > position1.x && position2.y < position1.y) {
							// 1st quad
							shape.setPosition(new Point(position1.x, position2.y));

						} else if (position2.x < position1.x && position2.y > position1.y) {
							// 3rd quad
							shape.setPosition(new Point(position2.x, position1.y));

						} else if (position2.x < position1.x && position2.y < position1.y) {
							// 2nd quad
							shape.setPosition(new Point(position2.x, position2.y));
						}

						properties.put("length", (double) (maxX - minX));
						properties.put("width", (double) (maxY - minY));
						break;
					case "triangle":
						properties.put("x1", (double) position1.x);
						properties.put("y1", (double) position1.y);
						properties.put("x2", (double) Math.abs(maxX - minX));
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
				if (!selectedShape.equals("") && !resizePressed && comboBox.getSelectedIndex() <= 6) {
					position1 = e.getPoint();

					switch (selectedShape) {
					case "Select Shape":

						break;
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
					shape = new myShape();
					shape = shapes.get(shapes.size() - 1);
					shape.setPosition(position1);
					shape.setColor(frameColor);
					if (isColorFill) {
						shape.setFillColor(fillColor);
					}

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!selectedShape.equals("") && comboBox.getSelectedIndex() <= 6) {
					shapes.clear();
					/**
					 * check if the user only clicked the screen without dragging.
					 */
					if (!(position1.x == e.getPoint().x && position1.y == e.getPoint().y)) {
						DrawEngine.getInstance().addShape(shape);
						list.setListData(DrawEngine.getInstance().getShapes());
					}
				}
				shape = new myShape();
				properties = new HashMap<>();
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
				// int option = chooser.showOpenDialog(frmPaint);
				chooser.setDialogTitle("Choose the file you want to load.");
				FileNameExtensionFilter f = new FileNameExtensionFilter("Paint Saved Files", "txt", "text");
				chooser.setFileFilter(f);
				chooser.setApproveButtonText("Load");
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					DrawEngine.getInstance().load(path);
				}
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				DrawEngine.getInstance().refresh(canvas.getGraphics());
				list.setListData(DrawEngine.getInstance().getShapes());

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
				if (comboBox.getSelectedIndex() > 6) {
					Class<?> clazz = null;

					try {
						clazz = Class.forName("eg.edu.alexu.csd.oop.draw." + selectedShape);
						shape = (Shape) clazz.newInstance();
						ArrayList<String> propertiesOfShape = new ArrayList<>(shape.getProperties().keySet());

						final JFrame frame = new JFrame("");
						frame.getContentPane().setLayout(new GridLayout(propertiesOfShape.size() + 3, 2));

						frame.setBounds(300, 200, 340, 65 * propertiesOfShape.size());
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								frame.setVisible(true);
							}
						});

						JLabel x = new JLabel("x");
						JLabel y = new JLabel("y");
						x.setFont(new Font("Times New Roman", Font.ITALIC, 24));
						y.setFont(new Font("Times New Roman", Font.ITALIC, 24));

						JTextArea inputy = new JTextArea();
						JTextArea inputx = new JTextArea();

						frame.getContentPane().add(x);
						frame.getContentPane().add(inputx);
						frame.validate();
						frame.repaint();

						frame.getContentPane().add(y);
						frame.getContentPane().add(inputy);
						frame.validate();
						frame.repaint();

						JLabel[] property = new JLabel[propertiesOfShape.size()];
						JTextArea[] inputs = new JTextArea[propertiesOfShape.size()];
						int i = 0;
						for (String key : shape.getProperties().keySet()) {
							System.out.println(key);
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
								Point thePoint = new Point();
								thePoint.x = Integer.parseInt(inputx.getText());
								thePoint.y = Integer.parseInt(inputy.getText());
								shape.setPosition(thePoint);
								int i = 0;
								for (String key : shape.getProperties().keySet()) {
									properties.put(key, Double.parseDouble(inputs[i].getText()));
									i++;
								}
								shape.setProperties(properties);
								shape.setColor(frameColor);
								shape.setFillColor(fillColor);
								DrawEngine.getInstance().addShape(shape);
								canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
								DrawEngine.getInstance().refresh(canvas.getGraphics());
								list.setListData(DrawEngine.getInstance().getShapes());
								frame.removeAll();// or remove(JComponent)
								frame.dispose();
								shape = new myShape();
							}
						});
						ok.setForeground(Color.WHITE);
						ok.setFont(new Font("Times New Roman", Font.ITALIC, 12));
						ok.setBackground(new Color(70, 130, 180));
						frame.getContentPane().add(ok);
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					try {
						for (int i = 0; i < indices.length; i++) {
							shape = (Shape) DrawEngine.getInstance().getShapes()[indices[i]].clone();
							DrawEngine.getInstance().addShape(shape);
						}
						canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
						DrawEngine.getInstance().refresh(canvas.getGraphics());
						list.setListData(DrawEngine.getInstance().getShapes());
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
					Class<?> clazz = null;
					Pattern p;
					p = Pattern.compile("(\\S+)@\\S+");
					Matcher shapeName = p.matcher(DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].toString());
					if (shapeName.find()) {
						try {
							clazz = Class.forName(shapeName.group(1));
							System.out.println(shapeName.group(1));
							shape = (Shape) clazz.newInstance();
							propertiesOfShape = new ArrayList<>(shape.getProperties().keySet());

						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e2) {
							e2.printStackTrace();
						}
					}
					final JFrame frame = new JFrame("");
					frame.getContentPane().setLayout(new GridLayout(propertiesOfShape.size() + 1, 2));

					frame.setBounds(300, 200, 340, 65 * propertiesOfShape.size());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							frame.setVisible(true);
						}
					});

					JLabel[] property = new JLabel[propertiesOfShape.size()];
					JTextArea[] inputs = new JTextArea[propertiesOfShape.size()];
					for (int i = 0; i < propertiesOfShape.size(); i++) {
						String key = propertiesOfShape.get(i);
						property[i] = new JLabel(key);
						property[i].setFont(new Font("Times New Roman", Font.ITALIC, 24));
						property[i].setBounds(10, 50, 80, 35);
						inputs[i] = new JTextArea();
						inputs[i].setBounds(50, 50, 50, 50);

						frame.getContentPane().add(property[i]);
						frame.getContentPane().add(inputs[i]);
						frame.validate();
						frame.repaint();
					}

					JButton ok = new JButton("Ok");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							for (int i = 0; i < propertiesOfShape.size(); i++) {
								properties.put(propertiesOfShape.get(i), Double.parseDouble(inputs[i].getText()));
							}
							try {
								shape = (Shape) DrawEngine.getInstance().getShapes()[list.getSelectedIndex()].clone();
								shape.setProperties(properties);
								DrawEngine.getInstance().updateShape(DrawEngine.getInstance().getShapes()[list.getSelectedIndex()], shape);
								canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
								DrawEngine.getInstance().refresh(canvas.getGraphics());
								frame.removeAll();// or remove(JComponent)
								frame.dispose();
							} catch (CloneNotSupportedException e1) {
								e1.printStackTrace();
							}
							list.setListData(DrawEngine.getInstance().getShapes());

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
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Select Shape", "circle", "ellipse",
				"lineSegment", "square", "rectangle", "triangle" }));
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
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				shape = new myShape();
				JColorChooser chooser = new JColorChooser();
				frameColor = JColorChooser.showDialog(null, "Select a color please.", Color.BLACK);
				frameColorPanel.setBackground(frameColor);
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					for (int i = 0; i < indices.length; i++) {
						try {
							shape = (Shape) DrawEngine.getInstance().getShapes()[indices[i]].clone();
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						shape.setColor(frameColor);
						DrawEngine.getInstance().updateShape(DrawEngine.getInstance().getShapes()[indices[i]], shape);
					}
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					DrawEngine.getInstance().refresh(canvas.getGraphics());
					list.setListData(DrawEngine.getInstance().getShapes());
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
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				shape = new myShape();
				JColorChooser chooser = new JColorChooser();
				isColorFill = true;
				fillColor = JColorChooser.showDialog(null, "Select a color please.", Color.WHITE);
				fillColorPanel.setBackground(fillColor);
				if (list.getSelectedIndex() != -1) {
					indices = list.getSelectedIndices();
					for (int i = 0; i < indices.length; i++) {
						try {
							shape = (Shape) DrawEngine.getInstance().getShapes()[indices[i]].clone();
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
						shape.setFillColor(fillColor);
						DrawEngine.getInstance().updateShape(DrawEngine.getInstance().getShapes()[indices[i]], shape);
					}
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					DrawEngine.getInstance().refresh(canvas.getGraphics());
					list.setListData(DrawEngine.getInstance().getShapes());
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
				supportedShapes = DrawEngine.getInstance().getSupportedShapes();
				supportedShapesNames = new String[supportedShapes.size() + 1];
				supportedShapesNames[0] = "Select Shape";
				for (int i = 0; i < supportedShapes.size(); i++) {
					supportedShapesNames[i + 1] = supportedShapes.get(i).getSimpleName();
				}
				comboBox.setModel(new DefaultComboBoxModel<String>(supportedShapesNames));
			}
		});
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				DrawEngine.getInstance().refresh(canvas.getGraphics());
				list.setListData(DrawEngine.getInstance().getShapes());
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					int index = list.getSelectedIndex();
					shape = DrawEngine.getInstance().getShapes()[index];
					DrawEngine.getInstance().removeShape(shape);
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					DrawEngine.getInstance().refresh(canvas.getGraphics());
					list.setListData(DrawEngine.getInstance().getShapes());
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
					DrawEngine.getInstance().save(chooser.getSelectedFile() + "\\" + Math.random() + "saved.XmL.txt");
					DrawEngine.getInstance().save(chooser.getSelectedFile() + "\\" + Math.random() + "saved.JsOn.txt");
					System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}

			}
		});
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				DrawEngine.getInstance().redo();
				DrawEngine.getInstance().refresh(canvas.getGraphics());
				list.setListData(DrawEngine.getInstance().getShapes());
			}
		});
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				DrawEngine.getInstance().undo();
				DrawEngine.getInstance().refresh(canvas.getGraphics());
				list.setListData(DrawEngine.getInstance().getShapes());
			}
		});

	}
}