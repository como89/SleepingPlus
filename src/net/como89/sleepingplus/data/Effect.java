package net.como89.sleepingplus.data;

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
