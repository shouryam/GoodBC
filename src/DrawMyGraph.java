import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DrawMyGraph extends DrawGraph {
	public static double step = 0.0001;
	public static double[] xV;
	public static double[] yV;
	public static double[] ydV;
	public static double[] y2V;
	public static double[][] holes;
	public static double[][] zeros;
	public static double[][] Maxs;
	public static double[][] Mins;
	public static double[][] inflection;
	public static boolean integrate;
	public static boolean integrate2;
	public static String userInput;
	public static int upper;
	public static int lower;
	public static double areaUnderFX = 0;
	public static double areaUnderFDX = 0;
	public static double areawithFTC = 0;
	public static String equation;

	public DrawMyGraph(int width, int height, int xSize, int ySize) {
		super(width, height, xSize, ySize);
	}

	@Override
	public void drawGraph() {
		// Draw you graph here
		// coordinates(-X_SIZE, X_SIZE), (Y_SIZE, Y_SIZE)
		xV = new double[(int) ((X_SIZE * 2) / step) + 1];
		holes = new double[xV.length][2];
		yV = new double[xV.length];
		ydV = new double[xV.length];
		y2V = new double[xV.length];
		zeros = new double[xV.length][2];
		Maxs = new double[xV.length][2];
		Mins = new double[xV.length][2];
		inflection = new double[xV.length][2];
		int a = 0;
		int numberofholes = 0;
		// String equation = JOptionPane.showInputDialog("equation: f(x) = ");
		MathEvaluator m = new MathEvaluator(equation);
		for (double i = -X_SIZE; i < X_SIZE; i += step) {
			m.addVariable("x", i);
			xV[a] = i;
			yV[a] = m.getValue();
			if (Double.isNaN(yV[a])) {
				holes[numberofholes][0] = xV[a];
				holes[numberofholes][1] = (yV[a - 1] + yV[a + 1]) / 2;
				numberofholes++;
			}
			a++;
		}
		drawPath(xV, yV);
		// Derivative
		for (int b = 0; b < xV.length - 1; b++) {
			if (!(Double.isNaN(yV[b]) && !(Double.isNaN(yV[b + 1])))) {
				ydV[b] = (yV[b + 1] - yV[b]) / (step);
			}
		}
		drawPathWithColor(xV, ydV, Color.PINK);
		// Second Derivative
		for (int b = 0; b < xV.length - 1; b++) {
			y2V[b] = (ydV[b + 1] - ydV[b]) / (step);
		}
		drawPathWithColor(xV, y2V, Color.BLUE);
		// Zeros
		int numberofzeros = 0;
		for (int b = 0; b < xV.length - 1; b++) {
			if (yV[b] == 0) {
				zeros[numberofzeros][0] = xV[b];
				zeros[numberofzeros][1] = 0;
				drawPoint(zeros[numberofzeros][0], zeros[numberofzeros][1], Color.RED);
				numberofzeros++;
			} else if ((yV[b] > 0 && yV[b + 1] < 0) || (yV[b] < 0 && yV[b + 1] > 0)) {
				zeros[numberofzeros][0] = (xV[b] + xV[b + 1]) / 2;
				zeros[numberofzeros][1] = 0;
				drawPoint(zeros[numberofzeros][0], zeros[numberofzeros][1], Color.RED);
				numberofzeros++;
			}

		}
		/*
		 * COLOR KEY Zeros - red Maxs - Cyan Mins - Magenta Inflection - Cyan Hole -
		 * Grey
		 */
		// Max and Min
		int numberofmaxes = 0;
		int numberofmins = 0;
		for (int b = 1; b < xV.length - 1; b++) {
			if (ydV[b] > 0 && ydV[b + 1] < 0) {
				Maxs[numberofmaxes][0] = (xV[b] + xV[b + 1]) / 2;
				// m.addVariable("x",Maxs[numberofmaxes][0]);
				Maxs[numberofmaxes][1] = (yV[b] + yV[b + 1]) / 2;
				drawPoint(Maxs[numberofmaxes][0], Maxs[numberofmaxes][1], Color.CYAN);
				numberofmaxes++;
			} else if (ydV[b] < 0 && ydV[b + 1] > 0) {
				Mins[numberofmins][0] = (xV[b] + xV[b + 1]) / 2;
				// m.addVariable("x",Mins[numberofmins][0]);
				Mins[numberofmins][1] = (yV[b] + yV[b + 1]) / 2;
				drawPoint(Mins[numberofmins][0], Mins[numberofmins][1], Color.MAGENTA);
				numberofmins++;
			} else if (ydV[b] == 0 && ydV[b - 1] < 0 && ydV[b + 1] > 0) {
				Mins[numberofmins][0] = (xV[b] + xV[b + 1]) / 2;
				// m.addVariable("x",Mins[numberofmins][0]);
				Mins[numberofmins][1] = (yV[b] + yV[b + 1]) / 2;
				drawPoint(Mins[numberofmins][0], Mins[numberofmins][1], Color.MAGENTA);
				numberofmins++;
			} else if (ydV[b] == 0 && ydV[b - 1] > 0 && ydV[b + 1] < 0) {

				Maxs[numberofmaxes][0] = (xV[b] + xV[b + 1]) / 2;
				// m.addVariable("x",Maxs[numberofmaxes][0]);
				Maxs[numberofmaxes][1] = (yV[b] + yV[b + 1]) / 2;
				drawPoint(Maxs[numberofmaxes][0], Maxs[numberofmaxes][1], Color.CYAN);
				numberofmaxes++;
			}

		}
		// Points of Inflection
		int numberofinflections = 0;
		for (int b = 0; b < xV.length - 1; b++) {
			if (y2V[b] >= 0.00001 && y2V[b + 1] <= 0.00001) {
				inflection[numberofinflections][0] = (xV[b] + xV[b + 1]) / 2;
				// m.addVariable("x",Maxs[numberofmaxes][0]);
				inflection[numberofinflections][1] = (yV[b] + yV[b + 1]) / 2;
				drawPoint(inflection[numberofinflections][0], inflection[numberofinflections][1], Color.GREEN);
				numberofinflections++;
			} else if (y2V[b] <= 0.00001 && y2V[b + 1] >= 0.00001) {
				inflection[numberofinflections][0] = (xV[b] + xV[b + 1]) / 2;
				// m.addVariable("x",Mins[numberofmins][0]);
				inflection[numberofinflections][1] = (yV[b] + yV[b + 1]) / 2;
				drawPoint(inflection[numberofinflections][0], inflection[numberofinflections][1], Color.GREEN);
				numberofinflections++;
			}
		}
		// Integration for area under f(x)
		if (integrate == true) {
			for (double c = lower; c < upper; c += step) {
				areaUnderFX = areaUnderFX
						+ (yV[(int) ((X_SIZE + c) / step)] + yV[(int) ((X_SIZE + c) / step) + 1]) / 2 * step;
			}
			System.out.println("The area under of f(x) from x = " + lower + " to x = " + upper + " is " + areaUnderFX);
		}
		// Trapezoidal sum for area under first derivative
		if (integrate2 == true) {
			for (double c = lower; c < upper; c += step) {
				areaUnderFDX = areaUnderFDX
						+ (ydV[(int) ((X_SIZE + c) / step)] + ydV[(int) ((X_SIZE + c) / step) + 1]) / 2 * step;
			}
			System.out.println("The area of the first derivative from x = " + lower + " to x = " + upper
					+ " using trapizoidal sum is " + areaUnderFDX);
		}
		// FTC integration for first derivative, area under first derivative curve
		areawithFTC = yV[(int) ((X_SIZE + upper) / step)] - yV[(int) ((X_SIZE + lower) / step)];
		System.out.println("The area of the first derivative from x = " + lower + " to x = " + upper + " using FTC is "
				+ areawithFTC);

		// Holes
		for (int i = 0; i < numberofholes; i++) {
			drawPoint(holes[i][0], holes[i][1], Color.GRAY);
		}
		/*
		 * // Examples of how to call the functions // Draw a line double x1 = -2, y1 =
		 * 2, x2 = -6, y2 = 6; drawLine(x1, y1, x2, y2);
		 * 
		 * // Draw a line with color x1 = -1; y1 = 3; x2 = -4; y2 = 4;
		 * drawLineWithColor(x1, y1, x2, y2, Color.RED);
		 * 
		 * // Draw a path double x2Points[] = {0, 2, 8, 10}; double y2Points[] = {0, 2,
		 * 0, 10}; drawPath(x2Points, y2Points);
		 * 
		 * // Draw a path with color double x2Points1[] = {0, -2, 8, 10}; double
		 * y2Points1[] = {0, -2, 0, 10}; drawPathWithColor(x2Points1, y2Points1,
		 * Color.ORANGE);
		 * 
		 * // Draw a string at a location drawString("Hi", 5, 5);
		 * 
		 * // Draw a string at a location with color drawStringWithColor("Hi", 2, 2,
		 * Color.GREEN);
		 */
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("My Drawing");
		int widht = 800, height = 800, xSize = 10, ySize = 10;
		xSize = Integer.parseInt(JOptionPane.showInputDialog("X Axis Length"));
		ySize = Integer.parseInt(JOptionPane.showInputDialog("Y Axis Length"));
		equation = JOptionPane.showInputDialog("equation: f(x) = ");
		userInput = JOptionPane.showInputDialog("Do you want to calculate the area under f(x)?(answer yes or no)");
		if (userInput.compareTo("yes") == 0) {
			integrate = true;
		} else
			integrate = false;
		userInput = JOptionPane
				.showInputDialog("Do you want to calculate the area under the first derivative?(answer yes or no)");
		if (userInput.compareTo("yes") == 0) {
			integrate2 = true;
		} else
			integrate2 = false;
		if (integrate == true || integrate2 == true) {
			lower = Integer.parseInt(JOptionPane.showInputDialog("Lower limit:"));
			upper = Integer.parseInt(JOptionPane.showInputDialog("Upper limit:"));
		}
		DrawMyGraph drawMyGraph = new DrawMyGraph(widht, height, xSize, ySize);
		frame.add(drawMyGraph);
		frame.pack();
		frame.setVisible(true);
	}
}