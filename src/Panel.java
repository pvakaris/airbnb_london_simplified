import javafx.scene.layout.*;

/** 
 *Panel interface, which will be implemented by the panels, 
 *such as the ones described in the task sheet.
 *@author Sowrab Chowdhury (k20028600)
 */
interface Panel {
    /** 
     * returns pane from the panel
     * @return The pane for the centre of the main window.
     */
    public Pane getPane() throws Exception;

    /** 
    *updates state of panel
    */
    public void update();

}
