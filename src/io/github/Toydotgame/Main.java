package io.github.Toydotgame;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	@Override
	public void onEnable() {
		System.out.print("[Thisway] Thisway Light is deprecated! Please use the original Thisway for bug support and updates.");
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(sender.isOp()) {
				if(args.length == 1) {
					if(args[0].matches("^[0-9]*$") && args[0] != "0") {
						thisway(sender, args);
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Not a number!");
						return false;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid argument amount!");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You aren't an operator!");
				return true;
			}
		} else {
			System.out.print("[Thisway] Players only!");
			return true;
		}
	}
	
	public void thisway(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		
		float yaw = p.getEyeLocation().getYaw();
		
		if(yaw < 0) {
			yaw += 360;
		}
		if(yaw >= 315 || yaw < 45) {
			DataStorage.f = 0;
		} else if(yaw < 135) {
			DataStorage.f = 1;
		} else if(yaw < 225) {
			DataStorage.f = 2;
		} else if(yaw < 315) {
			DataStorage.f = 3;
		}

		Location loc = p.getLocation();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		if(DataStorage.f == 2) {
			DataStorage.xMod = 0;
			DataStorage.zMod = Integer.parseInt("-" + args[0]);
		} else if(DataStorage.f == 0) {
			DataStorage.xMod = 0;
			DataStorage.zMod = Integer.parseInt(args[0]);
		} else if(DataStorage.f == 3) {
			DataStorage.xMod = Integer.parseInt(args[0]);
			DataStorage.zMod = 0;
		} else if(DataStorage.f == 1) {
			DataStorage.xMod = Integer.parseInt("-" + args[0]);
			DataStorage.zMod = 0;
		}
		
		double modX = x + DataStorage.xMod + 0.5;
		double modZ = z + DataStorage.zMod + 0.5;

		String dim = p.getLocation().getWorld().getName();

		float rot = p.getEyeLocation().getPitch();
		
		Location hLoc = new Location(Bukkit.getWorld(dim), modX, y + 1, modZ, yaw, rot);
		boolean s = Arrays.asList(DataStorage.sb).contains(String.valueOf(hLoc.getBlock().getType()));
		if(s == true) {
			Location nLoc = new Location(Bukkit.getWorld(dim), modX, y, modZ, yaw, rot);
			p.teleport(nLoc);
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid location to be TPed to! Aborting!");
		}
	}
}
