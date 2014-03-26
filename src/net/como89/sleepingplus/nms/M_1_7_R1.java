package net.como89.sleepingplus.nms;

import net.minecraft.server.v1_7_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R1.PacketPlayOutBed;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class M_1_7_R1 extends NMSCLASS{

	private PacketPlayOutAnimation poa;
	private PacketPlayOutBed pob;
	
	public M_1_7_R1(){
		
	}
	
	@Override
	public void inBed(Player player, Location loc) {
		pob = new PacketPlayOutBed(((CraftPlayer)player).getHandle(),loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(pob);
	}

	@Override
	public void outBed(Player player) {
		poa = new PacketPlayOutAnimation(((CraftPlayer)player).getHandle(),2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(poa);
	}

}
