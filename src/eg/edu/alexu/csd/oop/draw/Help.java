package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Help {

	public JFrame frame;
	private int count = 0;
	JLabel label = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help window = new Help();
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
	public Help() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1053, 413);
		
		ArrayList<String> bolds = new ArrayList<>();
		bolds.add("   To draw a shape :");
		bolds.add("   For undo & redo you will find the buttons at the top left corner there is only 20 steps allowed to undo.");
		
		ArrayList< ArrayList<String> > details = new ArrayList<>();
		
		ArrayList< String > one = new ArrayList<>();
		one.add("    Choose the frame color.");
		one.add("    Choose the fill color.");
		one.add("    Click on the combo box of shapes.");
		one.add("    Choose the shape you want to draw.");
		one.add("    If your shape is not in the combo box just click on Import shapes "
				+ " button and choose the jar file support your shape then you will find your shape in the combo box.");
		details.add(one);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));		
		JLabel lblHelping = new JLabel("helping");
		lblHelping.setForeground(new Color(128, 0, 0));
		lblHelping.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblHelping.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblHelping);
		lblHelping.setText("User Guide");	
		update(bolds, details);
			
	}
	
	void update(ArrayList<String> bolds, ArrayList< ArrayList<String> > details) {
		int n = 2 + details.get(count).size();
		frame.getContentPane().setLayout(new GridLayout(n, 2, 0, 0));
		label.setText(bolds.get(count));
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		label.setForeground(new Color(25, 25, 112));
		frame.getContentPane().add(label);
		for (int j = 0; j < details.get(count).size(); j++) {
			label = new JLabel();
			label.setText(details.get(count).get(j));
			label.setFont(new Font("Times New Roman", Font.BOLD, 14));
			frame.getContentPane().add(label);
		}
	}
}
