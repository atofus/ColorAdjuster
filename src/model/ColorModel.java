package model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;

/**
 * The Color class is used to encapsulate colors in the RGB color space. Color object are 
 * mutable. When the state of a Color object is mutated, a PropertyEvent is fired informing
 * listener of the change. 
 * 
 * @author Charles Bryan
 * @version Autumn 2018
 */
public class ColorModel implements PropertyChangeEnabledMutableColor {

    /** The maximum value for any of the Color's components. */
    public static final int MAX_VALUE = 255;
    
    /** The minimum value for any of the Color's components. */
    public static final int MIN_VALUE = 0;
    
    /** The first integer decimal value that requires 2 digits in hex. */ 
    private static final int MAX_SINGLE_DIGIT_HEX = 16;
    
    /** The. */
    private final PropertyChangeSupport myPcs;
    
    /** The color's red component. */
    private int myRed;
    
    /** The color's green component. */
    private int myGreen;
    
    /** The color's blue component. */
    private int myBlue;
        
    /**
     * Creates an opaque RGB color with the specified 
     * red, green, and blue values in the range (0 - 255). 
     * 
     * The alpha is set to 255.
     * 
     * @param theRed the red component
     * @param theGreen the green component
     * @param theBlue the blue component
     * @throws IllegalArgumentException if theRed, theGreen, or theBlue 
     *          are outside of the range 0 to 255, inclusive
     */
    public ColorModel(final int theRed, 
                           final int theGreen, 
                           final int theBlue) {
        super();
        myRed = theRed;
        myGreen = theGreen;
        myBlue = theBlue;
        myPcs = new PropertyChangeSupport(this);
    }

    @Override
    public int getRed() {
        return myRed;
    }

    @Override
    public void setRed(final int theRed) {
        if (theRed < MIN_VALUE || theRed > MAX_VALUE) {
            throw new IllegalArgumentException("The value for red must be [0-255]. ");
        }
        final int old = myRed;
        myRed = theRed;
        myPcs.firePropertyChange(PROPERTY_RED, old, myRed);
        myPcs.firePropertyChange(PROPERTY_COLOR, null, getColor());
    }
    
    @Override
    public void adjustRed(final int theOffSet) {
        setRed(myRed + theOffSet); 
    }

    @Override
    public int getGreen() {
        return myGreen;
    }

    @Override
    public void setGreen(final int theGreen) {
        if (theGreen < MIN_VALUE || theGreen > MAX_VALUE) {
            throw new IllegalArgumentException("The value for green must be [0-255]. ");
        }
        final int old = myGreen;
        myGreen = theGreen;
        myPcs.firePropertyChange(PROPERTY_GREEN, old, myGreen);
        myPcs.firePropertyChange(PROPERTY_COLOR, null, getColor());
    }
    
    @Override
    public void adjustGreen(final int theOffSet) {
        setGreen(myGreen + theOffSet); 
    }

    @Override
    public int getBlue() {
        return myBlue;
        
    }

    @Override
    public void setBlue(final int theBlue) {
        if (theBlue < MIN_VALUE || theBlue > MAX_VALUE) {
            throw new IllegalArgumentException("The value for blue must be [0-255]. ");
        }
        final int old = myBlue;
        myBlue = theBlue;
        myPcs.firePropertyChange(PROPERTY_BLUE, old, myBlue);
        myPcs.firePropertyChange(PROPERTY_COLOR, null, getColor());
    }
    
    @Override
    public void adjustBlue(final int theOffSet) {
        setBlue(myBlue + theOffSet); 
    }
    
    /**
     * Gets an instance of the java.awt.Color object based on this Color's 
     * component colors.
     * @return the java.awt.Color object 
     */
    public Color getColor() {
        return new Color(myRed, myGreen, myBlue);
    }
    
    /**
     * Returns a hexadecimal representation of this Color's component values.
     * 
     * @return the hexadecimal representation
     */
    public String getHexCode() {
        return "#" + getHex(myRed) + getHex(myGreen) + getHex(myBlue);
    }
    
    /**
     * A helper method for getHexCode. Returns the hex value of 
     * theInt as 2 character String. THis method will prepend a 
     * "0" to single digit hex values. 
     * 
     * @param theInt an integer to be converted to a string 
     * @return the 2 character hex string 
     */
    private String getHex(final int theInt) {
        if (theInt > MAX_VALUE) {
            throw new IllegalArgumentException("Argument should not be greater than 255. "
                            + "That would need a 3 or more character String.");
        }
        final StringBuilder result = new StringBuilder(Integer.toHexString(theInt));
        if (theInt < MAX_SINGLE_DIGIT_HEX) {
            result.insert(0, "0");
        }
        return result.toString().toUpperCase(Locale.US);
    }
    
    @Override
    public String toString() {
        return "[r=" + myRed + ", g=" + myGreen + ", b=" + myBlue + "]";
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }
    
}
