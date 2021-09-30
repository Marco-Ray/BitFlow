//import gifAnimation.*;
//GifMaker gifExport;

Flow[] flows; // Declare array
int numFlows;
int h;
int mainSpeed;
int direction = 1;
boolean run = true;

void setup() {
    size(500, 500);
    numFlows = 50;
    h = width / numFlows;
    mainSpeed = 30;
    flows = new Flow[numFlows]; // Create array
    for (int i = 0; i < flows.length; i++) {
        // Initialize each object
        flows[i] = new Flow(0, 0, 0);
    }
    
    //gifExport = new GifMaker(this, "BitFlow.gif");
    //gifExport.setRepeat(0);
}

void draw() {
    background(0,12);

    if (run && (frameCount % mainSpeed == 0)) {
        int i = int(random(numFlows));
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
    
    //gifExport.setDelay(1);
    //gifExport.addFrame();
}

void keyPressed() {
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

//void mousePressed() {
//  gifExport.finish();
//}

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

    void move() {
        x -= speed;
        if (x <= (-height - strWidth)) {
            x = 0;
            speed = 0;
        }
    }

    void display() {
        pushMatrix();
        noFill();
        if (frameCount % this.shakeRate == 0) {
            shakeStartF = frameCount;
        }
        if (frameCount - shakeStartF <= shakeDuration) {
            fill (0,255,0, int(random(0, 255))); // 利用每一帧的透明度不同产生类似闪烁的效果
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

    void init() {
        this.y = random(0, height);
        this.shakeRate = int(random(50,100));
        this.strSize = int(random(5, 40));
        this.speed = random(0.5, 2.0);
        this.codeStr = "";
        this.codeLength = int(random(1, 20));
        for (int i = 0; i < this.codeLength; i++) {
            this.codeStr += str(int(random(0,2)));
        }
    }

}
