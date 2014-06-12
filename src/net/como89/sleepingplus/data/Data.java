package net.como89.sleepingplus.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author como89
 * #French - Cette classe est une classe de regroupement des données en ArrayList.
 * #English - This class is a grouping of data in ArrayList.
 */
public class Data {

	private static HashMap<String,ArrayList<SleepPlayer>> listePlayer = new HashMap<String,ArrayList<SleepPlayer>>();
	private static ArrayList<Effect> listeEffect = new ArrayList<Effect>();
	
	static HashMap<String,ArrayList<SleepPlayer>> getListePlayer()
	{
		return listePlayer;
	}
	
	static ArrayList<Effect> getListeEffect()
	{
		return listeEffect;
	}
}
