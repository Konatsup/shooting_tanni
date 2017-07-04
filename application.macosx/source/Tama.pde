class Tama {
  float tx, ty, tr, dx, dy;

  Tama(float x, float y, float r, float ldx, float ldy) {
    tx = x;
    ty = y;
    tr = r;
    dx = ldx;
    dy = ldy;
  }

  boolean update() {
    tx += dx;
    ty += dy;
    stroke(255, 255, 0);
    noFill();
    ellipse(tx, ty, tr, tr);

    // area check 
    if (ty > height || ty < 0 || tx > width || tx < 0) {
      return false;
    }
    // hit check
//    if (dist(tx, ty, ship.sx, ship.sy) < (tr / 2) + 2)
   //  ship.shootHit();

    return true;
  }
}