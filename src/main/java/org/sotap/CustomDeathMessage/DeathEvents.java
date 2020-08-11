package org.sotap.CustomDeathMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.sotap.CustomDeathMessage.Utils.Files;

public final class DeathEvents implements Listener {
    
    public DeathEvents(CustomDeathMessage plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Entity en = e.getEntity();
        EntityDamageEvent lastdamageCause = en.getLastDamageCause();
        String custom;
        if (lastdamageCause instanceof EntityDamageByEntityEvent) {
            String key;
            EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) lastdamageCause;
            Entity damager = edbe.getDamager();
            if (damager instanceof Arrow) {
                Arrow arrow = (Arrow) damager;
                ProjectileSource shooter = arrow.getShooter();
                if (shooter instanceof Skeleton) {
                    key = "SKELETON";
                } else if (shooter instanceof Illusioner) {
                    key = "ILLUSIONER_ARROW";
                } else if (shooter instanceof Vindicator) {
                    key = "VINDICATOR_ARROW";
                } else if (shooter instanceof Piglin) {
                    key = "PIGLIN_ARROW";
                } else if (shooter instanceof Player) {
                    key = "PLAYER_ARROW";
                } else {
                    key = "ARROW";
                }
            } else {
                key = damager.getType().toString();
            }
            custom = Files.messages.getString("killed-by." + key);
        } else {
            custom = Files.messages.getString("reason." + lastdamageCause.getCause().toString());
        }
        if (custom != null) {
            e.setDeathMessage(custom);
        }
    }
}