package ru.stqa.pft.sandbox;

public class Point {
  public double x;
  public double y;

  public Point(double x, double y) {

    this.x = x;
    this.y = y;
    Point b;
    System.out.println("ru.stqa.pft.sandbox.Point has these coordinates: " + "(" + x + ":" + y + ")");

  }
  public double distance(Point b) {

    x = Math.abs(this.x - b.x);
    y = Math.abs(this.y - b.y);
    double z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    z = Math.round(z * 100);

    return z / 100;
  }
}
