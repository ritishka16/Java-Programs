import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Import the event
public class TicTacToe extends Canvas {
private static final long serialVersionUID = 3086638359652751354L;
private final int SCREEN_WIDTH = 800;
//in this lines we adujest the screen Width
private final int SCREEN_HEIGHT = 600;
//in this lines we adujest the screen Height
private final BufferStrategy strategy;
private int mousePressedX = -1;
private int mousePressedY = -1;
private boolean enterPressed = false;
private int leftEdge = 0;
private int topEdge = 0;
private int cellHCentre = 0;
private int cellVCentre = 0;
private char currentMove = 'X';
private char gameWinner = '0';
private char[][] gameBoard = new char[3][3];
private int moves = 0;
public TicTacToe() throws InterruptedException {
JFrame container = new JFrame("Tic Tac Toe");
JPanel panel = (JPanel) container.getContentPane();
panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
panel.setLayout(null);
setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
panel.add(this);
leftEdge = SCREEN_WIDTH / 3;
//in this lines we adujest the screen left Edge
topEdge = SCREEN_HEIGHT / 3;
//in this lines we adujest the screen right Edges
cellHCentre = leftEdge / 2;
cellVCentre = topEdge / 2;
setIgnoreRepaint(true);
container.pack();
container.setResizable(false);
container.setVisible(true);
container.addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent e) {
System.exit(0);
}
});
addKeyListener(new KeyboardListener());
addMouseListener(new MouseListener());
requestFocus();
createBufferStrategy(2);
strategy = getBufferStrategy();
//in this lines we set the buffer
}
private void newGame() {
System.out.println("New game starting...");
//in this lines we display on the screen New game Starting
for (int i = 0; i < 3; i++) {
for (int j = 0; j < 3; j++)
//Intiliaze Nested Loop
{
gameBoard[i][j] = '0';
}
}
moves = 9;
//in this lines we adujest the Move on 9
gameWinner = '0';
}
private void nextMove() {
if (currentMove == 'X')
currentMove = 'O';
else
currentMove = 'X'; moves--;
}
//in these lines we set the
public void gameLoop() {
long delta = 0;
long lastLoopTime = System.currentTimeMillis();
newGame();
while (true) {
delta = System.currentTimeMillis() - lastLoopTime;
lastLoopTime = System.currentTimeMillis();
Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
events(delta);
draw(g);
update(g);
try {
Thread.sleep(10);
} catch (Exception e) {
System.out.println(e.getMessage());
}
}
}
private void events(long delta) {
if ((mousePressedX >= 0 || mousePressedY >= 0) && gameWinner == '0') {
int xIndex = (int) Math.floor(mousePressedX / leftEdge);
int yIndex = (int) Math.floor(mousePressedY / topEdge);
if (gameBoard[yIndex][xIndex] == '0') {
gameBoard[yIndex][xIndex] = currentMove;
nextMove();
}
}
if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2])
//in this lines we set the if condition using &&

{
if (gameBoard[0][0] == 'X')
gameWinner = 'X';
else if (gameBoard[0][0] == 'O')
gameWinner = 'O';
}
else if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[0][2] == gameBoard[2][0])
//in this lines we set the else if condition using &&
{
if (gameBoard[0][2] == 'X')
gameWinner = 'X';
else if (gameBoard[0][2] == 'O')
gameWinner = 'O';
} else {
for (int i = 0; i < 3; i++)
//Intialize for loop
{
if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][0] == gameBoard[i][2])
//in this lines we set the if condition using &&

{
if (gameBoard[i][0] == 'X')
gameWinner = 'X';
else if (gameBoard[i][0] == 'O')
gameWinner = 'O';
}
else if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[0][i] == gameBoard[2][i])
//in this lines we set the else if condition using && for gameboard
{
if (gameBoard[0][i] == 'X')
gameWinner = 'X';
else if (gameBoard[0][i] == 'O')
gameWinner = 'O';
}
}
}
}
private void draw(Graphics2D g) {
g.setColor(new Color(245, 245, 245));
g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
g.setColor(new Color(199, 199, 199));
g.drawLine(0, topEdge, SCREEN_WIDTH, topEdge);
g.drawLine(0, topEdge * 2, SCREEN_WIDTH, topEdge * 2);
g.drawLine(leftEdge, 0, leftEdge, SCREEN_HEIGHT);
g.drawLine(leftEdge * 2, 0, leftEdge * 2, SCREEN_HEIGHT);
g.setFont(new Font("Verdana", 0, 60));
//in these lines we set the color for Screen

for (int i = 0; i < 3; i++) {
for (int j = 0; j < 3; j++) {
if (gameBoard[i][j] != '0') {
int xPos = j * leftEdge + cellHCentre;
int yPos = i * topEdge + cellVCentre;
if (gameBoard[i][j] == 'X') {
int strWidthOffset = g.getFontMetrics().stringWidth("X") / 2;
g.setColor(new Color(198, 113, 113));
g.drawString("X", xPos - strWidthOffset, yPos + 30);
} else {
int strWidthOffset = g.getFontMetrics().stringWidth("O") / 2;
g.setColor(new Color(113, 198, 113));
g.drawString("O", xPos - strWidthOffset, yPos + 30);
}
}
}
}
g.setFont(new Font("Verdana", 0, 24));
if (gameWinner != '0') {
g.setColor(new Color(245, 245, 245));
g.fillRect(0, SCREEN_HEIGHT - 44, SCREEN_WIDTH, 44);
g.setColor(new Color(255, 153, 18));
String output = "Player " + gameWinner + " wins! (Press enter to play again)";
int fontWidth = g.getFontMetrics().stringWidth(output);
g.drawString(output, SCREEN_WIDTH / 2 - fontWidth / 2, SCREEN_HEIGHT - 10);
if (enterPressed)
newGame();
} else if (moves == 0) {
g.setColor(new Color(245, 245, 245));
g.fillRect(0, SCREEN_HEIGHT - 44, SCREEN_WIDTH, 44);
g.setColor(new Color(255, 153, 18));
String output = "Tie game! (Press enter to play again)";
//Display on the screen String Press enter to play again
int fontWidth = g.getFontMetrics().stringWidth(output);
g.drawString(output, SCREEN_WIDTH / 2 - fontWidth / 2, SCREEN_HEIGHT - 10);
//adujest the screen Output
if (enterPressed)
//Set the if Condition
newGame();
}
}
private void update(Graphics2D g) {
g.dispose();
strategy.show();
}
private class KeyboardListener implements java.awt.event.KeyListener {
@Override
public void keyPressed(KeyEvent e) {
if (e.getKeyCode() == KeyEvent.VK_ENTER)
enterPressed = true;
}
@Override
public void keyReleased(KeyEvent e) {
if (e.getKeyCode() == KeyEvent.VK_ENTER)
enterPressed = false;
}
@Override
public void keyTyped(KeyEvent e) {
if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
System.exit(0);
}
}
private class MouseListener implements java.awt.event.MouseListener {
@Override
public void mouseClicked(MouseEvent e) {
}
@Override
public void mouseEntered(MouseEvent e) {
}
@Override
public void mouseExited(MouseEvent e) {
}
@Override
public void mousePressed(MouseEvent e) { mousePressedX = e.getX(); mousePressedY = e.getY();
}
@Override
public void mouseReleased(MouseEvent e) { mousePressedX = -1; mousePressedY = -1;
}
}
public static void main(String[] args) throws InterruptedException {
TicTacToe game = new TicTacToe();
game.gameLoop();
}
}