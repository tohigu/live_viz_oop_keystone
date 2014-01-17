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
CornerPinSurface surface3;

PGraphics offscreen;

PImage imgMask;

char current_mode = '0';
char prev_mode = '0';

boolean cursor = true;

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

//mode 7
PenroseTile PT;

//mode 8
Particles PS;

//mode 9
Spore1 Spo;

public void setup(){
  // Keystone will only work with P3D or OPENGL renderers, 
  // since it relies on texture mapping to deform
  //size(800, 600, P3D);
  size(displayWidth + 50, displayHeight + 50, P3D);
  ks = new Keystone(this);
  surface = ks.createCornerPinSurface(512, 700, 20);
  surface2 = ks.createCornerPinSurface(512, 700, 20);
  surface3 = ks.createCornerPinSurface(512, 700, 20);
  
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
  PT = new PenroseTile(offscreen);
  PS = new Particles(offscreen);
  //Spo = new Spore1(offscreen);
  
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

  //offscreen.textureWrap(REPEAT);
  // Draw the scene, offscreen
  offscreen.beginDraw();
  
  offscreen.resetShader();
  switch(current_mode){
  case '0':
    offscreen.background(0,0);
    break;
  case '1': 
    CS.draw_obj(surfaceMouse, offscreen);
    break;
  case '2': 
    Mon.draw_obj(surfaceMouse, offscreen);
    break;
  case '3': 
    offscreen.textureWrap(REPEAT);
    Def.draw_obj(surfaceMouse, offscreen);
    break;
  case '4': 
    NS.draw_obj(surfaceMouse, offscreen);
    break;
   case '5': 
    Neb.draw_obj(surfaceMouse, offscreen);
    break;
  case '6': 
    FT.draw_obj(surfaceMouse, offscreen);
    break;
  case '7':
    PT.draw_obj(surfaceMouse, offscreen);
    break;
  case '8':
    PS.draw_obj(surfaceMouse, offscreen);
    break;
  case '9':
    Spo.draw_obj(surfaceMouse, offscreen);
    break;
  default:
    offscreen.background(0,0);
    break;
  }

  //println("Frame rate: " + int(frameRate), offscreen.width/2, offscreen.height/2);
  offscreen.endDraw();
  //image(offscreen, 0, 0, offscreen.width, offscreen.height);
  // most likely, you'll want a black background to minimize
  // bleeding around your projection area
  background(0);
  offscreen.mask(imgMask);
  // render the scene, transformed using the corner pin surface
  surface.render(offscreen);
  surface2.render(offscreen);
  surface3.render(offscreen);
  
}
  
  public void keyPressed() {

  offscreen.background(0);
  switch(key){
  case '0':
    setup_default();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '1': 
    CS.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '2': 
    Mon.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '3': 
    Def.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '4': 
    NS.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '5': 
    Neb.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '6': 
    FT.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '7':
    PT.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  case '8':
    PS.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;
  /*case '9':
    Spo.setup_obj();
    prev_mode = current_mode;
    current_mode = key;
    break;*/

  //CONTROL KEYS
  case 'c':
    // enter/leave calibration mode, where surfaces can be warped 
    // and moved
    ks.toggleCalibration();
    break;
  case 'p':
    if (cursor == true){
      noCursor();
      cursor = false;
    }
    else{
      cursor();
    }
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
  case '0':
    break;
  case '1': 
    println("inactivate CS");
    CS.pause_obj();
    //CS = new ConwaySprinkles(offscreen);
    break;
  case '2': 
    Mon.pause_obj();
    break;
  case '3': 
    Def.pause_obj();
    break;
  case '4': 
    NS.pause_obj();
    break;
  case '5': 
    Neb.pause_obj();
    break;
  case '6': 
    FT.pause_obj();
    break;
  case '7': 
    PT.pause_obj();
    break;
  case '8': 
    PS.pause_obj();
    break;
  case '9': 
    Spo.pause_obj();
    break;
  default:
    break;
  }
  resetShader();
}


class Cell {
 int x, y;
 int black; 

 Cell(int xin, int yin) {
   x = xin;
   y = yin;
   black = color(0, 0, 0);
 }

   // Perform action based on surroundings
 public void run(PGraphics offscreen, World w) {
   // Fix cell coordinates
   while(x < 0) {
     x+=offscreen.width;
   }
   while(x > offscreen.width - 1) {
     x-=offscreen.width;
   }
   while(y < 0) {
     y+=offscreen.height;
   }
   while(y > offscreen.height - 1) {
     y-=offscreen.height;
   }
   
   // Cell instructions
   if (w.getpix(offscreen, x + 1, y) == black) {
     move(offscreen, 0, 1, w);
   } else if (w.getpix(offscreen, x, y - 1) != black && w.getpix(offscreen, x, y + 1) != black) {
     move(offscreen, (int)random(9) - 4, (int)random(9) - 4, w);
   }
 }
 
 // Will move the cell (dx, dy) units if that space is empty
 public void move(PGraphics offscreen, int dx, int dy, World w) {
   if (w.getpix(offscreen, x + dx, y + dy) == black) {
     w.setpix(offscreen, x + dx, y + dy, w.getpix(offscreen, x, y));
     w.setpix(offscreen, x, y, color(0));
     x += dx;
     y += dy;
   }
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

  //offscreen.textureWrap(REPEAT);
  
 
  deform = loadShader("deform.glsl");
  deform.set("resolution", PApplet.parseFloat(offscreen.width), PApplet.parseFloat(offscreen.height));

}

public void setup_obj() {
  //size(640, 360, P2D);
  
  //offscreen.textureWrap(REPEAT);
  offscreen.textureWrap(0);
  tex = loadImage("tex1.jpg");
}

public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  //offscreen.textureWrap(REPEAT);
  deform.set("time", millis() / 1000.0f);
  deform.set("mouse", surfaceMouse.x, surfaceMouse.y);
  offscreen.shader(deform);
  offscreen.image(tex, 0, 0, offscreen.width, offscreen.height);
  //offscreen.texture(tex);
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
 resetShader(TRIANGLES);
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
  //return 1;
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
class LSystem 
{
  int steps = 0;

  String axiom;
  String rule;
  String production;

  float startLength;
  float drawLength;
  float theta;

  int generations;

  LSystem() {
    axiom = "F";
    rule = "F+F-F";
    startLength = 190.0f;
    theta = radians(120.0f);
    reset();
  }

  public void reset() {
    production = axiom;
    drawLength = startLength;
    generations = 0;
  }

  public int getAge() {
    return generations;
  }

  public void render() {
    translate(width/2, height/2);
    steps += 5;          
    if (steps > production.length()) {
      steps = production.length();
    }
    for (int i = 0; i < steps; i++) {
      char step = production.charAt(i);
      if (step == 'F') {
        rect(0, 0, -drawLength, -drawLength);
        noFill();
        translate(0, -drawLength);
      } 
      else if (step == '+') {
        rotate(theta);
      } 
      else if (step == '-') {
        rotate(-theta);
      } 
      else if (step == '[') {
        pushMatrix();
      } 
      else if (step == ']') {
        popMatrix();            
      }
    }
  }

  public void simulate(int gen) {
    while (getAge() < gen) {
      production = iterate(production, rule);
    }
  }

  public String iterate(String prod_, String rule_) {
    drawLength = drawLength * 0.6f;
    generations++;
    String newProduction = prod_;          
    newProduction = newProduction.replaceAll("F", rule_);
    return newProduction;
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
    //offscreen.textureWrap(REPEAT);
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
    resetShader(TRIANGLES);
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
  offscreen.resetShader(TRIANGLES);
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


class Particle {

  PVector velocity;
  float lifespan = 255;
  
  PShape part;
  float partSize;
  
  PVector gravity = new PVector(0,0.1f);


  Particle(PImage sprite) {
    partSize = random(10,60);
    part = createShape();
    part.beginShape(QUAD);
    part.noStroke();
    part.texture(sprite);
    part.normal(0, 0, 1);
    part.vertex(-partSize/2, -partSize/2, 0, 0);
    part.vertex(+partSize/2, -partSize/2, sprite.width, 0);
    part.vertex(+partSize/2, +partSize/2, sprite.width, sprite.height);
    part.vertex(-partSize/2, +partSize/2, 0, sprite.height);
    part.endShape();
    
    rebirth(width/2,height/2);
    lifespan = random(255);
  }

  public PShape getShape() {
    return part;
  }
  
  public void rebirth(float x, float y) {
    float a = random(TWO_PI);
    float speed = random(0.5f,4);
    velocity = new PVector(cos(a), sin(a));
    velocity.mult(speed);
    lifespan = 255;   
    part.resetMatrix();
    part.translate(x, y); 
  }
  
  public boolean isDead() {
    if (lifespan < 0) {
     return true;
    } else {
     return false;
    } 
  }
  

  public void update() {
    lifespan = lifespan - 1;
    velocity.add(gravity);
    
    part.setTint(color(255,lifespan));
    part.translate(velocity.x, velocity.y);
  }
}
class ParticleSystem {
  ArrayList<Particle> particles;

  PShape particleShape;

  ParticleSystem(int n, PImage sprite) {
    particles = new ArrayList<Particle>();
    particleShape = createShape(PShape.GROUP);

    for (int i = 0; i < n; i++) {
      Particle p = new Particle(sprite);
      particles.add(p);
      particleShape.addChild(p.getShape());
    }
  }

  public void update() {
    for (Particle p : particles) {
      p.update();
    }
  }

  public void setEmitter(float x, float y) {
    for (Particle p : particles) {
      if (p.isDead()) {
        p.rebirth(x, y);
      }
    }
  }

  public void display(PGraphics offscreen) {

    offscreen.shape(particleShape);
  }
}

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

  public void setup_obj(){
    
  }

  public void pause_obj(){
    
  }

  public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
    offscreen.background(0);
    ps.update();
    ps.display(offscreen);
    
    ps.setEmitter(surfaceMouse.x,surfaceMouse.y);
    
    offscreen.fill(255);
    //textSize(16);
    //text("Frame rate: " + int(frameRate), 10, 20);
    
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

class PenroseLSystem extends LSystem {

  int steps = 0;
  float somestep = 0.1f;
  String ruleW;
  String ruleX;
  String ruleY;
  String ruleZ;

  PenroseLSystem() {
    axiom = "[X]++[X]++[X]++[X]++[X]";
    ruleW = "YF++ZF4-XF[-YF4-WF]++";
    ruleX = "+YF--ZF[3-WF--XF]+";
    ruleY = "-WF++XF[+++YF++ZF]-";
    ruleZ = "--YF++++WF[+ZF++++XF]--XF";
    startLength = 460.0f;
    theta = radians(36);  
    reset();
  }

  public void useRule(String r_) {
    rule = r_;
  }

  public void useAxiom(String a_) {
    axiom = a_;
  }

  public void useLength(float l_) {
    startLength = l_;
  }

  public void useTheta(float t_) {
    theta = radians(t_);
  }

  public void reset() {
    production = axiom;
    drawLength = startLength;
    generations = 0;
  }

  public int getAge() {
    return generations;
  }

  public void render(PGraphics offscreen) {
    offscreen.line(0, 0, 0, -drawLength);
    offscreen.translate(offscreen.width/2, offscreen.height/2);
    int pushes = 0;
    int repeats = 1;
    steps += 12;          
    if (steps > production.length()) {
      steps = production.length();
    }

    for (int i = 0; i < steps; i++) {
      char step = production.charAt(i);
      if (step == 'F') {
        offscreen.stroke(255, 60);
        for (int j = 0; j < repeats; j++) {
          offscreen.line(0, 0, 0, -drawLength);
          offscreen.noFill();
          offscreen.translate(0, -drawLength);
        }
        repeats = 1;
      } 
      else if (step == '+') {
        for (int j = 0; j < repeats; j++) {
          offscreen.rotate(theta);
        }
        repeats = 1;
      } 
      else if (step == '-') {
        for (int j =0; j < repeats; j++) {
          offscreen.rotate(-theta);
        }
        repeats = 1;
      } 
      else if (step == '[') {
        pushes++;            
        offscreen.pushMatrix();
      } 
      else if (step == ']') {
        offscreen.popMatrix();
        pushes--;
      } 
      else if ( (step >= 48) && (step <= 57) ) {
        repeats = (int)step - 48;
      }
    }

    // Unpush if we need too
    while (pushes > 0) {
      offscreen.popMatrix();
      pushes--;
    }
  }

  public String iterate(String prod_, String rule_) {
    String newProduction = "";
    for (int i = 0; i < prod_.length(); i++) {
      char step = production.charAt(i);
      if (step == 'W') {
        newProduction = newProduction + ruleW;
      } 
      else if (step == 'X') {
        newProduction = newProduction + ruleX;
      }
      else if (step == 'Y') {
        newProduction = newProduction + ruleY;
      }  
      else if (step == 'Z') {
        newProduction = newProduction + ruleZ;
      } 
      else {
        if (step != 'F') {
          newProduction = newProduction + step;
        }
      }
    }

    drawLength = drawLength * 0.5f;
    generations++;
    return newProduction;
  }

}

/** 
 * Penrose Tile L-System 
 * by Geraldine Sarmiento.
 *  
 * This code was based on Patrick Dwyer's L-System class. 
 */

 class PenroseTile{

	PenroseLSystem ds;
	int steps = 0;
	  float somestep = 0.1f;
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

	public void setup_obj(){

	}

	public void pause_obj(){
		println("changeFR");
		frameRate(60);
	}

	public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
		println("testttttt");
		frameRate(7);
	  	  offscreen.background(0);
	  	  ds.render(offscreen);


	   

	}

}




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
	int spore_color;
	// set lower for smoother animation, higher for faster simulation
	int runs_per_loop = 10000;
	int black = color(0, 0, 0);
	 
	public Spore1(PGraphics offscreen) {
	 //size(640, 360);
	 frameRate(24);
	 offscreen.background(0);
	 w = new World();
	 spore_color = color(172, 255, 128);
	 seed();
	}

	public void setup_obj(){
//
	}




	public void seed() {
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

	public void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
	 // Run cells in random order
	 for (int i = 0; i < runs_per_loop; i++) {
	   int selected = min((int)random(numcells), numcells - 1);
	   cells[selected].run(offscreen, w);
	 }
	}

	public void pause_obj(){

		frameRate(60);
	}






}
//  The World class simply provides two functions, get and set, which access the
//  display in the same way as getPixel and setPixel.  The only difference is that
//  the World class's get and set do screen wraparound ("toroidal coordinates").
class World {
 
 public void setpix(PGraphics offscreen , int x, int y, int c) {
   while(x < 0) x+= offscreen.width;
   while(x > width - 1) x-= offscreen.width;
   while(y < 0) y+= offscreen.height;
   while(y > height - 1) y-= offscreen.height;
   offscreen.set(x, y, c);
 }
 
 public int getpix(PGraphics offscreen , int x, int y) {
   while(x < 0) x+= offscreen.width;
   while(x > width - 1) x-= offscreen.width;
   while(y < 0) y+= offscreen.height;
   while(y > height - 1) y-= offscreen.height;
   return offscreen.get(x, y);
 }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "live_viz_oop_keystone" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
