import java.util.ArrayList;
import javafx.scene.layout.*;


/**
 * This class is the model of the mainWindow. The main window object 
 * will use this to track panels, including updating them when
 * user preferences change.
 *
 * @author Sowrab Chowdhury (k20028600)
 * @version 2021.03.21
 */
public class PanelManager 
{
    //The list of panels.
    private ArrayList<Panel> panels;

    //The dataset being used.
    private AirbnbProperties propertyData;

    //Indexing for "panels" list.
    private  int panelsIndex;

    //To add other panels after correct price range.
    private boolean otherPanelsAdded;

    /**
     * Constructor of PanelManager, 
     * Sets up dataset from AirbnbProperties class and
     * instantiates the other panels too.
     * 
     */
    public PanelManager() throws Exception
    {
        instantiateDataset();

        otherPanelsAdded = false;
        panels = new ArrayList<>();
        Panel reservationsPanel = new ReservationsPanel();
        addPanel(reservationsPanel);
    }

    /**
     * Used to create dataset, along with default values being
     * put in place.
     * 
     */
    private void instantiateDataset()
    {
        propertyData = new AirbnbProperties();
        propertyData.setFromPrice(0);
        propertyData.setToPrice(1000);
    }

    /**
     * Return the number of panels in the list.
     * @return The number of panels in the application.
     */
    public int getListSize(){
        return panels.size();
    }

    /**
     * Changes current panel to the next one, or circle back to first panel.
     */
    public void goNextPanel()
    {
        if (panelsIndex == getListSize() - 1){ 
            panelsIndex = -1;
        }
        panelsIndex++;
    }

    /**
     * Changes current panel to the previous ine, or circle to the final panel.
     */
    public void goPreviousPanel()
    {
        if (panelsIndex == 0){ 
            panelsIndex = getListSize();
        }
        panelsIndex--;
    }

    /**
     * Changes dataset's minimum price to display.
     * @param num The minimum value to set.
     */
    public void setFromValue(int num)
    {
        propertyData.setFromPrice(num);
        updatePanel();
    }

   /**
     * Changes dataset's maximum price to display.
     * @param num The maximum value to set.
     */
    public void setToValue(int num)
    {
        propertyData.setToPrice(num);
        updatePanel();
    }

    /**
     * Updates the current panel.
     */
    public void updatePanel(){
        //panels.stream().forEach(p -> p.update());
        panels.get(panelsIndex).update();
    }

    /**
     * Add a new panel, which can be displayed
     * at the centre of the window.
     * @param panel The pane representing the new panel.
     */
    public void addPanel(Panel panel){
        panels.add(panel);
    }

    /**
     * Adds the welcome panel at the beginning of the list.
     * @param welcomePanel The starting panel of the application.
     */
    public void addWelcomePanel(Panel welcomePanel){
        panels.add(0, welcomePanel);
    }

    /**
     * Adds the rest of the panels.
     * Called after the initial price range is set.
     */
    public void addOtherPanels()
    {
        if (!otherPanelsAdded){
            Panel mapPanel= new MapPanel(propertyData);
            Panel statisticsPanel = new StatisticsPanel(propertyData);
            addPanel(mapPanel);
            addPanel(statisticsPanel);
            otherPanelsAdded = true;
        }
    }

    /**
     * Returns the current panel the mainWindow should show.
     * @return The current panel.
     * @throws Exception
     */
    public Pane getCurrentPanel() {
        try {
            return panels.get(panelsIndex).getPane();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            return null;
    }
}
