package eg.edu.alexu.csd.oop.draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import javafx.scene.control.ComboBox;
import myShapes.circle;

public class painting extends JFrame{

	private JFrame frame;
	private DrawEngine engine = new DrawEngine();
	private String selectedShape = new String("");
	JComboBox<String> comboBox;
	JPanel paintingPane;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 631, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setMaximumRowCount(20);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Circle", "Ellipse", "LineSegment", "Square", "Rectangle", "Triangle"}));
		comboBox.setToolTipText("Draw");
		comboBox.setBounds(271, 11, 102, 28);
		panel.add(comboBox);
		
		JButton redo = new JButton("Redo");
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		redo.setBounds(101, 11, 69, 28);
		panel.add(redo);
		
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		undo.setBounds(22, 11, 69, 28);
		panel.add(undo);
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		refresh.setBounds(180, 11, 81, 28);
		panel.add(refresh);
		
		JButton delete = new JButton("Delete");
		delete.setBounds(383, 11, 81, 28);
		panel.add(delete);
		
		paintingPane = new JPanel(){
			   @Override
			   public void paintComponent(Graphics g) {
			      super.paintComponent(g);  // paint parent's background
			      g.setColor(Color.GREEN);
			      Shape c = new circle(new Point(200,50), 50.0, paintingPane.getGraphics());
			   }
			   
		};
		paintingPane.setBounds(22, 53, 442, 296);
		panel.add(paintingPane);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Move", "Resize"}));
		comboBox_1.setToolTipText("Draw");
		comboBox_1.setMaximumRowCount(20);
		comboBox_1.setBounds(474, 11, 118, 28);
		panel.add(comboBox_1);
		
		JButton button = new JButton("Import Shapes");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(474, 53, 118, 28);
		panel.add(button);
		
		JButton save = new JButton("Save");
		save.setBounds(474, 92, 118, 28);
		panel.add(save);
		
		JButton load = new JButton("Load");
		load.setBounds(474, 131, 118, 28);
		panel.add(load);
		
		JButton btnCircle = new JButton("Circle");
		btnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintingPane.getGraphics().setColor(Color.GREEN);
				Shape c = new circle(new Point(200,50), 50.0, paintingPane.getGraphics());
				
				c.draw(paintingPane.getGraphics());
			}
		});
		btnCircle.setBounds(474, 170, 89, 23);
		panel.add(btnCircle);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{comboBox, redo, undo, refresh}));
	      
	}
	
	public class ItemHandler implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == comboBox) {
				if (comboBox.getSelectedItem().equals("Circle")) {
					Shape c = new circle(new Point(200,50), 50.0, paintingPane.getGraphics());
				} else {
					
				}
			}
		}
	}
}
