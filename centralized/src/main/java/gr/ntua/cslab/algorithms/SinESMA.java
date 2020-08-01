/**
 * ESMA algorithm that uses a reproducible form of 
 */
package gr.ntua.cslab.algorithms;

/**
 *
 * @author Giannis Giannakopoulos
 */
public class SinESMA extends AbstractSMA {

    // protected abstract boolean menPropose();
    public SinESMA() {
        super();
        // this.randomPickSteps = Integer.MAX_VALUE;
    }

    
    @Override
    public boolean getTerminationCondition() {
        return (this.groupA.hasUnhappyPeople() || this.groupB.hasUnhappyPeople());
    }

    @Override
    protected boolean nextProposalTurn() {
        // estimates sin(k^log(k))
        return Math.sin(this.stepCounter*this.stepCounter)>0;
    }
    
    public static void main(String[] args) {
        AbstractSMA.runAlgorithm(SinESMA.class, args);
    }
}
