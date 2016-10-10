package com.bino.tracker.vo;

public class Driver {
	private String id;
	private String photo;
	private String name;
	private String phone;
	private Integer currentScore;
	private Integer workHour;
	private String violentRecord;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(Integer currentScore) {
		this.currentScore = currentScore;
	}

	public Integer getWorkHour() {
		return workHour;
	}

	public void setWorkHour(Integer workHour) {
		this.workHour = workHour;
	}

	public String getViolentRecord() {
		return violentRecord;
	}

	public void setViolentRecord(String violentRecord) {
		this.violentRecord = violentRecord;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
