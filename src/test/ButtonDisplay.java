package test;


import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ButtonDisplay {

	private final int buttonHeight=25;
	private final int buttonWidth=25;
	private final int row=9;	
	private final int col=9;
	private final int amtOfMines=10;
	
	private CreateField field;	
	private JFrame frame;
	private DrawBoard db;
	private JLabel[][] display;
	private JLabel[][] flags;
	
	private int btnsLeft=row*col;
	private int counter=0;
	
	public ButtonDisplay()
	{
		field = new CreateField(row,col,amtOfMines);
		frame = new JFrame("Minesweeper");
		
		frame.setSize(buttonWidth*col+6,buttonHeight*row+29);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		db = new DrawBoard(row,col,buttonHeight,buttonWidth, field);
		frame.add(db);
		
		MineListener mouse = new MineListener();
		db.addMouseListener(mouse);
		
		display = new JLabel[row+2][col+2];
		for(int i=1; i<row+1;i++)
		{
			for(int j=1; j<col+1;j++)
			{
				BufferedImage newButton;
				try {
					newButton = ImageIO.read(new File("BlankSquare.png"));
					Image blank= newButton.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT);
					display[i][j] = new JLabel(new ImageIcon(blank));
					db.add(display[i][j]);
					display[i][j].setBounds((j-1)*buttonWidth,(i-1)*buttonHeight, buttonWidth, buttonHeight);
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}
		
		flags = new JLabel[row+2][col+2];
		
	}
	
	public void reveal(int x, int y)
	{
		int row1 =(y+buttonHeight)/buttonHeight;
		int col1 =(x+buttonWidth)/buttonWidth;
		reveal2(row1,col1);
	}
	
	public void reveal2(int ypos, int xpos)
	{
		btnsLeft--;
		db.remove(display[ypos][xpos]);
		field.setMarked(ypos, xpos);
		if((field.getSpot(ypos,xpos))==0)
		{
			if(ypos!=1)
			{
				if(field.getMarked(ypos-1,xpos)==false){reveal2(ypos-1,xpos);}
				if(xpos!=1)
				{
					if(field.getMarked(ypos-1,xpos-1)==false){reveal2(ypos-1,xpos-1);}	
				}
				if(xpos!=col)
				{
					if(field.getMarked(ypos-1,xpos+1)==false){reveal2(ypos-1,xpos+1);}
				}
			}
			if(ypos!=row)
			{
				if(field.getMarked(ypos+1,xpos)==false){reveal2(ypos+1,xpos);}
				if(xpos!=1)
				{
					if(field.getMarked(ypos+1,xpos-1)==false){reveal2(ypos+1,xpos-1);}	
				}
				if(xpos!=col)
				{
					if(field.getMarked(ypos+1,xpos+1)==false){reveal2(ypos+1,xpos+1);}
				}
			}
			if(xpos!=1)
			{
				if(field.getMarked(ypos,xpos-1)==false){reveal2(ypos,xpos-1);}
			}
			if(xpos!=col)
			{
				if(field.getMarked(ypos,xpos+1)==false){reveal2(ypos,xpos+1);}
			}
		}
		else if (field.getSpot(ypos,xpos)==9)
		{
			JOptionPane.showMessageDialog(null, "You Lost");
			frame.dispose();
		}
		else 
		{
			field.setVisible(ypos, xpos);
		}
		if(counter==amtOfMines&&btnsLeft==amtOfMines)
		{
			JOptionPane.showMessageDialog(null, "You Won");
			frame.dispose();
		}
		
	}
	
	public void addFlag(int x, int y)
	{
		int r1 =(y+buttonHeight)/buttonHeight;
		int c1 =(x+buttonWidth)/buttonWidth;
		
		if(flags[r1][c1]!=null)
		{
			db.remove(flags[r1][c1]);
			db.add(display[r1][c1]);
			flags[r1][c1]=null;
			if(field.getSpot(r1, c1)==9)
			{
			counter--;
			}
		}
		
		else{
		if(field.getSpot(r1, c1)==9)
		{
			counter++;
		}
		db.remove(display[r1][c1]);
		BufferedImage newFlag;
		try {
			newFlag = ImageIO.read(new File("Flag.png"));
			Image flag= newFlag.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT);
			flags[r1][c1] = new JLabel(new ImageIcon(flag));
			db.add(flags[r1][c1]);
			flags[r1][c1].setBounds(x-x%buttonWidth, y-y%buttonHeight, buttonWidth, buttonHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(counter==amtOfMines&&btnsLeft==amtOfMines)
		{
			JOptionPane.showMessageDialog(null, "You Won");
			frame.dispose();
		}
		}
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	public CreateField getField()
	{
		return field;
	}
	
	private class MineListener implements MouseListener{
		public void mouseClicked(MouseEvent evt)
		{
			if(evt.getButton()==MouseEvent.BUTTON1)
			{
			reveal(evt.getX(),evt.getY());
			}
			else if(evt.getButton()==MouseEvent.BUTTON3)
			{
				addFlag(evt.getX(),evt.getY());
			}
		}
		public void mousePressed(MouseEvent evt){}
		public void mouseReleased(MouseEvent evt){}
		public void mouseEntered(MouseEvent evt){}
		public void mouseExited(MouseEvent evt){}
		
		}
	
	public static void main(String [] args)
	{
		ButtonDisplay bd = new ButtonDisplay();
		bd.getField().displayField();
		
	}
	
}
