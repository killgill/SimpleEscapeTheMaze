// Name:
// USC loginid:
// CS 455 PA3
// Fall 2017

import java.awt.Graphics;
import javax.swing.JComponent;

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
   private static final int INSET = 2; //how much smaller on each side to make entry/exit 
   private int rows;
   private int cols;
   private boolean[][] mazeData;
   private MazeCoord entryLoc;
   private MazeCoord exitLoc;

   
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

   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;

      Rectangle[][] mazeBoxes = new Rectangle[rows][cols](BOX_WIDTH,BOX_HEIGHT);
      for (int i = 0; i < rows; i++) {
	 for (int j = 0; j < cols; j++) {
	    mazeBoxes[i][j].setLocation(START_X+(i*BOX_WIDTH),START_Y+(j*BOX_HEIGHT)); 
	    if (mazeData[i][j]) {
	       g2.setColor(BLACK);
	       g2.fill(mazeBoxes[i][j]);
	    }
	    else {
	       g2.setColor(WHITE);
	       g2.fill(mazeBoxes[i][j]);
	    }
	 }
      }
      g2.setColor(YELLOW);
      Rectangle startLoc = new Rectangle(START_X + entryLoc.getRow()*BOX_WIDTH + INSET, START_Y + entryLoc.getCol()*BOX_HEIGHT + INSET, BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);
      g2.fill(startLoc);
      g2.setColor(GREEN);
      Rectangle endLoc = new Rectangle(START_X + exitLoc.getRow()*BOX_WIDTH + INSET, START_Y + exitLoc.getCol()*BOX_HEIGHT + INSET, BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);
      g2.fill(endLoc);
   }
}



