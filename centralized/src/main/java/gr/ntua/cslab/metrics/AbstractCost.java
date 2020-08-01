package gr.ntua.cslab.metrics;

import java.util.Iterator;

import gr.ntua.cslab.containers.Person;
import gr.ntua.cslab.containers.PersonList;

/**
 * This abstract class will be inherited by all these classes that 
 * implement a metric measuring the stability and the quality of the stable matching
 * algorithms.
 * @author Giannis Giannakopoulos
 *
 */
public abstract class AbstractCost {

	protected PersonList men, women;
	
	public AbstractCost(PersonList men, PersonList women) {
		this.men = men;
		this.women = women;
	}
	
	public abstract double get();

	public double getPercentage(){
		return get() / men.size();
	}
	
	protected static double getRanksSum(PersonList persons){

		final Iterator<Person> iterator = persons.getIterator();
		double sum = 0.0;
		int count = 0;

		while(iterator.hasNext()){

			final Person person = iterator.next();
			final int partnerRank = person.getCurrentPartnerRank();

			if(partnerRank == Integer.MAX_VALUE) continue;

			sum += partnerRank;
			count++;
		}

		return sum / count;
	}
}
