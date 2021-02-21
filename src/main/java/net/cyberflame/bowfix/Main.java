package net.cyberflame.bowfix;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public void onEnable() {
    saveResource("config.yml", false);
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new EntityDamageByEntityEventHandler(this), (Plugin)this);
  }
}
