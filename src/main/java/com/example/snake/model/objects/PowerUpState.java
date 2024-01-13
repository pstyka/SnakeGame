package com.example.snake.model.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public interface PowerUpState {

    Image getImg();
    void setPosition(int x, int y);
    void render(GraphicsContext gc);
    int getX();
    int getY();
}
