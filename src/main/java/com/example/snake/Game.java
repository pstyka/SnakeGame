package com.example.snake;

import com.example.snake.model.Score.ScoreManager;
import com.example.snake.model.Score.ScoreObserver;
import com.example.snake.model.objects.Fruit;
import com.example.snake.model.objects.PowerUp;
import com.example.snake.model.objects.Snail;
import com.example.snake.model.objects.Wind;
import com.example.snake.model.snake.BlueSnake;
import com.example.snake.model.snake.GreenSnake;
import com.example.snake.model.snake.RedSnake;
import com.example.snake.model.snake.Snake;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int COLUMNS = 16;
    public static final int ROWS = 16;
    public static final int SQUARE_SIZE = WIDTH / COLUMNS;

    private Snake snake;
    private Fruit fruit;
    private PowerUp powerUp;
    private Scene scene;
    private Timeline changeTimeLine;
    private Label scoreLabel;
    private int score = 0;
    private long lastChange = 0;
    private List<ScoreObserver> scoreObservers = new ArrayList<>();

    private StackPane root;
    private Canvas canvas;
    private GraphicsContext gc;
    ScoreManager scoreManager = ScoreManager.getInstance();


    public void start(Stage stage) throws IOException {
        stage.setTitle("SnakeGame");

        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        drawBoard(gc);

        root = new StackPane();
        scoreLabel = new Label("Score: 0 | Best: 0");
        scoreLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: white;");
        addScoreObserver(scoreManager);
        //root.getChildren().add(canvas);
        root.getChildren().addAll(canvas, scoreLabel);
        scene = new Scene(root, WIDTH, HEIGHT);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyPress(event.getCode());
            }

        });
        StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);
        stage.setScene(scene);

        initSnake();
        initFruit();
        initPowerUp();
        renderGame(gc);
        changeTimeLine = new Timeline(
                new KeyFrame(Duration.seconds(15), event -> {
                    snake.setState(new GreenSnake());
                    lastChange = System.nanoTime();
                })
        );
        changeTimeLine.setCycleCount(Timeline.INDEFINITE);
        changeTimeLine.play();

        new AnimationTimer() {
            long lastUpdate = 0;
            long changeTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1000000000 / snake.getState().getSpeed()) {
                    updateGame(changeTime);
                    changeTime+=1;
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    drawBoard(gc);
                    renderGame(gc);
                    lastUpdate = now;
                }
            }
        }.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initSnake() {
        snake = new Snake();
        snake.setPosition(COLUMNS / 2, ROWS / 2);
    }
    public void initFruit(){
        fruit = Fruit.getInstance();
        Random random = new Random();
        int randomX = random.nextInt(COLUMNS);
        int randomY = random.nextInt(ROWS);
        fruit.setPosition(randomX,randomY);
    }
    public void initPowerUp(){
        powerUp = new PowerUp();
        if(Math.random()> 0.5){
            powerUp.setState(new Snail());
        }
        else {
            powerUp.setState(new Wind());
        }
        Random random = new Random();
        int randomX = random.nextInt(COLUMNS);
        int randomY = random.nextInt(ROWS);
        powerUp.getState().setPosition(randomX,randomY);
    }

    public void generateFruit() {
        Random random = new Random();
        int randomX = random.nextInt(COLUMNS);
        int randomY = random.nextInt(ROWS);

        while (snake.collidesWith(randomX, randomY)) {
            randomX = random.nextInt(COLUMNS);
            randomY = random.nextInt(ROWS);
        }
        fruit.setPosition(randomX, randomY);
    }
    public void generatePowerUp(){
        Random random = new Random();
        int randomX = random.nextInt(COLUMNS);
        int randomY = random.nextInt(ROWS);

        while (snake.collidesWith(randomX, randomY)) {
            randomX = random.nextInt(COLUMNS);
            randomY = random.nextInt(ROWS);
        }
        if(Math.random()> 0.5){
            powerUp.setState(new Snail());
        }
        else {
            powerUp.setState(new Wind());
        }
        powerUp.getState().setPosition(randomX, randomY);
    }


    public void drawBoard(GraphicsContext gc) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(javafx.scene.paint.Color.rgb(92, 215, 246));
                } else {
                    gc.setFill(javafx.scene.paint.Color.rgb(92, 158, 255));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    public void updateGame(long time) {
        snake.move();
        if (snake.checkCollisionWithBody()) {
            System.out.println("GAME OVER - Collision with body!");
            restartGame();
        }
        checkCollisionWithFruit();
        checkCollisionWithPowerUp();
        checkCollisionWithWall(snake);
        scoreLabel.setText("Score: " + scoreManager.getCurrentScore() + " | Best: " + scoreManager.getBestScore());
    }

    public void handleKeyPress(KeyCode code) {
        if (code == KeyCode.UP && snake.getDirectionY() != 1) {
            snake.setDirection(0, -1);
        } else if (code == KeyCode.DOWN && snake.getDirectionY() != -1) {
            snake.setDirection(0, 1);
        } else if (code == KeyCode.LEFT && snake.getDirectionX() != 1) {
            snake.setDirection(-1, 0);
        } else if (code == KeyCode.RIGHT && snake.getDirectionX() != -1) {
            snake.setDirection(1, 0);
        } else if(code == KeyCode.SPACE){

        } else if (code == KeyCode.R) {
            restartGame();
        }
    }
    public void checkCollisionWithFruit(){

        if(snake.collidesWith(fruit.getX(), fruit.getY())){
            fruit.setPosition(-1,-1);
            snake.grow();
            scoreManager.updateCurrentScore(10);
            generateFruit();
        }
    }
    public void  checkCollisionWithWall(Snake snake){
        if(snake.getHead().getX()>Game.COLUMNS || snake.getHead().getX()<0|| snake.getHead().getY()>Game.ROWS || snake.getHead().getY()<0){
            System.out.println("GAME OVER!!!");
            restartGame();
        }
    }
    public void checkCollisionWithPowerUp(){
        if(snake.collidesWith(powerUp.getState().getX(), powerUp.getState().getY())){
            powerUp.getState().setPosition(-1,-1);
            if(powerUp.getState() instanceof Wind){
                snake.setState(new BlueSnake());
                scoreManager.updateCurrentScore(-1);
            } else if (powerUp.getState() instanceof Snail) {
                snake.setState(new RedSnake());
                scoreManager.updateCurrentScore(1);
            }

            generatePowerUp();
        }
    }

    public void renderGame(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT + 100);
        drawBoard(gc);
        fruit.render(gc);
        powerUp.getState().render(gc);
        snake.getState().render(gc, snake);
    }
    public void restartGame(){
                initSnake();
                initFruit();
                initPowerUp();
                scoreManager.updateScore(scoreManager.getCurrentScore());
                scoreManager.resetCurrentScore();
                renderGame(gc);
    }
    public void addScoreObserver(ScoreObserver observer) {
        scoreObservers.add(observer);
    }
    public void removeScoreObserver(ScoreObserver observer) {
        scoreObservers.remove(observer);
    }
    private void updateScore(int newScore) {
        for (ScoreObserver observer : scoreObservers) {
            observer.updateScore(newScore);
        }
    }
}
