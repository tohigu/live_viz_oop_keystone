import deadpixel.keystone.*;

Keystone ks;
CornerPinSurface surface;
CornerPinSurface surface2;

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

void setup(){
  // Keystone will only work with P3D or OPENGL renderers, 
  // since it relies on texture mapping to deform
  //size(800, 600, P3D);
  size(displayWidth + 50, displayHeight + 50, P3D);
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
  PT = new PenroseTile(offscreen);
  
  imgMask = loadImage("mask.png");
  

}

void setup_default(){

  background(0,0);
}

void draw(){

  //noCursor();
  
    // Convert the mouse coordinate into surface coordinates
  // this will allow you to use mouse events inside the 
  // surface from your screen. 
  PVector surfaceMouse = surface.getTransformedMouse();

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
  default:
    offscreen.background(0,0);
    break;
  }
  /*offscreen.fill(255);
  offscreen.textSize(16);
  offscreen.text("Frame rate: " + int(frameRate), offscreen.width/2, offscreen.height/2);*/
  println("Frame rate: " + int(frameRate), offscreen.width/2, offscreen.height/2);
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
  
  void keyPressed() {

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

  //CONTROL KEYS
  case 'c':
    // enter/leave calibration mode, where surfaces can be warped 
    // and moved
    ks.toggleCalibration();
    break;
  case 'p':
    if (cursor == true){
      noCursor();
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
  default:
    break;
  }
  resetShader();
}


