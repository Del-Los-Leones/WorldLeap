package cf.urgpa.worldleap;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldLeap extends JavaPlugin implements Listener  {
	@Override
	public void onEnable() {
		WorldCreator wc = new WorldCreator("flat");
		wc.createWorld();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onChat(PlayerCommandPreprocessEvent ev) {
		String cmd = ev.getMessage().split(" ")[0];
		Player p = ev.getPlayer();
		if (cmd.equals("/워프") && p.getWorld().getName().equals("flat")) {
			ev.setCancelled(true);
			p.sendMessage(ChatColor.AQUA + "[MPP] " + ChatColor.WHITE + "이곳에서는 사용하실 수 없는 명령어입니다. 되돌아가려면 "
					+ ChatColor.AQUA + "/월드리프 " + ChatColor.WHITE + "명령어를 이용해주세요.");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equals("월드리프")) {
			if (!(sender instanceof Player))
				return true;
			if (!((Player) sender).getWorld().getName().equals("flat")) {
				World world = getServer().getWorld("flat");
				if (world != null) {
					((Player) sender).teleport(world.getSpawnLocation());
					sender.sendMessage(ChatColor.AQUA + "[MPP] " + ChatColor.WHITE + "평지맵으로 이동되었습니다.");
					((Player) sender).setGameMode(GameMode.CREATIVE);
				}
			} else {
				World world = getServer().getWorld("world");
				if(world != null){
					Location spawn = world.getSpawnLocation();
					if(((Player) sender).getBedSpawnLocation() != null &&
							((Player) sender).getBedSpawnLocation().getWorld().getName().equals("world")){
						spawn = ((Player) sender).getBedSpawnLocation();
					}
					assert spawn != null;
					((Player) sender).teleport(spawn);
					sender.sendMessage(ChatColor.AQUA + "[MPP] " + ChatColor.WHITE + "야생맵으로 이동되었습니다.");
					((Player) sender).setGameMode(GameMode.SURVIVAL);
				}
			}
		}
		return true;
	}
}
