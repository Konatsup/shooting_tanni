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

  void  draw() {
    if (iy >= 0 && iy <= GAME_HEIGHT+100) {
      iy += i_speed;
    }

    switch(i_num) {
    case 1:    //レッドブル
      imageMode(CENTER);
      image(img1, ix, iy, iw, iw);
      ellipse(ix, iy, 3, 3);
      break;
    case 2: //モンスター
      imageMode(CENTER);
      image(img2, ix, iy, iw, iw);
      break;
    case 3: //リポD
      imageMode(CENTER);
      image(img3, ix, iy, iw, iw);
      break;
    case 4: //単位
      imageMode(CENTER);
      image(img4, ix, iy, iw, iw);
      break;
    }
  }

  void hitJudge() {
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




  boolean update() {
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