// Particles, by Daniel Shiffman.

class Particles{
  ParticleSystem ps;
  PImage sprite;  

  public Particles(PGraphics offscreen) {
    //size(1024, 768, P2D);
    //orientation(LANDSCAPE);
    sprite = loadImage("sprite.png");
    ps = new ParticleSystem(500, sprite);

    // Writing to the depth buffer is disabled to avoid rendering
    // artifacts due to the fact that the particles are semi-transparent
    // but not z-sorted.
    hint(DISABLE_DEPTH_MASK);
  } 

  void setup_obj(){
    
  }

  void pause_obj(){
    
  }

  void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
    offscreen.background(0);
    ps.update();
    ps.display(offscreen);
    
    ps.setEmitter(surfaceMouse.x,surfaceMouse.y);
    
    offscreen.fill(255);
    //textSize(16);
    //text("Frame rate: " + int(frameRate), 10, 20);
    
  }
}

