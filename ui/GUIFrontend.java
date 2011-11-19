/* GUIFrontend.java
 *
 * This class draws in a graphic console all information.
 * (c) 2006 Filippo Mondello
 *
 * This file is part of the EduMIPS64 project, and is released under the GNU
 * General Public License.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edumips64.ui;

import edumips64.core.*;
import edumips64.utils.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.util.*;
import java.util.logging.Logger;

/**
*This class draws in a graphic console all information.
* @author Filippo Mondello
*/
public class GUIFrontend {
	
	GUICycles cycles;
	GUIRegisters regs;
	GUIStatistics stats;
	GUIPipeline pipe;
	GUIData data;
	GUICode code;	

    // Array containing the six components, useful to write more compact code
    GUIComponent components[];

    private static final Logger logger = Logger.getLogger(GUIFrontend.class.getName());
	
	/**Creates the six internal component
	*/
	public GUIFrontend(){
		cycles=new GUICycles();
		regs=new GUIRegisters();
		stats=new GUIStatistics();
		pipe=new GUIPipeline();
		data=new GUIData();
		code=new GUICode();
		
        components = new GUIComponent[6];
        components[0] = cycles;
        components[1] = regs;
        components[2] = stats;
        components[3] = pipe;
        components[4] = data;
        components[5] = code;
	}
	
	/**Set the container for the Cycles component.
	* @param con the container sent by the main program.
	*/
	public void setCyclesContainer(Container con){
		cycles.setContainer(con);
	}
	
	/**Set the container for the Registers component.
	* @param con the container sent by the main program.
	*/
	public void setRegistersContainer(Container con){
		regs.setContainer(con);
	}
	
	/**Set the container for the Statistics component.
	* @param con the container sent by the main program.
	*/
	public void setStatisticsContainer(Container con){
		stats.setContainer(con);
	}
	
	/**Set the container for the Pipeline component.
	* @param con the container sent by the main program.
	*/
	public void setPipelineContainer(Container con){
		pipe.setContainer(con);
	}
	
	/**Set the container for the Data component.
	* @param con the container sent by the main program.
	*/
	public void setDataContainer(Container con){
		data.setContainer(con);
	}
	
	/**Set the container for the Code component.
	* @param con the container sent by the main program.
	*/
	public void setCodeContainer(Container con){
		code.setContainer(con);
	}
	
	
	/**
	* This method call the six component's update methods.
	*/
	public void updateComponents(){
        if(!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        for(GUIComponent c : components)
                            c.update();
                    }
                });
            } catch (InterruptedException e) {
                logger.info("InterruptedException in GUIFrontend");
            } catch (java.lang.reflect.InvocationTargetException e) {
                logger.info("InvocationTargetException in GUIFrontend");
            }
        }
        else {
            for(GUIComponent c : components)
                c.update();
        }
	}

	/**
	* This method call the six components' updateLanguageStrings methods.
	*/
    public void updateLanguageStrings() {
        for(GUIComponent c : components)
            c.updateLanguageStrings();
    }
	
	/**
	* This method call the six component's draw methods.
	*/
	public void represent(){
        if(!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        for(GUIComponent c : components)
                            c.draw();
                    }
                });
            } catch (InterruptedException e) {
                logger.info("InterruptedException in GUIFrontend");
            } catch (java.lang.reflect.InvocationTargetException e) {
                logger.info("InvocationTargetException in GUIFrontend");
            }
        }
        else {
            for(GUIComponent c : components)
                c.draw();
        }
	}

    /**
     * Static utility method that saves the column width of a table, redraws
     * its header labels and then restores the widths
     */
    public static void updateColumnHeaderNames(JTable table) {
        // We need to save and restore the column widths, because the
        // createDeafultColumnsFromModel(), the method that we use to refresh
        // the column names, restores the default width.
        TableColumnModel tcm = table.getColumnModel();
        int columnCount = tcm.getColumnCount();
        int[] widths = new int[columnCount];

        for(int i = 0; i < columnCount; ++i) {
            widths[i] = tcm.getColumn(i).getWidth();
        }

        // This will update the table header names if the getColumnName method
        // is dynamic, i.e., it queries the locale before returning the label.
        table.createDefaultColumnsFromModel();

        for(int i = 0; i < columnCount; ++i) {
            tcm.getColumn(i).setPreferredWidth(widths[i]);
        }
    }
	
	public static void main(String []arg){
		JFrame f = new JFrame("EduMIPS64");
		
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JInternalFrame frame1=new JInternalFrame("Pipeline",true,true,true,true);
		GUIFrontend front=new GUIFrontend();
		
		
		
		frame1.setVisible(true);
		frame1.setBounds(0,0,590,670);
		f.setBounds(0,0,600,710);
		f.setVisible(true);
		
		Container comp=frame1.getContentPane();
		front.setPipelineContainer(comp);
				
		f.getLayeredPane().add(frame1); //LE JInternalFrame VANNO AGGIUNTE NO ALLA FRAME PRINCIPALE, MA AD UNA JDesktopPane (che � figlia delle JLayeredPane)
		//for(int J=0;J<1000000000;J++);
		
		//front.prova();
		//for(int J=0;J<500000000;J++);
		
	}
}
