/*
 * Graph Plotting Tool for Java Runtime Environment
 *
 * Author: Rafael Sabe
 * Email: rafaelmsabe@gmail.com
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class Main
{
	public static JFrame appWindow = new JFrame();
	public static JPanel screen = new JPanel();
	public static JLabel text1 = new JLabel();
	public static JLabel text2 = new JLabel();
	public static JLabel[] parameterText = new JLabel[12];
	public static JTextArea[] parameterTextBox = new JTextArea[12];
	public static JButton button1 = new JButton();
	public static JCheckBox checkBox = new JCheckBox();
	public static RuntimeStatus runtimeStatus = RuntimeStatus.MAIN;
	public static ScreenLayout screenLayout = new ScreenLayout();

	public static Canvas graph = null;

	public static ActionListener actionManager = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			String cmd = event.getActionCommand();

			if(cmd.equals("BUTTON_1"))
			{
				switch(runtimeStatus)
				{
					case MAIN:
						if(captureParameters())
						{
							runtimeStatus = RuntimeStatus.DRAW;
							paintGraphScreen();
						}
						else
						{
							runtimeStatus = RuntimeStatus.ERROR;
							paintErrorScreen("Invalid parameters entered");
						}
						break;

					case DRAW:
						screen.remove(graph);

					case ERROR:
						runtimeStatus = RuntimeStatus.MAIN;
						paintMainScreen();
						break;
				}
			}
		}
	};

	public static void main(String[] args)
	{
		initScreen();
		appWindow.setTitle("Graph Plotter");
		appWindow.setSize(1280, 720);
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.add(screen);
		appWindow.setVisible(true);

		runtimeStatus = RuntimeStatus.MAIN;
		paintMainScreen();
	}

	public static void initScreen()
	{
		text1.setText("");
		text1.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		text1.setForeground(Color.BLACK);
		text1.setLayout(screenLayout);
		text1.setLocation(40, 20);
		text1.setSize(1200, 35);
		text1.setHorizontalAlignment(JLabel.CENTER);
		text1.setVisible(true);

		text2.setText("");
		text2.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		text2.setForeground(Color.BLACK);
		text2.setLayout(screenLayout);
		text2.setLocation(40, 60);
		text2.setSize(1200, 35);
		text2.setHorizontalAlignment(JLabel.CENTER);
		text2.setVisible(false);

		for(int i = 0; i < 12; i++)
		{
			parameterText[i] = new JLabel();
			parameterTextBox[i] = new JTextArea();

			parameterText[i].setText("");
			parameterText[i].setForeground(Color.BLACK);
			parameterText[i].setLayout(screenLayout);
			parameterText[i].setSize(400, 30);
			parameterText[i].setVisible(false);

			parameterTextBox[i].setText("");
			parameterTextBox[i].setForeground(Color.BLACK);
			parameterTextBox[i].setLayout(screenLayout);
			parameterTextBox[i].setSize(400, 30);
			parameterTextBox[i].setVisible(false);
		}

		for(int i = 0; i < 6; i++)
		{
			parameterText[2*i].setLocation(40, 100 + 80*i);
			parameterText[2*i + 1].setLocation(840, 100 + 80*i);

			parameterTextBox[2*i].setLocation(40, 140 + 80*i);
			parameterTextBox[2*i + 1].setLocation(840, 140 + 80*i);
		}

		button1.setText("");
		button1.setBackground(Color.WHITE);
		button1.setForeground(Color.BLACK);
		button1.addActionListener(actionManager);
		button1.setActionCommand("BUTTON_1");
		button1.setLayout(screenLayout);
		button1.setLocation(430, 660);
		button1.setSize(300, 20);
		button1.setVisible(true);

		checkBox.setText("Enable derivative and integral plotting");
		checkBox.setForeground(Color.BLACK);
		checkBox.setLayout(screenLayout);
		checkBox.setLocation(650, 660);
		checkBox.setSize(300, 20);
		checkBox.setVisible(false);
		checkBox.setSelected(false);

		screen.setSize(1280, 720);
		screen.setBackground(Color.LIGHT_GRAY);
		screen.setLayout(screenLayout);
		screen.add(text1);
		screen.add(text2);
		screen.add(button1);
		screen.add(checkBox);

		for(int i = 0; i < 12; i++)
		{
			screen.add(parameterText[i]);
			screen.add(parameterTextBox[i]);
		}

		screen.setVisible(true);

		parameterText[0].setText("Enter x initial value (Recommended: 0)");
		parameterText[1].setText("Enter x stop value (Recommended: 12.56 (2 cycles))");
		parameterText[2].setText("Enter x progression step (Recommended: 0.01)");
		parameterText[3].setText("Enter y amplitude (Recommended: 80)");
		parameterText[4].setText("Enter value of \"a\"");
		parameterText[5].setText("Enter value of \"b\"");
		parameterText[6].setText("Enter value of \"c\"");
		parameterText[7].setText("Enter value of \"d\"");
		parameterText[8].setText("Enter value of \"e\"");
		parameterText[9].setText("Enter value of \"f\"");
		parameterText[10].setText("Enter value of \"g\"");
		parameterText[11].setText("Enter value of \"h\"");
	}

	public static void paintMainScreen()
	{
		text1.setText("Enter Graph Parameters");

		text2.setText("Function Plot: \"f(x) = a*sin(b*x) + c*sin(d*x) + e*sin(f*x) + g*sin(h*x)\"");
		text2.setVisible(true);

		for(int i = 0; i < 12; i++)
		{
			parameterTextBox[i].setText("");

			parameterText[i].setVisible(true);
			parameterTextBox[i].setVisible(true);
		}

		button1.setLocation(330, 660);
		button1.setSize(300, 20);
		button1.setText("Plot Graph");
		checkBox.setVisible(true);
	}

	public static void paintGraphScreen()
	{
		text1.setText("Graph Result");

		button1.setLocation(540, 660);
		button1.setSize(200, 20);
		button1.setText("Return");

		text2.setVisible(false);
		checkBox.setVisible(false);

		for(int i = 0; i < 12; i++)
		{
			parameterText[i].setVisible(false);
			parameterTextBox[i].setVisible(false);
		}

		graph = new Graph();
		graph.setBackground(Color.BLACK);
		graph.setLocation(40, 60);
		graph.setSize(1200, 520);

		screen.add(graph);
		graph.setVisible(true);
	}

	public static void paintErrorScreen(String errorMessage)
	{
		text1.setText("ERROR OCCURRED");
		text2.setText("Error Message: " + errorMessage);
		text2.setVisible(true);

		button1.setLocation(540, 660);
		button1.setSize(200, 20);
		button1.setText("Return");

		checkBox.setVisible(false);

		for(int i = 0; i < 12; i++)
		{
			parameterText[i].setVisible(false);
			parameterTextBox[i].setVisible(false);
		}
	}

	public static boolean captureParameters()
	{
		try
		{
			Graph.xStart = Double.parseDouble(parameterTextBox[0].getText());
			Graph.xEnd = Double.parseDouble(parameterTextBox[1].getText());
			Graph.xStep = Double.parseDouble(parameterTextBox[2].getText());
			Graph.amplitude = Double.parseDouble(parameterTextBox[3].getText());
			Graph.aFactor = Double.parseDouble(parameterTextBox[4].getText());
			Graph.bFactor = Double.parseDouble(parameterTextBox[5].getText());
			Graph.cFactor = Double.parseDouble(parameterTextBox[6].getText());
			Graph.dFactor = Double.parseDouble(parameterTextBox[7].getText());
			Graph.eFactor = Double.parseDouble(parameterTextBox[8].getText());
			Graph.fFactor = Double.parseDouble(parameterTextBox[9].getText());
			Graph.gFactor = Double.parseDouble(parameterTextBox[10].getText());
			Graph.hFactor = Double.parseDouble(parameterTextBox[11].getText());

			Graph.plotDerivativeIntegral = checkBox.isSelected();
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}
}

