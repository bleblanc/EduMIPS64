package edumips64.core;
import edumips64.core.*;
import edumips64.utils.*;

/* TODO: need to call updateFSM() and updateLastBranch() after the outcome is known, from
 * CPU.java, also call PC.getValue() (use getBinString() instead?)
 */

public class Predictor {
	private final int rows = 4096;
	private final int cols = 2;
	private enum State {N0, N1, T0, T1}
	State[][] table = new State[rows][cols];
	boolean lastBranch = false;
	
	public Predictor() {
		initializeTable(table);
	}
	
	private void initializeTable(State[][] table) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				table[i][j] =  State.N0;
			}
		}		
	}
	
	public boolean makePrediction(long instrAddr) {	
		
		int row = (int) instrAddr % 4096;
		int col = selectColumn(lastBranch);
		
		State state = table[row][col];
		
		if (state == State.N0 || state == State.N1) {
			return false;
		} else {
			return true;
		}
	}	
	
	//select the state column based on the last branch
	private int selectColumn(boolean lastBranch) {
		if (lastBranch) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private void updateFSM(State state, boolean outcome) {
		switch (state) {
			case N0:
				if (outcome) {
					state = State.N1;
				} else {
					state = State.N0;
				}
			case N1:
				if (outcome) {
					state = State.T0;
				} else {
					state = State.N0;
				}
			case T0:
				if (outcome) {
					state = State.T1;
				} else {
					state = State.N1;
				}
			case T1:
				if (outcome) {
					state = State.T1;
				} else {
					state = State.T0;
				}					
		}
	}
	
	private void updateLastBranch(boolean outcome) {
		lastBranch = outcome;
	}
}
