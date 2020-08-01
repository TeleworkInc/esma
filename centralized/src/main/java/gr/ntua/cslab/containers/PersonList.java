package gr.ntua.cslab.containers;

import gr.ntua.cslab.containers.iterators.FreePersonIterator;
import gr.ntua.cslab.containers.iterators.MotivatedToBreakUpIterator;
import gr.ntua.cslab.containers.iterators.PersonIterator;
import gr.ntua.cslab.data.DatasetReader;

import java.util.Iterator;

/**
 * Class used to hold a list of people.
 * 
 * @author Giannis Giannakopoulos
 *
 */
public class PersonList {

	private Person[] people = null;

	public PersonList(int numOfPeople) {
		allocate(numOfPeople);
	}

	private void allocate(int numOfPeople) {
		people = new Person[numOfPeople];
	}

	public Person get(int id) {		
		return people[id - 1];
	}

	public void add(Person person) {
		people[person.getId() - 1] = person;
	}

	public int size() {
		return people.length;
	}

	public Iterator<Person> getIterator() {
		return new PersonIterator(this);
	}

	public Iterator<Person> getSinglePersonIterator() {
		return new FreePersonIterator(this);
	}

	public Iterator<Person> getMotivatedToBreakUpIterator() {
		return new MotivatedToBreakUpIterator(this);
	}

	public int getNumberOfSingles() {

		int count = 0;
		final Iterator<Person> it = getSinglePersonIterator();

		while (it.hasNext()) {
			it.next();
			count++;
		}

		return count;
	}

	public boolean hasSinglePeople() {
		return getNumberOfSingles() > 0;
	}

	public boolean hasUnhappyPeople() {
		Iterator<Person> it = getMotivatedToBreakUpIterator();
		return it.hasNext();
	}

	public int countPeopleWithCycles() {
		Iterator<Person> it = getIterator();
		int count = 0;
		while (it.hasNext()) {
			Person p = it.next();
			if (p.hasCycle())
				count++;
		}
		return count;
	}

	public int[] getRanksArray() {
		final Iterator<Person> it = getIterator();

		int[] sort = new int[size()];
		int i = 0;

		while (it.hasNext())
			sort[i++] = it.next().getCurrentPartnerRank();

		return sort;
	}

	/**
	 * Returns the number of satisfied people, given a satisfaction threshold. This
	 * threshold is expressed as a rank (refering to the partner): if the rank is
	 * below average
	 * 
	 * @param satisfactionThreshold
	 * @return
	 */
	public int getSatisfiedPeople(int satisfactionThreshold) {
		final Iterator<Person> it = getIterator();
		int count = 0;

		while (it.hasNext())
			if (it.next().getCurrentPartnerRank() < satisfactionThreshold)
				count++;

		return count;
	}

	@Override
	public String toString() {
		String buffer = "[";

		for (int i = 0; i < people.length; i++)
			buffer += people[i].toString() + ", ";

		buffer = buffer.substring(0, buffer.length() - 2) + "]";
		return buffer;
	}

	public static void main(String[] args) {
		final PersonList people = new DatasetReader(args[0]).getPeople();
		Iterator<Person> personIterator;
		System.out.println("List size: " + people.size());

		personIterator = people.getIterator();
		while (personIterator.hasNext())
			System.out.println(personIterator.next());

		people.get(1).propose(people.get(2));
		people.get(3).propose(people.get(2));

		System.out.println("after");
		System.out.println("Singles: \t" + people.getNumberOfSingles());

		personIterator = people.getSinglePersonIterator();
		while (personIterator.hasNext())
			System.out.println(personIterator.next());

		people.get(3).divorce();
		System.out.println("again");

		personIterator = people.getSinglePersonIterator();
		while (personIterator.hasNext())
			System.out.println(personIterator.next());

	}
}