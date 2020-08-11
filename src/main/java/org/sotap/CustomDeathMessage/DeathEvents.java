package org.sotap.CustomDeathMessage;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.sotap.CustomDeathMessage.Utils.Files;
import org.sotap.CustomDeathMessage.Utils.LogUtil;

public final class DeathEvents implements Listener {

    public DeathEvents(CustomDeathMessage plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Entity en = e.getEntity();
        Player p = (Player) en;
        EntityDamageEvent lastdamageCause = en.getLastDamageCause();
        String custom;
        String anotherPlayerName = "";
        String entityName = "";
        if (lastdamageCause instanceof EntityDamageByEntityEvent) {
            String key;
            EntityDamageByEntityEvent byEntity = (EntityDamageByEntityEvent) lastdamageCause;
            Entity damager = byEntity.getDamager();
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
            } else if (damager instanceof ShulkerBullet) {
                key = "SHULKER";
            } else if (damager instanceof Player) {
                key = "PLAYER";
                Player damagerPlayer = (Player) damager;
                anotherPlayerName = damagerPlayer.getName();
            } else {
                key = damager.getType().toString().toUpperCase();
            }
            custom = Files.messages.getString("killed-by." + key);
        } else if (lastdamageCause instanceof EntityDamageByBlockEvent) {
            String key;
            EntityDamageByBlockEvent byBlock = (EntityDamageByBlockEvent) lastdamageCause;
            Block damager = byBlock.getDamager();
            if (damager instanceof Bed) {
                key = "SPECIAL_SETTINGS";
            } else {
                key = lastdamageCause.getCause().toString();
            }
            custom = Files.messages.getString("reason." + key);
        } else {
            custom = Files.messages.getString("reason." + lastdamageCause.getCause().toString());
        }
        if (custom != null) {
            e.setDeathMessage(null);
            LogUtil.death(custom.replace("%playername%", p.getName())
                    .replace("%anotherplayer%", anotherPlayerName)
                    .replace("%entityname%", entityName));
        }
    }
}
