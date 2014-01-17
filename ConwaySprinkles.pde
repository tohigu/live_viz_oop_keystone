// GLSL version of Conway's game of life, ported from GLSL sandbox:
// http://glsl.heroku.com/e#207.3
// Exemplifies the use of the buffer uniform in the shader, that gives
// access to the previous frame.

class ConwaySprinkles{
  
  
PShader conway;
PGraphics pg;

public ConwaySprinkles(PGraphics offscreen){

  //pg = createGraphics(400, 400, P2D);
  //pg.noSmooth();
  conway = loadShader("conway.glsl");
  conway.set("resolution", float(offscreen.width), float(offscreen.height));  


}

public void setup_obj() {
  //size(400, 400, P3D);    

}

public void pause_obj(){
 offscreen.resetShader();
}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  conway.set("time", millis()/10000.0);
  float x = map(surfaceMouse.x, 0, offscreen.width, 0, 1);
  float y = map(surfaceMouse.y, 0, offscreen.height, 1, 0);
  conway.set("mouse", x, y);  
  //pg.beginDraw();
  offscreen.background(0);
  offscreen.shader(conway);
  offscreen.rect(0, 0, offscreen.width, offscreen.height);
  //pg.endDraw();  
  //image(offscreen, 0, 0, offscreen.width, offscreen.height);
}

}