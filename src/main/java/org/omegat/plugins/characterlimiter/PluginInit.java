package org.omegat.plugins.characterlimiter;

public class PluginInit {
    public static CharacterLimiterPlugin plugin;
    public static void loadPlugins() {
        plugin = new CharacterLimiterPlugin();
    }

    public static void unloadPlugins() {
        plugin = null;
    }
}
