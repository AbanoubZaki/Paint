package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import myShapes.circle;
import myShapes.lineSegment;
import myShapes.rectangle;
import myShapes.square;
import myShapes.triangle;

public class painting extends JFrame implements MouseListener, MouseMotionListener{

	private JFrame frame;
	private DrawEngine engine = new DrawEngine();
	private String selectedShape = new String("");
	JComboBox<String> comboBox;
	Canvas canvas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					painting window = new painting();
					window.frame.setVisible(true);
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
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 607, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		comboBox = new JComboBox<String>();
		comboBox.setMaximumRowCount(20);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Circle", "Ellipse", "LineSegment", "Square", "Rectangle", "Triangle"}));
		comboBox.setToolTipText("Draw");
		comboBox.setBounds(259, 11, 114, 28);
		panel.add(comboBox);
		
		JButton redo = new JButton("Redo");
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
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.getGraphics().clearRect(0, 0, 454, 306);
				engine.refresh(canvas.getGraphics());
			}
		});
		refresh.setBounds(168, 11, 81, 28);
		panel.add(refresh);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		delete.setBounds(383, 11, 81, 28);
		panel.add(delete);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"Move", "Resize"}));
		comboBox_1.setToolTipText("Draw");
		comboBox_1.setMaximumRowCount(20);
		comboBox_1.setBounds(474, 11, 118, 28);
		panel.add(comboBox_1);
		
		JButton button = new JButton("Import Shapes");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getSupportedShapes();
			}
		});
		button.setBounds(474, 53, 118, 28);
		panel.add(button);
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		save.setBounds(474, 92, 118, 28);
		panel.add(save);
		
		JButton load = new JButton("Load");
		load.setBounds(474, 131, 118, 28);
		panel.add(load);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape circle = new circle(new Point(200,50), 50.0);
				circle.setColor(Color.red);
				circle.setFillColor(Color.blue);
				circle.draw(canvas.getGraphics());
				engine.addShape(circle);
			}
		});
		btnCircle.setBounds(474, 170, 118, 23);
		panel.add(btnCircle);
		
		JButton ellipse = new JButton("Ellipse");
		ellipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape ellipse = new myShapes.ellipse(new Point(100, 50), 60.0, 30.0);
				ellipse.setColor(Color.green);
				ellipse.setFillColor(Color.CYAN);
				ellipse.draw(canvas.getGraphics());
				engine.addShape(ellipse);
			}
		});
		ellipse.setBounds(474, 204, 118, 23);
		panel.add(ellipse);
		
		JButton linesegment = new JButton("Line Segment");
		linesegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape lineSegment = new lineSegment(new Point(75, 50), new Point(60, 150));
				lineSegment.setColor(Color.DARK_GRAY);
				lineSegment.draw(canvas.getGraphics());
				engine.addShape(lineSegment);
			}
		});
		linesegment.setBounds(474, 238, 118, 23);
		panel.add(linesegment);
		
		JButton square = new JButton("Square");
		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape square = new square(new Point(75, 250), 40.0);
				square.setColor(Color.green);
				square.setFillColor(Color.CYAN);
				square.draw(canvas.getGraphics());
				engine.addShape(square);
			}
		});
		square.setBounds(474, 272, 118, 23);
		panel.add(square);
		
		JButton rectangle = new JButton("Rectangle");
		rectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape rect = new rectangle(new Point(175, 250), 100.0, 40.0);
				rect.setColor(Color.yellow);
				rect.setFillColor(Color.yellow);
				rect.draw(canvas.getGraphics());
				engine.addShape(rect);
			}
		});
		rectangle.setBounds(474, 306, 118, 23);
		panel.add(rectangle);
		
		JButton triangle = new JButton("Triangle");
		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Shape t = new triangle(
						new Double[] {10.0 , 250.0, 20.0}, new Double[] {10.0 , 50.0, 100.0});  
				t.setColor(Color.green);
				t.setFillColor(Color.CYAN);
				t.draw(canvas.getGraphics());
				engine.addShape(t);
			}
		});
		triangle.setBounds(474, 336, 118, 23);
		panel.add(triangle);
		
		canvas = new Canvas();
		canvas.setBounds(10, 53, 454, 306);
		canvas.setBackground(Color.WHITE);
		panel.add(canvas);
		
	 
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("dragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("moved");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("clicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("enterd");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("exited");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("released");
	}
	
}
