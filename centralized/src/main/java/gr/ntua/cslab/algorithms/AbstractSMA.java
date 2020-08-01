package gr.ntua.cslab.algorithms;

import java.util.Iterator;

import gr.ntua.cslab.containers.Person;
import gr.ntua.cslab.containers.PersonList;
import gr.ntua.cslab.data.DatasetReader;
import gr.ntua.cslab.diagnostics.Diagnostics;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractSMA {

    // Main algorithm hyperparameters
    protected PersonList groupA, groupB;
    protected int stepCounter = 0;
    protected boolean computedLastCycle, proposalChoice = false;

    // Diagnoistics etc.
    protected Diagnostics diagnostics = null;
    protected int stepsDiagnostics;
    protected boolean cycleDetected = false;

    // Misc.
    // protected int randomPickSteps = -1;
    protected long executionTime;

    // Abstract Overrides
    protected abstract boolean getTerminationCondition();

    protected abstract boolean nextProposalTurn();

    // Constructors
    public AbstractSMA() {
    }

    public AbstractSMA(PersonList groupA, PersonList groupB) {
        this.groupA = groupA;
        this.groupB = groupB;
    }

    public AbstractSMA setGroupA(PersonList groupA) {
        this.groupA = groupA;
        return this;
    }

    public AbstractSMA setGroupB(PersonList groupB) {
        this.groupB = groupB;
        return this;
    }

    public Boolean getProposalChoice() {
        return proposalChoice;
    }

    private AbstractSMA printDiagnostics(int stepsPrint) {
        stepsDiagnostics = stepsPrint;
        return this;
    }

    private static void log(String... messages) {
        
        String out = "";
        for (String message : messages)
            out += message + "\t";

        System.out.println(out);
    }

    private static void quit(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public void step() {
        stepCounter++;
        proposeStep(nextProposalTurn() ? groupA : groupB);
    }

    public void run() {

        diagnostics = new Diagnostics(groupA, groupB);
        executionTime = System.currentTimeMillis();

        while (getTerminationCondition())
            step();

        executionTime = System.currentTimeMillis() - executionTime;

        log("stepCounter:", String.valueOf(stepCounter));
        log("executionTime:", String.valueOf(executionTime));
        log("stability:", String.valueOf(diagnostics.resultsIsStable()));

    }

    protected void proposeStep(PersonList proposers) {
        
        final PersonList acceptors = (proposers == groupA ? groupB : groupA);

        // System.out.println("");
        // System.out.println("----- " + stepCounter + " -----");
        // System.out.println("");

        final Iterator<Person> proposersIterator = proposers.getMotivatedToBreakUpIterator();
        final Iterator<Person> acceptorsIterator = acceptors.getIterator();

        // final String proposerLabel = !proposalChoice ? "A" : "B";
        // final String acceptorLabel = proposalChoice ? "A" : "B";

        // System.out.println("PROPOSALS:");
        while (proposersIterator.hasNext()) {
            final Person proposer = proposersIterator.next();
            final int next = proposer.getPreferences().getNext();

            System.out.println(
                // proposerLabel 
                "m"
                + proposer.getId() 
                + "\t"
                + "w"
                // + acceptorLabel 
                + next
            );

            proposer.offer(acceptors.get(next));
        }

        // System.out.println("\nMATCHES:");
        while (acceptorsIterator.hasNext()) {
            Person acceptor = acceptorsIterator.next();

            if (acceptor.reviewOffers())
                System.out.println(
                    // proposerLabel 
                    + acceptor.getCurrentPartner().getId() 
                    + "\t" 
                    // + acceptorLabel
                    + acceptor.getId()
                );
        }
    }

    protected static void runAlgorithm(Class<? extends AbstractSMA> algorithmClass, String[] args) {
        try {

            long elapsedTime = System.currentTimeMillis();
            int stepsPrint = 0;

            if (args.length < 2)
                quit("SYNTAX: AbstractSMA.runAlgorithm(menData, womenData, ?stepsPrint)");

            else if (args.length >= 3)
                stepsPrint = Integer.valueOf(args[2]);

            final DatasetReader menData = new DatasetReader(args[0]);
            final DatasetReader womenData = new DatasetReader(args[1]);

            AbstractSMA algo = algorithmClass.newInstance();
            algo.setGroupA(menData.getPeople())
                .setGroupB(womenData.getPeople())
                .printDiagnostics(stepsPrint)
                .run();

            elapsedTime -= System.currentTimeMillis();
            System.out.println("Total runtime: " + -elapsedTime + "ms");

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AbstractSMA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}