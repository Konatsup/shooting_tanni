
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
  void hit() {
    hp--;
    if (hp <= 0) 
      gameover = true;
  }
  //
  void fire_360(float x, float y) {  
    for (int i = 0; i < 360; i+= 10) { 
      float rad = radians(i);
      danmaku.add(new Tama(x, y, 15, cos(rad), sin(rad)));
    }
  }
  //
  void update() {
    // boss update
    float dx;
    dx = 75.0 * sin(radians(frameCount * 6));
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