var map, map2;
var mouseDown = false;
var showEventPanel = false;
var showFleetPanel = false;
var showPlaybackPanel = false;
var showEmployeePanel = 'employee';
var stompClient = null;
var devices = [];
var markers = [];
var geofences = [];
var poi1, poi2, poi3, poi4, poi5, poi6, poi7, poi8, poi9, poi10, poi11, poi12;  
var poiTxt1, poiTxt2, poiTxt3, poiTxt4, poiTxt5, poiTxt6, poiTxt7, poiTxt8, poiTxt9, poiTxt10, poiTxt11, poiTxt12; 
var poiCircle;
var geofenceType = 'normal';
var playbackSpeedType = 'speed1';
var geofenceId = 1;
var strokeColor = '#29b6f6';
var strokeOpacity = 1.0;
var strokeWeight = 1;
var fillColor = '#b3e5fc';
var fillOpacity = 0.4;
var playbackMin = 65;
var playbackMax = 500;
var dragging = false;
var palying = false;
var playbackCurrent = 0;
var playbackMarker;
var markerOnMap2;
var playbackLineArray = [];

$(document).ready(function() {
	initObjectListPanel();
	$('select').material_select();
	$('.tooltipped').tooltip({
		delay : 50
	});
	$('.datepicker').pickadate({
		selectMonths : true,
		selectYears : 15,
		format : 'yyyy-mm-dd',
		closeOnSelect : true,
		closeOnClear : true,
	});
	$('.datepicker-1').pickadate({
		selectMonths : true,
		selectYears : 15,
		format : 'yyyy-mm-dd',
		closeOnSelect : true,
		closeOnClear : true,
	});
	bindClickEvents();
	connect();
	setInterval(playbackInterval, 1000);
});

$(window).resize(function() {
});

$(window).load(function() {
});

jQuery.fn.exists = function() {
	return this.length > 0;
}

function playbackInterval() {
	if (palying && !dragging) {
		if (playbackSpeedType == 'speed1') {
			playbackCurrent = playbackCurrent + 1;
		} else if (playbackSpeedType == 'speed2') {
			playbackCurrent = playbackCurrent + 2;
		} else if (playbackSpeedType == 'speed3') {
			playbackCurrent = playbackCurrent + 4;
		}
		updatePlaybackTime();
	}
}

function updatePlaybackTime() {
	$('#playback-time').text(secondsToTimeText());
	var left = playbackCurrent / 86400 * (playbackMax - playbackMin) + playbackMin;
	$('#playback-current-point').offset({
		left : left
	});
	updatePlaybackOnMap();
}

function updatePlaybackOnMap() {
	var playbackData = getPlaybackData();
	if (playbackData) {
		// console.log('updatePlaybackOnMap() lng = ' + playbackData.lng + ', lat = ' + playbackData.lat);
		updatePlaybackMarker(playbackData);
	}
}

function updatePlaybackBySlider(left) {
	if (left > playbackMin && left < playbackMax) {
		$('#playback-current-point').offset({
			left : left
		});
		playbackCurrent = (left - playbackMin) / (playbackMax - playbackMin) * 86400;
		$('#playback-time').text(secondsToTimeText());
	}
}

function secondsToTimeText() {
	var hh = Math.floor(playbackCurrent / 3600);
	var mm = Math.floor(playbackCurrent / 60 % 60);
	var ss = Math.floor(playbackCurrent % 60);
	hh = hh < 10 ? '0' + hh : hh;
	mm = mm < 10 ? '0' + mm : mm;
	ss = ss < 10 ? '0' + ss : ss;
	return hh + ':' + mm + ' ' + ss;
}

function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		panControl : true,
		zoomControl : true,
		scaleControl : true,
		streetViewControl : true,
		overviewMapControl : true,
		center : {
			lat : 33.246573,
			lng : 131.624879
		},
		zoom : 15,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		disableDefaultUI : false,
	});
	initTxtOverlay();
	drawPoi();
	map.addListener('click', function(event) {
		var lat = event.latLng.lat();
		var lng = event.latLng.lng();
		if (geofenceType == 'rect') {
			drawGeofenceRect(lat, lng);
			updateGeofenceListview();
		} else if (geofenceType == 'circle') {
			drawGeofenceCirlce(lat, lng);
			updateGeofenceListview();
		}
	});
	map2 = new google.maps.Map(document.getElementById('map2'), {
		panControl : false,
		zoomControl : false,
		scaleControl : false,
		streetViewControl : false,
		overviewMapControl : false,
		center : {
			lat : 33.246573,
			lng : 131.624879
		},
		zoom : 15,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		disableDefaultUI : false,
	});
	var icon = {
		path : google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
		fillColor : getSpeedFillColor(1),
		fillOpacity : 0.95,
		scale : 7,
		strokeColor : getSpeedStrokeColor(1),
		strokeWeight : 3,
		rotation : 45
	};
	markerOnMap2 = new google.maps.Marker({
		position : {
			lat : 33.246573,
			lng : 131.624879
		},
		map : map2,
		icon : icon
	});
}

function updateGeofenceListview(geofence) {
	for (var i = 0; i < geofences.length; i++) {
		if (!$('#' + geofences[i].id).exists()) {
			$('#geofence-content').append(makeGeofneceCollectionItemHtml(geofences[i]));
		}
	}
}

function removeGeofence(event, id) {
	var index = -1;
	for (var i = 0; i < geofences.length; i++) {
		if (geofences[i].id == id) {
			index = i;
		}
	}
	if (index >= 0) {
		geofences[index].setMap(null);
		geofences.splice(index, 1);
		$('#' + id).remove();
	}
	event.stopPropagation();
}

function drawGeofenceRect(lat, lng) {
	var size = 0.002
	var rectangle = new google.maps.Rectangle({
		id : 'Geofence-' + geofenceId++,
		lat : lat,
		lng : lng,
		strokeColor : strokeColor,
		strokeOpacity : strokeOpacity,
		strokeWeight : strokeWeight,
		fillColor : fillColor,
		fillOpacity : fillOpacity,
		map : map,
		editable : true,
		bounds : {
			north : lat + size / 2,
			south : lat - size / 2,
			east : lng + size / 2,
			west : lng - size / 2
		}
	});
	geofences.push(rectangle);
}

function drawGeofenceCirlce(lat, lng) {
	var circle = new google.maps.Circle({
		id : 'Geofence-' + geofenceId++,
		lat : lat,
		lng : lng,
		strokeColor : strokeColor,
		strokeOpacity : strokeOpacity,
		strokeWeight : strokeWeight,
		fillColor : fillColor,
		fillOpacity : fillOpacity,
		map : map,
		editable : true,
		center : {
			lat : lat,
			lng : lng
		},
		radius : 100
	});
	geofences.push(circle);
}

function drawPoi() {
	// POI1
	var latlng = new google.maps.LatLng(33.238309, 131.602416);
	poi1 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi1.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi1.setMap(map);
	var text = "<div>オアシスタワー</div>";
	poiTxt1 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI2
	latlng = new google.maps.LatLng(33.239139, 131.597833);
	poi2 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi2.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi2.setMap(map);
	text = "<div>中春日交差点</div>";
	poiTxt2 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI3
	latlng = new google.maps.LatLng(33.224528, 131.571444);
	poi3 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi3.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi3.setMap(map);
	text = "<div>大分IC</div>";
	poiTxt3 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI4
	latlng = new google.maps.LatLng(33.232389, 131.605694);
	poi4 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi4.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi4.setMap(map);
	text = "<div>大分駅南口</div>";
	poiTxt4 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI5
	latlng = new google.maps.LatLng(33.267972, 131.511278);
	poi5 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi5.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi5.setMap(map);
	text = "<div>東別府</div>";
	poiTxt5 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI6
	latlng = new google.maps.LatLng(33.279556, 131.506222);
	poi6 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi6.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi6.setMap(map);
	text = "<div>別府北浜</div>";
	poiTxt6 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI7
	latlng = new google.maps.LatLng(33.294750, 131.462944);
	poi7 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi7.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi7.setMap(map);
	text = "<div>別府IC</div>";
	poiTxt7 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI8
	latlng = new google.maps.LatLng(33.247028, 131.619917);
	poi8 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi8.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi8.setMap(map);
	text = "<div>弁天大橋</div>";
	poiTxt8 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI9
	latlng = new google.maps.LatLng(33.239417, 131.622222);
	poi9 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi9.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi9.setMap(map);
	text = "<div>舞鶴橋</div>";
	poiTxt9 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI10
	latlng = new google.maps.LatLng(33.243833, 131.587944);
	poi10 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi10.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi10.setMap(map);
	text = "<div>東生石交差点</div>";
	poiTxt10 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI11
	latlng = new google.maps.LatLng(33.226028, 131.595333);
	poi11 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi11.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi11.setMap(map);
	text = "<div>椎迫交差点</div>";
	poiTxt11 = new TxtOverlay(latlng, text, "poi-label", map);
	// POI12
	latlng = new google.maps.LatLng(33.239194, 131.611611);
	poi12 = new google.maps.Marker({
		position : latlng,
		map : map
	});
	poi12.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
	poi12.setMap(map);
	text = "<div>県庁北交差点</div>";
	poiTxt12 = new TxtOverlay(latlng, text, "poi-label", map);
}

function bindClickEvents() {
	$('#right-side-navigation-tracking').click(function() {
		$('#right-side-navigation-tracking').addClass('light-blue darken-2');
		$('#right-side-navigation-fleet').removeClass('light-blue darken-2');
		$('#right-side-navigation-geofence').removeClass('light-blue darken-2');
		$('#right-side-navigation-poi').removeClass('light-blue darken-2');
		$('#right-side-navigation-playback').removeClass('light-blue darken-2');
		$('#object-listview-panel').show();
		$('#poi-listview-panel').hide();
		hideEmployeeAndDevicePanels();
		$('#map').removeClass('blur');
		$('#left-side-navigation').hide();
		$('#geofence-tool-panel').hide();
		$('#geofence-listview-panel').hide();
		$('#playback-control-panel').hide();
		clearPoi();
		clearPlaybackPathOnMap();
		showFleetPanel = false;
		showPlaybackPanel = false;
		showCurrentMarkers();
		clearPlaybackMarker();
		$('#playback-object-listview-panel').hide();
	});

	$('#right-side-navigation-fleet').click(function() {
		$('#right-side-navigation-tracking').removeClass('light-blue darken-2');
		$('#right-side-navigation-fleet').addClass('light-blue darken-2');
		$('#right-side-navigation-geofence').removeClass('light-blue darken-2');
		$('#right-side-navigation-poi').removeClass('light-blue darken-2');
		$('#right-side-navigation-playback').removeClass('light-blue darken-2');
		$('#object-listview-panel').hide();
		$('#poi-listview-panel').hide();
		showEmployeeOrDevicePanel(showEmployeePanel);
		$('#map').addClass('blur');
		$('#left-side-navigation').show();
		$('#geofence-tool-panel').hide();
		$('#geofence-listview-panel').hide();
		$('#playback-control-panel').hide();
		showFleetPanel = true;
		showPlaybackPanel = false;
		clearPoi();
		clearPlaybackPathOnMap();
		hideToasts();
		showCurrentMarkers();
		clearPlaybackMarker();
		$('#playback-object-listview-panel').hide();
	});

	$('#right-side-navigation-poi').click(function() {
		$('#right-side-navigation-poi').addClass('light-blue darken-2');
		$('#right-side-navigation-tracking').removeClass('light-blue darken-2');
		$('#right-side-navigation-fleet').removeClass('light-blue darken-2');
		$('#right-side-navigation-geofence').removeClass('light-blue darken-2');
		$('#right-side-navigation-playback').removeClass('light-blue darken-2');
		$('#object-listview-panel').hide();
		$('#poi-listview-panel').show();
		hideEmployeeAndDevicePanels();
		$('#map').removeClass('blur');
		$('#left-side-navigation').hide();
		$('#geofence-tool-panel').hide();
		$('#geofence-listview-panel').hide();
		$('#playback-control-panel').hide();
		showFleetPanel = false;
		showPlaybackPanel = false;
		clearPlaybackPathOnMap();
		showCurrentMarkers();
		clearPlaybackMarker();
		$('#playback-object-listview-panel').hide();
	});

	$('#right-side-navigation-geofence').click(function() {
		$('#right-side-navigation-geofence').addClass('light-blue darken-2');
		$('#right-side-navigation-poi').removeClass('light-blue darken-2');
		$('#right-side-navigation-tracking').removeClass('light-blue darken-2');
		$('#right-side-navigation-fleet').removeClass('light-blue darken-2');
		$('#right-side-navigation-playback').removeClass('light-blue darken-2');
		$('#object-listview-panel').hide();
		$('#poi-listview-panel').hide();
		hideEmployeeAndDevicePanels();
		$('#map').removeClass('blur');
		$('#left-side-navigation').hide();
		$('#geofence-tool-panel').show();
		$('#geofence-listview-panel').show();
		$('#playback-control-panel').hide();
		clearPoi();
		showFleetPanel = false;
		showPlaybackPanel = false;
		clearPlaybackPathOnMap();
		showCurrentMarkers();
		clearPlaybackMarker();
		$('#playback-object-listview-panel').hide();
	});

	$('#right-side-navigation-playback').click(function() {
		$('#right-side-navigation-geofence').removeClass('light-blue darken-2');
		$('#right-side-navigation-poi').removeClass('light-blue darken-2');
		$('#right-side-navigation-tracking').removeClass('light-blue darken-2');
		$('#right-side-navigation-fleet').removeClass('light-blue darken-2');
		$('#right-side-navigation-playback').addClass('light-blue darken-2');
		$('#object-listview-panel').hide();
		$('#poi-listview-panel').hide();
		hideEmployeeAndDevicePanels();
		$('#map').removeClass('blur');
		$('#left-side-navigation').hide();
		$('#geofence-tool-panel').hide();
		$('#geofence-listview-panel').hide();
		$('#playback-control-panel').show();
		showFleetPanel = false;
		showPlaybackPanel = true;
		clearPoi();
		hideToasts();
		drawPlaybackPathOnMap();
		hideCurrentMarkers();
		palying = false;
		playbackCurrent = 0;
		updatePlaybackTime();
		$('#playback-play-button').children().eq(0).text('play_arrow');
		$('#playback-object-listview-panel').show();
	});

	$('#left-side-navigation-employee').click(function() {
		$('#left-side-navigation-employee').addClass('light-blue darken-2');
		$('#left-side-navigation-device-management').removeClass('light-blue darken-2');
		$('#left-side-navigation-violation').removeClass('light-blue darken-2');
		showEmployeePanel = 'employee';
		showEmployeeOrDevicePanel(showEmployeePanel);

	});

	$('#left-side-navigation-device-management').click(function() {
		$('#left-side-navigation-employee').removeClass('light-blue darken-2');
		$('#left-side-navigation-device-management').addClass('light-blue darken-2');
		$('#left-side-navigation-violation').removeClass('light-blue darken-2');
		showEmployeePanel = 'device';
		showEmployeeOrDevicePanel(showEmployeePanel);
	});

	$('#left-side-navigation-violation').click(function() {
		$('#left-side-navigation-employee').removeClass('light-blue darken-2');
		$('#left-side-navigation-device-management').removeClass('light-blue darken-2');
		$('#left-side-navigation-violation').addClass('light-blue darken-2');
		showEmployeePanel = 'violation';
		showEmployeeOrDevicePanel(showEmployeePanel);
		google.maps.event.trigger(map2, 'resize');
		$('.violation-item').each(function() {
			$(this).removeClass('violation-selected');
			$(this).addClass('violation-normal');
		});
		$($('.violation-item')[0]).addClass('violation-selected');
		$('.violation-detail-item').each(function() {
			$(this).removeClass('violation-selected');
			$(this).addClass('violation-normal');
		});
		$($('.violation-detail-item')[0]).addClass('violation-selected');
		$('#violation-detail-table-1').show();
		$('#violation-detail-table-2').hide();
		$('#violation-detail-table-3').hide();
		$('#violation-detail-table-4').hide();
		$('#violation-detail-table-5').hide();
		panToMarkerOnMap2(35.552103, 135.479522);
	});

	$('#right-side-events').click(function() {
		showEventPanel = !showEventPanel;
		if (showEventPanel) {
			$('#event-pnael').show();
			$('#right-side-events-icon').removeClass('grey-text text-lighten-5');
			$('#right-side-events-icon').addClass('red-text text-lighten-3');
			hideToasts();
		} else {
			$('#event-pnael').hide();
			$('#right-side-events-icon').addClass('grey-text text-lighten-5');
			$('#right-side-events-icon').removeClass('red-text text-lighten-3');
		}
	});

	$('#employee-create-button').click(function() {
		$('#create-employee-modal').openModal();
	});

	$('#create-driver-button').click(function() {
		createDriver($('#driver-id').val(), $('#dirver-name').val(), $('#driver-phone').val(), $('#unit-select').val());
	});

	$('#geofence-normal').click(function() {
		$('#geofence-normal').addClass('grey lighten-5');
		$('#geofence-rectangle').removeClass('grey lighten-5');
		$('#geofence-circle').removeClass('grey lighten-5');
		geofenceType = 'normal';
	});

	$('#geofence-rectangle').click(function() {
		$('#geofence-normal').removeClass('grey lighten-5');
		$('#geofence-rectangle').addClass('grey lighten-5');
		$('#geofence-circle').removeClass('grey lighten-5');
		geofenceType = 'rect';
	});

	$('#geofence-circle').click(function() {
		$('#geofence-rectangle').removeClass('grey lighten-5');
		$('#geofence-normal').removeClass('grey lighten-5');
		$('#geofence-circle').addClass('grey lighten-5');
		geofenceType = 'circle';
	});

	$('#playback-current-frame').mousedown(function(event) {
		dragging = true;
		updatePlaybackBySlider(event.pageX);
	});

	$('#playback-current-frame').mousemove(function(event) {
		if (dragging) {
			console.log('event.pageX = ' + event.pageX);
			updatePlaybackBySlider(event.pageX);
		}
	});

	$('#playback-current-frame').mouseup(function(event) {
		dragging = false;
	});

	$('#playback-current-frame').mouseleave(function(event) {
		dragging = false;
	});

	$('#playback-play-button').click(function() {
		palying = !palying;
		if (palying) {
			$(this).children().eq(0).text('pause');
		} else {
			$(this).children().eq(0).text('play_arrow');
		}
	});

	$('#playback-speed1').click(function() {
		$('#playback-speed1').addClass('playback-fastfoward-button-selected');
		$('#playback-speed2').removeClass('playback-fastfoward-button-selected');
		$('#playback-speed3').removeClass('playback-fastfoward-button-selected');
		playbackSpeedType = 'speed1';

	});

	$('#playback-speed2').click(function() {
		$('#playback-speed1').removeClass('playback-fastfoward-button-selected');
		$('#playback-speed2').addClass('playback-fastfoward-button-selected');
		$('#playback-speed3').removeClass('playback-fastfoward-button-selected');
		playbackSpeedType = 'speed2';
	});

	$('#playback-speed3').click(function() {
		$('#playback-speed1').removeClass('playback-fastfoward-button-selected');
		$('#playback-speed2').removeClass('playback-fastfoward-button-selected');
		$('#playback-speed3').addClass('playback-fastfoward-button-selected');
		playbackSpeedType = 'speed3';
	});
}

function drawPlaybackPathOnMap() {
	for (var i = 0; i < playbackDataArray.length - 1; i++) {
		var lineArray = [];
		lineArray.push({
			lat : playbackDataArray[i].lat,
			lng : playbackDataArray[i].lng
		});
		lineArray.push({
			lat : playbackDataArray[i + 1].lat,
			lng : playbackDataArray[i + 1].lng
		});
		var playbackPath = new google.maps.Polyline({
			path : lineArray,
			geodesic : true,
			strokeColor : getSpeedFillColor(playbackDataArray[i].extraSpeedLevel),
			strokeOpacity : 0.9,
			strokeWeight : 6
		});
		playbackPath.setMap(map);
		playbackLineArray.push(playbackPath);
	}
}

function clearPlaybackPathOnMap() {
	for (var i = 0; i < playbackLineArray.length; i++) {
		playbackLineArray[i].setMap(null);
	}
	playbackLineArray = [];
}

function createDriver(id, name, phone, unitId) {
	$('#employee-table tbody').append(makeUnitIdTable1(id, name, phone, unitId));
	$('#device-table-1 tbody').children().eq(5).children().eq(3).text(name);
	$('#device-table-2 tbody').children().eq(5).children().eq(4).text(name);
}

function hideEmployeeAndDevicePanels() {
	$('#employee-panel').hide();
	$('#device-panel').hide();
	$('#violation-panel').hide();
}

function showEmployeeOrDevicePanel(flag) {
	if (flag == 'employee') {
		$('#employee-panel').show();
		$('#device-panel').hide();
		$('#violation-panel').hide();
	} else if (flag == 'device') {
		$('#device-panel').show();
		$('#employee-panel').hide();
		$('#violation-panel').hide();
	} else if (flag == 'violation') {
		$('#employee-panel').hide();
		$('#device-panel').hide();
		$('#violation-panel').show();
	}
}

function hideToasts() {
	$('.toast').each(function() {
		$(this).hide();
	});
}

function panToMarkerByUnitId(unitId) {
	var device = findDeviceByUnitId(unitId);
	if (device) {
		map.panTo(new google.maps.LatLng(device.latitude, device.longitude));
		map.setZoom(17);
		bounceMarkerByUnitId(unitId);
	}
}

function panToMarker(lat, lng) {
	map.panTo(new google.maps.LatLng(lat, lng));
	map.setZoom(17);
	bounceMarker(lat, lng);
}

function panToMarkerOnMap2(lat, lng) {
	map2.panTo(new google.maps.LatLng(lat, lng));
	map2.setZoom(17);
	markerOnMap2.setPosition(new google.maps.LatLng(lat, lng));
	markerOnMap2.setOptions({
		animation : google.maps.Animation.BOUNCE
	});
	setTimeout(function() {
		markerOnMap2.setOptions({
			animation : null
		});
	}, 950)
}

function clearPoi() {
	$('.collection-item-selected').each(function() {
		$(this).removeClass('collection-item-selected');
	});
	if (poiCircle != undefined) {
		poiCircle.setMap(null);
	}
}

function unitOnClick(ele, unitId) {
	$('.collection-item-selected').each(function() {
		$(this).removeClass('collection-item-selected');
	});
	ele.addClass('collection-item-selected');
	panToMarkerByUnitId(unitId);
}

function violationOnClick(ele, driverId) {
	$('.violation-item').each(function() {
		$(this).removeClass('violation-selected');
		$(this).addClass('violation-normal');
	});
	ele.addClass('violation-selected');
	$('.violation-detail-item').each(function() {
		$(this).removeClass('violation-selected');
		$(this).addClass('violation-normal');
	});
	if (driverId == '001') {
		$('#violation-detail-table-1').show();
		$('#violation-detail-table-2').hide();
		$('#violation-detail-table-3').hide();
		$('#violation-detail-table-4').hide();
		$('#violation-detail-table-5').hide();
		$($('#violation-detail-table-1 .violation-detail-item')[0]).addClass('violation-selected');
		panToMarkerOnMap2(35.552103, 135.479522);
	} else if (driverId == '002') {
		$('#violation-detail-table-1').hide();
		$('#violation-detail-table-2').show();
		$('#violation-detail-table-3').hide();
		$('#violation-detail-table-4').hide();
		$('#violation-detail-table-5').hide();
		$($('#violation-detail-table-2 .violation-detail-item')[0]).addClass('violation-selected');
		panToMarkerOnMap2(33.005285, 131.250106);
	} else if (driverId == '003') {
		$('#violation-detail-table-1').hide();
		$('#violation-detail-table-2').hide();
		$('#violation-detail-table-3').show();
		$('#violation-detail-table-4').hide();
		$('#violation-detail-table-5').hide();
		$($('#violation-detail-table-3 .violation-detail-item')[0]).addClass('violation-selected');
		panToMarkerOnMap2(33.220117, 131.597689);
	} else if (driverId == '004') {
		$('#violation-detail-table-1').hide();
		$('#violation-detail-table-2').hide();
		$('#violation-detail-table-3').hide();
		$('#violation-detail-table-4').show();
		$('#violation-detail-table-5').hide();
		$($('#violation-detail-table-4 .violation-detail-item')[0]).addClass('violation-selected');
		panToMarkerOnMap2(35.552103, 135.479522);
	} else if (driverId == '005') {
		$('#violation-detail-table-1').hide();
		$('#violation-detail-table-2').hide();
		$('#violation-detail-table-3').hide();
		$('#violation-detail-table-4').hide();
		$('#violation-detail-table-5').show();
		$($('#violation-detail-table-5 .violation-detail-item')[0]).addClass('violation-selected');
		panToMarkerOnMap2(33.244872, 131.705530);
	}
}

function violationDetailOnClick(ele, lng, lat) {
	$('.violation-detail-item').each(function() {
		$(this).removeClass('violation-selected');
		$(this).addClass('violation-normal');
	});
	ele.addClass('violation-selected');
	panToMarkerOnMap2(lat, lng);
}

function poiOnClick(ele, lat, lng, range) {
	$('.collection-item-selected').each(function() {
		$(this).removeClass('collection-item-selected');
	});
	ele.addClass('collection-item-selected');
	panToMarker(lat, lng);
	if (poiCircle != undefined) {
		poiCircle.setMap(null);
	}
	poiCircle = new google.maps.Circle({
		strokeColor : strokeColor,
		strokeOpacity : strokeOpacity,
		strokeWeight : strokeWeight,
		fillColor : fillColor,
		fillOpacity : fillOpacity,
		map : map,
		center : {
			lat : lat,
			lng : lng
		},
		radius : range
	});
}

function initObjectListPanel() {
	var moveable = $('.moveable');
	var moveHandler = $('.listview-header');
	$(document).mousemove(function(e) {
		if (mouseDown) {
			moveable.css({
				'top' : e.pageY - 10,
				'left' : e.pageX - 150
			});
		}
	});

	moveHandler.mousedown(function(e) {
		mouseDown = true;
		moveHandler.css('cursor', 'move');
	});

	$(document).mouseup(function(e) {
		mouseDown = false;
		moveHandler.css('cursor', '');
	});
}

function connect() {
	var socket = new SockJS('/tracker/endpoint');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/topic/position', function(message) {
			message = JSON.parse(message.body);
			if (message.command == 'update' && !showPlaybackPanel) {
				updatePositions(message.data1);
				showEvents(message.data2);
			}
		});
	});
}

function showEvents(events) {
	for (var i = 0; i < events.length; i++) {
		if (!showEventPanel && !showFleetPanel && !showPlaybackPanel) {
			var $toastContent = $("<span><i class='toast-icon small material-icons light-blue-text text-lighten-4'>error</i><span class='toast-text'>" + events[i].message + "</span></span>");
			Materialize.toast($toastContent, 5000);
		}
		var count = $('#event-pnael').children().length
		if (count >= 8) {
			$('#event-pnael').children().eq(count - 1).remove();
		}
		$('#event-pnael').prepend(makeEventItemHtml(events[i]));
	}
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
}

function updatePositions(positions) {
	// $('#object-listview a').each(function() {
	// var unitId = $(this).contents().get(0).nodeValue;
	// var speed = $(this).children().eq(0).text();
	// // console.log('unitId = ' + unitId);
	// // console.log('speed = ' + speed);
	// for (var i = 0; i < positions.length; i++) {
	// if (positions[i].unitId == unitId) {
	// $(this).children().eq(0).text(positions[i].speed);
	// }
	// }
	// });

	$('.object-listview-collection-item').each(function() {
		for (var i = 0; i < positions.length; i++) {
			var unitId = $(this).children().eq(0).text();
			for (var i = 0; i < positions.length; i++) {
				if (positions[i].unitId == unitId) {
					if (positions[i].extraGsm == 0 || positions[i].extraGsm == 99) {
						setRedCircle($(this).children().eq(1).children().eq(0));
					} else {
						setGreenCircle($(this).children().eq(1).children().eq(0));
					}
					if (positions[i].extraGps >= 4) {
						setGreenCircle($(this).children().eq(2).children().eq(0));
					} else {
						setRedCircle($(this).children().eq(2).children().eq(0));
					}
					if (positions[i].extraEngineOn) {
						setGreenCircle($(this).children().eq(3).children().eq(0));
					} else {
						setRedCircle($(this).children().eq(3).children().eq(0));
					}
				}
			}
		}
	});

	// Update devices
	for (var i = 0; i < positions.length; i++) {
		var existed = false;
		for (var j = 0; j < devices.length; j++) {
			if (devices[j].unitId == positions[i].unitId) {
				devices[j] = positions[i];
				existed = true;
			}
		}
		if (!existed) {
			devices.push(positions[i]);
		}
	}

	// Update markers
	for (var i = 0; i < devices.length; i++) {
		var existed = false;
		for (var j = 0; j < markers.length; j++) {
			if (markers[j].unitId == devices[i].unitId) {
				existed = true;
				updateMarker(markers[j], devices[i]);
			}
		}
		if (!existed) {
			markers.push(createMarker(devices[i]));
		}
	}
}

function showCurrentMarkers() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setVisible(true);
	}
}

function hideCurrentMarkers() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setVisible(false);
	}
}

function setGreenCircle(ele) {
	if (ele) {
		ele.addClass('green-circle');
		ele.removeClass('red-circle');
	}
}

function setRedCircle(ele) {
	if (ele) {
		ele.removeClass('green-circle');
		ele.addClass('red-circle');
	}
}

function updateMarker(marker, device) {
	marker.setOptions({
		icon : createIcon(device)
	});
	marker.setPosition(new google.maps.LatLng(device.latitude, device.longitude));
}

function createIcon(device) {
	var icon = {
		path : google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
		fillColor : getSpeedFillColor(device.extraSpeedLevel),
		fillOpacity : 0.95,
		scale : 7,
		strokeColor : getSpeedStrokeColor(device.extraSpeedLevel),
		strokeWeight : 3,
		rotation : device.heading
	};
	return icon;
}

function getSpeedFillColor(level) {
	switch (level) {
	case 1:
		return '#A9D0F5';
	case 2:
		return '#A9BCF5';
	case 3:
		return '#A9A9F5';
	case 4:
		return '#BCA9F5';
	case 5:
		return '#D0A9F5';
	case 6:
		return '#E2A9F3';
	case 7:
		return '#F5A9F2';
	case 8:
		return '#F5A9E1';
	case 9:
		return '#F5A9D0';
	case 10:
		return '#F5A9BC';
	default:
		return '#039be5';
	}
}

function getSpeedStrokeColor(level) {
	switch (level) {
	case 1:
		return '#0080FF';
	case 2:
		return '#0040FF';
	case 3:
		return '#0000FF';
	case 4:
		return '#4000FF';
	case 5:
		return '#8000FF';
	case 6:
		return '#BF00FF';
	case 7:
		return '#FF00FF';
	case 8:
		return '#FF00BF';
	case 9:
		return '#FF0080';
	case 10:
		return '#FF0040';
	default:
		return '#01579b';
	}
}

function updatePlaybackMarker(playbackData) {
	if (playbackMarker) {
		playbackMarker.setOptions({
			icon : createIcon(playbackData)
		});
		playbackMarker.setPosition(new google.maps.LatLng(playbackData.lat, playbackData.lng));
	} else {
		playbackMarker = new google.maps.Marker({
			position : {
				lat : playbackData.lat,
				lng : playbackData.lng
			},
			map : map,
			icon : createIcon(playbackData)
		});
	}
}

function clearPlaybackMarker() {
	if (playbackMarker) {
		playbackMarker.setMap(null);
	}
	playbackMarker = null;
}

function createMarker(device) {
	var infowindow = new google.maps.InfoWindow();
	var marker = new google.maps.Marker({
		unitId : device.unitId,
		position : {
			lat : device.latitude,
			lng : device.longitude
		},
		map : map,
		icon : createIcon(device)
	});
	marker.addListener('click', function() {
		infowindow.setContent(makeMarkerContent(marker.unitId));
		infowindow.open(map, marker);
	});
	return marker;
}

function makeMarkerContent(unitId) {
	var device = findDeviceByUnitId(unitId);
	if (device) {
		var html = "<div>"
		html += device.unitId;
		html += "</div>";
		html += "<div>"
		html += device.longitude;
		html += "</div>";
		html += "<div>"
		html += device.latitude;
		html += "</div>";
		html += "<div>"
		html += device.heading;
		html += "</div>";
		html += "<div>"
		html += device.gpstime;
		html += "</div>";
		html += "<div>"
		html += device.rtctime;
		html += "</div>";
		html += "<div>"
		html += device.pstime;
		html += "</div>";
		html += "<div>"
		html += device.reportId;
		html += "</div>";
		html += "<div>"
		html += device.odometer;
		html += "</div>";
		html += "<div>"
		html += device.hdop;
		html += "</div>";
		return html
	}
}

function bounceMarkerByUnitId(unitId) {
	var marker = findMarkerByUnitId(unitId);
	if (marker) {
		marker.setOptions({
			animation : google.maps.Animation.BOUNCE
		});
		setTimeout(function() {
			marker.setOptions({
				animation : null
			});
		}, 950);
	}
}

function bounceMarker(lat, lng) {
	var poi;
	if (lat == 33.238309) {
		poi = poi1;
	} else if (lat == 33.239139) {
		poi = poi2;
	} else if (lat == 33.224528) {
		poi = poi3;
	} else if (lat == 33.232389) {
		poi = poi4;
	} else if (lat == 33.267972) {
		poi = poi5;
	} else if (lat == 33.279556) {
		poi = poi6;
	} else if (lat == 33.294750) {
		poi = poi7;
	} else if (lat == 33.247028) {
		poi = poi8;
	} else if (lat == 33.239417) {
		poi = poi9;
	} else if (lat == 33.243833) {
		poi = poi10;
	} else if (lat == 33.226028) {
		poi = poi11;
	} else if (lat == 33.239194) {
		poi = poi12;
	}
	
	if (poi) {
		poi.setOptions({
			animation : google.maps.Animation.BOUNCE
		});
		setTimeout(function() {
			poi.setOptions({
				animation : null
			});
		}, 950);
	}
}

function findDeviceByUnitId(unitId) {
	for (var i = 0; i < devices.length; i++) {
		if (devices[i].unitId == unitId) {
			return devices[i];
		}
	}
}

function findMarkerByUnitId(unitId) {
	for (var i = 0; i < markers.length; i++) {
		if (markers[i].unitId == unitId) {
			return markers[i];
		}
	}
}

Date.prototype.customFormat = function(formatString) {
	var YYYY, YY, MMMM, MMM, MM, M, DDDD, DDD, DD, D, hhhh, hhh, hh, h, mm, m, ss, s, ampm, AMPM, dMod, th;
	YY = ((YYYY = this.getFullYear()) + "").slice(-2);
	MM = (M = this.getMonth() + 1) < 10 ? ('0' + M) : M;
	MMM = (MMMM = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ][M - 1]).substring(0, 3);
	DD = (D = this.getDate()) < 10 ? ('0' + D) : D;
	DDD = (DDDD = [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ][this.getDay()]).substring(0, 3);
	th = (D >= 10 && D <= 20) ? 'th' : ((dMod = D % 10) == 1) ? 'st' : (dMod == 2) ? 'nd' : (dMod == 3) ? 'rd' : 'th';
	formatString = formatString.replace("#YYYY#", YYYY).replace("#YY#", YY).replace("#MMMM#", MMMM).replace("#MMM#", MMM).replace("#MM#", MM).replace("#M#", M).replace("#DDDD#", DDDD).replace("#DDD#", DDD).replace("#DD#", DD).replace("#D#", D).replace("#th#", th);
	h = (hhh = this.getHours());
	if (h == 0) {
		h = 24;
	}
	if (h > 12) {
		h -= 12;
	}
	hh = h < 10 ? ('0' + h) : h;
	hhhh = hhh < 10 ? ('0' + hhh) : hhh;
	AMPM = (ampm = hhh < 12 ? 'am' : 'pm').toUpperCase();
	mm = (m = this.getMinutes()) < 10 ? ('0' + m) : m;
	ss = (s = this.getSeconds()) < 10 ? ('0' + s) : s;
	return formatString.replace("#hhhh#", hhhh).replace("#hhh#", hhh).replace("#hh#", hh).replace("#h#", h).replace("#mm#", mm).replace("#m#", m).replace("#ss#", ss).replace("#s#", s).replace("#ampm#", ampm).replace("#AMPM#", AMPM);
};

function makeEventItemHtml(event) {
	var html = "<div class='event-panel-item'>";
	html += "<div class='event-panel-item-date'>";
	html += new Date(event.date).customFormat('#DD#/#MM#/#YYYY# #hh#:#mm#:#ss#');
	html += "</div>";
	html += "<div class='event-panel-item-icon'>";
	html += "<i class='toast-icon small material-icons light-blue-text text-lighten-4'>error</i>";
	html += "</div>";
	html += "<div class='event-panel-item-message'>";
	html += event.message;
	html += "</div>";
	html += "</div>";
	return html;
}

function makeUnitIdTable1(id, name, phone, unitId) {
	var html = "<tr>";
	html += "<td><img src='/tracker/img/user-icon.png' alt='' class='circle driver-icon'></td>";
	html += "<td class='light-blue-text text-darken-3 text-center'>";
	html += name;
	html += "</td>";
	html += "<td class='light-blue-text text-darken-3 text-center'>";
	html += phone;
	html += "</td>";
	html += "<td class='light-blue-text text-darken-3 text-center'>";
	html += "0";
	html += "</td>";
	html += "<td class='light-blue-text text-darken-3 text-center'>";
	html += "0";
	html += "</td>";
	html += "<td class='light-blue-text text-darken-3 text-center'>";
	html += "Not Yet";
	html += "</td>";
	html += "</tr>"
	return html;
}

function makeGeofneceCollectionItemHtml(geofence) {
	var html = "<div id='" + geofence.id + "' class='geofence-listview-collection-item waves-effect waves-blue' onclick='panToMarker(" + geofence.lat + ", " + geofence.lng + ")'>";
	html += "<div class='geofence-listivew-collection-item-1'>";
	if (geofence instanceof google.maps.Rectangle) {
		html += "<img src='/tracker/img/tool-rectangle.png' class='geofence-listivew-icon-img'>";
	} else if (geofence instanceof google.maps.Circle) {
		html += "<img src='/tracker/img/tool-circle.png' class='geofence-listivew-icon-img'>";
	}
	html += "</div>";
	html += "<div class='geofence-listivew-collection-item-2'>";
	html += geofence.id;
	html += "</div>";
	html += "<div class='geofence-listivew-collection-item-3'>";
	html += "<i class='small-icon material-icons tooltipped grey-text text-lighten-1' onclick='removeGeofence(event, \"" + geofence.id + "\")'>delete</i>"
	html += "</div>";
	html += "</div>";
	return html;
}

function TxtOverlay(pos, txt, cls, map) {
	this.pos = pos;
	this.txt_ = txt;
	this.cls_ = cls;
	this.map_ = map;
	this.div_ = null;
	this.setMap(map);
}

function initTxtOverlay() {

	TxtOverlay.prototype = new google.maps.OverlayView();

	TxtOverlay.prototype.onAdd = function() {
		var div = document.createElement('DIV');
		div.className = this.cls_;
		div.innerHTML = this.txt_;
		this.div_ = div;
		var overlayProjection = this.getProjection();
		var position = overlayProjection.fromLatLngToDivPixel(this.pos);
		div.style.left = position.x + 13 + 'px';
		div.style.top = position.y - 26 + 'px';
		var panes = this.getPanes();
		panes.floatPane.appendChild(div);
	}

	TxtOverlay.prototype.draw = function() {
		var overlayProjection = this.getProjection();
		var position = overlayProjection.fromLatLngToDivPixel(this.pos);
		var div = this.div_;
		div.style.left = position.x + 13 + 'px';
		div.style.top = position.y - 26 + 'px';

	}

	TxtOverlay.prototype.onRemove = function() {
		this.div_.parentNode.removeChild(this.div_);
		this.div_ = null;
	}

	TxtOverlay.prototype.hide = function() {
		if (this.div_) {
			this.div_.style.visibility = "hidden";
		}
	}

	TxtOverlay.prototype.show = function() {
		if (this.div_) {
			this.div_.style.visibility = "visible";
		}
	}

	TxtOverlay.prototype.toggle = function() {
		if (this.div_) {
			if (this.div_.style.visibility == "hidden") {
				this.show();
			} else {
				this.hide();
			}
		}
	}

	TxtOverlay.prototype.toggleDOM = function() {
		if (this.getMap()) {
			this.setMap(null);
		} else {
			this.setMap(this.map_);
		}
	}
}
