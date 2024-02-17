package org.omegat.plugins.characterlimiter;

import java.awt.*;
import javax.swing.*;
import org.omegat.util.gui.StaticUIUtils;
import org.openide.awt.Mnemonics;

public class PluginConfigDialog extends JDialog {

    PluginConfig plugin_config;
	
	public PluginConfigDialog(Frame parent, PluginConfig plugin_config) {
		super(parent, true);
        this.plugin_config = plugin_config;
		initComponents();
		pack();
        StaticUIUtils.fitInScreen(this);
        setLocationRelativeTo(parent);  
	}
	
	private void initComponents() {


        JPanel options_panel = new JPanel();
        JPanel down_button_panel = new JPanel();
        JPanel jPanel1 = new JPanel();
        JButton save_button = new JButton();
        JButton cancel_button = new JButton();
        JCheckBox allow_longer_strings_checkbox = new JCheckBox(CharacterLimiterPlugin.getLocalizedString("ALLOW_LONGER_STRINGS_LABEL"));
        JCheckBox enable_sound_checkbox = new JCheckBox(CharacterLimiterPlugin.getLocalizedString("ENABLE_SOUND_LABEL"));
        JCheckBox enable_global_character_limit = new JCheckBox(CharacterLimiterPlugin.getLocalizedString("ENABLE_GLOBAL_CHARACTER_LIMIT"));
        JLabel global_character_limit_label = new JLabel(CharacterLimiterPlugin.getLocalizedString("GLOBAL_LIMIT_LABEL"));
        JTextField global_character_limit_textfield = new JTextField("NOT SET");


        options_panel.setBackground(Color.BLUE);  // DEBUG
        down_button_panel.setBackground(Color.RED);
        jPanel1.setBackground(Color.ORANGE);


        setTitle(CharacterLimiterPlugin.getLocalizedString("CONFIG_DIALOG_TITLE"));
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
            	saveButtonActionPerformed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));
        options_panel.add(allow_longer_strings_checkbox);
        options_panel.add(enable_sound_checkbox);
        options_panel.add(enable_global_character_limit);
        options_panel.add(global_character_limit_label, BorderLayout.WEST);
        options_panel.add(global_character_limit_textfield, BorderLayout.CENTER);


        

        
        options_panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        options_panel.setLayout(new javax.swing.BoxLayout(options_panel, BoxLayout.PAGE_AXIS));

        down_button_panel.setAlignmentX(0.0F);
        down_button_panel.setLayout(new java.awt.BorderLayout());

        jPanel1.setAlignmentX(0.0F);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        Mnemonics.setLocalizedText(cancel_button, CharacterLimiterPlugin.getLocalizedString("BUTTON_CANCEL"));
        Mnemonics.setLocalizedText(save_button, CharacterLimiterPlugin.getLocalizedString("BUTTON_SAVE"));
        cancel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancel_button);
        jPanel1.add(save_button);

        down_button_panel.add(jPanel1, BorderLayout.EAST);

        options_panel.add(down_button_panel);

        getContentPane().add(options_panel, java.awt.BorderLayout.NORTH);
    }
	

	
	private void saveButtonActionPerformed(AWTEvent evt) {
		setVisible(false);
        dispose();
        plugin_config.save_config();
    }

    private void cancelButtonActionPerformed(AWTEvent evt) {
        setVisible(false);
        dispose();
    }
}