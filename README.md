# GPS-Tracking-Web-User-Interface
Use technologies including Spring WebSocket, Google Maps, Materialize to build this demo application.

This is a demo project for proof of concept. I try to bring a great and fancy user interface to people for them to monitor and manage their vehicles using this web application.

I design five major functions such as Tracking, Fleet, POI, Geo-Fence and Playback for POC purpose.

<h3 style="color:#01579b;">1. Tracking</h3>

Users can monitor all the positions of their vechicles on Google Maps, there is a traking unit list panel to the screen left side, uses can drag this panel to any their desird postions, once user click on any item in traking unit list, map will pan to the corresponding position, the corresponding marker will bounce for a while as well.

I adopt Spring WebSocket to transmit vehicle real-time position data to browsers, so that users can always know the vehicle'positions on the maps and events fired such as Engine-On and Engine-Off.

Take a look of the screenshot:

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/tracking.png)

<h3>2. Fleet</h3>

I desing this so that some kind of managers they can assign the GPS devices to the vehicles, and assign employees to the vehicles, just some simple table views as shown as the following:

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/fleet.png)

You can click on Employees and Devices button to switch tables.

<h3>3. POI</h3>

Just like Tracking, users can see the POI's positions on the map, there is POI list panel to the left side of screen.
Once vehicle enters or leaves some POIs, it will fire a event like POI-ENTERED and POI-LEFT, system will send notifications to you immediately.

Take a look of the screenshot:

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/poi.png)

<h3>4. GeoFence</h3>

In this function, user can add, edit or remove geo-fences on the map.

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/geofence.png)

<h3>5. Playback</h3>

Users are able to look the history vehicles position data using playback.

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/playback.png)





