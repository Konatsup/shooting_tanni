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

  void timer() {
    // start_time = start_time + millis()/1000;
    ms= millis();
    if (ms%100==0)
      s++;

    s=second();
    //m = minute();
  }


  void enemyEmerge1() {
    switch(Integer.parseInt(csv[loop_i][0])) {
    case 1:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        enemy_S1.add(new Enemy(Float.parseFloat(csv[loop_i][2]), enemy1.ey, enemy1.ew, enemy1.eh, enemy1.e_speed, enemy1.hp,enemy1.e_num, enemy1.e_score));  //敵の追加
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
          enemy_S1.get(i).hp-= 100;  //敵のHP
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
          enemy_S1.get(i).hp-= 100;  //敵のHP
          if (enemy_S1.get(i).hp <= 0) {
            info.upScore(enemy_S1.get(i).e_score);
            enemy_S1.get(i).e_death = true;
          }
        }
      }
    }
  }

  void enemyEmerge2() {
    switch(Integer.parseInt(csv[loop_i][0])) {
    case 2:
      if (frameCount == Integer.parseInt(csv[loop_i][1])&&(loop_i <=csvLength)) {
        enemy_S1.add(new Enemy(Float.parseFloat(csv[loop_i][2]), enemy2.ey, enemy2.ew, enemy2.eh, enemy2.e_speed, enemy2.hp,enemy2.e_num, enemy2.e_score));  //敵の追加
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
          enemy_S2.get(i).hp-= 100;  //敵のHP
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
          enemy_S2.get(i).hp-= 100;  //敵のHP
          if (enemy_S2.get(i).hp <= 0) {
            info.upScore(enemy_S2.get(i).e_score);
            enemy_S2.get(i).e_death = true;
          }
        }
      }
    }
  }



  void itemEmerge() {
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

  void update() {
    background(0); // clearß®
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