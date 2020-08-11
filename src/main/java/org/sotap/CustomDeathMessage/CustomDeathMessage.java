package org.sotap.CustomDeathMessage;

import org.bukkit.plugin.java.JavaPlugin;
import org.sotap.CustomDeathMessage.Utils.Files;
import org.sotap.CustomDeathMessage.Utils.LogUtil;

public class CustomDeathMessage extends JavaPlugin {
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Files.init(this);
        LogUtil.init(this);
        new DeathEvents(this);
        LogUtil.success("插件已&a启用&r。");
    }

    @Override
    public void onDisable() {
        LogUtil.success("插件已&c禁用&r。");
    }
}