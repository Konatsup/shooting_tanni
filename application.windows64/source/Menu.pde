class Menu {
  int stat ;

  Menu() {
    stat = 1;
  }

  void select() {
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
      text("→", GAME_WIDTH/2+50, 400);
      break;
    case 2:
      text("→", GAME_WIDTH/2+50, 450);
      break;
    case 3:
      text("→", GAME_WIDTH/2+50, 500);
      break;
    }
  }



  void  update(int menu_Stat) {
    switch(menu_Stat) {
    case 1:  //PAUSE
      background(0, 127);
      textSize(100);
      text("PAUSE", GAME_WIDTH/2, GAME_HEIGHT/2-10);
      textSize(30);
      text("続ける", GAME_WIDTH/2+110, 400);
      text("リトライ", GAME_WIDTH/2+90, 450);
      text("終わる", GAME_WIDTH/2+110, 500);
      select();
      break;
    case 2:  //CLEAR
      background(0, 127);
      textSize(100);
      text("CLEAR", GAME_WIDTH/2, GAME_HEIGHT/2-10);
      textSize(30);
      text("SCORE:", GAME_WIDTH/2+40, 400);
      text(info.score, GAME_WIDTH/2+230, 400);
      text("取得単位数:", GAME_WIDTH/2+40, 450);
      text(info.tanni, GAME_WIDTH/2+230, 450);
      text("落単数:", GAME_WIDTH/2+40, 500);
      text(info.rakutan, GAME_WIDTH/2+230, 500);
      text("進級可否:", GAME_WIDTH/2+70, 600);
      text("可", GAME_WIDTH/2+210, 600);
      break;
    }
  }
}