package libraryMgmt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
	String name;
	Integer maxNofBooks;
	long duration;
	List<Loan> loans = new ArrayList<>();
	int totActiveLoan;
	
	public User(String name, int maxNofBooks, int duration) {
		super();
		this.name = name;
		this.maxNofBooks = maxNofBooks;
		this.duration = duration;
	}
	
	boolean hasOverDueLoans(LocalDate ld) {
		for (Loan l : loans) {
			l.updateState(ld);
			if (l.state.equals(LoanState.OVERDUE)) {
				return true;
			}
		}
		return false;
	}
	
}
