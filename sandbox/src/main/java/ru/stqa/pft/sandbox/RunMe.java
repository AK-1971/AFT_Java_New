package ru.stqa.pft.sandbox;// Эта программа предлагает ввести координаты двух точек и вычисляет расстояние между ними

import java.util.Scanner;


public class RunMe {

  public static void main(String[] args) {
    System.out.println("First point");
    System.out.println("Input coordinates of point: ");
    System.out.println("Input integer x:");

    Scanner sc11 = new Scanner(System.in);
    double x1 = sc11.nextInt();

    System.out.println("Input integer y:");
    Scanner sc12 = new Scanner(System.in);
    double y1 = sc12.nextInt();

    Point p1 = new Point(x1, y1);

    System.out.println("Second point");
    System.out.println("Input coordinates of point: ");
    System.out.println("Input integer x:");

    Scanner sc21 = new Scanner(System.in);
    double x21 = sc21.nextInt();

    System.out.println("Input integer y:");
    Scanner sc22 = new Scanner(System.in);
    double y22 = sc22.nextInt();

    Point p2 = new Point(x21, y22);

    System.out.println("Distance beetwen these points is:" + p2.distance(p1));

  }


}
