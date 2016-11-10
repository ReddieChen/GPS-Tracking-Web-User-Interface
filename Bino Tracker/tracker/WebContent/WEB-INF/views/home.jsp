<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0">
<meta charset="utf-8">
<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" rel="stylesheet">
<title>Binodata Tracker</title>
<link href="<c:url value="/css/home.css" />" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="map"></div>
	<!-- Unit List -->
	<div id="object-listview-panel" class="listview-panel white z-depth-3 moveable">
		<div class="listview-header listview-header light-blue darken-4">
			<div class="listview-header-icon">
				<i class="small material-icons light-blue-text text-lighten-5">navigation</i>
			</div>
			<span class="listview-header-title grey-text text-lighten-5">位置情報</span>
		</div>
		<div class="listview-search">
			<div class="listview-search-input">
				<input class="light-blue-text text-darken-4" id="search" type="text" placeholder="車両検索">
			</div>
			<div class="listview-search-icon">
				<i class="small material-icons grey-text text-lighten-5">search</i>
			</div>
		</div>
		<div class="listview-column-header">
			<div class="object-listview-column">車両</div>
			<div class="listview-icon-column">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="GSM">settings_input_antenna</i>
			</div>
			<div class="listview-icon-column">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="GPS">language</i>
			</div>
			<div class="listview-icon-column">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="エンジン">power_settings_new</i>
			</div>
		</div>
		<div>
			<c:forEach var="position" items="${positions}">
				<div class="object-listview-collection-item waves-effect waves-blue" onclick="unitOnClick($(this), '${position.unitId}')">
					<div class="object-listview-column">${position.unitId}</div>
					<div class="listview-icon-column">
						<div
							class="
							<c:choose>
								<c:when test="${position.extraGsm == 0 ||  position.extraGsm == 99}">
									<c:out value="${'red-circle'}" escapeXml="true"/>
								</c:when>
							 	<c:otherwise>
							 		<c:out value="${'green-circle'}" escapeXml="true"/>
							 	</c:otherwise>
							</c:choose>
						"></div>
					</div>
					<div class="listview-icon-column">
						<div
							class="
							<c:choose>
								<c:when test="${position.extraGps >= 4}">
									<c:out value="${'green-circle'}" escapeXml="true"/>
								</c:when>
							 	<c:otherwise>
							 		<c:out value="${'red-circle'}" escapeXml="true"/>
							 	</c:otherwise>
							</c:choose>
						"></div>
					</div>
					<div class="listview-icon-column">
						<div
							class="
							<c:choose>
								<c:when test="${position.extraEngineOn}">
									<c:out value="${'green-circle'}" escapeXml="true"/>
								</c:when>
							 	<c:otherwise>
							 		<c:out value="${'red-circle'}" escapeXml="true"/>
							 	</c:otherwise>
							</c:choose>
						"></div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<!-- Playback Unit List -->
	<div id="playback-object-listview-panel" class="listview-panel white z-depth-3 moveable">
		<div class="listview-header listview-header light-blue darken-4">
			<div class="listview-header-icon">
				<i class="small material-icons light-blue-text text-lighten-5">restore</i>
			</div>
			<span class="listview-header-title grey-text text-lighten-5">走行履歴</span>
		</div>
		<div class="listview-search">
			<div class="listview-search-input">
				<input class="light-blue-text text-darken-4" id="search" type="text" placeholder="車両検索">
			</div>
			<div class="listview-search-icon">
				<i class="small material-icons grey-text text-lighten-5">search</i>
			</div>
		</div>
		<div class="listview-column-header">
			<div class="object-listview-column">車両</div>
			<div class="listview-icon-column">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="GSM">settings_input_antenna</i>
			</div>
			<div class="listview-icon-column">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="GPS">language</i>
			</div>
			<div class="listview-icon-column">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="エンジン">power_settings_new</i>
			</div>
		</div>
		<div class="playback-object-listview-collection-item waves-effect waves-blue">
			<div class="object-listview-column">車両01</div>
			<div class="listview-icon-column">
				<div class="green-circle"></div>
			</div>
			<div class="listview-icon-column">
				<div class="green-circle"></div>
			</div>
			<div class="listview-icon-column">
				<div class="green-circle"></div>
			</div>
		</div>
	</div>

	<!-- POI List -->
	<div id="poi-listview-panel" class="listview-panel white z-depth-3 moveable">
		<div class="listview-header light-blue darken-4">
			<div class="listview-header-icon">
				<i class="small material-icons light-blue-text text-lighten-5">location_on</i>
			</div>
			<span class="listview-header-title grey-text text-lighten-5">ポイント</span>
		</div>
		<div class="listview-search">
			<div class="listview-search-input">
				<input class="light-blue-text text-darken-4" id="search" type="text" placeholder="ポイント検索">
			</div>
			<div class="listview-search-icon">
				<i class="small material-icons grey-text text-lighten-5">search</i>
			</div>
		</div>
		<div class="listview-column-header">
			<div class="poi-listview-column">ポイント</div>
			<div class="listview-icon-column ">
				<i class="small-icon material-icons tooltipped" data-position="bottom" data-delay="50" data-tooltip="レンジ">track_changes</i>
			</div>
		</div>
		<div class="scrollable">
			<c:forEach var="poi" items="${pois}">
				<div class="poi-listview-collection-item waves-effect waves-blue" onclick="poiOnClick($(this), ${poi.latitude}, ${poi.longitude}, ${poi.range})">
					<div class="poi-listview-collection-item-1">
						<img src="/tracker/img/${poi.photo}" class="circle driver-icon">
					</div>
					<div class="poi-listview-collection-item-2">${poi.name}</div>
					<div class="poi-listview-collection-item-3">
						<span class="new badge">${poi.range}</span>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<!-- Geofence tool -->
	<div id="geofence-tool-panel" class="white z-depth-3">
		<div class="geofence-tool-header light-blue darken-4">
			<i class="small material-icons light-blue-text text-lighten-5">games</i>
		</div>
		<div id="geofence-normal" class="geofence-button grey lighten-5">
			<div class="geofence-button-icon">
				<img src="/tracker/img/tool-normal.png" class="geofence-button-icon-img">
			</div>
			<div class="geofence-button-label">ポイント</div>
		</div>
		<div id="geofence-rectangle" class="geofence-button">
			<div class="geofence-button-icon">
				<img src="/tracker/img/tool-rectangle.png" class="geofence-button-icon-img">
			</div>
			<div class="geofence-button-label">四角</div>
		</div>
		<div id="geofence-circle" class="geofence-button">
			<div class="geofence-button-icon">
				<img src="/tracker/img/tool-circle.png" class="geofence-button-icon-img">
			</div>
			<div class="geofence-button-label">丸</div>
		</div>
	</div>

	<!-- Geofence list -->
	<div id="geofence-listview-panel" class="listview-panel white z-depth-3">
		<div class="listview-header listview-header light-blue darken-4">
			<div class="listview-header-icon">
				<i class="small material-icons light-blue-text text-lighten-5">games</i>
			</div>
			<span class="listview-header-title grey-text text-lighten-5">ゾーン</span>
		</div>
		<div class="listview-search">
			<div class="geofence-listview-search-input">
				<input class="light-blue-text text-darken-4" id="geofence-search" type="text" placeholder="ゾーン検索">
			</div>
			<div class="listview-search-icon">
				<i class="small material-icons grey-text text-lighten-5">search</i>
			</div>
		</div>
		<div id="geofence-content"></div>
	</div>

	<!-- Left Side Navigation -->
	<div id="left-side-navigation" class="light-blue darken-4 z-depth-3">
		<div id="left-side-navigation-employee" class="light-blue darken-2 waves-effect waves-light left-side-navigation-button">
			<div class="left-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">supervisor_account</i>
			</div>
			<div class="left-side-navigation-label grey-text text-lighten-5">ドライバー</div>
		</div>

		<div id="left-side-navigation-device-management" class="waves-effect waves-light left-side-navigation-button">
			<div class="left-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">picture_in_picture</i>
			</div>
			<div class="left-side-navigation-label grey-text text-lighten-5">端末</div>
		</div>

		<div id="left-side-navigation-violation" class="waves-effect waves-light left-side-navigation-button">
			<div class="left-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">announcement</i>
			</div>
			<div class="left-side-navigation-label grey-text text-lighten-5">違反履歴</div>
		</div>

	</div>

	<!-- Right Side Navigation  -->
	<div id="right-side-navigation" class="light-blue darken-4 z-depth-3">
		<div class="right-side-navigation-small-button">
			<i class="small material-icons grey-text text-lighten-5 right-side-navigation-icon-user">perm_identity</i>
		</div>
		<div id="right-side-events" class="right-side-navigation-small-button">
			<i id="right-side-events-icon" class="small material-icons grey-text text-lighten-5 right-side-navigation-icon-alert">new_releases</i>
		</div>
		<div id="right-side-navigation-tracking" class="right-side-navigation-button light-blue darken-2 waves-effect waves-light">
			<div class="right-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">navigation</i>
			</div>
			<div class="right-side-navigation-label grey-text text-lighten-5">位置情報</div>
		</div>
		<div id="right-side-navigation-fleet" class="right-side-navigation-button waves-effect waves-light">
			<div class="right-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">view_list</i>
			</div>
			<div class="right-side-navigation-label grey-text text-lighten-5">車両管理</div>
		</div>
		<div id="right-side-navigation-poi" class="right-side-navigation-button waves-effect waves-light">
			<div class="right-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">location_on</i>
			</div>
			<div class="right-side-navigation-label grey-text text-lighten-5">ポイント</div>
		</div>
		<div id="right-side-navigation-geofence" class="right-side-navigation-button waves-effect waves-light">
			<div class="right-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">games</i>
			</div>
			<div class="right-side-navigation-label grey-text text-lighten-5">ゾーン</div>
		</div>
		<div id="right-side-navigation-playback" class="right-side-navigation-button waves-effect waves-light">
			<div class="right-side-navigation-icon">
				<i class="medium material-icons light-blue-text text-lighten-5">restore</i>
			</div>
			<div class="right-side-navigation-label grey-text text-lighten-5">走行履歴</div>
		</div>
	</div>

	<!-- Employees -->
	<div id="employee-panel">
		<div id="employee-panel-1">
			<div class="panel-header grey-text text-lighten-5">ドライバー管理</div>
			<div class="panel-action">
				<a id="employee-create-button" class="waves-effect waves-light btn light-blue darken-2"><i class="material-icons left">playlist_add</i>新規登録</a>
			</div>
			<div class="panel-content">
				<table id="employee-table" class="striped bordered">
					<thead class="table-header">
						<tr>
							<th class=""></th>
							<th class="text-center light-blue-text text-darken-4">氏名</th>
							<th class="text-center light-blue-text text-darken-4">連絡先</th>
							<th class="text-center light-blue-text text-darken-4">Rank</th>
							<th class="text-center light-blue-text text-darken-4">運転時間</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="driver" items="${drivers}">
							<tr>
								<td><img src="/tracker/img/${driver.photo}" alt="" class="circle driver-icon"></td>
								<td class="light-blue-text text-darken-3 text-center">${driver.name}</td>
								<td class="light-blue-text text-darken-3 text-center">${driver.phone}</td>
								<td class="light-blue-text text-darken-3 text-center">${driver.violentRecord}</td>
								<td class="light-blue-text text-darken-3 text-center">${driver.workHour}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel-splitter"></div>
		<div id="employee-panel-2">
			<div class="panel-header grey-text text-lighten-5">端末一覧</div>
			<div class="panel-content">
				<table id="device-table-1" class="striped bordered">
					<thead class="table-header">
						<tr>
							<th class=""></th>
							<th class="text-center light-blue-text text-darken-4">グループ</th>
							<th class="text-center light-blue-text text-darken-4">車両</th>
							<th class="text-center light-blue-text text-darken-4">運転者</th>
							<th class="text-center light-blue-text text-darken-4">ナンバー</th>
							<th class="text-center light-blue-text text-darken-4">車体</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="unit" items="${units}">
							<tr>
								<td><img src="/tracker/img/${unit.photo}" alt="" class="circle driver-icon"></td>
								<td class="light-blue-text text-darken-3">${unit.unitGroupId}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.unitId}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.currentDriver}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.vehiclePlate}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.brand}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Devices -->
	<div id="device-panel">
		<div id="device-panel-1">
			<div class="panel-header grey-text text-lighten-5">端末管理</div>
			<div class="panel-content">
				<table id="device-table-2" class="striped bordered">
					<thead class="table-header">
						<tr>
							<th class=""></th>
							<th class="text-center light-blue-text text-darken-4">IMEI</th>
							<th class="text-center light-blue-text text-darken-4">車両</th>
							<th class="text-center light-blue-text text-darken-4">グループ</th>
							<th class="text-center light-blue-text text-darken-4">ドライバー</th>
							<th class="text-center light-blue-text text-darken-4">ナンバー</th>
							<th class="text-center light-blue-text text-darken-4">車種</th>
							<th class="text-center light-blue-text text-darken-4">年式</th>
							<th class="text-center light-blue-text text-darken-4">メーカー</th>
							<th class="text-center light-blue-text text-darken-4">モデル</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="unit" items="${units}">
							<tr>
								<td><img src="/tracker/img/${unit.photo}" alt="" class="circle driver-icon"></td>
								<td class="light-blue-text text-darken-3">${unit.imei}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.unitId}</td>
								<td class="light-blue-text text-darken-3">${unit.unitGroupId}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.currentDriver}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.vehiclePlate}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.vehicleType}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.year}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.brand}</td>
								<td class="light-blue-text text-darken-3 text-center">${unit.model}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel-splitter"></div>
		<div id="device-panel-2">
			<div class="panel-header grey-text text-lighten-5">端末ステータス</div>
			<div class="panel-content light-blue-text text-darken-3">Track is not assigned or blocked</div>
		</div>
	</div>

	<!-- Violation -->
	<div id="violation-panel">
		<div id="violation-pnael-1">
			<div class="panel-header grey-text text-lighten-5">違反履歴</div>
			<div class="panel-action">
				<div>
					<input type="date" class="datepicker-1" value="2016-11-01"> <img src="/tracker/img/calendar.png" class="datepicker-icon-1">
				</div>
				<div class="violation-panel-action-1">~</div>
				<div>
					<input type="date" class="datepicker-1" value="2016-11-05"> <img src="/tracker/img/calendar.png" class="datepicker-icon-1">
				</div>
				<a class="waves-effect waves-light btn light-blue darken-2 violation-panel-action-2"><i class="material-icons left">search</i>Search</a>
			</div>
			<div class="panel-content">
				<table id="violation-table" class="bordered">
					<thead class="table-header">
						<tr>
							<th class=""></th>
							<th class="text-center light-blue-text text-darken-4">氏名</th>
							<th class="text-center light-blue-text text-darken-4">運転時間</th>
							<th class="text-center light-blue-text text-darken-4">スピードオーバー</th>
							<th class="text-center light-blue-text text-darken-4">急加速</th>
							<th class="text-center light-blue-text text-darken-4">急ハンドル</th>
							<th class="text-center light-blue-text text-darken-4">急ブレーキ</th>
							<th class="text-center light-blue-text text-darken-4">Total</th>
							<th class="text-center light-blue-text text-darken-4">Rank</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="driver" items="${drivers}">
							<tr class="violation-normal violation-item" onclick="violationOnClick($(this), '${driver.id}')">
								<td><img src="/tracker/img/${driver.photo}" alt="" class="circle driver-icon"></td>
								<td class="">${driver.name}</td>
								<td class="text-center">${driver.workHour}</td>
								<td class="text-center">${driver.violentSpeeding}</td>
								<td class="text-center">${driver.violentHarshAcceleration}</td>
								<td class="text-center">${driver.violentHarshCornering}</td>
								<td class="text-center">${driver.violentHarshBraking}</td>
								<td class="text-center">${driver.violentTotal}</td>
								<td class="text-center">${driver.violentRecord}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel-splitter"></div>
		<div id="violation-panel-2">
			<div class="panel-header grey-text text-lighten-5">Violation Detail</div>
			<div id="map2"></div>
			<div class="panel-content">
				<table id="violation-detail-table-1" class="bordered">
					<thead class="table-header">
						<tr>
							<th class="text-center light-blue-text text-darken-4">Date Time</th>
							<th class="text-center light-blue-text text-darken-4">Event</th>
							<th class="text-center light-blue-text text-darken-4">Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="v" items="${violationDetails1}">
							<tr class="violation-normal violation-detail-item" onclick="violationDetailOnClick($(this), ${v.longitude}, ${v.latitude})">
								<td class="text-center">${v.date}</td>
								<td class="text-center">${v.event}</td>
								<td class="text-center">${v.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<table id="violation-detail-table-2" class="bordered">
					<thead class="table-header">
						<tr>
							<th class="text-center light-blue-text text-darken-4">Date Time</th>
							<th class="text-center light-blue-text text-darken-4">Event</th>
							<th class="text-center light-blue-text text-darken-4">Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="v" items="${violationDetails2}">
							<tr class="violation-normal violation-detail-item" onclick="violationDetailOnClick($(this), ${v.longitude}, ${v.latitude})">
								<td class="text-center">${v.date}</td>
								<td class="text-center">${v.event}</td>
								<td class="text-center">${v.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<table id="violation-detail-table-3" class="bordered">
					<thead class="table-header">
						<tr>
							<th class="text-center light-blue-text text-darken-4">Date Time</th>
							<th class="text-center light-blue-text text-darken-4">Event</th>
							<th class="text-center light-blue-text text-darken-4">Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="v" items="${violationDetails3}">
							<tr class="violation-normal violation-detail-item" onclick="violationDetailOnClick($(this), ${v.longitude}, ${v.latitude})">
								<td class="text-center">${v.date}</td>
								<td class="text-center">${v.event}</td>
								<td class="text-center">${v.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<table id="violation-detail-table-4" class="bordered">
					<thead class="table-header">
						<tr>
							<th class="text-center light-blue-text text-darken-4">Date Time</th>
							<th class="text-center light-blue-text text-darken-4">Event</th>
							<th class="text-center light-blue-text text-darken-4">Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="v" items="${violationDetails4}">
							<tr class="violation-normal violation-detail-item" onclick="violationDetailOnClick($(this), ${v.longitude}, ${v.latitude})">
								<td class="text-center">${v.date}</td>
								<td class="text-center">${v.event}</td>
								<td class="text-center">${v.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<table id="violation-detail-table-5" class="bordered">
					<thead class="table-header">
						<tr>
							<th class="text-center light-blue-text text-darken-4">Date Time</th>
							<th class="text-center light-blue-text text-darken-4">Event</th>
							<th class="text-center light-blue-text text-darken-4">Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="v" items="${violationDetails5}">
							<tr class="violation-normal violation-detail-item" onclick="violationDetailOnClick($(this), ${v.longitude}, ${v.latitude})">
								<td class="text-center">${v.date}</td>
								<td class="text-center">${v.event}</td>
								<td class="text-center">${v.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Playback control panel -->
	<div id="playback-control-panel" class="z-depth-3">
		<div class="playback-pnael-header"></div>
		<div class="playback-pnael-content">
			<div class="playback-pnael-content-top">
				<div class="playback-pnael-content-top-1">
					<input type="date" class="datepicker" value="2016-11-01"> <img src="/tracker/img/calendar.png" class="datepicker-icon">
				</div>
				<div class="playback-pnael-content-top-2">
					<div id="playback-play-button" class="text-center z-depth-1 waves-effect waves-light">
						<i class="small material-icons white-text">play_arrow</i>
					</div>
				</div>
				<div class="playback-pnael-content-top-3">
					<div id="playback-speed1" class="playback-fastfoward-button text-center playback-fastfoward-button-selected">x1</div>
					<div id="playback-speed2" class="playback-fastfoward-button text-center">x2</div>
					<div id="playback-speed3" class="playback-fastfoward-button text-center">x4</div>
				</div>
				<div class="playback-pnael-content-top-4">
					<div id="playback-time">00:00 00</div>
				</div>
			</div>
			<div class="playback-pnael-content-bottom">
				<div id="playback-current-frame">
					<div id="playback-current-point"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- Events -->
	<div id="event-pnael" class="z-depth-3"></div>

	<!-- Modals -->
	<div id="create-employee-modal" class="modal modal-fixed-footer">
		<div class="modal-content">
			<h4 class="model-title light-blue-text text-darken-4">Create an Employee</h4>
			<div class="modal-left-div">
				<div class="modal-input-div">
					<div class="modal-input-div-label light-blue-text text-lighten-5">Driver ID</div>
					<input id="driver-id" class="modal-input-div-input light-blue-text text-darken-4" type="text" placeholder="Input driver Id here">
				</div>
				<div class="modal-input-div">
					<div class="modal-input-div-label light-blue-text text-lighten-5">Driver Name</div>
					<input id="dirver-name" class="modal-input-div-input light-blue-text text-darken-4" type="text" placeholder="Input driver name here">
				</div>
				<div class="modal-input-div">
					<div class="modal-input-div-label light-blue-text text-lighten-5">Phone Number</div>
					<input id="driver-phone" class="modal-input-div-input light-blue-text text-darken-4" type="text" placeholder="Input phone number here">
				</div>
				<div class="modal-input-div">
					<div class="modal-input-div-label light-blue-text text-lighten-5">Unit Id</div>
					<select id="unit-select" class="modal-input-div-select light-blue-text text-darken-4">
						<option value="" disabled selected>Choose desired unit id</option>
						<option value="1">車両 06</option>
					</select>
				</div>
			</div>
			<div class="modal-right-div">
				<img src="/tracker/img/user-icon.png" class="user-photo-img">
			</div>
		</div>
		<div class="modal-footer">
			<a id="create-driver-button" href="#!" class="modal-action modal-close waves-effect waves-green btn-flat light-blue-text text-darken-4 btn-flat">OK</a> <a href="#!"
				class="modal-action modal-close waves-effect waves-green btn-flat light-blue-text text-darken-4 btn-flat">Cancel</a>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
	<script src="//cdn.jsdelivr.net/sockjs/1/sockjs.min.js" type="text/javascript"></script>
	<script src="js/stomp.js" type="text/javascript" type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js" type="text/javascript"></script>
	<script src="js/data.js" type="text/javascript" type="text/javascript"></script>
	<script src="js/home.js" type="text/javascript" type="text/javascript"></script>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBK5mSp-25Ckw-walLsu-AZrEZ-Wyyrl8c&callback=initMap&libraries=drawing"></script>
</body>
</html>