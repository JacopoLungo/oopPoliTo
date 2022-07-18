package libraryMgmt;

import java.time.LocalDate;

public class Loan {
	Volume volume;
//	int volumeIndex = volume.volumeIndex;
	LocalDate dueDate;
	LocalDate returnDate = null;
	int loanIndex;
	LoanState state;
	User user;
	public Loan(Volume volume, LocalDate dueDate, int loanIndex, User user) {
		this.volume = volume;
		this.dueDate = dueDate;
		this.loanIndex = loanIndex;
		this.user = user;
	}
	void updateState(LocalDate ld) {
		if (ld.isAfter(dueDate) && returnDate == null) {
			state = LoanState.OVERDUE;
		}
		
		if (returnDate != null) {
			state = LoanState.CLOSED;

		}
	}
	
	
	String getInfo(LocalDate ld) {
		updateState(ld);
		String stateStr = state.toString().toLowerCase();
		String data = dueDate.toString() ;
		return user.name+":"+loanIndex+":"+volume.volumeIndex+":"+ data + ":" +stateStr;
	}
	

}
