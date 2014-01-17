/**
* Spore 1 
* by Mike Davis. 
* 
* A short program for alife experiments. Click in the window to restart.
* Each cell is represented by a pixel on the display as well as an entry in
* the array 'cells'. Each cell has a run() method, which performs actions
* based on the cell's surroundings.  Cells run one at a time (to avoid conflicts
* like wanting to move to the same space) and in random order.
*/

class Spore1{

	World w;
	int numcells = 0;
	int maxcells = 6700;
	Cell[] cells = new Cell[maxcells];
	color spore_color;
	// set lower for smoother animation, higher for faster simulation
	int runs_per_loop = 10000;
	color black = color(0, 0, 0);
	 
	public Spore1(PGraphics offscreen) {
	 //size(640, 360);
	 frameRate(24);
	 offscreen.background(0);
	 w = new World();
	 spore_color = color(172, 255, 128);
	 seed();
	}

	void setup_obj(){
//
	}




	void seed() {
	 // Add cells at random places
	 for (int i = 0; i < maxcells; i++)
	 {
	   int cX = (int)random(offscreen.width);
	   int cY = (int)random(offscreen.height);
	   if (w.getpix(offscreen, cX, cY) == black) {
	     w.setpix(offscreen, cX, cY, spore_color);
	     cells[numcells] = new Cell(cX, cY);
	     numcells++;
	   }
	 }
	}

	void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
	 // Run cells in random order
	 for (int i = 0; i < runs_per_loop; i++) {
	   int selected = min((int)random(numcells), numcells - 1);
	   cells[selected].run(offscreen, w);
	 }
	}

	void pause_obj(){

		frameRate(60);
	}






}