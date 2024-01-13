package com.example.snake.model.snake;

import com.example.snake.Game;
import com.example.snake.model.snake.Snake;
import com.example.snake.model.snake.SnakeState;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GreenSnake implements SnakeState {

    private Image body;
    private Image headImg;
    private int speed;

    public GreenSnake() {
        body = new Image("greenbody.png");
        headImg = new Image("greensnakehead.png");
        this.speed=5;

    }

    public int getSpeed() {
        return speed;
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
        gc.setFill(Color.rgb(0,255,0));
        for(int i =1;i<snake.getBody().size();i++){
            gc.drawImage(body,snake.getBody().get(i).getX()* Game.SQUARE_SIZE,snake.getBody().get(i).getY()* Game.SQUARE_SIZE,Game.SQUARE_SIZE,Game.SQUARE_SIZE);
        }
    }



}
