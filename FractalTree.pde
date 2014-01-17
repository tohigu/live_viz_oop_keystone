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

void setup_obj(){
 //break;
}

void pause_obj(){
 resetShader(TRIANGLES);
}

void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  offscreen.background(0);
  //frameRate(30);
  offscreen.stroke(255);
  // Let's pick an angle 0 to 90 degrees based on the mouse position
  float a = (surfaceMouse.x / (float) offscreen.width) * 90f;
  // Convert it to radians
  theta = radians(a);
  // Start the tree from the bottom of the screen
  offscreen.translate(offscreen.width/2,offscreen.height * .8);
  // Draw a line 120 pixels
  offscreen.line(0,0,0,-120);
  // Move to the end of that line
  offscreen.translate(0,-120);
  // Start the recursive branching!
  //return 1;
  branch(120);

}

void branch(float h) {
  // Each branch will be 2/3rds the size of the previous one
  h *= 0.66;
  
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
