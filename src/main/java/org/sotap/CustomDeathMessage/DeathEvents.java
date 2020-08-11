package org.sotap.CustomDeathMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.sotap.CustomDeathMessage.Utils.Files;

public final class DeathEvents implements Listener {
    
    public DeathEvents(CustomDeathMessage plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Entity en = e.getEntity();
        EntityDamageEvent ede = en.getLastDamageCause();
        String custom = Files.messages.getString(ede.getCause().toString());
        if (custom != null) {
            e.setDeathMessage(custom);
        }
    }
}