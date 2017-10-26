// Name:
// USC loginid:
// CS 455 PA3
// Fall 2017

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Scanner;
import java.io.Reader;
import java.io.InputStreamReader;
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
   private static final int PATH_INSET = 8;
   private static final int PATH_THICKNESS = 2;
   private static final int INSET = 2; //how much smaller on each side to make entry/exit 
   private int rows;
   private int cols;
   private boolean[][] mazeData;
   private MazeCoord entryLoc;
   private MazeCoord exitLoc;
   private Maze ourMaze;
   private final Color BLACK = new Color(0,0,0);
   private final Color WHITE = new Color(255,255,255);
   private final Color YELLOW = new Color(255,255,0);
   private final Color BLUE = new Color(0,0,255);
   private final Color GREEN = new Color(0,255,0);

   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze) 
   {   
      rows = maze.numRows();
      cols = maze.numCols();
      mazeData = new boolean[rows][cols];
      for (int i = 0; i< rows; i++) {
	 for (int j = 0; j< cols; j++) {
	    mazeData[i][j] = maze.hasWallAt(new MazeCoord(i,j));
	 }
      }
      entryLoc = maze.getEntryLoc();
      exitLoc = maze.getExitLoc();
      ourMaze = maze;

   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      LinkedList<MazeCoord> path = ourMaze.getPath();
      paintMaze(g2);
      entryExit(g2);
      drawPath(g2, path);

   }

   private void drawPath(Graphics2D g2, LinkedList<MazeCoord> path) {
      Debug.debug(String.valueOf(path.size()));
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
	 g2.drawLine(START_X + col1*BOX_WIDTH + PATH_INSET,START_Y + row1*BOX_HEIGHT + PATH_INSET, START_X + col2*BOX_WIDTH + PATH_INSET,START_Y + row2*BOX_HEIGHT + PATH_INSET);

//	 if (row1 != row2) {
//	    Rectangle r = new Rectangle(START_X + col1*BOX_WIDTH + PATH_INSET, START_Y + Math.min(row1, row2)*BOX_HEIGHT + PATH_INSET, PATH_THICKNESS, BOX_HEIGHT + PATH_THICKNESS);
//	    g2.fill(r);
//	 }
//	 else if (col1 != col2) {
//	    Rectangle r = new Rectangle(START_X + Math.min(col1, col2)*BOX_WIDTH + PATH_INSET, START_Y + row1*BOX_HEIGHT + PATH_INSET, BOX_WIDTH + PATH_THICKNESS, PATH_THICKNESS);
//	    g2.fill(r);
//	 }
	 it.previous();
      }
   }

   private void entryExit(Graphics2D g2) {
      g2.setColor(YELLOW);
      Rectangle startLoc = new Rectangle(START_X + entryLoc.getCol()*BOX_WIDTH + INSET, START_Y + entryLoc.getRow()*BOX_HEIGHT + INSET, BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);
      
      g2.fill(startLoc);
      g2.setColor(GREEN);
      Rectangle endLoc = new Rectangle(START_X + exitLoc.getCol()*BOX_WIDTH + INSET, START_Y + exitLoc.getRow()*BOX_HEIGHT + INSET, BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);

      g2.fill(endLoc);
   }

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
