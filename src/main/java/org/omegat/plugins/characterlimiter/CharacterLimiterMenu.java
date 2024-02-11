package org.omegat.plugins.characterlimiter;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

import org.omegat.core.Core;
import org.omegat.core.CoreEvents;
import org.omegat.core.events.IApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterLimiterMenu {

	private static final Logger logger = LoggerFactory.getLogger(CharacterLimiterMenu.class);
	private final JCheckBoxMenuItem enable_limiter_menu_item;

	boolean is_limiter_enabled() {
		return enable_limiter_menu_item.isSelected();
	}
	
	void setSelected(boolean enabled) {
		enable_limiter_menu_item.setSelected(enabled);
	}
	
	void setEnabled(boolean enabled) {
		enable_limiter_menu_item.setEnabled(enabled);
	}

	public CharacterLimiterMenu(CharacterLimiterPlugin character_limiter) {
		this.enable_limiter_menu_item = new JCheckBoxMenuItem(CharacterLimiterPlugin.getLocalizedString("MENU_ITEM_ENABLE"));
		this.enable_limiter_menu_item.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (enable_limiter_menu_item.isSelected()) {
					character_limiter.start();
				} else {
					character_limiter.disable();
				}
			}
		});
		this.enable_limiter_menu_item.setName("chars_limiter_enabler");
		this.enable_limiter_menu_item.setSelected(false);
		this.enable_limiter_menu_item.setEnabled(false);

		JMenuItem view_config_menu_item = new JMenuItem(CharacterLimiterPlugin.getLocalizedString("MENU_ITEM_VIEW_CONFIG"));
		view_config_menu_item.setName("limiter_view_config");
		view_config_menu_item.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PluginConfigDialog(Core.getMainWindow().getApplicationFrame(), character_limiter.get_config()).setVisible(true);

			}
		});

		CoreEvents.registerApplicationEventListener(new IApplicationEventListener() {
			@Override
			public void onApplicationStartup() {
				logger.info("Initializing onApplicationStartup");
				Core.getMainWindow().getMainMenu().getToolsMenu().add(enable_limiter_menu_item);
				Core.getMainWindow().getMainMenu().getToolsMenu().add(view_config_menu_item);
			}

			@Override
			public void onApplicationShutdown() {
			}
		});
	}
}