/** 
 * Penrose Tile L-System 
 * by Geraldine Sarmiento.
 *  
 * This code was based on Patrick Dwyer's L-System class. 
 */

 class PenroseTile{

	PenroseLSystem ds;
	int steps = 0;
	  float somestep = 0.1;
	  String ruleW;
	  String ruleX;
	  String ruleY;
	  String ruleZ;

	public PenroseTile(PGraphics offscreen) {
	  //size(640, 360);
	  //frameRate(7);
	  ds = new PenroseLSystem();
	  ds.simulate(4);
	}

	void setup_obj(){

	}

	void pause_obj(){
		println("changeFR");
		frameRate(60);
	}

	void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
		println("testttttt");
		frameRate(7);
	  	  offscreen.background(0);
	  	  ds.render(offscreen);


	   

	}

}




