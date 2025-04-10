package moe.orangemc.osu.coldfish;

import moe.orangemc.osu.al1s.console.api.plugin.PluginDescriptor;
import moe.orangemc.osu.al1s.console.api.plugin.PluginManager;
import moe.orangemc.osu.al1s.console.api.plugin.jvm.JvmPlugin;
import moe.orangemc.osu.al1s.inject.api.Inject;
import moe.orangemc.osu.al1s.inject.api.Injector;
import moe.orangemc.osu.coldfish.tournament.ColdfishRoomManager;

public class ColdFishPlugin extends JvmPlugin {
    @Inject
    private Injector injector;

    protected ColdFishPlugin(PluginDescriptor descriptor, PluginManager pluginManager) {
        super(descriptor, pluginManager);
    }

    @Override
    public void onEnable() {
        injector.getCurrentContext().registerModule(new ColdfishRoomManager.Provider());
    }
}
