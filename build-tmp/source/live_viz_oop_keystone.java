import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import deadpixel.keystone.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class live_viz_oop_keystone extends PApplet {



Keystone ks;
CornerPinSurface surface;
CornerPinSurface surface2;

PGraphics offscreen;

PImage imgMask;

int current_mode = 0;
int prev_mode = 0;

//mode 1
ConwaySprinkles CS;

//mode 2
Monjori Mon;

//mode 3
Deform Def;

//mode 4
NoiseSphere NS;

//mode 5
Nebula Neb;

//mode 6
FractalTree FT;

public void setup(){
  // Keystone will only work with P3D or OPENGL renderers, 
  // since it relies on texture mapping to deform
  //size(800, 600, P3D);
  size(1024, 768, P3D);
  ks = new Keystone(this);
  surface = ks.createCornerPinSurface(512, 700, 20);
  surface2 = ks.createCornerPinSurface(512, 700, 20);
  
  // We need an offscreen buffer to draw the surface we
  // want projected
  // note that we're matching the resolution of the
  // CornerPinSurface.
  // (The offscreen buffer can be P2D or P3D)
  offscreen = createGraphics(512, 700, P3D);
  
  //setup_default();
  CS = new ConwaySprinkles(offscreen);
  Mon = new Monjori(offscreen);
  Def = new Deform(offscreen);
  NS = new NoiseSphere(offscreen);
  Neb = new Nebula(offscreen);
  FT = new FractalTree(offscreen);
  
  imgMask = loadImage("mask.png");
  
  
}

public void setup_default(){

  background(0,0);
}

public void draw(){
  //noCursor();
  
    // Convert the mouse coordinate into surface coordinates
  // this will allow you to use mouse events inside the 
  // surface from your screen. 
  PVector surfaceMouse = surface.getTransformedMouse();

  // Draw the scene, offscreen
  offscreen.beginDraw();
  
  offscreen.resetShader();
  switch(current_mode){
  case 0:
    offscreen.background(0,0);
    break;
  case 1: 
    CS.draw_obj(surfaceMouse, offscreen);
    break;
  case 2: 
    Mon.draw_obj(surfaceMouse, offscreen);
    break;
  case 3: 
    Def.draw_obj(surfaceMouse, offscreen);
    break;
  case 4: 
    NS.draw_obj(surfaceMouse, offscreen);
    break;
   case 5: 
    Neb.draw_obj(surfaceMouse, offscreen);
    break;
  case 6: 
    FT.draw_obj(surfaceMouse, offscreen);
    break;
  default:
    offscreen.background(0,0);
    break;
  }
  offscreen.endDraw();
  //image(offscreen, 0, 0, offscreen.width, offscreen.height);
  // most likely, you'll want a black background to minimize
  // bleeding around your projection area
  background(0);
  offscreen.mask(imgMask);
  // render the scene, transformed using the corner pin surface
  surface.render(offscreen);
  surface2.render(offscreen);
  
}
  
  public void keyPressed() {

  offscreen.background(0);
  switch(key){
  case '0':
    setup_default();
    prev_mode = current_mode;
    current_mode = 0;
    break;
  case '1': 
    CS.setup_obj();
    prev_mode = current_mode;
    current_mode = 1;
    break;
  case '2': 
    Mon.setup_obj();
    prev_mode = current_mode;
    current_mode = 2;
    break;
  case '3': 
    Def.setup_obj();
    prev_mode = current_mode;
    current_mode = 3;
    break;
  case '4': 
    NS.setup_obj();
    prev_mode = current_mode;
    current_mode = 4;
    break;
  case '5': 
    Neb.setup_obj();
    prev_mode = current_mode;
    current_mode = 5;
    break;
  case '6': 
    FT.setup_obj();
    prev_mode = current_mode;
    current_mode = 6;
    break;
  case 'c':
    // enter/leave calibration mode, where surfaces can be warped 
    // and moved
    ks.toggleCalibration();
    break;
  case 'p':
    noCursor();
    break;
  case 'l':
    // loads the saved layout
    ks.load();
    break;
  case 's':
    // saves the layout
    ks.save();
    break;
  default:
    setup_default();
    prev_mode = current_mode;
    current_mode = 0;
  }
  
  
  switch(prev_mode){
  case 0:
    break;
  case 1: 
    println("inactivate CS");
    CS.pause_obj();
    //CS = new ConwaySprinkles(offscreen);
    break;
  case 2: 
    Mon.pause_obj();
    break;
  case 3: 
    Def.pause_obj();
    break;
  case 4: 
    NS.pause_obj();
    break;
  case 5: 
    Neb.pause_obj();
    break;
  case 6: 
    FT.pause_obj();
    break;
  default:
    break;
  }
}


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
  conway.set("resolution", PApplet.parseFloat(offscreen.width), PApplet.parseFloat(offscreen.height));  


}

public void setup_obj() {
  //size(400, 400, P3D);    

}

public void pause_obj(){
 offscreen.resetShader();
}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  conway.set("time", millis()/10000.0f);
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

  offscreen.textureWrap(REPEAT);
  tex = loadImage("tex1.jpg");
 
  deform = loadShader("deform.glsl");
  deform.set("resolution", PApplet.parseFloat(offscreen.width), PApplet.parseFloat(offscreen.height));

}

public void setup_obj() {
  //size(640, 360, P2D);
  
  //offscreen.textureWrap(REPEAT);

}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  //offscreen.textureWrap(REPEAT);
  deform.set("time", millis() / 1000.0f);
  deform.set("mouse", surfaceMouse.x, surfaceMouse.y);
  offscreen.shader(deform);
  offscreen.image(tex, 0, 0, offscreen.width, offscreen.height);
}

public void pause_obj(){
    //offscreen.textureWrap(CLAMP);
    offscreen.resetShader();

}

}
/**
 * Recursive Tree
 * by Daniel Shiffman.  
 * 
 * Renders a simple tree-like structure via recursion. 
 * The branching angle is calculated as a function of 
 * the horizontal mouse location. Move the mouse left
 * and right to change the angle.
 */
 
 
class FractalTree{
  
float theta;   

public FractalTree(PGraphics offscreen) {
  //size(640, 360);
}

public void setup_obj(){
 //break;
}

public void pause_obj(){
 //break;
}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  offscreen.background(0);
  //frameRate(30);
  offscreen.stroke(255);
  // Let's pick an angle 0 to 90 degrees based on the mouse position
  float a = (surfaceMouse.x / (float) offscreen.width) * 90f;
  // Convert it to radians
  theta = radians(a);
  // Start the tree from the bottom of the screen
  offscreen.translate(offscreen.width/2,offscreen.height * .8f);
  // Draw a line 120 pixels
  offscreen.line(0,0,0,-120);
  // Move to the end of that line
  offscreen.translate(0,-120);
  // Start the recursive branching!
  branch(120);

}

public void branch(float h) {
  // Each branch will be 2/3rds the size of the previous one
  h *= 0.66f;
  
  // All recursive functions must have an exit condition!!!!
  // Here, ours is when the length of the branch is 2 pixels or less
  if (h > 2) {
    offscreen.pushMatrix();    // Save the current state of transformation (i.e. where are we now)
    offscreen.rotate(theta);   // Rotate by theta
    offscreen.line(0, 0, 0, -h);  // Draw the branch
    offscreen.translate(0, -h); // Move to the end of the branch
    branch(h);       // Ok, now call myself to draw two new branches!!
    offscreen.popMatrix();     // Whenever we get back here, we "pop" in order to restore the previous matrix state
    
    // Repeat the same thing, only branch off to the "left" this time!
    offscreen.pushMatrix();
    offscreen.rotate(-theta);
    offscreen.line(0, 0, 0, -h);
    offscreen.translate(0, -h);
    branch(h);
    offscreen.popMatrix();
  }
}

}
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
    monjori.set("resolution", PApplet.parseFloat(offscreen.width), PApplet.parseFloat(offscreen.height));
  }

  public void setup_obj() {
    //size(640, 360, P2D);
    //noStroke();
    offscreen.textureWrap(CLAMP);
  }

  public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
    monjori.set("time", millis() / 1000.0f);

    offscreen.shader(monjori);
    // This kind of effects are entirely implemented in the
    // fragment shader, they only need a quad covering the  
    // entire view area so every pixel is pushed through the 
    // shader.   
    offscreen.image(tex, 0, 0, offscreen.width, offscreen.height);
  }

  public void pause_obj() {
    //offscreen.stroke(0, 0);
    offscreen.resetShader();
  }
}

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
  nebula.set("resolution", PApplet.parseFloat(offscreen.width), PApplet.parseFloat(offscreen.height));
  
}

public void setup_obj() {
  //size(640, 360, P2D);
  //noStroke();

  
}

public void pause_obj(){
  offscreen.resetShader();
}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  nebula.set("time", millis() / 1000.0f);  
  offscreen.shader(nebula); 
  // This kind of raymarching effects are entirely implemented in the
  // fragment shader, they only need a quad covering the entire view 
  // area so every pixel is pushed through the shader. 
  offscreen.rect(0, 0, offscreen.width, offscreen.height);
}
}
/**
 * Noise Sphere 
 * by David Pena.  
 * 
 * Uniform random distribution on the surface of a sphere. 
 */
 
 class NoiseSphere{

int cuantos = 4000;
Pelo[] lista ;
float[] z = new float[cuantos]; 
float[] phi = new float[cuantos]; 
float[] largos = new float[cuantos]; 
float radio;
float rx = 0;
float ry =0;

public NoiseSphere(PGraphics offscreen){
  radio = offscreen.width/3;
  lista = new Pelo[cuantos];
  for (int i=0; i<cuantos; i++) {
    lista[i] = new Pelo(radio);
  }
  noiseDetail(3);

}


public void setup_obj() {
  //size(640, 360, P3D);

}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  offscreen.background(0);
  offscreen.translate(offscreen.width/2, offscreen.height/2);

  float rxp = ((surfaceMouse.x-(offscreen.width/2))*0.005f);
  float ryp = ((surfaceMouse.y-(offscreen.height/2))*0.005f);
  rx = (rx*0.9f)+(rxp*0.1f);
  ry = (ry*0.9f)+(ryp*0.1f);
  offscreen.rotateY(rx);
  offscreen.rotateX(ry);
  offscreen.fill(0);
  offscreen.noStroke();
  offscreen.sphere(radio);

  for (int i = 0;i < cuantos; i++) {
    lista[i].dibujar(offscreen);
  }
}

public void pause_obj(){
  offscreen.fill(0);
}

 }


class Pelo {
  
  float z ;
  float phi ;
  float largo;
  float theta;
  float radio;
  
  
  
  public Pelo(float r){
    radio = r;
  z = random(-radio, radio);
  phi = random(TWO_PI);
  largo = random(1.15f, 1.2f);
  theta = asin(z/radio);
  
  }

  public void dibujar(PGraphics offscreen) {
    float off = (noise(millis() * 0.0005f, sin(phi))-0.5f) * 0.3f;
    float offb = (noise(millis() * 0.0007f, sin(z) * 0.01f)-0.5f) * 0.3f;

    float thetaff = theta+off;
    float phff = phi+offb;
    float x = radio * cos(theta) * cos(phi);
    float y = radio * cos(theta) * sin(phi);
    float z = radio * sin(theta);
    float msx= screenX(x, y, z);
    float msy= screenY(x, y, z);

    float xo = radio * cos(thetaff) * cos(phff);
    float yo = radio * cos(thetaff) * sin(phff);
    float zo = radio * sin(thetaff);

    float xb = xo * largo;
    float yb = yo * largo;
    float zb = zo * largo;

    offscreen.beginShape(LINES);
    offscreen.stroke(0);
    offscreen.vertex(x, y, z);
    offscreen.stroke(200, 150);
    offscreen.vertex(xb, yb, zb);
    offscreen.endShape();
  }
}
///**
// * Spore 1 
// * by Mike Davis. 
// * 
// * A short program for alife experiments. Click in the window to restart.
// * Each cell is represented by a pixel on the display as well as an entry in
// * the array 'cells'. Each cell has a run() method, which performs actions
// * based on the cell's surroundings.  Cells run one at a time (to avoid conflicts
// * like wanting to move to the same space) and in random order.
// */
//
//World w;
//int numcells = 0;
//int maxcells = 6700;
//Cell[] cells = new Cell[maxcells];
//color spore_color;
//// set lower for smoother animation, higher for faster simulation
//int runs_per_loop = 10000;
//color black = color(0, 0, 0);
//  
//void setup() {
//  size(640, 360);
//  frameRate(24);
//  reset();
//}
//
//void reset() {
//  clearScreen();  
//  w = new World();
//  spore_color = color(172, 255, 128);
//  seed();
//}
//
//void seed() {
//  // Add cells at random places
//  for (int i = 0; i < maxcells; i++)
//  {
//    int cX = (int)random(width);
//    int cY = (int)random(height);
//    if (w.getpix(cX, cY) == black) {
//      w.setpix(cX, cY, spore_color);
//      cells[numcells] = new Cell(cX, cY);
//      numcells++;
//    }
//  }
//}
//
//void draw() {
//  // Run cells in random order
//  for (int i = 0; i < runs_per_loop; i++) {
//    int selected = min((int)random(numcells), numcells - 1);
//    cells[selected].run();
//  }
//}
//
//void clearScreen() {
//  background(0);
//}
//
//class Cell {
//  int x, y;
//  Cell(int xin, int yin) {
//    x = xin;
//    y = yin;
//  }
//
//    // Perform action based on surroundings
//  void run() {
//    // Fix cell coordinates
//    while(x < 0) {
//      x+=width;
//    }
//    while(x > width - 1) {
//      x-=width;
//    }
//    while(y < 0) {
//      y+=height;
//    }
//    while(y > height - 1) {
//      y-=height;
//    }
//    
//    // Cell instructions
//    if (w.getpix(x + 1, y) == black) {
//      move(0, 1);
//    } else if (w.getpix(x, y - 1) != black && w.getpix(x, y + 1) != black) {
//      move((int)random(9) - 4, (int)random(9) - 4);
//    }
//  }
//  
//  // Will move the cell (dx, dy) units if that space is empty
//  void move(int dx, int dy) {
//    if (w.getpix(x + dx, y + dy) == black) {
//      w.setpix(x + dx, y + dy, w.getpix(x, y));
//      w.setpix(x, y, color(0));
//      x += dx;
//      y += dy;
//    }
//  }
//}
//
////  The World class simply provides two functions, get and set, which access the
////  display in the same way as getPixel and setPixel.  The only difference is that
////  the World class's get and set do screen wraparound ("toroidal coordinates").
//class World {
//  
//  void setpix(int x, int y, int c) {
//    while(x < 0) x+=width;
//    while(x > width - 1) x-=width;
//    while(y < 0) y+=height;
//    while(y > height - 1) y-=height;
//    set(x, y, c);
//  }
//  
//  color getpix(int x, int y) {
//    while(x < 0) x+=width;
//    while(x > width - 1) x-=width;
//    while(y < 0) y+=height;
//    while(y > height - 1) y-=height;
//    return get(x, y);
//  }
//}
//
//void mousePressed() {
//  numcells = 0;
//  reset();
//}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "live_viz_oop_keystone" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
