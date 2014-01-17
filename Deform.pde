/**
 * Deform. 
 * 
 * A GLSL version of the oldschool 2D deformation effect, by Inigo Quilez.
 * Ported from the webGL version available in ShaderToy:
 * http://www.iquilezles.org/apps/shadertoy/
 * (Look for Deform under the Plane Deformations Presets)
 * 
 */
 
class Deform{
 
PImage tex;
PShader deform;

public Deform(PGraphics offscreen){

  //offscreen.textureWrap(REPEAT);
  
 
  deform = loadShader("deform.glsl");
  deform.set("resolution", float(offscreen.width), float(offscreen.height));

}

void setup_obj() {
  //size(640, 360, P2D);
  
  //offscreen.textureWrap(REPEAT);
  offscreen.textureWrap(0);
  tex = loadImage("tex1.jpg");
}

void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  //offscreen.textureWrap(REPEAT);
  deform.set("time", millis() / 1000.0);
  deform.set("mouse", surfaceMouse.x, surfaceMouse.y);
  offscreen.shader(deform);
  offscreen.image(tex, 0, 0, offscreen.width, offscreen.height);
  //offscreen.texture(tex);
}

void pause_obj(){
    //offscreen.textureWrap(CLAMP);
    offscreen.resetShader();

}

}
