package contoller;

import static model.PropertyChangeEnabledMutableColor.PROPERTY_GREEN;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.ColorModel;
import model.PropertyChangeEnabledMutableColor;

/**
 * Represents a Panel with components used to change and display the Green value for the 
 * backing Color model.
 *
 * @author Charles Bryan
 * @author Alan To
 * @version Winter 2023
 */
public class GreenRowPanel extends JPanel implements PropertyChangeListener {

    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 2284116355218892348L;
    
    /** The size of the increase/decrease buttons. */
    private static final Dimension BUTTON_SIZE = new Dimension(26, 26);
    
    /** The size of the text label. */
    private static final Dimension LABEL_SIZE = new Dimension(45, 26);
    
    /** The number of columns in width of the TextField. */
    private static final int TEXT_FIELD_COLUMNS = 3;
    
    /** The amount of padding for the change panel. */
    private static final int HORIZONTAL_PADDING = 30;
    
    /** The backing model for the system. */
    private final PropertyChangeEnabledMutableColor myColor;

    /** The CheckBox that enables/disables editing of the TextField. */
    private final JCheckBox myEnableEditButton;
    
    /** The TextField that allows the user to type input for the color value. */
    private final JTextField myValueField;
    
    /** The Button that when clicked increases the color value. */
    private final JButton myIncreaseButton;
    
    /** The Button that when clicked decreases the color value. */
    private final JButton myDecreaseButton;
    
    /** The Slider that when adjusted, changes the color value. */
    private final JSlider myValueSlider;
    
    /** The panel that visually displays ONLY the GREEN value for the color. */
    private final JPanel myColorDisplayPanel;
    
    /**
     * Creates a Panel with components used to change and display the Green value for the 
     * backing Color model. 
     * @param theColor the backing model for the system
     */
    public GreenRowPanel(final PropertyChangeEnabledMutableColor theColor) {
        super();
        myColor = theColor;
        myEnableEditButton = new JCheckBox("Enable edit");
        myValueField = new JTextField();
        myIncreaseButton = new JButton();
        myDecreaseButton = new JButton();
        myValueSlider = new JSlider();
        myColorDisplayPanel = new JPanel();
        layoutComponents();
        addListeners();
    }
    
    /**
     * Setup and add the GUI components for this panel. 
     */
    private void layoutComponents() {
        myColorDisplayPanel.setPreferredSize(BUTTON_SIZE);
        myColorDisplayPanel.setBackground(new Color(0, myColor.getGreen(), 0));
        final JLabel rowLabel = new JLabel("Green: ");
        rowLabel.setPreferredSize(LABEL_SIZE);
        myValueField.setText(String.valueOf(myColor.getGreen()));
        myValueField.setEditable(false);
        myValueField.setColumns(TEXT_FIELD_COLUMNS);
        myValueField.setHorizontalAlignment(JTextField.RIGHT);
        
        final JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 
                                                             HORIZONTAL_PADDING, 
                                                             0, 
                                                             HORIZONTAL_PADDING));
        rightPanel.setBackground(rightPanel.getBackground().darker());
        myIncreaseButton.setIcon(new ImageIcon("./images/ic_increase_value.png"));
        myIncreaseButton.setPreferredSize(BUTTON_SIZE);
        myValueSlider.setMaximum(ColorModel.MAX_VALUE);
        myValueSlider.setMinimum(ColorModel.MIN_VALUE);
        myValueSlider.setValue(myColor.getGreen());
        myValueSlider.setBackground(rightPanel.getBackground());
        myDecreaseButton.setIcon(new ImageIcon("./images/ic_decrease_value.png"));
        myDecreaseButton.setPreferredSize(BUTTON_SIZE);
        myDecreaseButton.setEnabled(false);
        rightPanel.add(myDecreaseButton);
        rightPanel.add(myValueSlider);
        rightPanel.add(myIncreaseButton);
        
        add(myColorDisplayPanel);
        add(rowLabel);
        add(myValueField);
        add(myEnableEditButton);
        add(rightPanel);

    }
    
    /**
     * Add listeners (event handlers) to any GUI components that require handling.  
     */
    private void addListeners() {
        //DO not remove the following statement.
        myColor.addPropertyChangeListener(this);

        myEnableEditButton.addActionListener(new ActionListener() {
            /**
             * When checked, the value field should be editable, uneditable otherwise.
             * @param theE the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent theE) {
                myValueField.setEditable(myEnableEditButton.isSelected());
            }
        });

        myIncreaseButton.addActionListener(theE -> {
            myIncreaseButton.setEnabled(myColor.getGreen() != ColorModel.MAX_VALUE);
            final int initial = myColor.getGreen();
            try {
                final int res = Integer.parseInt(myValueField.getText());
                if (res > ColorModel.MAX_VALUE || res < ColorModel.MIN_VALUE) {
                    //if what's in the value field is over 255 or under 0 and we press
                    //the increase button, it should go back to its initial value before
                    myValueField.setText(String.valueOf(initial));
                    myValueSlider.setValue(initial);
                } else {
                    //incrementing the value in the field and the color.
                    myValueField.setText(String.valueOf(
                            Integer.parseInt(myValueField.getText()) + 1));
                    myColor.setGreen(Integer.parseInt(myValueField.getText()));
                    myValueField.setText(String.valueOf(myColor.getGreen()));
                }
            } catch (final NumberFormatException nfe) {
                //if what's in the field is a string and not an int
                myValueField.setText(String.valueOf(initial));
                myValueSlider.setValue(initial);
            }
        });

        myDecreaseButton.addActionListener(theE -> {
            myDecreaseButton.setEnabled(myColor.getGreen() != ColorModel.MIN_VALUE);
            final int initial = myColor.getGreen();
            try {
                final int res = Integer.parseInt(myValueField.getText());
                if (res > ColorModel.MAX_VALUE || res < ColorModel.MIN_VALUE) {
                    //if what's in the value field is over 255 or under 0 and we press
                    //the decrease button, it should go back to its initial value before
                    myValueField.setText(String.valueOf(initial));
                    myValueSlider.setValue(initial);
                } else {
                    //incrementing the value in the field and the color.
                    myValueField.setText(String.valueOf(
                            Integer.parseInt(myValueField.getText()) - 1));
                    myColor.setGreen(Integer.parseInt(myValueField.getText()));
                    myValueField.setText(String.valueOf(myColor.getGreen()));
                    // myColor.setGreen(myColor.getGreen() - 1);
                }
            } catch (final NumberFormatException nfe) {
                //if what's in the field is a string and not an int
                myValueField.setText(String.valueOf(initial));
                myValueSlider.setValue(initial);
            }
        });

        myValueField.addActionListener(theE -> {
            final int initial = myColor.getGreen();
            try {
                final int res = Integer.parseInt(myValueField.getText());
                final String str = myValueField.getText();
                if (res > ColorModel.MAX_VALUE || res < ColorModel.MIN_VALUE) {
                    myValueField.setText(String.valueOf(initial));
                    myValueSlider.setValue(initial);
                } else {
                    myValueField.setText(str);
                    myValueSlider.setValue(res);
                }
            } catch (final NumberFormatException nfe) {
                myValueField.setText(String.valueOf(initial));
                myValueSlider.setValue(initial);
            }
        });

        myValueSlider.addChangeListener(new ChangeListener() {
            /**
             * When moving the slider, the color of green should change. We should also consider
             * the increase and decrease buttons.
             * @param theE  a ChangeEvent object
             */
            @Override
            public void stateChanged(final ChangeEvent theE) {
                myIncreaseButton.setEnabled(myValueSlider.getValue() != ColorModel.MAX_VALUE);
                myDecreaseButton.setEnabled(myValueSlider.getValue() != 0);
                myColor.setGreen(myValueSlider.getValue());
                myValueField.setText(String.valueOf(myColor.getGreen()));
            }
        });
    }
    

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_GREEN.equals(theEvent.getPropertyName())) {
            myValueField.setText(theEvent.getNewValue().toString());
            myValueSlider.setValue((Integer) theEvent.getNewValue());
            myColorDisplayPanel.
                setBackground(new Color(0, (Integer) theEvent.getNewValue(), 0));
        }
        
    }
}
