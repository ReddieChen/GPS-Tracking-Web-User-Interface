package com.bino.tracker.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bino.tracker.vo.Driver;
import com.bino.tracker.vo.Poi;
import com.bino.tracker.vo.Position;
import com.bino.tracker.vo.PositionEvent;
import com.bino.tracker.vo.Unit;
import com.bino.tracker.vo.ViolationDetail;

@Service
public class PositionSimulator {
	private Thread thread;
	private boolean running = true;
	private List<Position> positions = new ArrayList<Position>();
	private List<PositionEvent> positionEvents = new ArrayList<PositionEvent>();
	private List<Driver> drivers = new ArrayList<Driver>();
	private List<Unit> units = new ArrayList<Unit>();
	private List<Poi> pois = new ArrayList<Poi>();
	private List<Frame> car1Frames = new ArrayList<Frame>();
	private List<Frame> car2Frames = new ArrayList<Frame>();
	private List<Frame> car3Frames = new ArrayList<Frame>();
	private List<Frame> car4Frames = new ArrayList<Frame>();
	private List<ViolationDetail> violationDetails1 = new ArrayList<ViolationDetail>();
	private List<ViolationDetail> violationDetails2 = new ArrayList<ViolationDetail>();
	private List<ViolationDetail> violationDetails3 = new ArrayList<ViolationDetail>();
	private List<ViolationDetail> violationDetails4 = new ArrayList<ViolationDetail>();
	private List<ViolationDetail> violationDetails5 = new ArrayList<ViolationDetail>();
	private long index = 1;
	private int eventIndex = 0;
	private int frame1Index = 0;
	
	@Autowired
    ServletContext context; 

	public List<Position> getPositions() {
		return positions;
	}

	public List<PositionEvent> getPositionEvents() {
		return positionEvents;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void clearPositionEvents() {
		positionEvents.clear();
	}

	public List<Unit> getUnits() {
		return units;
	}

	public List<Poi> getPois() {
		return pois;
	}

	public List<ViolationDetail> getViolationDetails1() {
		return violationDetails1;
	}

	public List<ViolationDetail> getViolationDetails2() {
		return violationDetails2;
	}

	public List<ViolationDetail> getViolationDetails3() {
		return violationDetails3;
	}

	public List<ViolationDetail> getViolationDetails4() {
		return violationDetails4;
	}

	public List<ViolationDetail> getViolationDetails5() {
		return violationDetails5;
	}

	public void startThread() {
		if (thread == null && running) {
			init();
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (running) {
						try {
							doWork();
							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
			});
			thread.start();
		}
	}

	public void stopThread() {
		running = false;
		if (thread != null) {
			thread.interrupt();
		}
	}

	private void init() {
		initDrivers();
		initUnits();
		initPositions();
		initPois();
		initViolationDetails1();
		initViolationDetails2();
		initViolationDetails3();
		initViolationDetails4();
		initViolationDetails5();
	}

	private void initViolationDetails1() {
		ViolationDetail v = new ViolationDetail();
		v.setDate("2016-11-01 01:17 AM");
		v.setEvent("スピードオーバー");
		v.setLatitude(35.552103);
		v.setLongitude(135.479522);
		v.setLocation("県道21号線");
		violationDetails1.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 02:55 AM");
		v.setEvent("急加速");
		v.setLatitude(33.244872);
		v.setLongitude(131.705530);
		v.setLocation("大分県大分市花江川9-4");
		violationDetails1.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 04:27 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.005285);
		v.setLongitude(131.250106);
		v.setLocation("豊後街道");
		violationDetails1.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 07:47 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.182515);
		v.setLongitude(131.606444);
		v.setLocation("県道682号線");
		violationDetails1.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 08:55 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.220117);
		v.setLongitude(131.597689);
		v.setLocation("大分県大分市永興1665");
		violationDetails1.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:55 AM");
		v.setEvent("急加速");
		v.setLatitude(33.210289);
		v.setLongitude(131.552074);
		v.setLocation("医大バイパス");
		violationDetails1.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 01:55 PM");
		v.setEvent("スピードオーバー");
		v.setLatitude(33.215239);
		v.setLongitude(131.603224);
		v.setLocation("大分県大分市羽屋35-9");
		violationDetails1.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 03:25 PM");
		v.setEvent("急ハンドル");
		v.setLatitude(33.222680);
		v.setLongitude(131.595284);
		v.setLocation("大道バイパス");
		violationDetails1.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:45 PM");
		v.setEvent("急ブレーキ");
		v.setLatitude(34.708785);
		v.setLongitude(134.857064);
		v.setLocation("県道539号線");
		violationDetails1.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:46 PM");
		v.setEvent("急加速");
		v.setLatitude(32.478121);
		v.setLongitude(131.630523);
		v.setLocation("東九州自動車道");
		violationDetails1.add(v);
	}
	
	private void initViolationDetails2() {
		ViolationDetail v = new ViolationDetail();
		v.setDate("2016-11-01 04:27 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.005285);
		v.setLongitude(131.250106);
		v.setLocation("豊後街道");
		violationDetails2.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 07:47 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.182515);
		v.setLongitude(131.606444);
		v.setLocation("県道682号線");
		violationDetails2.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 08:55 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.220117);
		v.setLongitude(131.597689);
		v.setLocation("大分県大分市永興1665");
		violationDetails2.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:55 AM");
		v.setEvent("急加速");
		v.setLatitude(33.210289);
		v.setLongitude(131.552074);
		v.setLocation("医大バイパス");
		violationDetails2.add(v);
	}
	
	private void initViolationDetails3() {
		ViolationDetail v = new ViolationDetail();
		v.setDate("2016-11-01 08:55 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.220117);
		v.setLongitude(131.597689);
		v.setLocation("大分県大分市永興1665");
		violationDetails3.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:55 AM");
		v.setEvent("急加速");
		v.setLatitude(33.210289);
		v.setLongitude(131.552074);
		v.setLocation("医大バイパス");
		violationDetails3.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 01:55 PM");
		v.setEvent("スピードオーバー");
		v.setLatitude(33.215239);
		v.setLongitude(131.603224);
		v.setLocation("大分県大分市羽屋35-9");
		violationDetails3.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 03:25 PM");
		v.setEvent("急ハンドル");
		v.setLatitude(33.222680);
		v.setLongitude(131.595284);
		v.setLocation("大道バイパス");
		violationDetails3.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:45 PM");
		v.setEvent("急ブレーキ");
		v.setLatitude(34.708785);
		v.setLongitude(134.857064);
		v.setLocation("県道539号線");
		violationDetails3.add(v);
		
		v = new ViolationDetail();
		v.setDate("2016-11-01 09:46 PM");
		v.setEvent("急加速");
		v.setLatitude(32.478121);
		v.setLongitude(131.630523);
		v.setLocation("東九州自動車道");
		violationDetails3.add(v);
	}
	
	private void initViolationDetails4() {
		ViolationDetail v = new ViolationDetail();
		v.setDate("2016-11-01 01:17 AM");
		v.setEvent("スピードオーバー");
		v.setLatitude(35.552103);
		v.setLongitude(135.479522);
		v.setLocation("県道21号線");
		violationDetails4.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 02:55 AM");
		v.setEvent("急加速");
		v.setLatitude(33.244872);
		v.setLongitude(131.705530);
		v.setLocation("大分県大分市花江川9-4");
		violationDetails4.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 04:27 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.005285);
		v.setLongitude(131.250106);
		v.setLocation("豊後街道");
		violationDetails4.add(v);
	}
	
	private void initViolationDetails5() {
		ViolationDetail v = new ViolationDetail();
		v.setDate("2016-11-01 02:55 AM");
		v.setEvent("急加速");
		v.setLatitude(33.244872);
		v.setLongitude(131.705530);
		v.setLocation("大分県大分市花江川9-4");
		violationDetails5.add(v);

		v = new ViolationDetail();
		v.setDate("2016-11-01 04:27 AM");
		v.setEvent("急ブレーキ");
		v.setLatitude(33.005285);
		v.setLongitude(131.250106);
		v.setLocation("豊後街道");
		violationDetails5.add(v);
	}

	private void initPositions() {
		Position p = new Position();
		p.setAnalog(0);
		p.setDriverId(0);
		p.setGpstime(new Date());
		p.setHdop(0);
		p.setHeading(0);
		p.setInputStatus(0);
		p.setLatitude(0.0);
		p.setLongitude(0.0);
		p.setOdometer(0);
		p.setOutputStatus(0);
		p.setPrefix("");
		p.setPstime(new Date());
		p.setReportId(0);
		p.setRtctime(new Date());
		p.setSpeed(0);
		p.setTemperature1("");
		p.setTemperature2("");
		p.setText("");
		p.setUnitId("車両01");
		p.setExtraGps(4);
		p.setExtraGsm(20);
		p.setExtraEngineOn(false);
		p.setExtraSpeedLevel(0);
		positions.add(p);
		initCar1Frames();

		p = new Position();
		p.setAnalog(0);
		p.setDriverId(0);
		p.setGpstime(new Date());
		p.setHdop(0);
		p.setHeading(0);
		p.setInputStatus(0);
		p.setLatitude(25.059348);
		p.setLongitude(121.533166);
		p.setOdometer(0);
		p.setOutputStatus(0);
		p.setPrefix("");
		p.setPstime(new Date());
		p.setReportId(0);
		p.setRtctime(new Date());
		p.setSpeed(0);
		p.setTemperature1("");
		p.setTemperature2("");
		p.setText("");
		p.setUnitId("車両02");
		p.setExtraGps(0);
		p.setExtraGsm(0);
		p.setExtraEngineOn(false);
		positions.add(p);
		initCar2Frames();

		p = new Position();
		p.setAnalog(0);
		p.setDriverId(0);
		p.setGpstime(new Date());
		p.setHdop(0);
		p.setHeading(0);
		p.setInputStatus(0);
		p.setLatitude(25.066819);
		p.setLongitude(121.537333);
		p.setOdometer(0);
		p.setOutputStatus(0);
		p.setPrefix("");
		p.setPstime(new Date());
		p.setReportId(0);
		p.setRtctime(new Date());
		p.setSpeed(0);
		p.setTemperature1("");
		p.setTemperature2("");
		p.setText("");
		p.setUnitId("車両03");
		p.setExtraGps(4);
		p.setExtraGsm(0);
		p.setExtraEngineOn(false);
		positions.add(p);

		p = new Position();
		p.setAnalog(0);
		p.setDriverId(0);
		p.setGpstime(new Date());
		p.setHdop(0);
		p.setHeading(0);
		p.setInputStatus(0);
		p.setLatitude(25.071337);
		p.setLongitude(121.530205);
		p.setOdometer(0);
		p.setOutputStatus(0);
		p.setPrefix("");
		p.setPstime(new Date());
		p.setReportId(0);
		p.setRtctime(new Date());
		p.setSpeed(0);
		p.setTemperature1("");
		p.setTemperature2("");
		p.setText("");
		p.setUnitId("車両04");
		p.setExtraGps(4);
		p.setExtraGsm(99);
		p.setExtraEngineOn(false);
		positions.add(p);
	}

	private void initDrivers() {
		Driver d = new Driver();
		d.setId("001");
		d.setName("石田 真一");
		d.setPhone("+886918323232");
		d.setCurrentScore(87);
		d.setWorkHour(207);
		d.setPhoto("driver1.png");
		d.setViolentRecord("C");
		d.setViolentSpeeding(2);
		d.setViolentHarshAcceleration(3);
		d.setViolentHarshBraking(4);
		d.setViolentHarshCornering(1);
		d.setViolentTotal(10);
		drivers.add(d);

		d = new Driver();
		d.setId("002");
		d.setName("坂本 重雄");
		d.setPhone("+886918775255");
		d.setCurrentScore(12);
		d.setWorkHour(223);
		d.setPhoto("driver2.png");
		d.setViolentRecord("A");
		d.setViolentSpeeding(0);
		d.setViolentHarshAcceleration(1);
		d.setViolentHarshBraking(3);
		d.setViolentHarshCornering(0);
		d.setViolentTotal(4);
		drivers.add(d);

		d = new Driver();
		d.setId("003");
		d.setName("齋藤 徳重");
		d.setPhone("+8863819302939");
		d.setCurrentScore(87);
		d.setWorkHour(211);
		d.setPhoto("driver3.png");
		d.setViolentRecord("B");
		d.setViolentSpeeding(1);
		d.setViolentHarshAcceleration(2);
		d.setViolentHarshBraking(2);
		d.setViolentHarshCornering(1);
		d.setViolentTotal(6);
		drivers.add(d);

		d = new Driver();
		d.setId("004");
		d.setName("佐藤 正治");
		d.setPhone("+8860192929333");
		d.setCurrentScore(127);
		d.setWorkHour(212);
		d.setPhoto("driver4.png");
		d.setViolentRecord("A");
		d.setViolentSpeeding(1);
		d.setViolentHarshAcceleration(1);
		d.setViolentHarshBraking(1);
		d.setViolentHarshCornering(0);
		d.setViolentTotal(3);
		drivers.add(d);

		d = new Driver();
		d.setId("005");
		d.setName("古澤 昌之");
		d.setPhone("+8860912322338");
		d.setCurrentScore(23);
		d.setWorkHour(227);
		d.setPhoto("driver5.png");
		d.setViolentRecord("S");
		d.setViolentSpeeding(0);
		d.setViolentHarshAcceleration(1);
		d.setViolentHarshBraking(1);
		d.setViolentHarshCornering(0);
		d.setViolentTotal(2);
		drivers.add(d);
	}
	
	private void initCar1Frames() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(context.getRealPath("WEB-INF/data-car1.txt")));
			String line = br.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					String[] data = line.split(",");
					double lat = Double.parseDouble(data[0].trim());
					double lng = Double.parseDouble(data[1].trim());
					int dir = Integer.parseInt(data[2].trim());
					int spd = Integer.parseInt(data[3].trim());
					String evt = data.length == 5 ? data[4].trim() : null;
					System.out.println("Count = " + data.length + ", lat = " + lat + ", lng = " + lng + ", dir = " + dir + ", spd = " + spd);
					car1Frames.add(new Frame(lat, lng, dir, spd, evt));
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void initCar2Frames() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(context.getRealPath("WEB-INF/data-car2.txt")));
			String line = br.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					String[] data = line.split(",");
					double lat = Double.parseDouble(data[0].trim());
					double lng = Double.parseDouble(data[1].trim());
					int dir = Integer.parseInt(data[2].trim());
					int spd = Integer.parseInt(data[3].trim());
					String evt = data.length == 5 ? data[4].trim() : null;
					System.out.println("Count = " + data.length + ", lat = " + lat + ", lng = " + lng + ", dir = " + dir + ", spd = " + spd);
					car2Frames.add(new Frame(lat, lng, dir, spd, evt));
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initUnits() {
		Unit u = new Unit();
		u.setPhoto("device1.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("石田 真一");
		u.setImei("352964051849371");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("車両01");
		u.setVehiclePlate("DA-763A");
		u.setVehicleType("Sedan");
		u.setYear(2005);
		units.add(u);

		u = new Unit();
		u.setPhoto("device2.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("古澤 昌之");
		u.setImei("352964051849372");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("車両02");
		u.setVehiclePlate("AS-1QAZ");
		u.setVehicleType("Sedan");
		u.setYear(2006);
		units.add(u);

		u = new Unit();
		u.setPhoto("device3.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("齋藤 徳重");
		u.setImei("352964051849373");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("車両03");
		u.setVehiclePlate("BB-CD11");
		u.setVehicleType("Truck");
		u.setYear(2009);
		units.add(u);

		u = new Unit();
		u.setPhoto("device1.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("坂本 重雄");
		u.setImei("352964051849374");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("車両04");
		u.setVehiclePlate("AA-1234");
		u.setVehicleType("Truck");
		u.setYear(2010);
		units.add(u);

		u = new Unit();
		u.setPhoto("device2.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("佐藤 正治");
		u.setImei("352964051849375");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("車両05");
		u.setVehiclePlate("D5-4321");
		u.setVehicleType("Sedan");
		u.setYear(2005);
		units.add(u);

		u = new Unit();
		u.setPhoto("device3.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("- - -");
		u.setImei("352964051849376");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("車両06");
		u.setVehiclePlate("FD-12DA");
		u.setVehicleType("Sedan");
		u.setYear(2005);
		units.add(u);
	}

	private void initPois() {
		// 1
		Poi p = new Poi();
		p.setPhoto("オアシスタワー.png");
		p.setLatLng(33.238309, 131.602416);
		p.setName("オアシスタワー");
		p.setRange(100);
		pois.add(p);

		// 2
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.239139, 131.597833);
		p.setName("中春日交差点");
		p.setRange(80);
		pois.add(p);
		
		// 3
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.224528, 131.571444);
		p.setName("大分IC");
		p.setRange(300);
		pois.add(p);
		
		// 4
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.232389, 131.605694);
		p.setName("大分駅南口");
		p.setRange(60);
		pois.add(p);
		
		// 5
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.267972, 131.511278);
		p.setName("東別府");
		p.setRange(80);
		pois.add(p);
		
		// 6
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.279556, 131.506222);
		p.setName("別府北浜");
		p.setRange(80);
		pois.add(p);
		
		// 7
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.294750, 131.462944);
		p.setName("別府IC");
		p.setRange(200);
		pois.add(p);
		
		// 8
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.247028, 131.619917);
		p.setName("弁天大橋");
		p.setRange(100);
		pois.add(p);
		
		// 9
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.239417, 131.622222);
		p.setName("舞鶴橋");
		p.setRange(100);
		pois.add(p);
		
		// 10
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.243833, 131.587944);
		p.setName("東生石交差点");
		p.setRange(60);
		pois.add(p);
		
		// 11
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.226028, 131.595333);
		p.setName("椎迫交差点");
		p.setRange(60);
		pois.add(p);
		
		// 12
		p = new Poi();
		p.setPhoto("default.png");
		p.setLatLng(33.239194, 131.611611);
		p.setName("県庁北交差点");
		p.setRange(80);
		pois.add(p);
	}

	private void doWork() {
		update(positions.get(0), car1Frames);
		update(positions.get(1), car2Frames);
		index++;
	}

	public void update(Position p, List<Frame> frames) {
		Frame f = getNext(frames);
		p.setHeading(f.Dir);
		p.setLongitude(f.Lng);
		p.setLatitude(f.Lat);
		p.setExtraEngineOn(true);
		p.setExtraSpeedLevel(f.Spd);
		if (f.Evt != null && !f.Evt.isEmpty()) {
			addPositionEvent(p.getUnitId(), f.Evt);
		}
	}

	private void addPositionEvent(String unitId, String message) {
		PositionEvent e = new PositionEvent();
		e.setId(eventIndex++);
		e.setMessage(message);
		e.setUnitId(unitId);
		e.setDate(new Date());
		positionEvents.add(e);
	}

	public Frame getNext(List<Frame> frames) {
		frame1Index = (int) (index % frames.size());
		return frames.get(frame1Index);
	}

	private float getRandomFloat() {
		float min = -0.0001f;
		float max = 0.0001f;
		Random rand = new Random();
		return rand.nextFloat() * (max - min) + min;
	}

	private int getRandomInt() {
		int min = 1;
		int max = 5;
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	private static class Frame {
		public double Lng;
		public double Lat;
		public int Dir;
		public int Spd;
		public String Evt;

		public Frame(double Lat, double Lng, int Dir,int Spd, String Evt) {
			this.Lng = Lng;
			this.Lat = Lat;
			this.Dir = Dir;
			this.Spd = Spd;
			this.Evt = Evt;
		}
	}
	
//	public void initCar1Frames() {
//		car1Frames.add(new Frame(25.068440, 121.522863, 250));
//		car1Frames.add(new Frame(25.068143, 121.522644, 181));
//		car1Frames.add(new Frame(25.067924, 121.522569, 179));
//		car1Frames.add(new Frame(25.067608, 121.522499, 181));
//		car1Frames.add(new Frame(25.067312, 121.522440, 182));
//		car1Frames.add(new Frame(25.067118, 121.522413, 180));
//		car1Frames.add(new Frame(25.066865, 121.522343, 180));
//		car1Frames.add(new Frame(25.066569, 121.522295, 180));
//		car1Frames.add(new Frame(25.066316, 121.522252, 180));
//		car1Frames.add(new Frame(25.065893, 121.522166, 180));
//		car1Frames.add(new Frame(25.065572, 121.522166, 180));
//		car1Frames.add(new Frame(25.065257, 121.522150, 180));
//		car1Frames.add(new Frame(25.064931, 121.522170, 180));
//		car1Frames.add(new Frame(25.064722, 121.522170, 180));
//		car1Frames.add(new Frame(25.064474, 121.522181, 180));
//		car1Frames.add(new Frame(25.064299, 121.522229, 180));
//		car1Frames.add(new Frame(25.064123, 121.522260, 180));
//		car1Frames.add(new Frame(25.063930, 121.522263, 180));
//		car1Frames.add(new Frame(25.063682, 121.522311, 180));
//		car1Frames.add(new Frame(25.063495, 121.522341, 180));
//		car1Frames.add(new Frame(25.063364, 121.522373, 180));
//		car1Frames.add(new Frame(25.063223, 121.522408, 180));
//		car1Frames.add(new Frame(25.063121, 121.522427, 180));
//		car1Frames.add(new Frame(25.062970, 121.522475, 180));
//		car1Frames.add(new Frame(25.062813, 121.522559, 120));
//		car1Frames.add(new Frame(25.062730, 121.522709, 100));
//		car1Frames.add(new Frame(25.062677, 121.522870, 95));
//		car1Frames.add(new Frame(25.062666, 121.523102, 94));
//		car1Frames.add(new Frame(25.062676, 121.523357, 90));
//		car1Frames.add(new Frame(25.062678, 121.523617, 90));
//		car1Frames.add(new Frame(25.062685, 121.523923, 90));
//		car1Frames.add(new Frame(25.062700, 121.524256, 90));
//		car1Frames.add(new Frame(25.062678, 121.524691, 90));
//		car1Frames.add(new Frame(25.062668, 121.524884, 90));
//		car1Frames.add(new Frame(25.062639, 121.525219, 90));
//		car1Frames.add(new Frame(25.062634, 121.525549, 90));
//		car1Frames.add(new Frame(25.062634, 121.525549, 90));
//		car1Frames.add(new Frame(25.062634, 121.525549, 90));
//		car1Frames.add(new Frame(25.062714, 121.525638, 45));
//		car1Frames.add(new Frame(25.062918, 121.525643, 0));
//		car1Frames.add(new Frame(25.063139, 121.525630, 0));
//		car1Frames.add(new Frame(25.063393, 121.525623, 0));
//		car1Frames.add(new Frame(25.063709, 121.525685, 0));
//		car1Frames.add(new Frame(25.063928, 121.525685, 0));
//		car1Frames.add(new Frame(25.063867, 121.525696, 0));
//		car1Frames.add(new Frame(25.063709, 121.525685, 0));
//		car1Frames.add(new Frame(25.063952, 121.525680, 0));
//		car1Frames.add(new Frame(25.064168, 121.525688, 0));
//		car1Frames.add(new Frame(25.064394, 121.525683, 0));
//		car1Frames.add(new Frame(25.064739, 121.525688, 0));
//		car1Frames.add(new Frame(25.065045, 121.525699, 0));
//		car1Frames.add(new Frame(25.065378, 121.525694, 0));
//		car1Frames.add(new Frame(25.065738, 121.525707, 0));
//		car1Frames.add(new Frame(25.066139, 121.525712, 0));
//		car1Frames.add(new Frame(25.066496, 121.525731, 0));
//		car1Frames.add(new Frame(25.066901, 121.525728, 0));
//		car1Frames.add(new Frame(25.067333, 121.525749, 0));
//		car1Frames.add(new Frame(25.067637, 121.525760, 0));
//		car1Frames.add(new Frame(25.067937, 121.525780, 0));
//		car1Frames.add(new Frame(25.068338, 121.525764, 280));
//		car1Frames.add(new Frame(25.068428, 121.525482, 270));
//		car1Frames.add(new Frame(25.068462, 121.524988, 270));
//		car1Frames.add(new Frame(25.068467, 121.524749, 270));
//		car1Frames.add(new Frame(25.068469, 121.524373, 270));
//		car1Frames.add(new Frame(25.068481, 121.523984, 270));
//		car1Frames.add(new Frame(25.068472, 121.523739, 270));
//		car1Frames.add(new Frame(25.068484, 121.523433, 270));
//		car1Frames.add(new Frame(25.068478, 121.523122, 270));
//	}
}
