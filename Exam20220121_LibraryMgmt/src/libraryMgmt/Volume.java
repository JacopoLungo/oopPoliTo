package libraryMgmt;

public class Volume {
	int volumeIndex;
	String[] authors;
	String title;
	boolean available = true;
	
	public Volume(int volumeIndex, String[] authors, String title) {
		super();
		this.volumeIndex = volumeIndex;
		this.authors = authors;
		this.title = title;
	}
	
	

}
