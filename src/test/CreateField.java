package test;

public class CreateField {
	int[][] field;
	boolean[][] isVisible;
	boolean[][] isMarked;
	int row;
	int col;
	int amtOfMines;
	public CreateField(int row, int col, int amtOfMines)
	{
	this.row=row;
	this.col=col;
	this.amtOfMines=amtOfMines;
	field = new int[row+2][col+2];
	boolean check=true;
	for(int i=0; i<amtOfMines; i++)
	{
		check=true;
		while(check)
		{
		int tempRow = (int) (Math.random()*row)-1;
		int tempCol = (int) (Math.random()*col)-1;
		if(field[tempRow+2][tempCol+2]!=9)
		{
			field[tempRow+2][tempCol+2] = 9;
			check=false;
		}
		}
	}
	int num=0;
	for(int i=1; i<row+1; i++)
	{
		for(int j=1; j<col+1; j++)
		{
			if(field[i][j]!=9)
			{
			num=0;
			if(field[i-1][j-1]==9){num++;}
			if(field[i-1][j]==9){num++;}
			if(field[i-1][j+1]==9){num++;}
			if(field[i][j-1]==9){num++;}
			if(field[i][j+1]==9){num++;}
			if(field[i+1][j-1]==9){num++;}
			if(field[i+1][j]==9){num++;}
			if(field[i+1][j+1]==9){num++;}
			field[i][j]=num;
			}
		}
	}
	
	isVisible = new boolean[row+2][col+2];
	isMarked = new boolean[row+2][col+2];
	}
	
	public int getSpot(int row, int col)
	{
		return field[row][col];
	}
	
	public void displayField()
	{
		for(int i=1; i<row+1; i++)
		{
			for(int j=1; j<col+1;j++)
			{
				System.out.print(field[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public boolean getVisible(int r, int c)
	{
		return isVisible[r][c];
	}
	
	public void setVisible(int r,int c)
	{
		isVisible[r][c]=true;
	}
	
	public boolean getMarked(int r, int c)
	{
		return isMarked[r][c];
	}
	
	public void setMarked(int r,int c)
	{
		isMarked[r][c]=true;
	}
	
}
