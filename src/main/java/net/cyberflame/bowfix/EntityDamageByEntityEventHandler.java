package net.cyberflame.bowfix;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class EntityDamageByEntityEventHandler implements Listener {
  private Main bowFix;
  
  public EntityDamageByEntityEventHandler(Main bowFix) {
    this.bowFix = bowFix;
  }
  
  @EventHandler
  public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
    if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
      Player p = (Player)e.getEntity();
      Projectile projectile = (Projectile)e.getDamager();
      if (projectile.getType() == EntityType.ARROW && projectile.getShooter() instanceof Player && ((Player)projectile.getShooter()).getUniqueId().equals(p.getUniqueId())) {
        ItemStack item = p.getItemInHand();
        if (this.bowFix.getConfig().getBoolean("settings.punch-only", false) && !item.containsEnchantment(Enchantment.ARROW_KNOCKBACK)) {
          return;
        }
        if (this.bowFix.getConfig().getBoolean("settings.punch-only", true) && item.containsEnchantment(Enchantment.ARROW_KNOCKBACK)) {
          int enchLevel = item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
          if (enchLevel != 2) {
            return;
          }
        } 
        
        if (e.getEntity().getWorld().getName().startsWith("PlayerWarp") && 
          Zone.contains(e.getEntity().getLocation(), 21, 27, -6, 0)) {
          e.setCancelled(true);
          sendMessage((CommandSender)e.getEntity(), "&cYou cannot use this in a safezone.");
          
          return;
        } 
        
        e.setDamage(1.0D);
        this.bowFix.getServer().getScheduler().runTaskLater((Plugin)this.bowFix, () -> {
              Vector velocity = paramPlayer.getEyeLocation().getDirection().multiply(this.bowFix.getConfig().getDouble("settings.velocity-multiplier", 2.5D));
              velocity.setY(0.33D);
              paramPlayer.setVelocity(velocity);
            }, 0L);
      } 
    } 
  }
  
  public static String replace(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
  
  public static void sendMessage(CommandSender sender, String message) {
    sender.sendMessage(replace(message));
  }
}
