// Name: Karan Singh Gill
// USC loginid: karansig
// CS 455 PA3
// Fall 2017

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ListIterator;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{

   private static final int START_X = 10; // top left of corner of maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
   private static final int BOX_HEIGHT = 20;
   private static final int PATH_INSET = 8; //inset for the path line
   private static final int PATH_THICKNESS = 2; //thickness of the pathline
   private static final int INSET = 2; //how much smaller on each side to make entry/exit 
   private int rows;
   private int cols;
   private boolean[][] mazeData;
   private MazeCoord entryLoc;
   private MazeCoord exitLoc;
   private Maze maze;
   private final Color BLACK = new Color(0,0,0); //just defining colors that will be used
   private final Color WHITE = new Color(255,255,255);
   private final Color YELLOW = new Color(255,255,0);
   private final Color BLUE = new Color(0,0,255);
   private final Color GREEN = new Color(0,255,0);

   
   /**
      Constructs the component.
      @param maze   the maze to display
      We find the number of rows and columns
      We also create an array for the mazeData 
      for convenience, so we don't have to call 
      maze.mazeData each time
      For the same reason, we have the 
      variables entryLoc and exitLoc
   */
   public MazeComponent(Maze maze) 
   {   
      rows = maze.numRows();
      cols = maze.numCols();
      mazeData = new boolean[rows][cols]; //required because maze.mazeData is private
      for (int i = 0; i < rows; i++) {
	 for (int j = 0; j < cols; j++) {
	    mazeData[i][j] = maze.hasWallAt(new MazeCoord(i,j));
	 }
      }
      entryLoc = maze.getEntryLoc();
      exitLoc = maze.getExitLoc();
      this.maze = maze;
   }

   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
     It will draw the outline of the maze, and then call functions to draw the 
     maze components.
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(BLACK);
      Rectangle r = new Rectangle(START_X - 1, START_Y -1 , 
      cols*BOX_WIDTH + 1, rows*BOX_HEIGHT + 1); 
      //offset all values for the rectangle by 1 to ensure that an outer edge is drawn
      g2.draw(r);
      paintMaze(g2);
      entryExit(g2);
      drawPath(g2);

   }

   /**
   Draws the solution path through the maze
   */
   private void drawPath(Graphics2D g2) {
      LinkedList<MazeCoord> path = maze.getPath();//gets the list of coordinates
      ListIterator<MazeCoord> it = path.listIterator();
      MazeCoord coord1;
      MazeCoord coord2;
      g2.setColor(BLUE);
      for (int i = 0; i < path.size() -1; i++) {
	 coord1 = it.next();
	 coord2 = it.next();
	 int row1 = coord1.getRow();
	 int row2 = coord2.getRow();
	 int col1 = coord1.getCol();
	 int col2 = coord2.getCol();
	 //creates a rectangle from the center of one box to another
	 //the dimensions ensure that the path is 2 pixels wide (keeping it centered)
	 if (row1 != row2) { // for the case that a vertical line is needed
	    Rectangle r = new Rectangle(START_X + col1*BOX_WIDTH + PATH_INSET, 
	    START_Y + Math.min(row1, row2)*BOX_HEIGHT + PATH_INSET, 
	    PATH_THICKNESS, BOX_HEIGHT + PATH_THICKNESS);
	    g2.fill(r);
	 }
	 else if (col1 != col2) { // for the case when a horizontal line is needed
	    Rectangle r = new Rectangle(START_X + Math.min(col1, col2)*BOX_WIDTH + PATH_INSET, 
	    START_Y + row1*BOX_HEIGHT + PATH_INSET, 
	    BOX_WIDTH + PATH_THICKNESS, PATH_THICKNESS);
	    g2.fill(r);
	 }
	 it.previous(); //moves iterator back by one for next loop iteration
      }
   }

   /**
   Creates a yellow rectangle on the entrance and a green one
   on the exit. The INSET specified is used to make these rectangles smaller
   so that it's visible if they're on a wall
   */
   private void entryExit(Graphics2D g2) {
      g2.setColor(YELLOW);
      Rectangle startLoc = new Rectangle(START_X + entryLoc.getCol()*BOX_WIDTH + INSET, 
      START_Y + entryLoc.getRow()*BOX_HEIGHT + INSET, 
      BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);
      g2.fill(startLoc);

      g2.setColor(GREEN);
      Rectangle endLoc = new Rectangle(START_X + exitLoc.getCol()*BOX_WIDTH + INSET, 
      START_Y + exitLoc.getRow()*BOX_HEIGHT + INSET, 
      BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);
      g2.fill(endLoc);
   }

   /**
   Loops through the mazeData, filling in a black rectangle for a wall and a white one
   for a free area
   */
   private void paintMaze(Graphics2D g2) {
      Rectangle[][] mazeBoxes = new Rectangle[rows][cols];
      for (int i = 0; i < rows; i++) {
	 for (int j = 0; j < cols; j++) {
	    Rectangle r = new Rectangle();
	    r.setBounds(START_X+(j*BOX_WIDTH),START_Y+(i*BOX_HEIGHT),BOX_WIDTH,BOX_HEIGHT); 
	    if (mazeData[i][j]) {
	       g2.setColor(BLACK);
	       g2.fill(r);
	    }
	    else {
	       g2.setColor(WHITE);
	       g2.fill(r);
	    }
	    mazeBoxes[i][j] = r;
	 }
      }
   }
}
