package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Point;

public class TestForPointsDistance {

  @Test
  public void testDistance() {
    Point p1 = new Point(1, 2);
    Point p2 = new Point(4, 6);
    Assert.assertEquals(p1.distance(p2), 5.0);

  }
}
