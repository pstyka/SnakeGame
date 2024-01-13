package com.example.snake.model.objects;

import com.example.snake.Game;
import com.example.snake.model.objects.PowerUpState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wind implements PowerUpState {

    private int x;
    private int y;
    private Image windImg = new Image("tornado.png");

    public Wind() {
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
        gc.drawImage(windImg,this.x* Game.SQUARE_SIZE, this.y * Game.SQUARE_SIZE, Game.SQUARE_SIZE-1,Game.SQUARE_SIZE-1);
    }

    @Override
    public Image getImg() {
        return windImg;
    }
}
