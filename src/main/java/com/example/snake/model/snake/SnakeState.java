package com.example.snake.model.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public interface SnakeState {

    void render(GraphicsContext gc, Snake snake);
    Image getHeadImg();
    int getSpeed();

}
