// Name:
// USC loginid:
// CS 455 PA3
// Fall 2017

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   private int rows = 0;
   private int cols = 0;
   private int[][] mazeData;
   private MazeCoord startLoc;
   private MazeCoord exitLoc;
   private LinkedList<MazeCoord> path;
   
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
      rows = mazeData.length;
      cols = mazeData[0].length;
      this.mazeData = new int[rows][cols];

      for (int i=0;i<rows;i++) {
	 for (int j=0; j<cols;j++){
	    if (mazeData[i][j]) {
	       this.mazeData[i][j] = 2; //2 represents wall
	    }
	    else {
	       this.mazeData[i][j] = 0; //0 represents free
	    }
	 }
      }

      this.startLoc = startLoc;
      this.exitLoc = exitLoc;
//      LinkedList<MazeCoord> path = new LinkedList<MazeCoord>();
   }


   /**
      Returns the number of rows in the maze
      @return number of rows
   */
   public int numRows() {
      return rows;   // DUMMY CODE TO GET IT TO COMPILE
   }

   
   /**
      Returns the number of columns in the maze
      @return number of columns
   */   
   public int numCols() {
      return cols;   // DUMMY CODE TO GET IT TO COMPILE
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      int row = loc.getRow();
      int col = loc.getCol();
      if (mazeData[row][col] == 2) {
	 return true;
      }
      else{
	 return false;
      }
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return startLoc;   // DUMMY CODE TO GET IT TO COMPILE
   }
   
   
   /**
     Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return exitLoc;   // DUMMY CODE TO GET IT TO COMPILE
   }

   
   /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {

      return path;// DUMMY CODE TO GET IT TO COMPILE

   }


   /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.

      @return whether a path was found.
    */
   public boolean search(MazeCoord loc)  {  
      if (hasWallAt(loc)) {
	 return false;
      }
      else if (wasVisited(loc)) {
	 return false;
      }
      else if (loc.equals(exitLoc)) {
	 path.addFirst(loc);
	 return true;
      }
      else {
	 int row = loc.getRow();
	 int col = loc.getCol();
	 mazeData[row][col] = 1; //sets it as visited
	 if (row !=0) {
	    if (search(new MazeCoord(row-1,col))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
	 else if (row != rows) {
	    if (search(new MazeCoord(row+1,col))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
	 else if (col != 0) {
	    if (search(new MazeCoord(row,col-1))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
	 else if (col != cols) {
	    if (search(new MazeCoord(row,col+1))) {
	       path.addFirst(loc);
	       return true;
	    }
	 }
//	 if ((row != 0) && search(new MazeCoord(row-1,col))) {
//	    path.addFirst(loc);
//	    return true;
//	 }
//	 else if ((row != rows) && search(new MazeCoord(row+1,col))) {
//	    path.addFirst(loc);
//	    return true;
//	 }
//	 else if ((col != 0) && search(new MazeCoord(row,col-1))) {
//	    path.addFirst(loc);
//	    return true;
//	 }
//	 else if ((col != cols) && search(new MazeCoord(row,col+1))) {
//	    path.addFirst(loc);
//	    return true;
//	 }
      }
      return false;
   }
   public boolean wasVisited(MazeCoord loc) {
      int row = loc.getRow();
      int col = loc.getCol();
      if (mazeData[row][col] == 1) {
	 return true;
      }
      else{
	 return false;
      }
   }
}


