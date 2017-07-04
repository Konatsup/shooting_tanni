
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
    e_num = num; //敵の種類
    e_death = false;
    e_score = score;
  }

  void draw() {
    if (ey >= 0 && ey <= GAME_HEIGHT) {
      ey += e_speed;

      switch(e_num) {
      case 1:  //レポート
        imageMode(CENTER);
        image(img0, ex, ey, ew, ew);
        break;
      case 2: //実験レポート
        imageMode(CENTER);
        image(img9, ex, ey, ew, ew);
        break;
      case 3:  //サークル

        break;
      case 4:  //バイト

        break;
      }
    }else if(ey>=GAME_HEIGHT){
      ey = 720;
    }
  }


  boolean update() {
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