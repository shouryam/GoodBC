import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import javax.swing.JFrame;

/*
 * Cartesian coordinate plane
 */
public class DrawGraph extends Canvas {
	int WIDTH;
	int HEIGHT;
	int X_SIZE;
	int Y_SIZE;
	
	int X_SCALE;
	int Y_SCALE;
	
	Graphics2D g;
	
	public DrawGraph(int width, int height, int xSize, int ySize) {
		super.setSize(width, height);
		this.WIDTH = width;
		this.HEIGHT = height;
		this.X_SIZE = xSize;
		this.Y_SIZE = ySize;
		this.X_SCALE = WIDTH / (2 * X_SIZE);
		this.Y_SCALE = HEIGHT / (2 * Y_SIZE);
	}
	
	private double scaleX(double x) {
		return x * X_SCALE;
	}
	
	private double scaleY(double y) {
		return -1 * y * Y_SCALE;
	}
	
	protected void drawLine(double x1, double y1, double x2, double y2) {
		g.draw(new Line2D.Double(scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2)));
	}
	protected void drawPoint (double x1, double y1, Color c) {
		g.setColor(c);
		Ellipse2D.Double shape = new Ellipse2D.Double(scaleX(x1) - 5, scaleY(y1) - 5,10.0,10.0);
	    
	    g.fill(shape);
	}
	
	protected void drawLineWithColor(double x1, double y1, double x2, double y2, Color color) {
		Color originalColor = g.getColor(); // save original color
		g.setColor(color);
		drawLine(x1, y1, x2, y2);
		g.setColor(originalColor); // restore original color
	}
	
	protected void drawString(String str, double x, double y) {
		g.drawString(str, (float)scaleX(x), (float)scaleY(y));
	}
	
	protected void drawStringWithColor(String str, double x, double y, Color color) {
		Color originalColor = g.getColor(); // save original color
		g.setColor(color);
		drawString(str, x, y);
		g.setColor(originalColor); // restore original color
	}
	
	protected void drawPath(double[] x2Points, double[] y2Points) {
		GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);

		polyline.moveTo (scaleX(x2Points[0]), scaleY(y2Points[0]));

		for (int index = 1; index < x2Points.length; index++) {
		         polyline.lineTo(scaleX(x2Points[index]), scaleY(y2Points[index]));
		};

		g.draw(polyline);
	}
	
	protected void drawPathWithColor(double[] x2Points, double[] y2Points, Color color) {
		Color originalColor = g.getColor(); // save original color
		g.setColor(color);
		drawPath(x2Points, y2Points);
		g.setColor(originalColor); // restore original color
	}
	
	@Override
	public void paint(Graphics gp) {
		g = (Graphics2D) gp;
		transformToCartesianCoordinateSystem();
		drawGraph();
    }

	private void transformToCartesianCoordinateSystem() {
		// Move the origin to the center
		double originX = WIDTH/2, originY = HEIGHT/2;
		g.translate(originX, originY); 
		
		// x-axis - positive
		double x1 = 0, y1 = 0, x2 = X_SIZE, y2 = 0;
		drawLine(x1, y1, x2, y2);
		
		// y-axis - positive
		x1 = 0; y1 = 0; x2 = 0; y2 = Y_SIZE;
		drawLine(x1, y1, x2, y2);
		
		// x-axis - negative
		x1 = 0; y1 = 0; x2 = -X_SIZE; y2 = 0;
		drawLine(x1, y1, x2, y2);
		
		// y-axis - negative
		x1 = 0; y1 = 0; x2 = 0; y2 = -Y_SIZE;
		drawLine(x1, y1, x2, y2);

		double x = 0, y = 0;
		drawString("(0,0)", x-0.37f, y);
		
		for (int i = 1; i <= X_SIZE; i = i + X_SIZE / 10) { // x axis positive numbering
			drawString(""+i, i*1f, y);
		}
		for (int i = 0; i <= Y_SIZE; i = i + Y_SIZE / 10) { // y axis positive numbering
			drawString(""+i, x, i*1f);
		}
		for (int i = 1; i <= X_SIZE; i = i + X_SIZE / 10) { // x axis negative numbering
			drawStringWithColor("-"+i, -i*1f, y, Color.RED);
		}
		for (int i = 0; i <= Y_SIZE; i = i + Y_SIZE / 10) { // y axis negative numbering
			drawStringWithColor("-"+i, x, -i*1f, Color.RED);
		}				
	}
	
	public void drawGraph() {
		// Draw you graph here 
		// coordinates(-X_SIZE, X_SIZE), (Y_SIZE, Y_SIZE)
		
		// Draw a line
		double x1 = 0, y1 = 0, x2 = 5, y2 = 5;
		drawLine(x1, y1, x2, y2);
		
		// Draw a path
		double x2Points[] = {0, 5, 8, 10};
		double y2Points[] = {0, 5, 0, 10};
		drawPath(x2Points, y2Points);
		
		// Draw a string at a location
		drawString("Hi", 5, 5);
	}

	public static void main(String[] args) {
        JFrame frame = new JFrame("My Drawing");
        int WIDTH = 800;
    	int HEIGHT = 800;
    	int X_SIZE = 10;
    	int Y_SIZE = 10;
        Canvas canvas = new DrawGraph(WIDTH, HEIGHT, X_SIZE, Y_SIZE);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
}