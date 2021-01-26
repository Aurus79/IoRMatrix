bool led =false ;
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
    led = true;
  }
  if (input == 'b') {
    led = false ;    
  }
  if (led){
    digitalWrite(13,HIGH);
  }else{
    digitalWrite(13,LOW);
  }
}
