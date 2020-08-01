package gr.ntua.cslab.containers.iterators;

import gr.ntua.cslab.containers.Person;
import gr.ntua.cslab.containers.PersonList;

import java.util.Iterator;

public class PersonIterator implements Iterator<Person>{

	private int id = 1;
	private PersonList list;

	public PersonIterator(PersonList list) {
		this.list = list;
	}

	@Override
	public boolean hasNext() {
		return id <= list.size();
	}

	@Override
	public Person next() {
		return list.get(id++);
	}

	@Override
	public void remove() {
		// does nothing
		
	}
}