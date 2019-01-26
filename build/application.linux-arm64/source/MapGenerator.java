import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MapGenerator extends PApplet {

// Rishabh Bector



ControlP5 cp5;
Slider2D s;

// Parameters //

int mapWidth = 800;
int mapHeight = 800;
int cellSize = 8;

// Water, Sand, Grass, Mountain, Snow

int l1Color = color(0, 0, 200);
int l2Color = color(255, 245, 220);
int l3Color = color(10, 200, 10);
int l4Color = color(160, 82, 45);
int l5Color = color(255, 255, 255);

int layer1 = 100;
int layer2 = 130;
int layer3 = 160;
int layer4 = 180;

////////////////

float noiseScale = 0.1f;
int numCells = (mapHeight * mapWidth) / cellSize;

public void resetParams() {
  layer1 = PApplet.parseInt(random(30, 110));
  layer2 = PApplet.parseInt(random(layer1, 140));
  layer3 = PApplet.parseInt(random(layer2, 170));
  layer4 = PApplet.parseInt(random(layer3, 190));
}

public String randomSeed() {
  String seed = "";
  for(int i = 0; i < 9; i++) {
    seed += str(PApplet.parseInt(random(10)));
  }
  return seed;
}

public int[][] generateNoise() {
  int[][] noise = new int[mapWidth][mapHeight];
  for(int x = 0; x < mapWidth / cellSize; x += 1) {
    for(int y = 0; y < mapHeight / cellSize; y += 1) {
      noise[x][y] = color(noise(x * noiseScale, y * noiseScale) * 255);
    }
  }
  return noise;
}

public int[][] mapColors(int[][] noise) {
  int[][] colors = new int[mapWidth][mapHeight];
  for(int x = 0; x < mapWidth / cellSize; x += 1) {
    for(int y = 0; y < mapHeight / cellSize; y += 1) {
      float heightVal = blue(noise[x][y]);
      if(heightVal <= layer1) {
        colors[x][y] = l1Color;
      }
      if(heightVal > layer1 && heightVal <= layer2) {
        colors[x][y] = l2Color;
      }
      if(heightVal > layer2 && heightVal <= layer3) {
        colors[x][y] = l3Color;
      }
      if(heightVal > layer3 && heightVal <= layer4) {
        colors[x][y] = l4Color;
      }
      if(heightVal > layer4) {
        colors[x][y] = l5Color;
      }
    }
  }
  return colors;
}

public void grid(int[][] colors) {
  for(int x = 0; x < mapWidth / cellSize; x += 1) {
    for(int y = 0; y < mapHeight / cellSize; y += 1) {
      fill(colors[x][y]);
      rect(x * cellSize, y * cellSize, cellSize, cellSize);
    }
  }
}

public void RandomBiome(int val) {
  resetParams();
  updateGrid();
}

public void RandomSeed(int val) {
  String seed = randomSeed();
  print("SEED: " + seed + "\n");
  noiseSeed(PApplet.parseInt(seed));
  randomSeed(PApplet.parseInt(seed));
  updateGrid();
}

public void ResetBiome(int val) {
  layer1 = 100;
  layer2 = 130;
  layer3 = 160;
  layer4 = 180;
  updateGrid();
}

public void updateGrid() {
  int[][] noise = generateNoise();
  int[][] colors = mapColors(noise);
  grid(colors);
}

public void setup() {
  
  cp5 = new ControlP5(this);
  cp5.addButton("RandomBiome").setValue(0).setPosition(850, 100).setSize(100, 50);
  cp5.addButton("RandomSeed").setValue(0).setPosition(850, 200).setSize(100, 50);
  cp5.addButton("ResetBiome").setValue(0).setPosition(850, 300).setSize(100, 50);
}

public void draw() {}
  public void settings() {  size(1000, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MapGenerator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
