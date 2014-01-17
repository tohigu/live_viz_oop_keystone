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


void setup_obj() {
  //size(640, 360, P3D);

}

void draw_obj(PVector surfaceMouse, PGraphics offscreen) {
  offscreen.background(0);
  offscreen.translate(offscreen.width/2, offscreen.height/2);

  float rxp = ((surfaceMouse.x-(offscreen.width/2))*0.005);
  float ryp = ((surfaceMouse.y-(offscreen.height/2))*0.005);
  rx = (rx*0.9)+(rxp*0.1);
  ry = (ry*0.9)+(ryp*0.1);
  offscreen.rotateY(rx);
  offscreen.rotateX(ry);
  offscreen.fill(0);
  offscreen.noStroke();
  offscreen.sphere(radio);

  for (int i = 0;i < cuantos; i++) {
    lista[i].dibujar(offscreen);
  }
}

void pause_obj(){
  offscreen.fill(0);
}

 }


