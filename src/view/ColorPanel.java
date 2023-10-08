package view;

import static model.PropertyChangeEnabledMutableColor.PROPERTY_COLOR;
import static model.PropertyChangeEnabledMutableColor.PROPERTY_RED;
import static model.PropertyChangeEnabledMutableColor.PROPERTY_GREEN;
import static model.PropertyChangeEnabledMutableColor.PROPERTY_BLUE;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;

/**
 * A demo of Observer design pattern implemented with PropertyChange API. 
 * 
 * @author Charles Bryan
 * @version Autumn 2015
 */
public class ColorPanel extends JPanel implements PropertyChangeListener {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8385732728740430466L;
    
    /**
     * Create a color panel with the supplied color.
     * @param theColor the color for the background
     */
    public ColorPanel(final Color theColor) {
        super();
        setBackground(theColor);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        Color c = getBackground();
        if (PROPERTY_COLOR.equals(theEvent.getPropertyName())) {
            c = (Color) theEvent.getNewValue();
        } else if (PROPERTY_RED.equals(theEvent.getPropertyName())) {
            final int r = (Integer) theEvent.getNewValue();
            c = new Color(r, c.getGreen(), c.getBlue());
        } else if (PROPERTY_GREEN.equals(theEvent.getPropertyName())) {
            final int g = (Integer) theEvent.getNewValue();
            c = new Color(c.getRed(), g, c.getBlue());
        } else if (PROPERTY_BLUE.equals(theEvent.getPropertyName())) {
            final int b = (Integer) theEvent.getNewValue();
            c = new Color(c.getRed(), c.getGreen(), b);
        }  
        setBackground(c);
    }
}
