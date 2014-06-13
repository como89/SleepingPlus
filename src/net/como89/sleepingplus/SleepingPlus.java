package net.como89.sleepingplus;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import net.como89.sleepingplus.data.Effect;
import net.como89.sleepingplus.data.ManageData;
import net.como89.sleepingplus.data.MsgLang;
import net.como89.sleepingplus.event.ChairEvent;
import net.como89.sleepingplus.event.EntityEvent;
import net.como89.sleepingplus.event.PlayerEvent;
import net.como89.sleepingplus.nms.M_1_5;
import net.como89.sleepingplus.nms.M_1_6;
import net.como89.sleepingplus.nms.M_1_7_R1;
import net.como89.sleepingplus.nms.M_1_7_R2;
import net.como89.sleepingplus.nms.M_1_7_R3;
import net.como89.sleepingplus.nms.NMSCLASS;
import net.como89.sleepingplus.task.TaskQuitPlayer;
import net.como89.sleepingplus.task.TaskSitOnChair;
import net.como89.sleepingplus.task.TaskSleep;
import net.como89.sleepingplus.task.TaskTimeNoSleep;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author como89
 * #French - Cette classe est la classe principale du plugin. Elle gère la config, enregistre l'event pour le joueur et celui de la commande /sp.
 * #English - This class is the main class of the plugin. It manages the config, records the event for the player and the command /sp.
 */
@SuppressWarnings("deprecation")
public class SleepingPlus extends JavaPlugin{

	private PluginDescriptionFile pdFile;
	private FileConfiguration customMsg;
	private File fileMsg;
	private Plugin vault;
	private Logger log;
	
	public static Permission perm;
	
	private String listeEffet;
	private static String language;
	
	private ManageData manData;
	
	private long delaisTime;
	private boolean delais;
	private boolean permissions;
	private boolean useXpBar;
	private boolean activateFatigue;
	private boolean activateBedAtDay;
	
	private int timeNoSleep;
	private long timeExitServer;
	private String[] listWorld;
	
	private int timeInBed;
	private int timeOnChair;
	private int nbFatigueRate;
	private int nbRateWithDeath;
	
	public boolean isActiveFatigue()
	{
		return activateFatigue;
	}
	
	public boolean isActiveBedAtDay()
	{
		return activateBedAtDay;
	}
	
	public boolean isDelais()
	{
		return delais;
	}
	
	public boolean isXpBar()
	{
		return useXpBar;
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
	
	public int getNbRatWithDeath()
	{
		return nbRateWithDeath;
	}
	
	public int getTimeInBed()
	{
		return timeInBed;
	}
	
	public int getTimeOnChair(){
		return timeOnChair;
	}
	
	public int getNbFatigueRate(){
		return nbFatigueRate;
	}
	
	public static String getLangage()
	{
		return language;
	}
	
	public boolean isPermit()
	{
		return permissions;
	}
	
	public String[] getListWorlds(){
		return listWorld;
	}
	
	@Override
	public void onEnable()
	{
		File dossier = new File("plugins/SleepingPlus/DataPlayer/");
		dossier.mkdirs();
		log = getServer().getLogger();
		pdFile = getDescription();
		vault = getServer().getPluginManager().getPlugin("Vault");
		manData = new ManageData(this);
		this.saveDefaultConfig();
		loadConfig();
		for(World world : Bukkit.getWorlds()){
			if(listWorld != null){
				for(String worldName : listWorld){
					if(world.getName().equals(worldName)){
						addWorld(world);
					}
				}
			}
			else{
				addWorld(world);
			}
		}
		NMSCLASS netminecraftclass = loadMinecraftClass();
		if(netminecraftclass == null){
			logWarning("This version of craftbukkit is not compatible with SleepingPlus!");
			logWarning("You will not be able to use the bed at day option.");
			activateBedAtDay = false;
		}
		if(isPermit())
		{
			if(isVault())
			{
				if(isPermissions())
				{
					logInfo("Link with vault succesfully.");
				}
				else
				{
				logWarning("You need a permission plugin compatible with vault.");
				this.setEnabled(false);
				return;
				}
			}
			else
			{
				logWarning("You need vault.");
				this.setEnabled(false);
				return;
			}
		}
		String [] lignes = loadMsg();
		MsgLang.initialiseMsg(lignes);
		getServer().getPluginManager().registerEvents(new PlayerEvent(this,netminecraftclass,manData), this);
		getServer().getPluginManager().registerEvents(new EntityEvent(this), this);
		
		getCommand("spp").setExecutor(new Commands(this,manData));
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TaskTimeNoSleep(manData), 20, 20 * getTimeNoSleep());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TaskSleep(manData),20,20 * getTimeInBed());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TaskQuitPlayer(manData),20, 20 * getTimeExitServer());
		
		if(getServer().getPluginManager().getPlugin("Chairs") != null){
		getServer().getPluginManager().registerEvents(new ChairEvent(manData), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TaskSitOnChair(manData),20, 20 * getTimeOnChair());
		logInfo("Link to chairs complete!");
		}
		
		logInfo("Author : " + pdFile.getAuthors());
		logInfo("Plugin enable");
	}
	
	private void addWorld(World world){
		File dossierWorld = new File("plugins/SleepingPlus/DataPlayer/"+world.getName()+"/");
		dossierWorld.mkdirs();
		manData.addWorld(world.getName());
	}
	
	private NMSCLASS loadMinecraftClass(){
		String craftVersion = Bukkit.getVersion();
		String[] craftVersionTab = craftVersion.split(":");
		craftVersion = craftVersionTab[1];
		if(craftVersion.contains("1.5.2")){
			return new M_1_5();
		}
		if(craftVersion.contains("1.6.4")){
			return new M_1_6();
		}
		if(craftVersion.contains("1.7.2")){
			return new M_1_7_R1();
		}
		if(craftVersion.contains("1.7.5")){
			return new M_1_7_R2();
		}
		if(craftVersion.contains("1.7.9")){
			return new M_1_7_R3();
		}
		return null;
	}
	
	private String[] loadMsg(){
		if(fileMsg == null){
			fileMsg = new File("plugins/SleepingPlus/msg.yml");
		}
		customMsg = YamlConfiguration.loadConfiguration(fileMsg);
		
		 if (!fileMsg.exists()) {            
	         this.saveResource("msg.yml", false);
	     	InputStream defCustomMsg = this.getResource("msg.yml");
			customMsg = YamlConfiguration.loadConfiguration(defCustomMsg);
	     }
		 
		 String [] lignes = new String[7];
		 String lang = language.toLowerCase();
		 lignes[0] = customMsg.getString(lang+".ACTIVATE_PLUGIN");
		 lignes[1] = customMsg.getString(lang+".DISABLED_PLUGIN");
		 lignes[2] = customMsg.getString(lang+".NO_PERMISSION");
		 lignes[3] = customMsg.getString(lang+".CONFIG_RELOAD");
		 lignes[4] = customMsg.getString(lang+".LESS_TIRED");
		 lignes[5] = customMsg.getString(lang+".NUMBER_FATIGUE_POINT_YOU");
		 lignes[6] = customMsg.getString(lang+".NUMBER_FATIGUE_POINT_OTHER");
		 
		return lignes;
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
		return vault != null;
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
		nbFatigueRate = this.getConfig().getInt("nbFatigueRate");
		timeInBed = this.getConfig().getInt("timeInBed");
		timeOnChair = this.getConfig().getInt("timeOnChair");
		nbRateWithDeath = this.getConfig().getInt("nbRateWithDeath");
		useXpBar = this.getConfig().getBoolean("useXpBar");
		activateFatigue = this.getConfig().getBoolean("activateFatigueOnConnect");
		activateBedAtDay = this.getConfig().getBoolean("activateBedAtDay");
		String worlds = this.getConfig().getString("listworld");
		if(!worlds.equals("null")){
		listWorld = worlds.split(":");
		}
		manData.clearEffect();
		loadEffect();
	}
	
	private void loadEffect()
	{
		String [] tabEffet = listeEffet.split("/"); 
		for(String effetString : tabEffet)
		{
			String [] lignes = effetString.split(",");
			Effect effet = new Effect(lignes[0],Integer.parseInt(lignes[1]),Integer.parseInt(lignes[2]),Integer.parseInt(lignes[3]));
			manData.addEffect(effet);
		}
	}
	
	private long convertMinutesInSecond(int minutes)
	{
		return (minutes*60);
	}
}
