import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BitFlow extends PApplet {

Flow[] flows; // Declare array
int numFlows;
int h;
int mainSpeed;
int direction = 1;
boolean run = true;

public void setup() {
    
    numFlows = 70;
    h = width / numFlows;
    mainSpeed = 30;
    flows = new Flow[numFlows]; // Create array
    for (int i = 0; i < flows.length; i++) {
        // Initialize each object
        flows[i] = new Flow(0, 0, 0);
    }
}

public void draw() {
    background(0,12);

    if (run && (frameCount % mainSpeed == 0)) {
        int i = PApplet.parseInt(random(numFlows));
        if (flows[i].speed == 0) {
            flows[i].init();
        }
    }

    for (Flow f : flows) {
        f.move();
        f.display();
    }

    if (!run) {
        int count = 0;
        for (Flow f : flows) {
            if (f.speed != 0) {
                count += 1;
                break;
            }
        }
        run = (count == 0) ? true : false;
        direction = run ? -direction : direction;
    }
}

public void keyPressed() {
    // if (keyCode == UP && mainSpeed < height/2 - 5) {
    //     println("UP");
    //     mainSpeed += 5;
    // } else if (keyCode == DOWN && mainSpeed > 5) {
    // println("DOWN");
    // mainSpeed -= 5;
    if (keyCode == UP) {
        println("UP");
        if (direction != -1) {
            run = false;
        }
    } else if (keyCode == DOWN) {
        println("DOWN");
        if (direction != 1) {
            run = false;
        }
    } else if (keyCode == LEFT && mainSpeed < height/2) {
        println("LEFT");
        mainSpeed += 1;
    } else if (keyCode == RIGHT && mainSpeed > 1) {
        println("RIGHT");
        mainSpeed -= 1;
    } else if (keyCode == ALT) {
        println("ALT");
        mainSpeed = 30;
    }
    // println(mainSpeed);
}


// Flow Class
class Flow {
    float x, y;
    float speed;
    int codeLength;
    int strSize = 5;
    int shakeRate = 999;
    int shakeDuration = 20;
    int shakeStartF = 0;
    String codeStr = "";
    float strWidth;

    // Constructor
    Flow(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void move() {
        x -= speed;
        if (x <= (-height - strWidth)) {
            x = 0;
            speed = 0;
        }
    }

    public void display() {
        pushMatrix();
        noFill();
        if (frameCount % this.shakeRate == 0) {
            shakeStartF = frameCount;
        }
        if (frameCount - shakeStartF <= shakeDuration) {
            fill (0,255,0, PApplet.parseInt(random(0, 255))); // 利用每一帧的透明度不同产生类似闪烁的效果
        } else {
            fill(0,255,0); 
        }
        if (direction == -1) {
            translate(width, height);
        }
        rotate((2+direction)*HALF_PI);
        textLeading(h);
        textSize(this.strSize);
        this.strWidth = textWidth(codeStr);
        text(codeStr, x, y);
        popMatrix();
    }

    public void init() {
        this.y = random(0, height);
        this.shakeRate = PApplet.parseInt(random(50,100));
        this.strSize = PApplet.parseInt(random(5, 40));
        this.speed = random(0.5f, 2.0f);
        this.codeStr = "";
        this.codeLength = PApplet.parseInt(random(1, 20));
        for (int i = 0; i < this.codeLength; i++) {
            this.codeStr += str(PApplet.parseInt(random(0,2)));
        }
    }

}
  public void settings() {  size(700, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BitFlow" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
