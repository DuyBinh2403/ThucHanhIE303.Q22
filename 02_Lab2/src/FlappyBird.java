import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    Image backgroundImg, birdImg, topPipeImg, bottomPipeImg;

    // Set default bird location and bird's size
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX, y = birdY, width = birdWidth, height = birdHeight;
        Image img;
        Bird(Image img) { this.img = img; }
    }

    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX, y = pipeY, width = pipeWidth, height = pipeHeight;
        Image img;
        boolean passed = false; // Check the bird is passed or not
        Pipe(Image img) { this.img = img; }
    }

    Bird bird;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;


    // Constructor of FlappyBird
    public FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // Create new pipe after 2 seconds
        placePipesTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        // Create gameloop
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

    }

    // Method creates Random Pipes
    public void placePipes() {
        // Define TopPipeY Location
        int randomPipeY = (int) (pipeY - (pipeHeight/4) - Math.random()*(pipeHeight/2));
        // Create Opening space for bird (Default is pipeHeight* 0.3)
        int openingSpace = (int) (boardHeight*0.3);

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        // Define bottomPipe
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    // Method paint UI
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Method draw Graphic
    public void draw(Graphics g) {
        // draw background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // draw bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // draw pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // draw Score
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        if (gameOver) {
            g.drawString("Game Over with " + (int) score, 10, 35);
            g.drawString("Press 'R' to Restart", 10, 70);
        } else {
            g. drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // Logic move of Bird
    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // Move pipes
        for (Pipe pipe : pipes) {
            if (score >= 10) {
                pipe.x -= 6;
            } else {
                pipe.x -= 4;
            }

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5; // Each pipes in Y location + 0.5 score
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }


        }

        // gameOver for the bird falls to the ground
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    // Method check collision
    public boolean collision(Bird bird, Pipe pipe) {
        return bird.x < pipe.x + pipe.width &&
                bird.x + bird.width > pipe.x &&
                bird.y < pipe.y + pipe.height &&
                bird.y + bird.height > pipe.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            placePipesTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            velocityY = -9;
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            placePipesTimer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {};
    @Override
    public void keyReleased(KeyEvent e) {};

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird game = new FlappyBird();
        frame.add(game);
        frame.pack();

        game.requestFocus();
        frame.setVisible(true);
    }
}