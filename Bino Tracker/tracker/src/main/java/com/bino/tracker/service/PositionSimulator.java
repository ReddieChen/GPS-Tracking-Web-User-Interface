package com.bino.tracker.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bino.tracker.vo.Driver;
import com.bino.tracker.vo.Poi;
import com.bino.tracker.vo.Position;
import com.bino.tracker.vo.PositionEvent;
import com.bino.tracker.vo.Unit;

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
	private long index = 1;
	private int eventIndex = 0;
	private int frame1Index = 0;

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
		p.setExtraEngineOn(true);
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
		p.setExtraEngineOn(true);
		positions.add(p);

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
		d.setName("Walter Hsu");
		d.setPhone("+886918323232");
		d.setCurrentScore(87);
		d.setWorkHour(7);
		d.setPhoto("driver1.png");
		d.setViolentRecord("Speeding");
		drivers.add(d);

		d = new Driver();
		d.setId("002");
		d.setName("David Lin");
		d.setPhone("+886918775255");
		d.setCurrentScore(12);
		d.setWorkHour(23);
		d.setPhoto("driver2.png");
		d.setViolentRecord("Speeding");
		drivers.add(d);

		d = new Driver();
		d.setId("003");
		d.setName("Albert Chen");
		d.setPhone("+8863819302939");
		d.setCurrentScore(87);
		d.setWorkHour(11);
		d.setPhoto("driver3.png");
		d.setViolentRecord("Speeding");
		drivers.add(d);

		d = new Driver();
		d.setId("004");
		d.setName("Batman");
		d.setPhone("+8860192929333");
		d.setCurrentScore(127);
		d.setWorkHour(12);
		d.setPhoto("driver4.png");
		d.setViolentRecord("Speeding");
		drivers.add(d);

		d = new Driver();
		d.setId("005");
		d.setName("Death Shot");
		d.setPhone("+8860912322338");
		d.setCurrentScore(23);
		d.setWorkHour(27);
		d.setPhoto("driver5.png");
		d.setViolentRecord("Speeding");
		drivers.add(d);
	}

	private void initUnits() {
		Unit u = new Unit();
		u.setPhoto("device1.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("Walter Hsu");
		u.setImei("352964051849371");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("Vehicle 01");
		u.setVehiclePlate("DA-763A");
		u.setVehicleType("Sedan");
		u.setYear(2005);
		units.add(u);

		u = new Unit();
		u.setPhoto("device2.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("David Lin");
		u.setImei("352964051849372");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("Vehicle 02");
		u.setVehiclePlate("AS-1QAZ");
		u.setVehicleType("Sedan");
		u.setYear(2006);
		units.add(u);

		u = new Unit();
		u.setPhoto("device3.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("Albert Chen");
		u.setImei("352964051849373");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("Vehicle 03");
		u.setVehiclePlate("BB-CD11");
		u.setVehicleType("Truck");
		u.setYear(2009);
		units.add(u);

		u = new Unit();
		u.setPhoto("device1.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("Batman");
		u.setImei("352964051849374");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("Vehicle 04");
		u.setVehiclePlate("AA-1234");
		u.setVehicleType("Truck");
		u.setYear(2010);
		units.add(u);

		u = new Unit();
		u.setPhoto("device2.png");
		u.setBrand("Toyota");
		u.setCurrentDriver("Death Shot");
		u.setImei("352964051849375");
		u.setModel("Altis");
		u.setUnitGroupId("A-Team");
		u.setUnitId("Vehicle 05");
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
		u.setUnitId("Vehicle 06");
		u.setVehiclePlate("FD-12DA");
		u.setVehicleType("Sedan");
		u.setYear(2005);
		units.add(u);
	}

	private void initPois() {
		Poi p = new Poi();
		p.setPhoto("poi-binodata.png");
		p.setLongitude(121.522324);
		p.setLatitude(25.063516);
		p.setName("Binodata Office");
		p.setRange(50);
		pois.add(p);

		p = new Poi();
		p.setPhoto("poi-mrt.png");
		p.setLongitude(121.526397);
		p.setLatitude(25.062628);
		p.setName("Zhongshan elementary school station");
		p.setRange(80);
		pois.add(p);
	}

	private void doWork() {
		update(positions.get(0), car1Frames);
		index++;
	}

	public void update(Position p, List<Frame> frames) {
		Frame f = getNext(frames);
		p.setHeading(f.Dir);
		p.setLongitude(f.Lng);
		p.setLatitude(f.Lat);
		p.setExtraEngineOn(!p.getExtraEngineOn());
		p.setExtraSpeedLevel(p.getExtraSpeedLevel() + 1);
		if (p.getExtraSpeedLevel() > 10) {
			p.setExtraSpeedLevel(1);
		}
		// if (p.getExtraEngineOn()) {
		// addPositionEvent(p.getUnitId(), p.getUnitId() + " turns on engine. " + frame1Index);
		// } else {
		// addPositionEvent(p.getUnitId(), p.getUnitId() + " turns off engine. " + frame1Index);
		// }
		if (frame1Index == 17) {
			// addPositionEvent(p.getUnitId(), p.getUnitId() + " enters Binodata Office.");
			String poiName = "Binodata Office";
			addPositionEvent(p.getUnitId(), poiName + " に " + p.getUnitId() + "が入りました");
		} else if (frame1Index == 23) {
			// addPositionEvent(p.getUnitId(), p.getUnitId() + " leaves Binodata Office.");
			String poiName = "Binodata Office";
			addPositionEvent(p.getUnitId(), poiName + " から " + p.getUnitId() + "が出ました");
		} else if (frame1Index == 38) {
			// addPositionEvent(p.getUnitId(), p.getUnitId() + " enters Zhongshan elementary school station.");
			String poiName = "Zhongshan elementary school station";
			addPositionEvent(p.getUnitId(), poiName + " に " + p.getUnitId() + "が入りました");
		} else if (frame1Index == 40) {
			// addPositionEvent(p.getUnitId(), p.getUnitId() + " leaves Zhongshan elementary school station.");
			String poiName = "Zhongshan elementary school station";
			addPositionEvent(p.getUnitId(), poiName + " から " + p.getUnitId() + "が出ました");
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

	public void initCar1Frames() {
		car1Frames.add(new Frame(25.068440, 121.522863, 250));
		car1Frames.add(new Frame(25.068143, 121.522644, 181));
		car1Frames.add(new Frame(25.067924, 121.522569, 179));
		car1Frames.add(new Frame(25.067608, 121.522499, 181));
		car1Frames.add(new Frame(25.067312, 121.522440, 182));
		car1Frames.add(new Frame(25.067118, 121.522413, 180));
		car1Frames.add(new Frame(25.066865, 121.522343, 180));
		car1Frames.add(new Frame(25.066569, 121.522295, 180));
		car1Frames.add(new Frame(25.066316, 121.522252, 180));
		car1Frames.add(new Frame(25.065893, 121.522166, 180));
		car1Frames.add(new Frame(25.065572, 121.522166, 180));
		car1Frames.add(new Frame(25.065257, 121.522150, 180));
		car1Frames.add(new Frame(25.064931, 121.522170, 180));
		car1Frames.add(new Frame(25.064722, 121.522170, 180));
		car1Frames.add(new Frame(25.064474, 121.522181, 180));
		car1Frames.add(new Frame(25.064299, 121.522229, 180));
		car1Frames.add(new Frame(25.064123, 121.522260, 180));
		car1Frames.add(new Frame(25.063930, 121.522263, 180));
		car1Frames.add(new Frame(25.063682, 121.522311, 180));
		car1Frames.add(new Frame(25.063495, 121.522341, 180));
		car1Frames.add(new Frame(25.063364, 121.522373, 180));
		car1Frames.add(new Frame(25.063223, 121.522408, 180));
		car1Frames.add(new Frame(25.063121, 121.522427, 180));
		car1Frames.add(new Frame(25.062970, 121.522475, 180));
		car1Frames.add(new Frame(25.062813, 121.522559, 120));
		car1Frames.add(new Frame(25.062730, 121.522709, 100));
		car1Frames.add(new Frame(25.062677, 121.522870, 95));
		car1Frames.add(new Frame(25.062666, 121.523102, 94));
		car1Frames.add(new Frame(25.062676, 121.523357, 90));
		car1Frames.add(new Frame(25.062678, 121.523617, 90));
		car1Frames.add(new Frame(25.062685, 121.523923, 90));
		car1Frames.add(new Frame(25.062700, 121.524256, 90));
		car1Frames.add(new Frame(25.062678, 121.524691, 90));
		car1Frames.add(new Frame(25.062668, 121.524884, 90));
		car1Frames.add(new Frame(25.062639, 121.525219, 90));
		car1Frames.add(new Frame(25.062634, 121.525549, 90));
		car1Frames.add(new Frame(25.062634, 121.525549, 90));
		car1Frames.add(new Frame(25.062634, 121.525549, 90));
		car1Frames.add(new Frame(25.062714, 121.525638, 45));
		car1Frames.add(new Frame(25.062918, 121.525643, 0));
		car1Frames.add(new Frame(25.063139, 121.525630, 0));
		car1Frames.add(new Frame(25.063393, 121.525623, 0));
		car1Frames.add(new Frame(25.063709, 121.525685, 0));
		car1Frames.add(new Frame(25.063928, 121.525685, 0));
		car1Frames.add(new Frame(25.063867, 121.525696, 0));
		car1Frames.add(new Frame(25.063709, 121.525685, 0));
		car1Frames.add(new Frame(25.063952, 121.525680, 0));
		car1Frames.add(new Frame(25.064168, 121.525688, 0));
		car1Frames.add(new Frame(25.064394, 121.525683, 0));
		car1Frames.add(new Frame(25.064739, 121.525688, 0));
		car1Frames.add(new Frame(25.065045, 121.525699, 0));
		car1Frames.add(new Frame(25.065378, 121.525694, 0));
		car1Frames.add(new Frame(25.065738, 121.525707, 0));
		car1Frames.add(new Frame(25.066139, 121.525712, 0));
		car1Frames.add(new Frame(25.066496, 121.525731, 0));
		car1Frames.add(new Frame(25.066901, 121.525728, 0));
		car1Frames.add(new Frame(25.067333, 121.525749, 0));
		car1Frames.add(new Frame(25.067637, 121.525760, 0));
		car1Frames.add(new Frame(25.067937, 121.525780, 0));
		car1Frames.add(new Frame(25.068338, 121.525764, 280));
		car1Frames.add(new Frame(25.068428, 121.525482, 270));
		car1Frames.add(new Frame(25.068462, 121.524988, 270));
		car1Frames.add(new Frame(25.068467, 121.524749, 270));
		car1Frames.add(new Frame(25.068469, 121.524373, 270));
		car1Frames.add(new Frame(25.068481, 121.523984, 270));
		car1Frames.add(new Frame(25.068472, 121.523739, 270));
		car1Frames.add(new Frame(25.068484, 121.523433, 270));
		car1Frames.add(new Frame(25.068478, 121.523122, 270));
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

		public Frame(double Lat, double Lng, int Dir) {
			this.Lng = Lng;
			this.Lat = Lat;
			this.Dir = Dir;
		}
	}
}
