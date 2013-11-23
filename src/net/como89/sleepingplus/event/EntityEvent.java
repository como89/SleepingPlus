package net.como89.sleepingplus.event;

import net.como89.sleepingplus.SleepingPlus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * @author como89
 * #French - Cette classe permet d'écouter les différents évenements des entités.
 * #English - This class allows you to listen to the various events of entity.
 * Events Listen :
 * -EntityDeathEvent
 */

public class EntityEvent implements Listener {

	private SleepingPlus plugin;
	
	public EntityEvent(SleepingPlus plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeathEvent (EntityDeathEvent event)
	{
		if(plugin.isXpBar())
		{
			event.setDroppedExp(0);
		}
	}
}
