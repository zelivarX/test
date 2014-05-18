package helper.classes;

public class UserInfo {
	String title;
	int socre;
	int id;
	
	public UserInfo(String title, int score, int id) {
		this.title = title;
		this.socre = score;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getSocre() {
		return socre;
	}
	
	public String getTitle() {
		return title;
	}
}
