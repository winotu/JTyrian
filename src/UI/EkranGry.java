package UI;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author WinoTu
 */
public class EkranGry extends GameCanvas implements Runnable {

    private int w = 0;
    private int h = 0;
    private int krok = 6;
    private int krokPocisku=-10;
    private int opoznienie = 80;

    // sprawdzenie czy klawisz nadal wcisniety
    boolean czyLEFT = false;
    boolean czyRIGHT = false;
    boolean rysujPocisk=false;

    int statekZwrot = 0;
    int animTimer = 4;
    Sprite statek = null;
    Sprite pocisk = null;
    Graphics g = null;

    public EkranGry() {
        super(true);
        setFullScreenMode(true);
        start();
    }

    private void initialize() {

        g = getGraphics();
        w = getWidth();
        h = getHeight();

        try {
            statek = new Sprite(Image.createImage("/pics/statek.png"), 46, 60);
            statek.setFrameSequence(new int[]{0, 1, 2, 3, 4});
            statek.setFrame(2);
            statek.setPosition((int) w / 2 - 23, h - 65);

            pocisk = new Sprite(Image.createImage("/pics/banana_pocisk.png"), 12,14);
            pocisk.setFrameSequence(new int[]{0, 1, 2, 3});
            pocisk.setTransform(Sprite.TRANS_ROT270);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void ruchLewo() {

        czyLEFT = true;
        if (animTimer == 0 || statekZwrot == 0) {
            statekZwrot = -1;
            animTimer = 4;
        }
        if (statek.getX() > krok) {
            statek.move(-krok, 0);
        }

    }

    private void ruchPrawo() {

        czyRIGHT = true;
        if (animTimer == 0 || statekZwrot == 0) {
            statekZwrot = 1;
            animTimer = 4;
        }
        if (statek.getX() < w - (46 + krok)) {
            statek.move(krok, 0);
        }

    }

    public void start() {
        if (statek == null) {
            initialize();
        }
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {

        while (true) {
            try {

                manageButtons();        //   logika rekacji na przycisk
                manageLogic();          //   logika gry
                managePaint();          //   logika rysowania elementow

                Thread.sleep(opoznienie);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void manageButtons() {
        int ks = getKeyStates();

        if ((ks & LEFT_PRESSED) != 0) {
            ruchLewo();
        } else if ((ks & RIGHT_PRESSED) != 0) {
            ruchPrawo();
        }
        if ((ks & UP_PRESSED) != 0)
            if(statek.getY()>krok+5)statek.setPosition(statek.getX(),statek.getY() -(krok+5));
        if ((ks & DOWN_PRESSED) != 0)
            if(statek.getY()<h-(60+krok+5))statek.setPosition(statek.getX(),statek.getY()+ krok+5);
        if ((ks & FIRE_PRESSED) != 0)
            strzal();

    }

    private void manageLogic() {

        if (statekZwrot == -1) {
            if (animTimer > 2) {
                statek.prevFrame();
            } else if (animTimer == 2 && czyLEFT) {
                animTimer = 3;
                statek.setFrame(0);
            } else if (animTimer != 0) {
                statek.nextFrame();
            } else {
                statekZwrot = 0;
            }
            animTimer--;
            czyLEFT = false;
        } else if (statekZwrot == 1) {
            if (animTimer > 2) {
                statek.nextFrame();
            } else if (animTimer == 2 && czyRIGHT) {
                animTimer = 3;
                statek.setFrame(4);
            } else if (animTimer != 0) {
                statek.prevFrame();
            } else {
                statekZwrot = 0;
            }
            animTimer--;
            czyRIGHT = false;
        } else {
            statek.setFrame(2);
        }
        if(rysujPocisk){
            //przesuwanie pocisku animacja
            pocisk.move(0,krokPocisku);
            pocisk.nextFrame();
            if(pocisk.getY()<=0){
                rysujPocisk=false;
            }
        }

    }

    private void managePaint() {

        // ustawienia tla obrazu
        g.setColor(0xE6E5DA);
        g.fillRect(0, 0, w, h);

        // malowanie statku
        statek.paint(g);

        //malowanie pocisku
        if(rysujPocisk)pocisk.paint(g);

        // wymuszenie odmalowania wszystkiego
        flushGraphics();
    }

    private void strzal() {
        rysujPocisk=true;
        pocisk.setPosition(statek.getX()+18, statek.getY()-3);
    }
}
