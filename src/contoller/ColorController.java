 
package contoller;

import static model.PropertyChangeEnabledMutableColor.PROPERTY_COLOR;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.ColorModel;
import model.PropertyChangeEnabledMutableColor;
import view.ColorPanel;

/**
 *  A controller for ObservableColor.
 * 
 *  @author Charles Bryan
 *  @version Autumn 2015 
 */
public class ColorController extends JPanel implements PropertyChangeListener {
    
    /**  
     * A generated serial version UID for object Serialization. 
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8452917670991316606L;
    
    // constants to capture screen dimensions
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** The number of rows. */
    private static final int ROW = 4;
    
    /** The number of columns. */
    private static final int COL = 1;
    
    /** Amount in Pixels for the Horizontal margin. */
    private static final int HORIZONATAL_MARGIN = 20; 
    
    /** Amount in Pixels for the Vertical margin. */
    private static final int VERTICALL_MARGIN = 10; 
    
    /** Amount in columns for the text area. */
    private static final int TEXT_AREA_COLS = 25; 
    
    /** The default size of the color panel. */
    private static final int WINDOW_SIZE = 200;
    
    /** The Color object this class controls. */
    private final PropertyChangeEnabledMutableColor myColor;
    
    /** The output window for color updates. */ 
    private final JTextArea myOutput;
    
    /**
     * Constructs a ColorSlider.
     * 
     * @param theColor the color object this class controls
     */
    public ColorController(final ColorModel theColor) {
        super(new GridLayout(ROW, COL, 0, HORIZONATAL_MARGIN));
        setBorder(BorderFactory.createEmptyBorder(VERTICALL_MARGIN, 
                                                  HORIZONATAL_MARGIN, 
                                                  VERTICALL_MARGIN, 
                                                  VERTICALL_MARGIN));
        myColor = theColor;
        
        myOutput = new JTextArea(ROW, TEXT_AREA_COLS);
        
        layoutComponents();
    }
    
    /**
     * Lay out the components.
     */
    private void layoutComponents() {

        add(new RedRowPanel(myColor));
        add(new GreenRowPanel(myColor));
        add(new BlueRowPanel(myColor));
        
        final JScrollPane scrollPane = new JScrollPane(myOutput);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }
    
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * 
     * NOTE: This is the place where all of the parts and pieces of this project are in 
     *      the same place. This is where we should instantiate our MOdel and add it to the
     *      controller and view.  
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        final JFrame frame = new JFrame("Change the Color");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final ColorModel color = new ColorModel(51, 0, 111);
       
        //Create and set up the content pane.
        final ColorController pane = new ColorController(color);
        pane.setOpaque(true); //content panes must be opaque
        
        //Add the ColorSlider object itself as a PCL to the model.  
        color.addPropertyChangeListener(pane);
        frame.setContentPane(pane);
        
        //Create a color panel to listen to and demonstrate our 
        //ObservableColor.
        final ColorPanel cop = new ColorPanel(color.getColor());
        cop.setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
        color.addPropertyChangeListener(ColorModel.PROPERTY_COLOR, cop);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame colorFrame = new JFrame();
                colorFrame.setTitle("Color Display");
                colorFrame.setContentPane(cop);
                colorFrame.pack();
                colorFrame.setVisible(true);
            }
        });
        
        
        //Display the window.
        // position the frame in the center of the screen
        frame.pack();
        frame.setLocation(SCREEN_SIZE.width / 2 - frame.getWidth() / 2,
                    SCREEN_SIZE.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_COLOR.equals(theEvent.getPropertyName())) {
            myOutput.setCaretPosition(myOutput.getDocument().getLength());
            myOutput.append(theEvent.getNewValue().toString());
            myOutput.append(System.lineSeparator());
        }
    }

}