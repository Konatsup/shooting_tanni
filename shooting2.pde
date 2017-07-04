//final int DIS_SIZE_X = 960;
//final int DIS_SIZE_Y = 720;
Ship ship;
Enemy enemy1;
Enemy enemy2;
Enemy enemy3;
Enemy enemy4;
Item item1;
Item item2;
Item item3;
Item item4;
Stage stage1;
Bomb bomb;
Bullet bullet1;
Bullet bullet2;
Info info;
Menu menu;
Effect effect;
Background background;
PImage img;
PImage img0;
PImage img1;
PImage img2;
PImage img3;
PImage img4;
PImage img5;
PImage img6;
PImage img7;
PImage img8;
PImage img9;
PFont font;
boolean gameover;

final int GAME_HEIGHT = 720;
final int GAME_WIDTH = 640;
final int LIMIT_GAUGE = 256;
int enemy1_score = 100;
int enemy2_score = 200;
int enemy3_score = 300;
int enemy4_score = 400;
float enemy1_speed = 1.5;
float enemy2_speed = 0.5;
int keyStat; //キーの状態を格納する変数
boolean key_flag = true;
boolean menu_flag = false;
boolean effect_flag = false;
//Factory factory = new Factory();
String[] stage1_data;
String[] stage2_data;
String[] stage3_data;
int csvLength;
int csvWidth=0;
String [][] csv;
int menu_Stat = 1;
float ft;
float error_t;

void setup() {
  size(960, 720);
  frameRate(60);
  rectMode(CENTER); // center mode
  img = loadImage("_shot_green.png");
  // img = loadImage("marukin.jpg"); 
  img0 = loadImage("report_3.png");
  img1 = loadImage("redbull.png");
  img2 = loadImage("monster.png");
  img3 = loadImage("ripo_d2.png");
  img4 = loadImage("TANNI_2.png");
  img5 = loadImage("redbull_logo.jpg");
  img6 = loadImage("monster_logo.png");
  img7 = loadImage("ripod_logo.jpg");
  img8 = loadImage("sky.jpeg");
  img9 = loadImage("report_4.png");
  ship = new Ship();
  String lines[] = loadStrings("course.csv");
  //calculate max width of csv file
  for (int i=0; i < lines.length; i++) { 
    String [] chars=split(lines[i], ','); 
    if (chars.length>csvWidth) {
      csvWidth=chars.length;
    }
  }
  //create csv array based on # of rows and columns in csv file
  csv = new String [lines.length][csvWidth];
  csvLength =lines.length; //
  //parse values into 2d array
  for (int i=0; i < lines.length; i++) {
    String [] temp = new String [lines.length];
    temp= split(lines[i], ',');
    for (int j=0; j < temp.length; j++) {
      csv[i][j]=temp[j];
    }
  }

  enemy1 = new Enemy(0, 0, 50, 100, enemy1_speed,500, 1, enemy1_score);
  enemy2 = new Enemy(30, 0, 70, 100, enemy1_speed,1600, 2, enemy2_score);
//  enemy3 = new Enemy(600, 0, 600, 600, 1, 3, 2);
  //enemy4 = new Enemy(200, 300, 50, 50, 1, 4, 3);
  item1 = new Item(200, 0, 32, 32, 1, 1);
  item2 = new Item(400, 0, 32, 32, 1, 2);
  item3 = new Item(300, 0, 32, 32, 1, 3);
  item4 = new Item(350, 0, 32, 32, 1, 4);
  bomb = new Bomb();
  bullet1 = new Bullet(ship.sx, ship.sy+3, 20, 30, radians(-90), 1);
  bullet2 = new Bullet(ship.sx, ship.sy+3, 20, 0, 0, 2);
  stage1 = new Stage(1);
  background = new Background();
  menu = new Menu();
  effect = new Effect();
  //er1 = new E_Report(100,0,10,8);
  font = createFont("FFScala", 24);
  textFont(font);
  gameover = false;
  error_t = 0;
}

// print time
void print_time() {
  //ft = (float)millis() / 1000 - error_t;
  //text("Time:",680,30);
  //text(nf(ft, 1, 2), 800, 30);
  text("Framecount:", 680, 50);
  text(frameCount, 880, 50);
}

void keyPressed() {
  if (key_flag) {
    if (key == CODED) {
      switch(keyCode) {
        //ビットセット
      case UP:
        keyStat|=0x1;
        break;
      case DOWN:
        keyStat|=0x2;
        break;
      case LEFT:
        keyStat|=0x4;
        break;
      case RIGHT:
        keyStat|=0x8;
        break;
      case SHIFT:
        keyStat|=0x10;
        break;
      }
    }
    switch(key) {
    case 'z':
    case 'Z':
      keyStat|=0x20;
      break;
    case 'x':
    case 'X':
      keyStat|=0x40;
      break;
    case's':
    case'S':
      keyStat|=0x80;
      break;
    case 'a':
    case 'A':
      keyStat|=0x100;
      break;
    }
  }
}

void keyReleased() {
  if (key == CODED) {
    switch(keyCode) {
      //ビットクリア
    case UP:
      keyStat&=~0x1;
      break;
    case DOWN:
      keyStat&=~0x2;
      break;
    case LEFT:
      keyStat&=~0x4;
      break;
    case RIGHT:
      keyStat&=~0x8;
      break;
    case SHIFT:
      keyStat&=~0x10;
      break;
    }
  }

  switch(key) {
  case 'z':
  case 'Z':
    keyStat&=~0x20;
    break;
  case 'x':
  case 'X':
    keyStat&=~0x40;
    break;
  case's':
  case'S':
    keyStat&=~0x80;
    break;
  case'a':
  case'A':
    keyStat&=~0x100;
    break;
  }
}

//
void draw() {
  if (menu_flag==false) {
    stage1.update();
  } else {
    menu.update(menu_Stat);
  }
}