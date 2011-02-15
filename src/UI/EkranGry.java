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
    private int pozStatX = 120;
    private int pozStatY = 160;
    private static int[] pozycje = {0, 1, 2, 3, 4};
    Sprite statek = null;
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

        pozStatY = h-40;

        try {
            statek = new Sprite(Image.createImage("/pics/statek.png"), 46, 60);
            statek.setFrameSequence(pozycje);
            statek.setFrame(2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void przesunGora() {
        if (pozStatY >= 30+krok) {
            int pozS = 1;
            while (pozS-- >= 0) {
                if (pozStatY >= 30+krok) pozStatY -= krok;
                try {
                    render(g);
                    flushGraphics();
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if ((getKeyStates() & UP_PRESSED) != 0)pozS++;
            }
            pozStatY -= krok;
        }
    }
        private void przesunDol() {
        if (pozStatY <= h - (30+krok)) {
            int pozS = 1;
            while (pozS-- >= 0) {
                if (pozStatY <= h - (30+krok)) pozStatY += krok;
                try {
                    render(g);
                    flushGraphics();
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if ((getKeyStates() & DOWN_PRESSED) != 0)pozS++;
            }
            pozStatY += krok;
        }
    }
    private void przesunLewo() {
        if (pozStatX >= 23+krok) {
            int pozS = 1;
            while (pozS >= 0) {
                if (pozStatX >= 23+krok) pozStatX -= krok;
                statek.setFrame(pozS--);
                try {
                    render(g);
                    flushGraphics();
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if ((getKeyStates() & LEFT_PRESSED) != 0)pozS++;
            }
            pozStatX -= krok;
            statek.setFrame(2);
        }
    }
    private void przesunPrawo() {
        if (pozStatX <= w - (23+krok)) {
            int pozS = 3;
            while (pozS <= 4) {
                if (pozStatX <= w - (23+krok)) pozStatX += krok;
                statek.setFrame(pozS++);
                try {
                    render(g);
                    flushGraphics();
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if ((getKeyStates() & RIGHT_PRESSED) != 0)pozS--;
            }
            pozStatX += krok;
            statek.setFrame(2);
        }
    }
    public void start() {
        // mTrucking = true;
        Thread t = new Thread(this);
        t.start();
    }

    public void stop() {
        //mTrucking = false;
    }

    public void render(Graphics g) {
        g.setColor(0xE6E5DA);
        g.fillRect(0, 0, w, h);

        statek.setPosition(pozStatX - 23, pozStatY - 30);
        statek.paint(g);
    }

    public void run() {
        if (statek == null) {
            initialize();
        }
        while (true) {
            //
            //     Wylapanie ruchu
            //
            int ks = getKeyStates();
            if ((ks & LEFT_PRESSED) != 0)
                przesunLewo();
            else if((ks & RIGHT_PRESSED) != 0)
                przesunPrawo();
            else if ((ks & UP_PRESSED) != 0)
                przesunGora();
            else if((ks & DOWN_PRESSED) != 0)
                przesunDol();

            render(g);
            flushGraphics();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
        }
    }
}
