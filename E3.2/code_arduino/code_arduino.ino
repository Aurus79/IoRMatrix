bool led_on =false ;
char input = 'b';

void setup(){
  Serial.begin(9600);
  pinMode(13,OUTPUT);
}

void loop(){
  if (Serial.available()) {
    input = Serial.read();
  }
  Serial.println();
  Serial.print("Input: ");
  Serial.print(input);
  
  
  if (input== 'a'){
    led_on = true;
  }
  if (input == 'b') {
    led_on = false ;    
  }
  if (led_on){
    digitalWrite(13,HIGH);
  }else{
    digitalWrite(13,LOW);
  }
}
