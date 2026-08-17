package inputs;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class KeyBindings {
    private static final String CONFIG_FILE = "controls.properties";

    // Player 1 Keys
    public static int p1Left = KeyEvent.VK_A;
    public static int p1Right = KeyEvent.VK_D;
    public static int p1Jump = KeyEvent.VK_W;
    public static int p1Defend = KeyEvent.VK_S;
    public static int p1Attack1 = KeyEvent.VK_Z;
    public static int p1Attack2 = KeyEvent.VK_X;
    public static int p1Attack3 = KeyEvent.VK_C;
    public static int p1Special = KeyEvent.VK_V;

    // Player 2 Keys
    public static int p2Left = KeyEvent.VK_LEFT;
    public static int p2Right = KeyEvent.VK_RIGHT;
    public static int p2Jump = KeyEvent.VK_UP;
    public static int p2Defend = KeyEvent.VK_DOWN;
    public static int p2Attack1 = KeyEvent.VK_NUMPAD1;
    public static int p2Attack2 = KeyEvent.VK_NUMPAD2;
    public static int p2Attack3 = KeyEvent.VK_NUMPAD3;
    public static int p2Special = KeyEvent.VK_NUMPAD4;

    // Pause Key
    public static int pauseKey = KeyEvent.VK_P;

    public static void load() {
        Properties props = new Properties();
        File f = new File(CONFIG_FILE);
        if (f.exists()) {
            try (FileInputStream in = new FileInputStream(f)) {
                props.load(in);
                
                p1Left = Integer.parseInt(props.getProperty("p1Left", String.valueOf(KeyEvent.VK_A)));
                p1Right = Integer.parseInt(props.getProperty("p1Right", String.valueOf(KeyEvent.VK_D)));
                p1Jump = Integer.parseInt(props.getProperty("p1Jump", String.valueOf(KeyEvent.VK_W)));
                p1Defend = Integer.parseInt(props.getProperty("p1Defend", String.valueOf(KeyEvent.VK_S)));
                p1Attack1 = Integer.parseInt(props.getProperty("p1Attack1", String.valueOf(KeyEvent.VK_Z)));
                p1Attack2 = Integer.parseInt(props.getProperty("p1Attack2", String.valueOf(KeyEvent.VK_X)));
                p1Attack3 = Integer.parseInt(props.getProperty("p1Attack3", String.valueOf(KeyEvent.VK_C)));
                p1Special = Integer.parseInt(props.getProperty("p1Special", String.valueOf(KeyEvent.VK_V)));

                p2Left = Integer.parseInt(props.getProperty("p2Left", String.valueOf(KeyEvent.VK_LEFT)));
                p2Right = Integer.parseInt(props.getProperty("p2Right", String.valueOf(KeyEvent.VK_RIGHT)));
                p2Jump = Integer.parseInt(props.getProperty("p2Jump", String.valueOf(KeyEvent.VK_UP)));
                p2Defend = Integer.parseInt(props.getProperty("p2Defend", String.valueOf(KeyEvent.VK_DOWN)));
                p2Attack1 = Integer.parseInt(props.getProperty("p2Attack1", String.valueOf(KeyEvent.VK_NUMPAD1)));
                p2Attack2 = Integer.parseInt(props.getProperty("p2Attack2", String.valueOf(KeyEvent.VK_NUMPAD2)));
                p2Attack3 = Integer.parseInt(props.getProperty("p2Attack3", String.valueOf(KeyEvent.VK_NUMPAD3)));
                p2Special = Integer.parseInt(props.getProperty("p2Special", String.valueOf(KeyEvent.VK_NUMPAD4)));
                pauseKey = Integer.parseInt(props.getProperty("pauseKey", String.valueOf(KeyEvent.VK_P)));
                
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading controls, using defaults.");
            }
        } else {
            save(); // Create with defaults
        }
    }

    public static void save() {
        Properties props = new Properties();
        props.setProperty("p1Left", String.valueOf(p1Left));
        props.setProperty("p1Right", String.valueOf(p1Right));
        props.setProperty("p1Jump", String.valueOf(p1Jump));
        props.setProperty("p1Defend", String.valueOf(p1Defend));
        props.setProperty("p1Attack1", String.valueOf(p1Attack1));
        props.setProperty("p1Attack2", String.valueOf(p1Attack2));
        props.setProperty("p1Attack3", String.valueOf(p1Attack3));
        props.setProperty("p1Special", String.valueOf(p1Special));

        props.setProperty("p2Left", String.valueOf(p2Left));
        props.setProperty("p2Right", String.valueOf(p2Right));
        props.setProperty("p2Jump", String.valueOf(p2Jump));
        props.setProperty("p2Defend", String.valueOf(p2Defend));
        props.setProperty("p2Attack1", String.valueOf(p2Attack1));
        props.setProperty("p2Attack2", String.valueOf(p2Attack2));
        props.setProperty("p2Attack3", String.valueOf(p2Attack3));
        props.setProperty("p2Special", String.valueOf(p2Special));
        props.setProperty("pauseKey", String.valueOf(pauseKey));

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "MIDGAME Control Bindings");
        } catch (IOException e) {
            System.err.println("Error saving controls.");
        }
    }
}
