final int SHOOT_SPEED = 5;
final int STOP_TIME1 = 1;
final int STOP_TIME2 = 4;
int stop_time = 0;
class Ship extends MyObject {
  int hp;
  int sx, sy, sw, sh;
  int lx0, ly0, lx1, ly1;
  int l_err; //移動誤差
  //int mvx, mvy;
  int s_speed, d_speed; //自機の速さ、低速移動時の速さ
  boolean shoot_judge;//玉を打っている状態かどうか
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
    s_speed = 5; //自機の速さ
    d_speed = 1; //downspeed
    shoot_judge = false;
    shoot_count = 0;
  }

  boolean stop(int num) {
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



  void shoot(ArrayList<Bullet> b) {
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

  void bomb() {
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

  boolean update() {
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