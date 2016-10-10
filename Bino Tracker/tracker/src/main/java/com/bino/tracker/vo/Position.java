package com.bino.tracker.vo;

import java.util.Date;

import com.bino.tracker.util.ClassUtil;

public class Position {
	private Long id;
	private String unitId;
	private String prefix;
	private Date gpstime;
	private Date rtctime;
	private Date pstime;
	private Double longitude;
	private Double latitude;
	private Integer heading;
	private Integer reportId;
	private Integer odometer;
	private Integer hdop;
	private Integer inputStatus;
	private Integer speed;
	private Integer outputStatus;
	private Integer analog;
	private Integer driverId;
	private String temperature1;
	private String temperature2;
	private String text = "";
	private Integer extraGsm;
	private Integer extraGps;
	private Boolean extraEngineOn;
	private Integer extraSpeedLevel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Date getGpstime() {
		return gpstime;
	}

	public void setGpstime(Date gpstime) {
		this.gpstime = gpstime;
	}

	public Date getRtctime() {
		return rtctime;
	}

	public void setRtctime(Date rtctime) {
		this.rtctime = rtctime;
	}

	public Date getPstime() {
		return pstime;
	}

	public void setPstime(Date pstime) {
		this.pstime = pstime;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getHeading() {
		return heading;
	}

	public void setHeading(Integer heading) {
		this.heading = heading;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getOdometer() {
		return odometer;
	}

	public void setOdometer(Integer odometer) {
		this.odometer = odometer;
	}

	public Integer getHdop() {
		return hdop;
	}

	public void setHdop(Integer hdop) {
		this.hdop = hdop;
	}

	public Integer getInputStatus() {
		return inputStatus;
	}

	public void setInputStatus(Integer inputStatus) {
		this.inputStatus = inputStatus;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getOutputStatus() {
		return outputStatus;
	}

	public void setOutputStatus(Integer outputStatus) {
		this.outputStatus = outputStatus;
	}

	public Integer getAnalog() {
		return analog;
	}

	public void setAnalog(Integer analog) {
		this.analog = analog;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getTemperature1() {
		return temperature1;
	}

	public void setTemperature1(String temperature1) {
		this.temperature1 = temperature1;
	}

	public String getTemperature2() {
		return temperature2;
	}

	public void setTemperature2(String temperature2) {
		this.temperature2 = temperature2;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return ClassUtil.toString(this);
	}

	public Integer getExtraGsm() {
		return extraGsm;
	}

	public void setExtraGsm(Integer extraGsm) {
		this.extraGsm = extraGsm;
	}

	public Integer getExtraGps() {
		return extraGps;
	}

	public void setExtraGps(Integer extraGps) {
		this.extraGps = extraGps;
	}

	public Boolean getExtraEngineOn() {
		return extraEngineOn;
	}

	public void setExtraEngineOn(Boolean extraEngineOn) {
		this.extraEngineOn = extraEngineOn;
	}

	public Integer getExtraSpeedLevel() {
		return extraSpeedLevel;
	}

	public void setExtraSpeedLevel(Integer extraSpeedLevel) {
		this.extraSpeedLevel = extraSpeedLevel;
	}
}
