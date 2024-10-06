#include <AccelStepper.h>
#include "IRremote.h"

#define motorPin1 8
#define motorPin2 9
#define motorPin3 10
#define motorPin4 11

int stepsPerRevolution = 64;
float degreePerRevolution = 5.625;
unsigned long startTime;
int initialPosition = -1; // from 0 to 6 (7 posible values)
int positionValues[7]; // from 0 to 6 (7 values)
char pinName[7] = {A6,A5,A4,A3,A2,A1,A0};

AccelStepper stepper(AccelStepper::HALF4WIRE, motorPin1, motorPin3, motorPin2, motorPin4);

int receiver = 7; // Signal Pin of IR receiver to Arduino Digital Pin 7

IRrecv irrecv(receiver);     // create instance of 'irrecv'
decode_results results;      // create instance of 'decode_results'

void translateIR() // takes action based on IR code received

// describing Remote IR codes 

{

  switch(results.value)

  {
  case 0xFF6897: initialPosition = 0;    break;
  case 0xFF30CF: initialPosition = 1;     break;
  case 0xFF18E7: initialPosition = 2;     break;
  case 0xFF7A85: initialPosition = 3;     break;
  case 0xFF10EF: initialPosition = 4;    break;
  case 0xFF38C7: initialPosition = 5;     break;
  case 0xFF5AA5: initialPosition = 6;     break; 

  default: 
    initialPosition = -1;

  }// End Case

  delay(500); // Do not get immediate repeat


} //END translateIR

void setup() {
  Serial.begin(9600);

  stepper.setMaxSpeed(1000.0);
  stepper.setAcceleration(50.0);
  stepper.setSpeed(200);

  irrecv.enableIRIn(); // Start the receiver

  while(initialPosition == -1){
    while(!irrecv.decode(&results)) // have we received an IR signal?

    translateIR(); 
    irrecv.resume();
  }
}

void loop() {
  int max = -1;
  int maxi = -1;
  for (int i = 0; i < 7; i++)
    {
      positionValues[i] = analogRead(pinName[i]);
      if(max < positionValues[i])
      {
        max = positionValues[i];
        maxi = i;
      }
      Serial.print("Pozitia ");
      Serial.print(i);
      Serial.print(": ");
      Serial.println(positionValues[i]);
    }
  stepper.moveTo(380*(initialPosition - maxi));
  startTime = millis();
  while(millis() - startTime <= 15000)
    {
      stepper.run();

    }
  Serial.println(maxi);

  delay(500);
}