package org.omegat.plugins.characterlimiter;

import java.awt.AWTEvent;
import java.awt.Frame;

import javax.swing.*;

import org.omegat.util.OStrings;
import org.omegat.util.gui.StaticUIUtils;
import org.openide.awt.Mnemonics;

public class ConfigDialog extends JDialog {
	
	public ConfigDialog(Frame parent, PluginConfig stats) {
		super(parent, true);
		
		initComponents(stats);
		
		pack();
		
        StaticUIUtils.fitInScreen(this);
        setLocationRelativeTo(parent);  
	}
	
	private void initComponents(PluginConfig stats) {
		JLabel allow_longer_strings_label = new JLabel();
        JCheckBox allow_longer_strings_checkbox = new JCheckBox();

		JLabel enable_sound_label = new JLabel();
        JCheckBox enable_sound_checkbox = new JCheckBox();



        JPanel button_panel = new JPanel();
        JPanel jPanel3 = new JPanel();
        JPanel jPanel1 = new JPanel();
        JButton ok_button = new JButton();


        setTitle(CharacterLimiterPlugin.getLocalizedString("CONFIG_DIALOG_TITLE"));
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
            	okButtonActionPerformed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));
        
        Mnemonics.setLocalizedText(allow_longer_strings_label, CharacterLimiterPlugin.getLocalizedString("ALLOW_LONGER_STRINGS_LABEL", "123"));
        button_panel.add(allow_longer_strings_label);
        button_panel.add(allow_longer_strings_checkbox);


        Mnemonics.setLocalizedText(enable_sound_label, CharacterLimiterPlugin.getLocalizedString("ENABLE_SOUND_LABEL", "456"));
        button_panel.add(enable_sound_label);
        button_panel.add(enable_sound_checkbox);


        

        
        button_panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button_panel.setLayout(new javax.swing.BoxLayout(button_panel, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel3.setAlignmentX(0.0F);
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        Mnemonics.setLocalizedText(ok_button, OStrings.getString("BUTTON_OK"));
        ok_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ok_button);

        jPanel3.add(jPanel1, java.awt.BorderLayout.EAST);

        button_panel.add(jPanel3);

        getContentPane().add(button_panel, java.awt.BorderLayout.SOUTH);
    }
	

	
	private void okButtonActionPerformed(AWTEvent evt) {
		setVisible(false);
        dispose();
        // TODO - save config here
    }
}