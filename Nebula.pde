/**
 * Nebula. 
 * 
 * From CoffeeBreakStudios.com (CBS)
 * Ported from the webGL version in GLSL Sandbox:
 * http://glsl.heroku.com/e#3265.2
 */
 
class Nebula{
PShader nebula;

public Nebula(PGraphics offscreen){
  nebula = loadShader("nebula.glsl");
  nebula.set("resolution", float(offscreen.width), float(offscreen.height));
  
}

void setup_obj() {
  //size(640, 360, P2D);
  //noStroke();

  
}

void pause_obj(){
  offscreen.resetShader(TRIANGLES);
}

void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  nebula.set("time", millis() / 1000.0);  
  offscreen.shader(nebula); 
  // This kind of raymarching effects are entirely implemented in the
  // fragment shader, they only need a quad covering the entire view 
  // area so every pixel is pushed through the shader. 
  offscreen.rect(0, 0, offscreen.width, offscreen.height);
}
}
