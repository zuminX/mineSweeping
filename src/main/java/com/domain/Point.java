package com.domain;

import java.util.Objects;

public class Point {
    private int i;
    private int j;

    public Point() {
    }

    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Point(Point point) {
        this.i = point.i;
        this.j = point.j;
    }

    public Point add(Point point) {
        return new Point(this.i + point.i, this.j + point.j);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Objects.equals(i, point.i) &&
                Objects.equals(j, point.j);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "Point{" + "i=" + i + ", j=" + j + '}';
    }
}
