package net.como89.sleepingplus.data;

/**
 * @author como89
 * #French - Cette classe est une classe sur les effets de potion avec ses caractéristiques.
 * #English - This is a class on the effects of the potion with its features.
 */
public class Effect {
	
	private String nomEffet;
	private int nbTemps;
	private int nbFatigueRate;
	
	public Effect(String nomEffet,int nbTemps,int nbFatigueRate)
	{
		this.nomEffet = nomEffet;
		this.nbTemps = nbTemps;
		this.nbFatigueRate = nbFatigueRate;
	}
	
	public String getNom()
	{
		return nomEffet;
	}
	
	public int getNbTemps()
	{
		return nbTemps;
	}
	
	public int getNbFatigueRate()
	{
		return nbFatigueRate;
	}
}
