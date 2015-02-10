package com.insano10.explorerchallenge.explorer.state;

/**
 * Created by mikec on 2/3/15.
 */
public class StateFactory
{
	private static OrderedWanderingState ORDERED_WANDERER;
	private static SmartArseOrderedWanderingState SMART_ORDERED_WANDERER;
	private static TremauxWanderingState TREMAUX_WANDERER;
	private static RandomWanderingState RANDOM_WANDERER;
	private static TremauxRandomWanderingState TREMAUX_RANDOM_WANDERER;
	private static QuittingState QUITTER;

	public static State getOrderedWanderingState()
	{
		if (StateFactory.ORDERED_WANDERER == null)
		{
			StateFactory.ORDERED_WANDERER = new OrderedWanderingState();
		}
		return StateFactory.ORDERED_WANDERER;
	}

	public static State getSmartOrderedWanderingState()
	{
		if (StateFactory.SMART_ORDERED_WANDERER == null)
		{
			StateFactory.SMART_ORDERED_WANDERER = new SmartArseOrderedWanderingState();
		}
		return StateFactory.SMART_ORDERED_WANDERER;
	}

	public static State getTremauxWanderingState()
	{
		if (StateFactory.TREMAUX_WANDERER == null)
		{
			StateFactory.TREMAUX_WANDERER = new TremauxWanderingState();
		}
		return StateFactory.TREMAUX_WANDERER;
	}

	public static State getRandomWanderingState()
	{
		if (StateFactory.RANDOM_WANDERER == null)
		{
			StateFactory.RANDOM_WANDERER = new RandomWanderingState();
		}
		return StateFactory.RANDOM_WANDERER;
	}

	public static State getTremauxRandomWanderingState()
	{
		if (StateFactory.TREMAUX_RANDOM_WANDERER == null)
		{
			StateFactory.TREMAUX_RANDOM_WANDERER = new TremauxRandomWanderingState();
		}
		return StateFactory.TREMAUX_RANDOM_WANDERER;
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
