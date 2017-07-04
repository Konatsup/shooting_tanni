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
  void Redbull() {
    ship.s_speed = 8;
    imageMode(CENTER);
    image(img5, ship.sx, ship.sy, RDBL_WIDTH, RDBL_HEIGHT);
  }

  void Monster(ArrayList<Bullet> b) {
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

  void Ripod() {
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


  boolean update(ArrayList<Bullet> b) {
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