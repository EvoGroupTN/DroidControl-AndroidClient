package evo.droidcontrol;
public class Command {
	 
	private int id;
	private String isbn;
	private String titre;
 
	public Command(){}
 
	public Command(String isbn, String titre){
		this.isbn = isbn;
		this.titre = titre;
	}
 
	public Command(int id2, String isbn2, String titre2) {
		this.id = id2;
		this.isbn = isbn2;
		this.titre = titre2;
	}

	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getIsbn() {
		return isbn;
	}
 
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
 
	public String getTitre() {
		return titre;
	}
 
	public void setTitre(String titre) {
		this.titre = titre;
	}
 
	public String toString(){
		return "ID : "+id+"\nNameCMD : "+isbn+"\nValCMD : "+titre;
	}
}