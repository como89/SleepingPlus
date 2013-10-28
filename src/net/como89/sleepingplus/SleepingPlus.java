package net.como89.sleepingplus;

import java.io.File;
import java.util.logging.Logger;

import net.como89.sleepingplus.data.Effect;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.event.PlayerEvent;
import net.como89.sleepingplus.task.TaskTimeNoSleep;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SleepingPlus extends JavaPlugin{

	private PluginDescriptionFile pdFile;
	private PluginDescriptionFile pdfFileVault;
	private Logger log;
	
	public static Permission perm;
	
	private String listeEffet;
	private static String language;
	
//	private long timePluginStart;
//	private long timePluginStop;
	
	private long delaisTime;
	private boolean delais;
	private boolean permissions;
	
	private int timeNoSleep;
	private long timeExitServer;
	
	private long timeInBed;
	private long nbRateWithDeath;
	
//	public long getTimePluginStart()
//	{
//		return timePluginStart;
//	}
//	
//	public long getTimePluginStop()
//	{
//		return timePluginStop;
//	}
	
	public boolean isDelais()
	{
		return delais;
	}
	
	public long getTimeDelais()
	{
		return delaisTime;
	}
	
	public int getTimeNoSleep()
	{
		return timeNoSleep;
	}
	
	public long getTimeExitServer()
	{
		return timeExitServer;
	}
	
	public long getNbRatWithDeath()
	{
		return nbRateWithDeath;
	}
	
	public long getTimeInBed()
	{
		return timeInBed;
	}
	
	public static String getLangage()
	{
		return language;
	}
	
	public boolean isPermit()
	{
		return permissions;
	}
	
	@Override
	public void onEnable()
	{
		File dossier = new File("plugins/SleepingPlus/DataPlayer/");
		dossier.mkdirs();
		log = getServer().getLogger();
		pdFile = getDescription();
		pdfFileVault = getServer().getPluginManager().getPlugin("Vault").getDescription();
		this.saveDefaultConfig();
		loadConfig();
		if(isVault())
		{
			if(isPermissions())
			{
				new ManageData(this);
				getServer().getPluginManager().registerEvents(new PlayerEvent(this), this);
				getCommand("sp").setExecutor(new Commands(this));
				Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TaskTimeNoSleep(), 20, 20);
				logInfo("Link with vault succesfully.");
				logInfo("Author : " + pdFile.getAuthors());
				logInfo("Plugin enable");
				return;
			}
		}
		logWarning("You need Vault with a permission plugin.");
		this.setEnabled(false);
	}
	
	private boolean isPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            perm = permissionProvider.getProvider();
        }
		return perm != null;
	}

	@Override
	public void onDisable()
	{
		logInfo("Plugin disable");
	}
	
	private boolean isVault()
	{
		return pdfFileVault != null;
	}
	
	private void logInfo(String message)
	{
		log.info("[" + pdFile.getName() + "] " + message);
	}
	
	private void logWarning(String message)
	{
		log.warning("[" + pdFile.getName() + "] " + message);
	}
	
	void loadConfig()
	{
		this.reloadConfig();
		language = this.getConfig().getString("language");
		permissions = this.getConfig().getBoolean("permissions");
		listeEffet = this.getConfig().getString("potionsEffect");
		timeNoSleep =  this.getConfig().getInt("timeNoSleep");
		int minute = this.getConfig().getInt("timeExitServer");
		timeExitServer = convertMinutesInSecond(minute);
		timeInBed = this.getConfig().getInt("timeInBed");
		nbRateWithDeath = this.getConfig().getInt("nbRateWithDeath");
		loadEffect();
	}
	
	private void loadEffect()
	{
		String [] tabEffet = listeEffet.split("/"); 
		for(String effetString : tabEffet)
		{
			String [] lignes = effetString.split(",");
			Effect effet = new Effect(lignes[0],Integer.parseInt(lignes[1]),Integer.parseInt(lignes[2]));
			ManageData.addEffect(effet);
		}
	}
	
	private long convertMinutesInSecond(int minutes)
	{
		return (minutes*60);
	}
}
