package com.example.snake.model.snake;

import com.example.snake.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BlueSnake implements SnakeState {
    private Image body;
    private Image headImg;
    private int speed;

    public BlueSnake() {
        body = new Image("bluebody.png");
        headImg = new Image("bluesnakehead.png");
        this.speed = 10;
    }

    public Image getBody() {
        return body;
    }

    public Image getHeadImg() {
        return headImg;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void render(GraphicsContext gc, Snake snake) {

        gc.drawImage(snake.getHeadImg(), snake.getHead().getX() * Game.SQUARE_SIZE, snake.getHead().getY() * Game.SQUARE_SIZE, Game.SQUARE_SIZE - 1, Game.SQUARE_SIZE - 1);
        for(int i =1;i<snake.getBody().size();i++){
            gc.drawImage(body,snake.getBody().get(i).getX()* Game.SQUARE_SIZE,snake.getBody().get(i).getY()* Game.SQUARE_SIZE,Game.SQUARE_SIZE,Game.SQUARE_SIZE);
        }
    }
}
