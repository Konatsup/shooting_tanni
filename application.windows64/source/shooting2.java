import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class shooting2 extends PApplet {

//final int DIS_SIZE_X = 960;
//final int DIS_SIZE_Y = 720;
Boss boss;
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
float enemy1_speed = 1.5f;
float enemy2_speed = 0.5f;
int keyStat; //\u30ad\u30fc\u306e\u72b6\u614b\u3092\u683c\u7d0d\u3059\u308b\u5909\u6570
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

public void setup() {
  
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
  boss = new Boss(width / 2, 60, 40);
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
public void print_time() {
  //ft = (float)millis() / 1000 - error_t;
  //text("Time:",680,30);
  //text(nf(ft, 1, 2), 800, 30);
  text("Framecount:", 680, 50);
  text(frameCount, 880, 50);
}

public void keyPressed() {
  if (key_flag) {
    if (key == CODED) {
      switch(keyCode) {
        //\u30d3\u30c3\u30c8\u30bb\u30c3\u30c8
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

public void keyReleased() {
  if (key == CODED) {
    switch(keyCode) {
      //\u30d3\u30c3\u30c8\u30af\u30ea\u30a2
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
public void draw() {
  if (menu_flag==false) {
    stage1.update();
  } else {
    menu.update(menu_Stat);
  }
}
class Background{
  int x , y;
  boolean flag;
  Background(){
    x = 320;
    y = 0;
    flag=false;
  }
 
 public void update(){

 }
 } 
 
 
final int REDBULL_TIME = 20;
final int MONSTER_TIME = 115;
final int RIPOD_TIME =12;
final int RDBL_POWER = 300;
final int MSTR_POWER = 300;
final int RIPD_POWER = 300;
final int RDBL_WIDTH = 100;
final int RDBL_HEIGHT = 100;
final int MSTR_WIDTH = 150;
final int MSTR_HEIGHT = 150;
final int RIPD_WIDTH = 150;
final int RIPD_HEIGHT = 150;
int ripd_speed = 5;

class Bomb extends MyObject {
  boolean bomb_flag, rdbl_flag, mstr_flag, ripd_flag; 
  int b_y, b_w, b_h;
  int time_count;
  Bomb() {
    b_y=0;
    bomb_flag = false;
    rdbl_flag = false;
    mstr_flag = false;
    ripd_flag = false;
    time_count = 0;
  }
  public void Redbull() {
    ship.s_speed = 8;
    imageMode(CENTER);
    image(img5, ship.sx, ship.sy, RDBL_WIDTH, RDBL_HEIGHT);
  }

  public void Monster(ArrayList<Bullet> b) {
    float v = 5;
    float r = 0;
    r = radians(time_count*time_count/10+10);
    imageMode(CENTER);
    image(img6, GAME_WIDTH/2, GAME_HEIGHT/2, MSTR_WIDTH, MSTR_HEIGHT);
    if (frameCount%10 == 0) {

      //tint(#FF0000);
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r, 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(45), 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(90), 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(135), 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(180), 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(225), 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(270), 2));
      b.add(new Bullet(GAME_WIDTH/2, GAME_HEIGHT/2, bullet2.b_w, v, r+radians(315), 2));
    }
  }

  public void Ripod() {
    if (b_y >= 0 && b_y <= GAME_HEIGHT+100) {
      tint(255, 255, 255, 210);
      imageMode(CENTER);
      image(img7, ship.sx, ship.sy - RIPD_HEIGHT, RIPD_WIDTH, RIPD_HEIGHT);
      imageMode(CENTER);
      image(img7, ship.sx, ship.sy - RIPD_HEIGHT*2, RIPD_WIDTH, RIPD_HEIGHT);
      imageMode(CENTER);
      image(img7, ship.sx, ship.sy - RIPD_HEIGHT*3, RIPD_WIDTH, RIPD_HEIGHT);
      imageMode(CENTER);
      image(img7, ship.sx, ship.sy - RIPD_HEIGHT*4, RIPD_WIDTH, RIPD_HEIGHT);
      imageMode(CENTER);
      image(img7, ship.sx, ship.sy - RIPD_HEIGHT*5, RIPD_WIDTH, RIPD_HEIGHT);
      noTint();
    }
  }


  public boolean update(ArrayList<Bullet> b) {
    if (rdbl_flag) {
      Redbull();
      if (frameCount%10 == 0) {
        time_count++;
      }
      if (time_count >= REDBULL_TIME) {
        rdbl_flag = false;
        ship.s_speed = 5;
        time_count = 0;
        bomb_flag = false;
      }
    }

    if (mstr_flag) {
      Monster(b);
      if (frameCount%5 == 0) {
        time_count+=2;
      }
      if (time_count >= MONSTER_TIME) {
        mstr_flag = false;
        time_count = 0;
        bomb_flag = false;
      }
    }

    if (ripd_flag) {
      Ripod();
      if (frameCount%10 == 0) {
        time_count++;
      }
      if (time_count >= RIPOD_TIME) {
        ripd_flag = false;
        time_count = 0;
        bomb_flag = false;
      }
    }



    return true;
  }
}

class Boss {
  int hp, bw;
  float bx, by, bcx;
  ArrayList danmaku;

  Boss(float x, float y, int w) {
    bx = bcx = x;
    by = y;
    bw = w;
    hp = 256;
    danmaku = new ArrayList();
  }
  //
  public void hit() {
    hp--;
    if (hp <= 0) 
      gameover = true;
  }
  //
  public void fire_360(float x, float y) {  
    for (int i = 0; i < 360; i+= 10) { 
      float rad = radians(i);
      danmaku.add(new Tama(x, y, 15, cos(rad), sin(rad)));
    }
  }
  //
  public void update() {
    // boss update
    float dx;
    dx = 75.0f * sin(radians(frameCount * 6));
    bx = bcx + dx;
    stroke(0, 255, 0);
    fill(256 - hp, 0, 0);
    rect(bx, by, bw, 20);

    // fire
    if (frameCount % 30 == 0)
      fire_360(bx, by);

    // danmaku update
    for (int i = danmaku.size() -1; i >= 0; i--) {
      Tama t = (Tama)danmaku.get(i);
      if (t.update() == false)
        danmaku.remove(i);
    }
  }
}
class Bullet extends MyObject {
  float b_x, b_y, b_w,b_rad, b_vx, b_vy;
  int b_type;
  boolean b_num;

  Bullet(float x, float y, float w, float s, float r, int t) {
    b_x = x;
    b_y = y;
    b_w = w;
    b_rad = r;
    b_vx = s*cos(r);
    b_vy = s*sin(r);
    b_type = t;
    b_num = false;
  }

  public void draw() {
    b_x += b_vx;
    b_y += b_vy;

pushMatrix();
    imageMode(CENTER);
    image(img, this.b_x, this.b_y, this.b_w, this.b_w*2);
    rotate(b_rad);
    popMatrix();
  }

  public boolean update() {

    draw();
    if (this.b_y < 0 || this.b_y >GAME_HEIGHT || ((this.b_type==2) &&(this.b_x >GAME_WIDTH))){
      return false;
    } else {
      return true;
    }
  }
}
class Draw {
  int dx, dy;

  Draw(int x, int y) {
    dx = x;
    dy = y;


   
  }
  
/*  boolean update() {
    tx += dx;
    ty += dy;
    stroke(255, 255, 0);
    noFill();
    ellipse(tx, ty, tr, tr);

    // area check 
    if (ty > height || ty < 0 || tx > width || tx < 0) {
      return false;
    }
    // hit check
    if (dist(tx, ty, ship.sx, ship.sy) < (tr / 2) + 2)
      ship.hit();

    return true;
  }

   // enemyS1.add(new Enemy(dx, dy, 100, 3, 1));*/
  
}
final int ENEMY_EFFECT_TIME=3;
class Effect {
  int t;
  Effect() {
    t=0;
  }
  public void update(float ex, float ey) {
    if (frameCount%60==0) {
      t++;
    }
    
    if (t<ENEMY_EFFECT_TIME) {
      fill(0, 0, 255);
      textSize(20);
      text("dead", ex-50/2, ey+50/2);
    } else {
       t=0;
      effect_flag = false;
    }
  }
}

class Enemy extends MyObject {
  int hp, ew, eh,  e_num, e_score;
  float ex, ey, ecx,e_speed;
  boolean e_death;

  Enemy(float x, float y, int w, int h, float speed, int t_hp,int num, int score ) {
    ex = ecx = x;
    ey = y;
    ew = w;
    eh = h;
    e_speed = speed;
    hp = t_hp;
    e_num = num; //\u6575\u306e\u7a2e\u985e
    e_death = false;
    e_score = score;
  }

  public void draw() {
    if (ey >= 0 && ey <= GAME_HEIGHT) {
      ey += e_speed;

      switch(e_num) {
      case 1:  //\u30ec\u30dd\u30fc\u30c8
        imageMode(CENTER);
        image(img0, ex, ey, ew, ew);
        break;
      case 2: //\u5b9f\u9a13\u30ec\u30dd\u30fc\u30c8
        imageMode(CENTER);
        image(img9, ex, ey, ew, ew);
        break;
      case 3:  //\u30b5\u30fc\u30af\u30eb

        break;
      case 4:  //\u30d0\u30a4\u30c8

        break;
      }
    }else if(ey>=GAME_HEIGHT){
      ey = 720;
    }
  }


  public boolean update() {
    // enemy update
    // float dx;
    draw();
    // hit_judge(b1);

    if (this.ey < 0) {
      return false;
    } else if (this.ey >GAME_HEIGHT) {
      this.ey =720;
      if (ship.stop(this.e_num)==false) {
        return false;
      }
    }
    if (this.e_death) 
      return false;

    return true;
  }
}
static class Factory {
  private static ArrayList<MyObject>  myobject; 

  public static void bullet_create(MyObject tmp) {

 //   myobject.add(new MyObject(tmp));
    
  }
  
  
  
  
  
}
int u_power = 2;
int d_power = 2;
final int RDBL_SCORE = 100;
final int MSTR_SCORE = 100;
final int RIPD_SCORE = 100;
final int TANNI_SCORE = 300;
class Info {
  int power;  //\u30d1\u30ef\u30fc
  int score;  //\u5f97\u70b9
  int tanni;  //\u6240\u5f97\u5358\u4f4d\u6570
  int rakutan; //\u843d\u5358\u6570(\u6212\u3081)
  int rdbl;  //\u30ec\u30c3\u30c9\u30d6\u30eb\u306e\u6240\u6301\u6570
  int mstr;  //\u30e2\u30f3\u30b9\u30bf\u30fc\u306e\u6240\u6301\u6570
  int ripd;  //\u30ea\u30ddD\u306e\u6240\u6301\u6570
  int bomb_SELECT;

  Info(int p, int t) {
    score = 0;
    power = p;
    tanni = t;
    rakutan = 0;
    rdbl = 0;
    mstr = 0;
    ripd = 0;
    bomb_SELECT = 1;
  }


  public void getItem(int num) {
    switch(num) {
    case  1:
      rdbl++;
      upScore(RDBL_SCORE);
      break;
    case 2:
      mstr++;
      upScore(MSTR_SCORE);
      break;
    case 3:
      ripd++;
      upScore(RIPD_SCORE);
      break;
    case 4:
      tanni++;
      upScore(TANNI_SCORE);
      break;
    }
  }

  public void upScore(int s) {
    score +=  s;
  }


  public void downGauge() {
    power -= d_power;
  }
  public void upGauge() {
    power += u_power;
  }


  public void update() {
    if ((keyStat&0x80)!=0) {
      keyStat&=~0x80;
      if (bomb_SELECT == 3) {
        bomb_SELECT = 1;
      } else {
        bomb_SELECT++;
      }
    }

    line(640, 720, 640, 0);
    fill(255, 0, 0);
    quad(720, 280, 720, 300, info.power/2 + 720, 300, info.power/2 + 720, 280);
    textSize(30);
    text("SCORE:", 680, 100);
    text(score, 800, 100);
    text("RedBull:", 680, 350);
    text(rdbl, 820, 350);
    text("Monster:", 680, 400);
    text(mstr, 820, 400);
    text("RipoD:", 680, 450);
    text(ripd, 820, 450);
    text("\u5358\u4f4d\u6570:", 680, 500);
    text(tanni, 820, 500);
    text("\u843d\u5358\u6570:", 680, 550);
    text(rakutan, 820, 550);
    //text(bomb_SELECT, 680, 550);
    switch(bomb_SELECT) {
    case 1:
      text("\u2192", 650, 350);
      break;
    case 2:
      text("\u2192", 650, 400);
      break;
    case 3:
      text("\u2192", 650, 450);
      break;
    }
  }
}
class Item {
  int i_num;
  float ix, iy;
  int iw, ih, i_speed;
  boolean i_death;
  Item(float x, float y, int w, int h, int speed, int n) {
    ix = x;
    iy = y;
    iw = w;
    ih = h;
    i_num = n;
    i_speed = speed;
    i_death = false;
  }

  public void  draw() {
    if (iy >= 0 && iy <= GAME_HEIGHT+100) {
      iy += i_speed;
    }

    switch(i_num) {
    case 1:    //\u30ec\u30c3\u30c9\u30d6\u30eb
      imageMode(CENTER);
      image(img1, ix, iy, iw, iw);
      ellipse(ix, iy, 3, 3);
      break;
    case 2: //\u30e2\u30f3\u30b9\u30bf\u30fc
      imageMode(CENTER);
      image(img2, ix, iy, iw, iw);
      break;
    case 3: //\u30ea\u30ddD
      imageMode(CENTER);
      image(img3, ix, iy, iw, iw);
      break;
    case 4: //\u5358\u4f4d
      imageMode(CENTER);
      image(img4, ix, iy, iw, iw);
      break;
    }
  }

  public void hitJudge() {
    if (bomb.rdbl_flag) {
      if ((abs(ship.sx - this.ix) < (this.iw / 2) + RDBL_WIDTH/2) && (abs(ship.sy - this.iy) < (this.ih / 2) + RDBL_HEIGHT/2)) { 
        info.getItem(this.i_num); 
        this.i_death = true;
      }
    } else {
      if ((abs(ship.sx - this.ix) < (this.iw / 2))&& (abs(ship.sy - this.iy) < (this.ih / 2))) { 
        info.getItem(this.i_num); 
        this.i_death = true;
      }
    }
  }




  public boolean update() {
    draw();
    hitJudge();
    if (this.i_num == 4) {
      if (this.iy > GAME_HEIGHT+50) {
        info.rakutan++;
        return false;
      }
    }
    if (this.iy < 0 || this.iy > GAME_HEIGHT+50) {
        return false;
      }
    
    if (this.i_death) {  // game over
      return false;
    }

    return true;
  }
}
class Menu {
  int stat ;

  Menu() {
    stat = 1;
  }

  public void select() {
    if ((keyStat&0x01) !=0) {
      keyStat&=~0x01;
      if (stat==1) {
        stat=3;
      } else {
        stat--;
      }
    }

    if ((keyStat&0x02) !=0) {
      keyStat&=~0x02;
      if (stat==3) {
        stat=1;
      } else {
        stat++;
      }
    }

    if ((keyStat&0x20) !=0) {
      keyStat&=~0x20;
      switch(stat) {
      case 1:
        menu_flag=false;
        break;
      case 2:
        menu_Stat = 1;
        menu_flag=false;
        stage1 = new Stage(1);
        break;
      case 3:
        exit();
        break;
      }
    }

    switch(stat) {
    case 1:
      text("\u2192", GAME_WIDTH/2+50, 400);
      break;
    case 2:
      text("\u2192", GAME_WIDTH/2+50, 450);
      break;
    case 3:
      text("\u2192", GAME_WIDTH/2+50, 500);
      break;
    }
  }



  public void  update(int menu_Stat) {
    switch(menu_Stat) {
    case 1:  //PAUSE
      background(0, 127);
      textSize(100);
      text("PAUSE", GAME_WIDTH/2, GAME_HEIGHT/2-10);
      textSize(30);
      text("\u7d9a\u3051\u308b", GAME_WIDTH/2+110, 400);
      text("\u30ea\u30c8\u30e9\u30a4", GAME_WIDTH/2+90, 450);
      text("\u7d42\u308f\u308b", GAME_WIDTH/2+110, 500);
      select();
      break;
    case 2:  //CLEAR
      background(0, 127);
      textSize(100);
      text("CLEAR", GAME_WIDTH/2, GAME_HEIGHT/2-10);
      textSize(30);
      text("SCORE:", GAME_WIDTH/2+40, 400);
      text(info.score, GAME_WIDTH/2+230, 400);
      text("\u53d6\u5f97\u5358\u4f4d\u6570:", GAME_WIDTH/2+40, 450);
      text(info.tanni, GAME_WIDTH/2+230, 450);
      text("\u843d\u5358\u6570:", GAME_WIDTH/2+40, 500);
      text(info.rakutan, GAME_WIDTH/2+230, 500);
      text("\u9032\u7d1a\u53ef\u5426:", GAME_WIDTH/2+70, 600);
      text("\u53ef", GAME_WIDTH/2+210, 600);
      break;
    }
  }
}
class MyObject {
  MyObject() {
  }
  MyObject( MyObject obj) {
   // this=obj;
  }  
  public void register() {
  }
  public void draw() {
  }
  public boolean update() {
    return true;
  }
}
final int SHOOT_SPEED = 5;
final int STOP_TIME1 = 1;
final int STOP_TIME2 = 4;
int stop_time = 0;
class Ship extends MyObject {
  int hp;
  int sx, sy, sw, sh;
  int lx0, ly0, lx1, ly1;
  int l_err; //\u79fb\u52d5\u8aa4\u5dee
  //int mvx, mvy;
  int s_speed, d_speed; //\u81ea\u6a5f\u306e\u901f\u3055\u3001\u4f4e\u901f\u79fb\u52d5\u6642\u306e\u901f\u3055
  boolean shoot_judge;//\u7389\u3092\u6253\u3063\u3066\u3044\u308b\u72b6\u614b\u304b\u3069\u3046\u304b
  int shoot_count;
  Ship() {
    l_err = 5;
    hp = 256;
    sx = 300;
    sy = 300;
    sw = 10;
    sh = 7;
    lx0 = 0 + l_err; 
    ly0 = 0 + l_err;
    lx1 = 640 - l_err;
    ly1 = 720 - l_err;
    s_speed = 5; //\u81ea\u6a5f\u306e\u901f\u3055
    d_speed = 1; //downspeed
    shoot_judge = false;
    shoot_count = 0;
  }

  public boolean stop(int num) {
    key_flag = false;
    fill(255, 255, 0);
    ellipse(sx, sy, 50, 50);
    if (frameCount % 20 == 0) {
      stop_time++;
    }
    switch(num){
    case 1:
    if (stop_time > STOP_TIME1) {
      key_flag = true;
      stop_time = 0;
      return false;
    }
    break;
    case 2:
    if (stop_time > STOP_TIME2) {
      key_flag = true;
      stop_time = 0;
      return false;
    }
    break;
    }
    return true;
  }



  public void shoot(ArrayList<Bullet> b) {
    if ((info.power>0) &&(keyStat&0x20)!=0) {
      shoot_count++;
      if (shoot_count >= SHOOT_SPEED) {
        b.add(new Bullet(ship.sx, ship.sy, bullet1.b_w, 30, radians(-90), 1));
        shoot_count= 0;
        info.downGauge();
      }
    } else {
      if ((keyStat&0x20)==0) {
        shoot_count = SHOOT_SPEED -1;
      }
    }
  }

  public void bomb() {
    if ((keyStat&0x40)!=0) {
      if ( bomb.bomb_flag==false) {
        switch(info.bomb_SELECT) {
        case 1:
          if (info.rdbl > 0) {
            bomb.rdbl_flag = true;
            info.rdbl--;
            bomb.bomb_flag = true;
          }
          break;
        case 2:
          if (info.mstr > 0) {
            bomb.mstr_flag = true;
            info.mstr--;
            bomb.bomb_flag = true;
          }
          break;
        case 3:
          if (info.ripd > 0) {
            bomb.ripd_flag = true;
            info.ripd--;
            bomb.bomb_flag = true;
          }
          break;
        }
      }
    }
  }

  public boolean update() {
    int tmp_speed = s_speed;


    if ((keyStat&0x10)!=0) {
      tmp_speed = d_speed;
      if (info.power <= LIMIT_GAUGE && frameCount%2==0) {

        info.upGauge();
      }
    }
    if ((keyStat&0x1)!=0 && sy >= ly0) {
      sy -= tmp_speed;
      if (sy<0)sy=0;
    }
    if ((keyStat&0x2)!=0 && sy <= ly1) {
      sy += tmp_speed;
      if (sy>height)sy=height;
    }
    if ((keyStat&0x4)!=0 && sx >= lx0) {
      sx -= tmp_speed;
      if (sx<0)sx=0;
      if (sx<lx0) {
        sx = lx1 -5;
      }
    }

    if ((keyStat&0x8)!=0 && sx <= lx1) {
      sx += tmp_speed;
      if (sx>width)sx=width;
      if (sx>lx1) {
        sx = lx0;
      }
    }
    //    shoot();
    bomb();
    stroke(255, 255, 255);
    fill(255 - hp, 0, 0);
    triangle( sx, sy - 7, sx - 10, 7 + sy, 10+ sx, 7 + sy);
    ellipse(sx, sy, 3, 3);

    return true;
  }
}
final int BULLET_SCORE = 10;

class Stage {
  int stage_num;
  ArrayList<Enemy> enemy_S1;
  ArrayList<Enemy> enemy_S2;
  ArrayList<Item> item_S1;
  ArrayList<Item> item_S2;
  ArrayList<Item> item_S3;
  ArrayList<Item> item_S4;
  ArrayList<Bullet> bullet_S1;
  ArrayList<Bullet> bullet_S2;
  float eff_en_x, eff_en_y;
  long   start_time;
  int ms, m, s, h;
  int loop_i;
  Stage(int s_num) {
    stage_num = s_num;
    frameCount = 0;
    info = new Info(LIMIT_GAUGE, 0);
    enemy_S1 = new ArrayList<Enemy>();
    enemy_S2 = new ArrayList<Enemy>();
    item_S1 = new ArrayList<Item>();
    item_S2 = new ArrayList<Item>();
    item_S3 = new ArrayList<Item>();
    item_S4 = new ArrayList<Item>();
    bullet_S1 = new ArrayList<Bullet>();
    bullet_S2 = new ArrayList<Bullet>();
    loop_i = 0;
    ms = millis();
    s = second();
    m = minute();
    h = hour();
    start_time = + s*1000 +ms;
  }

  public void timer() {
    // start_time = start_time + millis()/1000;
    ms= millis();
    if (ms%100==0)
      s++;

    s=second();
    //m = minute();
  }


  public void enemyEmerge1() {
    switch(Integer.parseInt(csv[loop_i][0])) {
    case 1:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        enemy_S1.add(new Enemy(Float.parseFloat(csv[loop_i][2]), enemy1.ey, enemy1.ew, enemy1.eh, enemy1.e_speed, enemy1.hp,enemy1.e_num, enemy1.e_score));  //\u6575\u306e\u8ffd\u52a0
        loop_i++;
      }
      break;
      
    }


    for (int i = enemy_S1.size() -2; i >= 0; --i) {
      if (bomb.rdbl_flag) {
        if ((abs(ship.sx - enemy_S1.get(i).ex) < (enemy_S1.get(i).ew / 2 + RDBL_WIDTH/2 )) &&(abs(ship.sy - enemy_S1.get(i).ey) < (enemy_S1.get(i).eh / 3)+ RDBL_HEIGHT/2)) {
          enemy_S1.get(i).hp -= RDBL_POWER;
        }           
        if (enemy_S1.get(i).hp <= 0) {
          info.upScore(enemy_S1.get(i).e_score);
          enemy_S1.get(i).e_death = true;
        }
      }

      if (bomb.ripd_flag) {
        if (abs(ship.sx - enemy_S1.get(i).ex) < enemy_S1.get(i).ew/2+50) {
          enemy_S1.get(i).hp -= RIPD_POWER;
        }
        if (enemy_S1.get(i).hp <= 0) {
          info.upScore(enemy_S1.get(i).e_score);
          enemy_S1.get(i).e_death = true;
        }
      }

      if ( enemy_S1.get(i).update()==false) {
        effect_flag = true;
        eff_en_x = enemy_S1.get(i).ex;
        eff_en_y = enemy_S1.get(i).ey;
        enemy_S1.remove(i);
      }


      for (int j =bullet_S1.size() -1; j>=0; --j) {
        if ((abs(bullet_S1.get(j).b_x - enemy_S1.get(i).ex) < (enemy_S1.get(i).ew / 2)) &&(abs(bullet_S1.get(j).b_y - enemy_S1.get(i).ey) < (enemy_S1.get(i).eh / 2))) { 
          bullet_S1.remove(j);
          info.upScore(BULLET_SCORE);
          enemy_S1.get(i).hp-= 100;  //\u6575\u306eHP
          if (enemy_S1.get(i).hp <= 0) {
            info.upScore(enemy_S1.get(i).e_score);
            enemy_S1.get(i).e_death = true;
          }
        }
      }


      for (int j =bullet_S2.size() -1; j>=0; --j) {
        if ((abs(bullet_S2.get(j).b_x - enemy_S1.get(i).ex) < (enemy_S1.get(i).ew / 2)) &&(abs(bullet_S2.get(j).b_y - enemy_S1.get(i).ey) < (enemy_S1.get(i).eh / 2))) { 
          bullet_S2.remove(j);
          info.upScore(BULLET_SCORE);
          enemy_S1.get(i).hp-= 100;  //\u6575\u306eHP
          if (enemy_S1.get(i).hp <= 0) {
            info.upScore(enemy_S1.get(i).e_score);
            enemy_S1.get(i).e_death = true;
          }
        }
      }
    }
  }

  public void enemyEmerge2() {
    switch(Integer.parseInt(csv[loop_i][0])) {
    case 2:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        enemy_S1.add(new Enemy(Float.parseFloat(csv[loop_i][2]), enemy2.ey, enemy2.ew, enemy2.eh, enemy2.e_speed, enemy2.hp,enemy2.e_num, enemy2.e_score));  //\u6575\u306e\u8ffd\u52a0
        loop_i++;
      }
      break;
    }


    for (int i = enemy_S2.size() -2; i >= 0; --i) {
      if (bomb.rdbl_flag) {
        if ((abs(ship.sx - enemy_S2.get(i).ex) < (enemy_S2.get(i).ew / 2 + RDBL_WIDTH/2 )) &&(abs(ship.sy - enemy_S2.get(i).ey) < (enemy_S2.get(i).eh / 3)+ RDBL_HEIGHT/2)) {
          enemy_S2.get(i).hp -= RDBL_POWER;
        }
        if (enemy_S2.get(i).hp <= 0) {
          info.upScore(enemy_S2.get(i).e_score);
          enemy_S2.get(i).e_death = true;
        }
      }

      if (bomb.ripd_flag) {
        if (abs(ship.sx - enemy_S2.get(i).ex) < enemy_S2.get(i).ew/2+50) {
          enemy_S2.get(i).hp -= RIPD_POWER;
        }
        if (enemy_S2.get(i).hp <= 0) {
          info.upScore(enemy_S2.get(i).e_score);
          enemy_S2.get(i).e_death = true;
        }
      }

      if ( enemy_S2.get(i).update()==false) {
        effect_flag = true;
        eff_en_x = enemy_S2.get(i).ex;
        eff_en_y = enemy_S2.get(i).ey;
        enemy_S1.remove(i);
      }

      for (int j =bullet_S1.size() -1; j>=0; --j) {
        if ((abs(bullet_S1.get(j).b_x - enemy_S2.get(i).ex) < (enemy_S2.get(i).ew / 2)) &&(abs(bullet_S1.get(j).b_y - enemy_S2.get(i).ey) < (enemy_S2.get(i).eh / 2))) { 
          bullet_S1.remove(j);
          info.upScore(BULLET_SCORE);
          enemy_S2.get(i).hp-= 100;  //\u6575\u306eHP
          if (enemy_S2.get(i).hp <= 0) {
            info.upScore(enemy_S2.get(i).e_score);
            enemy_S2.get(i).e_death = true;
          }
        }
      }

      for (int j =bullet_S2.size() -1; j>=0; --j) {
        if ((abs(bullet_S2.get(j).b_x - enemy_S2.get(i).ex) < (enemy_S2.get(i).ew / 2)) &&(abs(bullet_S2.get(j).b_y - enemy_S2.get(i).ey) < (enemy_S2.get(i).eh / 2))) { 
          bullet_S2.remove(j);
          info.upScore(BULLET_SCORE);
          enemy_S2.get(i).hp-= 100;  //\u6575\u306eHP
          if (enemy_S2.get(i).hp <= 0) {
            info.upScore(enemy_S2.get(i).e_score);
            enemy_S2.get(i).e_death = true;
          }
        }
      }
    }
  }



  public void itemEmerge() {
    switch(Integer.parseInt(csv[loop_i][0])) {
    case 5:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        item_S1.add(new Item(Float.parseFloat(csv[loop_i][2]), item1.iy, item1.iw, item1.ih, item1.i_speed, item1.i_num));
        loop_i++;
      }
      break;
    case 6:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        item_S2.add(new Item(Float.parseFloat(csv[loop_i][2]), item2.iy, item2.iw, item2.ih, item2.i_speed, item2.i_num));
        loop_i++;
      }
      break;
    case 7:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        item_S3.add(new Item(Float.parseFloat(csv[loop_i][2]), item3.iy, item3.iw, item3.ih, item3.i_speed, item3.i_num));
        loop_i++;
      }
      break;
    case 8:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        item_S4.add(new Item(Float.parseFloat(csv[loop_i][2]), item4.iy, item4.iw, item4.ih, item4.i_speed, item4.i_num));
        loop_i++;
      }
      break;
    }

    for (int i = item_S1.size() -1; i >= 0; --i) {
      if (item_S1.get(i).update() == false)
        item_S1.remove(i);
    }


    for (int i = item_S2.size() -1; i >= 0; --i) {
      if (item_S2.get(i).update() == false)
        item_S2.remove(i);
    }

    for (int i = item_S3.size() -1; i >= 0; --i) {
      if (item_S3.get(i).update() == false)
        item_S3.remove(i);
    }

    for (int i = item_S4.size() -1; i >= 0; --i) {
      if (item_S4.get(i).update() == false)
        item_S4.remove(i);
    }
  }

  public void update() {
    background(0); // clear\u00df\u00ae
    background.update();
    enemyEmerge1();
    enemyEmerge2();
    ship.shoot(bullet_S1);
    ship.update();

    for (int i = bullet_S1.size() -1; i >= 0; --i) {
      if (bullet_S1.get(i).update() == false) {
        bullet_S1.remove(i);
      }
    }
    for (int i = bullet_S2.size() -1; i >= 0; --i) {
      if (bullet_S2.get(i).update() == false) {
        bullet_S2.remove(i);
      }
    }

    bomb.update(bullet_S2);
    itemEmerge();
    if (effect_flag) {
      effect.update(eff_en_x, eff_en_y);
    }
    info.update();
    if (key_flag==false) {
      keyStat&=~0x1;
      keyStat&=~0x2;
      keyStat&=~0x4;
      keyStat&=~0x8;
      keyStat&=~0x10;
      keyStat&=~0x20;
      keyStat&=~0x40;
      keyStat&=~0x80;
    }

    if ((keyStat&0x100) !=0) {
      menu_Stat =1;
      menu_flag = true;
    }
    if ((Integer.parseInt(csv[loop_i][0]) == 0) && (frameCount ==Integer.parseInt(csv[loop_i][1]))) {
      menu_Stat = 2;
      menu_flag = true;
    }
    /*
    for (int i=0; i<csvLength; i++) {
     for (int j=0; j< csvWidth; j++) {
     textSize(10);
     text(csv[i][j], 100+j*80, 100+i*20);
     }
     }
     */
    print_time();
  }
}
class Tama {
  float tx, ty, tr, dx, dy;

  Tama(float x, float y, float r, float ldx, float ldy) {
    tx = x;
    ty = y;
    tr = r;
    dx = ldx;
    dy = ldy;
  }

  public boolean update() {
    tx += dx;
    ty += dy;
    stroke(255, 255, 0);
    noFill();
    ellipse(tx, ty, tr, tr);

    // area check 
    if (ty > height || ty < 0 || tx > width || tx < 0) {
      return false;
    }
    // hit check
//    if (dist(tx, ty, ship.sx, ship.sy) < (tr / 2) + 2)
   //  ship.shootHit();

    return true;
  }
}

  public void settings() {  size(960, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "shooting2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
