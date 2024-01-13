package com.example.snake.model.objects;

import com.example.snake.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fruit {
    private static Fruit instance;
    private int x;
    private int y;
    private Image fruitImg = new Image("apple.png");

    private Fruit() {
        this.x = 120;
        this.y = 120;

    }
    public static Fruit getInstance() {
        if (instance == null) {
            instance = new Fruit();
        }
        return instance;
    }
    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(fruitImg,this.x* Game.SQUARE_SIZE, this.y * Game.SQUARE_SIZE, Game.SQUARE_SIZE-1,Game.SQUARE_SIZE-1);
    }
}
