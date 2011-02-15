package UI;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author WinoTu
 */
public class EkranPowitalny extends Canvas {

    protected void paint(Graphics g) {
        
        int w = getWidth();
        int h = getHeight();

        g.setColor(0x666666);
        g.fillRect(0, 0, w, h);

        g.setColor(0x00FF00);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE));
        g.drawString("JTYRIAN", 120 , 160, Graphics.BASELINE | Graphics.HCENTER);
    }
}
