package ezdeadtime;

import com.efiAnalytics.plugin.ApplicationPlugin;
import com.efiAnalytics.plugin.ecu.ControllerAccess;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EzDeadTime extends JPanel implements ApplicationPlugin{
    ControllerAccess controllerAccess = null;

    public EzDeadTime(){
        buildUi();
    }

    private void buildUi(){
        setLayout(new BorderLayout());

        JPanel pSouth = new JPanel();
        pSouth.setLayout(new GridLayout(0,1,5,5));

        ScatterPlot scatterPlot = new ScatterPlot();
        pSouth.add(scatterPlot);

        add(BorderLayout.SOUTH, pSouth);
    }

    public String getIdName() {
        return "ezDeadTime";
    }

    public int getPluginType() {
        return PERSISTENT_DIALOG_PANEL;
        //return TAB_PANEL; // not supported in TunerStudio 1.3x .
    }

    public String getDisplayName() {
        return "EzDeadTime";
    }

    public String getDescription() {
        return "A cool plugin.";
    }


    public void initialize(ControllerAccess ca) {
        this.controllerAccess = ca;
    }

    public boolean displayPlugin(String serialSignature) {
        return true;
    }

    public String getAuthor() {
        return "Nils Asejevs";
    }

    public JComponent getPluginPanel() {
        return this;
    }

    public void close() {

    }
    /**
     * This main is likely not used, but TunerStudio will check the manifest
     * for the an ApplicationPlugin: assignment, if one is not found it will check
     * for a main class and expect that to be an implementation of
     * <code>ApplicationPlugin</code>. 
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }

    /**
     * Here is is simply always enabled, and a more complex
     * condition that evaluates an expression using controllerAccess.evaluateExpression
     */
    public boolean isMenuEnabled() {
        return true;
    }

    /**
     * The URL to be used on the help menu. if null is returned the help menu
     * will be suppressed.
     */
    public String getHelpUrl() {
        return "https://www.msextra.com/forums/";
    }

    /**
     * Return a version number for informational purposes.
     */
    public String getVersion() {
        return "1.0";
    }

    /**
     * Return the PluginAPI specification this plug in requires.
     */
    public double getRequiredPluginSpec() {
        return 1.0;
    }

}
