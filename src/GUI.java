import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class GUI {
	GUI() {
		JFrame f = new JFrame("GUI");
		JButton graph = new JButton("Graph");
		JButton FTC = new JButton("FTC");
		f.add(FTC,BorderLayout.WEST);
		f.add(graph, BorderLayout.NORTH);
		f.getContentPane().setLayout(new FlowLayout());
		JTextField Function = new JTextField("Function", 10);
		JTextField LowerBound = new JTextField("LowerBound", 10);
		JTextField UpperBound = new JTextField("Upperbound", 10);
	    f.pack();
		f.getContentPane().add(Function,BorderLayout.SOUTH);
		f.getContentPane().add(LowerBound,BorderLayout.EAST);
		f.getContentPane().add(UpperBound);
		f.add(graph);
		f.add(FTC);
		f.setLayout(new GridLayout(3,3));
		f.setSize(1000, 1000);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new GUI();

	}
}