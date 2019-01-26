// Rishabh Bector

import controlP5.*;

ControlP5 cp5;
Slider2D s;

// Parameters //

int mapWidth = 800;
int mapHeight = 800;
int cellSize = 8;

// Water, Sand, Grass, Mountain, Snow

color l1Color = color(0, 0, 200);
color l2Color = color(255, 245, 220);
color l3Color = color(10, 200, 10);
color l4Color = color(160, 82, 45);
color l5Color = color(255, 255, 255);

int layer1 = 100;
int layer2 = 130;
int layer3 = 160;
int layer4 = 180;

////////////////

float noiseScale = 0.1;
int numCells = (mapHeight * mapWidth) / cellSize;

void resetParams() {
  layer1 = int(random(30, 110));
  layer2 = int(random(layer1, 140));
  layer3 = int(random(layer2, 170));
  layer4 = int(random(layer3, 190));
}

String randomSeed() {
  String seed = "";
  for(int i = 0; i < 9; i++) {
    seed += str(int(random(10)));
  }
  return seed;
}

color[][] generateNoise() {
  color[][] noise = new color[mapWidth][mapHeight];
  for(int x = 0; x < mapWidth / cellSize; x += 1) {
    for(int y = 0; y < mapHeight / cellSize; y += 1) {
      noise[x][y] = color(noise(x * noiseScale, y * noiseScale) * 255);
    }
  }
  return noise;
}

color[][] mapColors(color[][] noise) {
  color[][] colors = new color[mapWidth][mapHeight];
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

void grid(color[][] colors) {
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
  noiseSeed(int(seed));
  randomSeed(int(seed));
  updateGrid();
}

public void ResetBiome(int val) {
  layer1 = 100;
  layer2 = 130;
  layer3 = 160;
  layer4 = 180;
  updateGrid();
}

void updateGrid() {
  color[][] noise = generateNoise();
  color[][] colors = mapColors(noise);
  grid(colors);
}

void setup() {
  size(1000, 800);
  cp5 = new ControlP5(this);
  cp5.addButton("RandomBiome").setValue(0).setPosition(850, 100).setSize(100, 50);
  cp5.addButton("RandomSeed").setValue(0).setPosition(850, 200).setSize(100, 50);
  cp5.addButton("ResetBiome").setValue(0).setPosition(850, 300).setSize(100, 50);
}

void draw() {}
