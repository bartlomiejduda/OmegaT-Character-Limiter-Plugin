package org.omegat.plugins.characterlimiter;

import org.omegat.core.Core;
import org.omegat.util.OStrings;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginInit {
    public static CharacterLimiterPlugin plugin;
    public static void loadPlugins() {

        String plugin_name = "CharacterLimiter";

        try {
            String requiredVersion = "6.0.0";
            String requiredUpdate = "0";
            try {
                Class<?> clazz = Class.forName("org.omegat.util.VersionChecker");
                Method compareVersions = clazz.getMethod("compareVersions", String.class, String.class, String.class, String.class);
                if ((int)compareVersions.invoke(clazz, OStrings.VERSION, OStrings.UPDATE, requiredVersion, requiredUpdate) < 0) {
                    Core.pluginLoadingError("Plugin " + plugin_name + " cannot be loaded because OmegaT Version "+OStrings.VERSION+" is lower than required version "+requiredVersion);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                     InvocationTargetException e) {
                Core.pluginLoadingError("Plugin " + plugin_name + " cannot be loaded because this OmegaT version is not supported");
            }

            // plugin load logic
            plugin = new CharacterLimiterPlugin();

        } catch(Throwable ex) {
            Core.pluginLoadingError("Plugin " + plugin_name + " cannot be loaded because this version of OmegaT is not supported");
        }

    }

    public static void unloadPlugins() {
        plugin = null;
    }
}
