import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class MainGrapher extends JFrame {
	public static double xlength;
	public static double ylength;
	public static double step = 0.0005;
	public static double[] xV;
	public static double[] yV;
	public static double[] ydV;
	public static double [] y2V;
	public static double [][] zeros;
	public static double [][] Maxs;
	public static double [][] min;
	public static double [][] inflection;
	public MainGrapher() {
		setTitle("Main Graph");
		setSize(700,700);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*xlength = Integer.parseInt(JOptionPane.showInputDialog("X Axis Length"));
		ylength = Integer.parseInt(JOptionPane.showInputDialog("Y Axis Length"));
		xV = new double [(int)((xlength*2)/step) + 1];
		yV = new double [xV.length];*/
	}
	public void paint (Graphics g) {
		g.drawLine(350, 700, 350, 0);
		g.drawLine(0, 350, 700, 350);
		xlength = Integer.parseInt(JOptionPane.showInputDialog("X Axis Length"));
		ylength = Integer.parseInt(JOptionPane.showInputDialog("Y Axis Length"));
		xV = new double [(int)((xlength*2)/step) + 1];
		yV = new double [xV.length];
		ydV = new double [xV.length];
		y2V = new double [xV.length];
		zeros = new double [xV.length][2];
		int a = 0;
		String equation = JOptionPane.showInputDialog("equation");
   		MathEvaluator m = new MathEvaluator(equation);
   		for (double i = -xlength; i < xlength; i += step) {
   			m.addVariable("x",i);
   			xV[a] = i;
   			yV[a] = m.getValue(); 
   			a++;
   		}
		for (int b = 0; b < xV.length - 1; b++) {
   			g.drawLine(350 + (int)(xV[b] * 350 / xlength), 350 - (int)(yV[b] * 350 / ylength), 350 + (int)(xV[b+1] * 350 / xlength), 350 - (int)(yV[b+1] * 350 / ylength));
   		}
		//Derivative
		for(int b = 0; b < xV.length - 1; b++) {
			ydV[b] = ( yV[b+1]-yV[b])/(step);
		}
		for (int b = 0; b < xV.length - 1; b++) {
   			g.drawLine(350 + (int)(xV[b] * 350 / xlength), 350 - (int)(ydV[b] * 350 / ylength), 350 + (int)(xV[b+1] * 350 / xlength), 350 - (int)(ydV[b+1] * 350 / ylength));
   		}
		//Second Derivative
		for(int b = 0; b < xV.length - 1; b++) {
			y2V[b] = ( ydV[b+1]-ydV[b])/(step);
		}
		for (int b = 0; b < xV.length - 1; b++) {
   			g.drawLine(350 + (int)(xV[b] * 350 / xlength), 350 - (int)(y2V[b] * 350 / ylength), 350 + (int)(xV[b+1] * 350 / xlength), 350 - (int)(y2V[b+1] * 350 / ylength));
   		}
		//Zeros
		int numberofzeros = 0;
		for(int b = 0; b < xV.length - 1; b++) {
			if(yV[b] == 0) {
				System.out.println(xV[b]);
				zeros[numberofzeros][0] = xV[b];
				zeros[numberofzeros][1] = 0;
				numberofzeros++;
				System.out.println(zeros[numberofzeros][0] + " " + zeros[numberofzeros][1] + "\n" + numberofzeros);
			}
			else if(yV[b] > 0 && yV[b+1] < 0 || yV[b] < 0 && yV[b+1] > 0) {
				System.out.println(xV[b]);

				zeros[numberofzeros][0] = (xV[b] + xV[b+1])/2;
				zeros[numberofzeros][1] = 0;
				numberofzeros++;
				System.out.println(zeros[numberofzeros][0] + " " + zeros[numberofzeros][1] + "\n" + numberofzeros);
			}
			
		}
		//repaint();
	}
	/*public void run() {
		try {
			
			Thread.sleep(100);
			int ax,by;
			ax = (int)xlength - 300;
			by = (int)ylength - 300;
			by = (int) (50*Math.sin(by/3.14));
			xlength = ax+300;
			ylength =300 -by;
			xlength++;
				
		}
		catch(Exception e) {
			System.out.println("Error");
					
		}
	}*/
	/*public void generatePoints() {
		int a = 0;
		String equation = JOptionPane.showInputDialog("equation");
   		MathEvaluator m = new MathEvaluator(equation);
   		for (double i = -xlength; i < xlength; i += step) {
   			m.addVariable("x",i);
   			xV[a] = i;
   			yV[a] = m.getValue(); 
   			a++;
   		}
	}*/

	
	public static void main(String[] args) {
		new MainGrapher();
   	}
}