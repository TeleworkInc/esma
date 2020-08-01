package gr.ntua.cslab.algorithms;

import gr.ntua.cslab.metrics.SexEqualnessCost;
import java.util.Random;

public class ESMA extends AbstractSMA{

	private Random random = new Random();
	
	public ESMA() {}

	public ESMA(int seed) {
		this.random = new Random(seed);
	}

	@Override
	protected boolean getTerminationCondition() {
		return groupA.hasUnhappyPeople() || groupB.hasUnhappyPeople();
	}

	@Override
	protected boolean nextProposalTurn() {
		return random.nextBoolean()
			? invertPriorRoles().getProposalChoice()
			: computeEqualityCost().getProposalChoice();
	}

	private ESMA computeEqualityCost() {
		final SexEqualnessCost c = new SexEqualnessCost(groupA, groupB);
		final Boolean nonzeroCost = c.get() > 0;
		
		computedLastCycle = true;
		proposalChoice = (nonzeroCost || !groupB.hasUnhappyPeople()) && groupA.hasUnhappyPeople();
		return this;
	}

	private ESMA invertPriorRoles() {
		computedLastCycle = false;
		proposalChoice = !proposalChoice;
		return this;
	}
	
	public static void main(String[] args) {
		AbstractSMA.runAlgorithm(ESMA.class, args);
	}

}
