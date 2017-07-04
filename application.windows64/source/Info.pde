int u_power = 2;
int d_power = 2;
final int RDBL_SCORE = 100;
final int MSTR_SCORE = 100;
final int RIPD_SCORE = 100;
final int TANNI_SCORE = 300;
class Info {
  int power;  //パワー
  int score;  //得点
  int tanni;  //所得単位数
  int rakutan; //落単数(戒め)
  int rdbl;  //レッドブルの所持数
  int mstr;  //モンスターの所持数
  int ripd;  //リポDの所持数
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


  void getItem(int num) {
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

  void upScore(int s) {
    score +=  s;
  }


  void downGauge() {
    power -= d_power;
  }
  void upGauge() {
    power += u_power;
  }


  void update() {
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
    text("単位数:", 680, 500);
    text(tanni, 820, 500);
    text("落単数:", 680, 550);
    text(rakutan, 820, 550);
    //text(bomb_SELECT, 680, 550);
    switch(bomb_SELECT) {
    case 1:
      text("→", 650, 350);
      break;
    case 2:
      text("→", 650, 400);
      break;
    case 3:
      text("→", 650, 450);
      break;
    }
  }
}