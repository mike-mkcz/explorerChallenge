package com.insano10.explorerchallenge.explorer;

/**
 * Created by mikec on 2/3/15.
 */
public class StateFactory
{
	private static WanderingState WANDERER;
	private static QuittingState QUITTER;

	public static State getWanderingState()
	{
		if (StateFactory.WANDERER == null)
		{
			StateFactory.WANDERER = new WanderingState();
		}
		return StateFactory.WANDERER;
	}

	public static State getQuittingState()
	{
		if (StateFactory.QUITTER == null)
		{
			StateFactory.QUITTER = new QuittingState();
		}
		return StateFactory.QUITTER;
	}
}
