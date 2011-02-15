package main;

import UI.EkranGry;
import UI.EkranPowitalny;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author WinoTu
 */
public class StartTyrian extends MIDlet implements CommandListener{

    private Displayable ekran;

    public StartTyrian(){
        ekran = new EkranPowitalny();
    }

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }
    protected void startApp(){

        ekran.addCommand(new Command("Graj", Command.SCREEN, 0));
        ekran.addCommand(new Command("Exit", Command.EXIT, 0));
        ekran.setCommandListener(this);
        getDisplay().setCurrent(ekran);
    }

    protected void pauseApp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        destroyApp(true);
        notifyDestroyed();
    }

    public void commandAction(Command c, Displayable d) {
       if(c.getCommandType() ==Command.EXIT){
            notifyDestroyed();
        }
        else
        if(c.getLabel().equals("Graj")){
            ekran = new EkranGry();
            ekran.addCommand(new Command("Exit", Command.EXIT, 0));
            ekran.setCommandListener(this);
            getDisplay().setCurrent(ekran);
        }
    }

}
