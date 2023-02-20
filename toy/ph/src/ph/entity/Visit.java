package ph.entity;

public class Visit {
	private int id;//Ö÷¼ü
	private int petId;
	private int vetId;
	private String vetname;
	private String visitdate;
	private String description;
	private String treatment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public int getVetId() {
		return vetId;
	}
	public void setVetId(int vetId) {
		this.vetId = vetId;
	}
	public String getVetname() {
		return vetname;
	}
	public void setVetname(String vetname) {
		this.vetname = vetname;
	}
	public String getVisitdate() {
		return visitdate;
	}
	public void setVisitdate(String visitdate) {
		this.visitdate = visitdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
}
