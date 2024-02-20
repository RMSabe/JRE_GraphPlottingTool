/*
 * Graph Plotting Tool for Java Runtime Environment
 *
 * Author: Rafael Sabe
 * Email: rafaelmsabe@gmail.com
 */

import java.awt.*;

public class Graph extends Canvas
{
	public static double xStart = 0.0;
	public static double xEnd = 0.0;
	public static double xStep = 0.0;

	public static double aFactor = 0.0;
	public static double bFactor = 0.0;
	public static double cFactor = 0.0;
	public static double dFactor = 0.0;
	public static double eFactor = 0.0;
	public static double fFactor = 0.0;
	public static double gFactor = 0.0;
	public static double hFactor = 0.0;

	public static double amplitude = 0.0;

	public static boolean plotDerivativeIntegral = false;

	public void paint(Graphics g)
	{
		double x = 0.0;
		double y = 0.0;

		double yi = 0.0;
		double d = 0.0;
		double i = 0.0;

		Color funcColor;

		if(plotDerivativeIntegral)
		{
			funcColor = Color.RED;

			g.setColor(funcColor);
			g.drawString("Function f(x)", 20, 20);
			g.setColor(Color.GREEN);
			g.drawString("Derivative d(f(x))", 20, 50);
			g.setColor(Color.BLUE);
			g.drawString("Integral i(f(x))", 20, 80);
		}
		else funcColor = Color.WHITE;

		int cx = 0;
		int cy = 0;

		double range = Math.abs(xEnd - xStart);
		double offset = -xStart;

		x = xStart;
		while(x < xEnd)
		{
			y = aFactor*Math.sin(bFactor*x) + cFactor*Math.sin(dFactor*x) + eFactor*Math.sin(fFactor*x) + gFactor*Math.sin(hFactor*x);

			cx = (int) (Math.round((x + offset)*1200.0/range));

			cy = (int) (260 - Math.round(amplitude*y));
			g.setColor(funcColor);
			g.drawLine(cx, cy, cx, cy);

			if(plotDerivativeIntegral)
			{
				d = (y - yi)/xStep;
				i += y*xStep;
				yi = y;

				cy = (int) (260 - Math.round(amplitude*d));
				g.setColor(Color.GREEN);
				g.drawLine(cx, cy, cx, cy);

				cy = (int) (260 - Math.round(amplitude*i));
				g.setColor(Color.BLUE);
				g.drawLine(cx, cy, cx, cy);
			}

			x += xStep;
		}
	}
}

