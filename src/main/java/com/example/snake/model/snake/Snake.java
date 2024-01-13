package com.example.snake.model.snake;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Snake{
    private SnakeState state;
    private int x;
    private int y;
    private int speed;
    private List<Point2D> body;
    private int directionX;
    private int directionY;



    public Snake() {
        this.state = new GreenSnake();
        body = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            body.add(new Point2D(40, 40));
        }
        this.speed = 5;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }
    public void setPosition(int x, int y) {
        this.directionX = 1;
        this.directionY = 0;


        body.set(0, new Point2D(x, y));
    }
    public List<Point2D> getBody() {
        return body;
    }
    public void move(){
        Point2D head = getHead();
        Point2D newHead = new Point2D(head.getX() + directionX, head.getY() + directionY);


        for (int i = body.size() - 1; i > 0; i--) {
            body.set(i, body.get(i - 1));
        }


        body.set(0, newHead);
    }
    public SnakeState getState() {
        return state;
    }
    public void setState(SnakeState state) {
        this.state = state;
    }
    public void setDirection(int x, int y) {
        this.directionX = x;
        this.directionY = y;
    }

    public Image getHeadImg() {
        return this.getState().getHeadImg();
    }
    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }
    public Point2D getHead() {
        return body.get(0);
    }


    public void render(GraphicsContext gc) {
        this.getState().render(gc,this);
    }

    public void grow() {
        Point2D lastBodyPart = body.get(body.size() - 1);
        Point2D newBodyPart = new Point2D(lastBodyPart.getX(), lastBodyPart.getY());
        body.add(newBodyPart);
    }
    public boolean collidesWith(int x, int y) {

        if (x == getHeadX() && y == getHeadY()) {
            return true;
        }
        for (Point2D segment : body.subList(1, body.size())) {
            if (x == segment.getX() && y == segment.getY()) {
                return true;
            }
        }
        return false;
    }

    private int getHeadX() {
        return (int) getHead().getX();
    }

    private int getHeadY() {
        return (int) getHead().getY();
    }

    public boolean checkCollisionWithBody() {
        Point2D head = getHead();

        for (int i = 1; i < body.size(); i++) {
            Point2D bodySegment = body.get(i);


            if (head.equals(bodySegment)) {
                return true;
            }
        }

        return false;
    }
    public void handleItem(){

    }
}
