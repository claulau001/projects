//YWROBOT
//Compatible with the Arduino IDE 1.0
//Library version:1.1
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

#define PLAYER1 1
#define PLAYER2 2
#define POTENTIOMETRUPLAYER1 A0
#define POTENTIOMETRUPLAYER2 A0

  
LiquidCrystal_I2C lcd(0x27,16,2);  


class Paddle{
  public:
    uint8_t PaddleColArray[16];
    uint8_t paddlePos = 7;
    uint8_t playerNum;
    uint8_t score = 0;
    Paddle(uint8_t playerNum);
    void printPaddle();
    void updatePaddle();
    uint8_t getPaddlePos();
};

Paddle p1(PLAYER1), p2(PLAYER2);

void updateScore(uint8_t ballX) {
  if (ballX <= 12)
    p2.score++;
  if (ballX >= 69)
    p1.score++;
}


uint8_t Paddle :: getPaddlePos() {
  return paddlePos;
}

void Paddle :: updatePaddle() {
  uint8_t newPaddlePos;
  if (playerNum == PLAYER1){
    newPaddlePos = (analogRead(POTENTIOMETRUPLAYER1) * (12.0 / 1023.0)) + 2;
  if (newPaddlePos != paddlePos)
    {
      PaddleColArray[paddlePos] = 0;
      PaddleColArray[paddlePos+1] = 0;
      PaddleColArray[paddlePos+2] = 0;
      PaddleColArray[paddlePos-1] = 0;
      PaddleColArray[paddlePos-2] = 0;

      PaddleColArray[newPaddlePos] = 1;
      PaddleColArray[newPaddlePos+1] = 1;
      PaddleColArray[newPaddlePos+2] = 1;
      PaddleColArray[newPaddlePos-1] = 1;
      PaddleColArray[newPaddlePos-2] = 1;

      paddlePos = newPaddlePos;
    }
  }
  if (playerNum == PLAYER2){
    newPaddlePos = (analogRead(POTENTIOMETRUPLAYER2) * (12.0 / 1023.0)) + 2;
  if (newPaddlePos != paddlePos)
    {
      PaddleColArray[paddlePos] = 0;
      PaddleColArray[paddlePos+1] = 0;
      PaddleColArray[paddlePos+2] = 0;
      PaddleColArray[paddlePos-1] = 0;
      PaddleColArray[paddlePos-2] = 0;

      PaddleColArray[newPaddlePos] = 16;
      PaddleColArray[newPaddlePos+1] = 16;
      PaddleColArray[newPaddlePos+2] = 16;
      PaddleColArray[newPaddlePos-1] = 16;
      PaddleColArray[newPaddlePos-2] = 16;

      paddlePos = newPaddlePos;
    }
  }

    Paddle :: printPaddle();
}

Paddle :: Paddle(uint8_t playerNum){
  this->playerNum = playerNum;
  uint8_t i;
  if (playerNum == PLAYER1){
    for (i = 0; i < 16; i++){
      if(i >= 5 && i <= 9)
        PaddleColArray[i] = 1;
      else
        PaddleColArray[i] = 0;
    }
  }

  if (playerNum == PLAYER2){
    for (i = 0; i < 16; i++){
      if(i >= 5 && i <= 9)
        PaddleColArray[i] = 16;
      else
        PaddleColArray[i] = 0;
    }
  }
}

void Paddle :: printPaddle()
  {
    if(playerNum == PLAYER2)
    {
      // Each character must have a unique character ID and array to print
      lcd.createChar(0, PaddleColArray);
      lcd.createChar(1, PaddleColArray+8);

      // Move cursor to 14th character on the 1st row
      lcd.setCursor(14, 0);
      lcd.write(byte(0));

      // Move cursor to 14th character on the 2nd row
      lcd.setCursor(14, 1);
      lcd.write(byte(1));
    }
  
    //if(playerNum == PLAYER1)
    else
    {
      lcd.createChar(2, PaddleColArray);
      lcd.createChar(3, PaddleColArray+8);
  
      lcd.setCursor(1, 0);
      lcd.write(byte(2));

      lcd.setCursor(1, 1);
      lcd.write(byte(3));
    }
  }


class Ball{
  uint8_t ballX;
  uint8_t ballY;
  uint8_t ballCharArray[8];
  uint8_t ballDirX;
  uint8_t ballDirY;
public:
  Ball();
  void generateBallArray();
  void printBall();
  void updateBall(uint8_t, uint8_t);
  bool modifyDirections(uint8_t);
  void resetBall();
};

bool Ball :: modifyDirections(uint8_t paddlePos) {
  
  // it will change the Y direction based on where the paddle hit
  if (ballY == paddlePos){ 
    ballDirY = 0;
    ballDirX = -ballDirX;
  }
  else if (ballY == paddlePos - 1){
    ballDirY = -1;
    ballDirX = -ballDirX;
  }
  else if (ballY == paddlePos - 2){ 
    ballDirY = -2;
    ballDirX = -ballDirX;
  }
  else if (ballY == paddlePos + 1){ 
    ballDirY = 1;
    ballDirX = -ballDirX;
  }
  else if (ballY == paddlePos + 2){ 
    ballDirY = 2;
    ballDirX = -ballDirX;
  }
  else { // if it doesn't hit the paddle at all
    lcd.clear();
    return true;
  }
  return false;
}

void Ball :: updateBall(uint8_t pos1, uint8_t pos2) {

  bool scored = false;
  if (ballX <= 11){ //if it hits/pass the left paddle
    scored = Ball :: modifyDirections(pos1);
  }

  if (ballX >= 70){ //if it hits/pass the right paddle
    scored = Ball :: modifyDirections(pos2);
  }

  if(scored == false){
    // delete the ball from current postion
    uint8_t LCDCol = (ballX-1) / 5; 
    uint8_t LCDRow = (ballY <= 7) ? 0 : 1;
    lcd.setCursor(LCDCol, LCDRow);
    lcd.print(" ");

    ballX = ballX + ballDirX;
    ballY = ballY + ballDirY;
    Serial.println(ballY);
    if (ballY > 15 && ballY < 20)
      {ballY = 15; ballDirY = -ballDirY;}
    if (ballY >= 250)
      {ballY = 0; ballDirY = -ballDirY;}
    Ball :: generateBallArray();
    }
    else { // daca s-a inscris
      updateScore(ballX);
      Ball :: resetBall();
      showScore();
    }
}

void Ball :: resetBall() {
  ballX = 40;
  ballY = 8;
  ballDirY = 0;
  ballDirX = -ballDirX;
}




void showScore() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Player 1 - ");
  lcd.print(p1.score);
  lcd.setCursor(0,1);
  lcd.print("Player 2 - ");
  lcd.print(p2.score);

  delay(2000);

  lcd.clear();
}


void Ball :: generateBallArray()
{
  for(uint8_t i=0; i<8; i++)
    {
    if(i == (ballY % 8))
    {
      ballCharArray[i] = 2 << (4 - (ballX % 5));
      if (ballCharArray[i] == 32)
        ballCharArray[i] = 1;
    }
    else
    {
      ballCharArray[i] = 0;
    }
  }
}

void Ball :: printBall()
{
  // Calculate the column we will draw in
  uint8_t LCDCol = (ballX-1) / 5;

  // Either the top or bottom row
  uint8_t LCDRow = (ballY <= 7) ? 0 : 1;

  // Assign array to the charNum
  lcd.createChar(4, ballCharArray);

  // Move the cursor into position
  lcd.setCursor(LCDCol,LCDRow);

  // Draw the character
  lcd.write(byte(4));
}

Ball :: Ball(){
  ballX = 40;
  ballY = 8;
  ballDirX = 1;
  ballDirY = 0;
  Ball :: generateBallArray();
}



Ball b;

void setup()
{
  lcd.init();                      // initialize the lcd 
  lcd.backlight();
  p1.printPaddle();
  p2.printPaddle();
  b.printBall();
  Serial.begin(9600);
  
}





void loop()
{
  p1.updatePaddle();
  p2.updatePaddle();
  b.updateBall(p1.getPaddlePos(),p2.getPaddlePos());
  b.printBall();
  delay(10);
}

