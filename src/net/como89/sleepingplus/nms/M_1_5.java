package net.como89.sleepingplus.nms;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_5_R3.Packet17EntityLocationAction;
import net.minecraft.server.v1_5_R3.Packet18ArmAnimation;

@Deprecated
public class M_1_5 extends NMSCLASS{

	Packet18ArmAnimation pa;
	Packet17EntityLocationAction pea;
	
	public M_1_5(){
		pa = new Packet18ArmAnimation();
		pea = new Packet17EntityLocationAction();
	}
	@Override
	public void inBed(Player player, Location loc) {
		pea = new Packet17EntityLocationAction(((CraftPlayer)player).getHandle(),0,loc.getBlockX(),loc.getBlockY(),loc.getBlockZ());
		 ((CraftPlayer)player).getHandle().playerConnection.sendPacket(pea);
	}

	@Override
	public void outBed(Player player) { 
		 pa.a = player.getEntityId();
		 pa.b = 3;
		 ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pa);
	}

}
