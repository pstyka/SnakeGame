package com.example.snake.model.objects;

public class PowerUp {
    private PowerUpState state;
    private int x;
    private int y;

    public PowerUp() {

    }

    public PowerUpState getState() {
        return state;
    }

    public void setState(PowerUpState state) {
        this.state = state;
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
}
