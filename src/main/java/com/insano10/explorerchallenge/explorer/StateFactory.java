package com.insano10.explorerchallenge.explorer;

/**
 * Created by mikec on 2/3/15.
 */
public class StateFactory
{
	private static OrderedWanderingState ORDERED_WANDERER;
	private static RandomWanderingState RANDOM_WANDERER;
	private static QuittingState QUITTER;

	public static State getOrderedWanderingState()
	{
		if (StateFactory.ORDERED_WANDERER == null)
		{
			StateFactory.ORDERED_WANDERER = new OrderedWanderingState();
		}
		return StateFactory.ORDERED_WANDERER;
	}

	public static State getRandomWanderingState()
	{
		if (StateFactory.RANDOM_WANDERER == null)
		{
			StateFactory.RANDOM_WANDERER = new RandomWanderingState();
		}
		return StateFactory.RANDOM_WANDERER;
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