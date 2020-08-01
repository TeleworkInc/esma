package gr.ntua.cslab.containers.iterators;

import gr.ntua.cslab.containers.Person;
import gr.ntua.cslab.containers.PersonList;

import java.util.Iterator;

public class MotivatedToBreakUpIterator implements Iterator<Person> {

	private int id = 1;
	private PersonList people;
	
	public MotivatedToBreakUpIterator(PersonList people) {
		this.people = people;
	}
	
	@Override
	public boolean hasNext() {
		
		while(id <= people.size() && !people.get(id).isMotivatedToBreakUp())
			id++;

		return id <= people.size();
	}

	@Override
	public Person next() {
		return people.get(id++);
	}

	@Override
	public void remove() {
		// does nothing by default
	}

}
