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
  largo = random(1.15, 1.2);
  theta = asin(z/radio);
  
  }

  void dibujar(PGraphics offscreen) {
    float off = (noise(millis() * 0.0005, sin(phi))-0.5) * 0.3;
    float offb = (noise(millis() * 0.0007, sin(z) * 0.01)-0.5) * 0.3;

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
