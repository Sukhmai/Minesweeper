package test;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawBoard extends JPanel{
	private static final long serialVersionUID = -4836625697783217392L;
	private int row;
	private int col;
	private int bHeight;
	private int bWidth;
	private CreateField field;
	
	DrawBoard(int r, int c, int h, int w, CreateField field)
	{
		row=r;
		col=c;
		bHeight=h;
		bWidth=w;
		this.field=field;
	}

	public void paintComponent(Graphics g)
	{
	super.paintComponent(g);
	this.setBackground(Color.WHITE);
	
	g.setColor(Color.BLACK);
	for(int i=bHeight; i<col*bHeight; i+=bHeight)
	{
		g.drawLine(i, 0, i, row*bHeight);
	}
	for(int i=bWidth;i<row*bWidth;i+=bWidth)
	{
		g.drawLine(0, i, col*bWidth, i);
	}
	
	for(int i=0;i<row;i++)
	{
		for(int j=0;j<col; j++)
		{
			if(field.getVisible(i+1,j+1))
			{
				g.drawString(""+field.getSpot(i+1, j+1), j*bWidth+(bWidth/2),i*bHeight+(bHeight/2));
			}
		}
	}
	repaint();
	}
	
}
