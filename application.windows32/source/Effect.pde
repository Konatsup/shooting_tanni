final int ENEMY_EFFECT_TIME=3;
class Effect {
  int t;
  Effect() {
    t=0;
  }
  void update(float ex, float ey) {
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