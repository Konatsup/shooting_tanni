class Draw {
  int dx, dy;

  Draw(int x, int y) {
    dx = x;
    dy = y;


   
  }
  
/*  boolean update() {
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
    if (dist(tx, ty, ship.sx, ship.sy) < (tr / 2) + 2)
      ship.hit();

    return true;
  }

   // enemyS1.add(new Enemy(dx, dy, 100, 3, 1));*/
  
}