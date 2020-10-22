/*
 *   Copyright (c) 2005-2012, EFI Analytics & Phil Tobin. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of EFI Analytics.
 *  ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Ideas And Solutions.
 *
 *  EFI Analytics grants the right to distribute this software as
 *  distributed by EFI Analytics and only with a legal agreement
 *  Distribution with any commercial
 *  product or for profit or benefit to any commercial entity must be covered
 *  by and agreement with EFI Analytics or Philip S Tobin.
 *
 *  For questions or additional information contact:
 *  Phil Tobin
 *  EFI Analytics
 *  p_tobin@yahoo.com
 *
 */

package ezdeadtime;

import com.efiAnalytics.plugin.ecu.ControllerAccess;
import com.efiAnalytics.plugin.ecu.ControllerException;
import com.efiAnalytics.plugin.ecu.ControllerParameter;
import com.efiAnalytics.plugin.ecu.ControllerParameterChangeListener;
import com.efiAnalytics.plugin.ecu.servers.ControllerParameterServer;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Philip Tobin
 */
public class ParameterSample extends JPanel implements ControllerParameterChangeListener{

    public final static String NO_PARAMETER = "";
    ControllerParameterServer parameterServer = null;
    String mainConfigName = null;
    String currentParameterName = NO_PARAMETER;

    JComboBox choiceParameter = new JComboBox();

    JTextField deadTimeValue = new JTextField("", 10);

    JTextField incrementValue = new JTextField("", 10);
    JButton incrementButton = new JButton("+");
    JButton decrementButton = new JButton("-");

    public ParameterSample(){
        setBorder(BorderFactory.createTitledBorder("Injector Dead Time"));
        setLayout(new BorderLayout(5,5));

//        btnUpdate.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                sendValue();
//            }
//        });
//        btnUpdate.setMaximumSize(new Dimension(100, 10));

        deadTimeValue.setEnabled(false);

        JPanel incrementPanel = new JPanel();
        JPanel deadTimePanel = new JPanel();

        incrementPanel.add(incrementValue, BorderLayout.WEST);
        incrementPanel.add(incrementButton, BorderLayout.CENTER);
        incrementPanel.add(decrementButton, BorderLayout.EAST);

        deadTimePanel.add(deadTimeValue, BorderLayout.CENTER);

        add(deadTimePanel, BorderLayout.CENTER);
        add(incrementPanel, BorderLayout.CENTER);

        choiceParameter.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    changeParameter();
                }
            }
        });
    }

    public void initialize(ControllerAccess controllerAccess){
        this.parameterServer = controllerAccess.getControllerParameterServer();
        this.mainConfigName = controllerAccess.getEcuConfigurationNames()[0];

        String[] allParameters = parameterServer.getParameterNames(mainConfigName);
        choiceParameter.addItem(NO_PARAMETER);// an empty one at the top
        for(int i=0; i<allParameters.length; i++){
            try {
                ControllerParameter param = parameterServer.getControllerParameter(mainConfigName, allParameters[i]);
                if(param.getParamClass().equals(ControllerParameter.PARAM_CLASS_SCALAR)){
                    // only adding Scalar, array need different UI
                    choiceParameter.addItem(allParameters[i]);
                }
            } catch (ControllerException ex) {
                JOptionPane.showMessageDialog(this, "Error retrieving Paramter: "+ allParameters[i]);
                Logger.getLogger(ParameterSample.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void changeParameter(){
        // don't forget the clean up...
//        unsubscribe();
//        // now onto the new parameter.
//        currentParameterName = choiceParameter.getSelectedItem().toString();
//        if(currentParameterName.equals(NO_PARAMETER)){
//            txtValue.setText("");
//        }else{
//            try {
//                parameterServer.subscribe(mainConfigName, currentParameterName, this);
//                parameterValueChanged(currentParameterName);
//            } catch (ControllerException ex) {
//                Logger.getLogger(ParameterSample.class.getName()).log(Level.SEVERE, null, ex);
//                currentParameterName = NO_PARAMETER;
//                choiceParameter.setSelectedItem(NO_PARAMETER);
//            }
//        }

    }

    private void sendValue(){
//        String paramName = choiceParameter.getSelectedItem().toString();
//        if(!paramName.equals("")){
//            try {
//                double val = Double.parseDouble(txtValue.getText());
//                parameterServer.updateParameter(mainConfigName, paramName, val);
//            } catch (ControllerException ex) {
//                JOptionPane.showMessageDialog(txtValue, "Failed to update value for "+currentParameterName +"\nError:\n"+ex.getMessage());
//                parameterValueChanged(currentParameterName);
//            } catch(NumberFormatException nfe){
//                JOptionPane.showMessageDialog(txtValue, "Invalid Value for "+currentParameterName );
//                // reset it back to current value
//                parameterValueChanged(currentParameterName);
//            }
//        }

    }

    private void unsubscribe(){
        // You would likely want to maintain specific listeners, but for
        // simplisity sake in an example
        parameterServer.unsubscribe(this);
    }

    public void parameterValueChanged(String parameter) {
//        try {
//            ControllerParameter param = parameterServer.getControllerParameter(mainConfigName, parameter);
//            txtValue.setText(Double.toString(param.getScalarValue()));
//        } catch (ControllerException ex) {
//            Logger.getLogger(ParameterSample.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
