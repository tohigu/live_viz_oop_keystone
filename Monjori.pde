/**
 * Monjori. 
 * 
 * GLSL version of the 1k intro Monjori from the demoscene 
 * (http://www.pouet.net/prod.php?which=52761)
 * Ported from the webGL version available in ShaderToy:
 * http://www.iquilezles.org/apps/shadertoy/
 * (Look for Monjori under the Plane Deformations Presets) 
 */

class Monjori {
  PShader monjori;
  PImage tex;
  
  public Monjori(PGraphics offscreen) {
    tex = loadImage("tex1.jpg");
    monjori = loadShader("monjori.glsl");
    monjori.set("resolution", float(offscreen.width), float(offscreen.height));
  }

  void setup_obj() {
    //size(640, 360, P2D);
    //noStroke();
    //offscreen.textureWrap(REPEAT);
  }

  void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
    monjori.set("time", millis() / 1000.0);

    offscreen.shader(monjori);
    // This kind of effects are entirely implemented in the
    // fragment shader, they only need a quad covering the  
    // entire view area so every pixel is pushed through the 
    // shader.   
    offscreen.image(tex, 0, 0, offscreen.width, offscreen.height);
  }

  void pause_obj() {
    //offscreen.stroke(0, 0);
    resetShader(TRIANGLES);
  }
}

