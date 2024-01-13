package com.example.snake.model.snake;

import com.example.snake.Game;
import com.example.snake.model.snake.Snake;
import com.example.snake.model.snake.SnakeState;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RedSnake implements SnakeState {
    private Image body;
    private Image headImg;
    private int speed;

    public RedSnake() {
        body = new Image("redbody.png");
        headImg = new Image("redsnakehead.png");
        this.speed = 2;
    }

    public Image getBody() {
        return body;
    }

    public void setBody(Image body) {
        this.body = body;
    }

    public Image getHeadImg() {
        return headImg;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    public void setHeadImg(Image headImg) {
        this.headImg = headImg;
    }


    private boolean isValidPosition(Point2D position) {
        return position.getX() >= 0 && position.getX() < Game.COLUMNS &&
                position.getY() >= 0 && position.getY() < Game.ROWS;
    }

    @Override
    public void render(GraphicsContext gc, Snake snake) {

        gc.drawImage(snake.getHeadImg(), snake.getHead().getX() * Game.SQUARE_SIZE, snake.getHead().getY() * Game.SQUARE_SIZE, Game.SQUARE_SIZE - 1, Game.SQUARE_SIZE - 1);
        for(int i =1;i<snake.getBody().size();i++){
            gc.drawImage(body,snake.getBody().get(i).getX()* Game.SQUARE_SIZE,snake.getBody().get(i).getY()* Game.SQUARE_SIZE,Game.SQUARE_SIZE,Game.SQUARE_SIZE);
        }
    }
}
