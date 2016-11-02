package com.bino.tracker.vo;

public class Driver {
	private String id;
	private String photo;
	private String name;
	private String phone;
	private Integer currentScore;
	private Integer workHour;
	private String violentRecord;
	private Integer violentSpeeding;
	private Integer violentHarshAcceleration;
	private Integer violentHarshCornering;
	private Integer violentHarshBraking;
	private Integer violentTotal;

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

	public Integer getViolentSpeeding() {
		return violentSpeeding;
	}

	public void setViolentSpeeding(Integer violentSpeeding) {
		this.violentSpeeding = violentSpeeding;
	}

	public Integer getViolentHarshAcceleration() {
		return violentHarshAcceleration;
	}

	public void setViolentHarshAcceleration(Integer violentHarshAcceleration) {
		this.violentHarshAcceleration = violentHarshAcceleration;
	}

	public Integer getViolentHarshCornering() {
		return violentHarshCornering;
	}

	public void setViolentHarshCornering(Integer violentHarshCornering) {
		this.violentHarshCornering = violentHarshCornering;
	}

	public Integer getViolentHarshBraking() {
		return violentHarshBraking;
	}

	public void setViolentHarshBraking(Integer violentHarshBraking) {
		this.violentHarshBraking = violentHarshBraking;
	}

	public Integer getViolentTotal() {
		return violentTotal;
	}

	public void setViolentTotal(Integer violentTotal) {
		this.violentTotal = violentTotal;
	}
}
