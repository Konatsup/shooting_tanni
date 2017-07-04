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

  void draw() {
    b_x += b_vx;
    b_y += b_vy;

pushMatrix();
    imageMode(CENTER);
    image(img, this.b_x, this.b_y, this.b_w, this.b_w*2);
    rotate(b_rad);
    popMatrix();
  }

  boolean update() {

    draw();
    if (this.b_y < 0 || this.b_y >GAME_HEIGHT || ((this.b_type==2) &&(this.b_x >GAME_WIDTH))){
      return false;
    } else {
      return true;
    }
  }
}