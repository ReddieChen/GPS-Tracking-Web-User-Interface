# GPS-Tracking-Web-User-Interface
Use technologies including Spring WebSocket, Google Maps, Materialize to build this demo application.

This is a demo project for proof of concept. I try to bring a great and fancy user interface to people for them to monitor and manage their vehicles using this web application.

I design five major functions such as Tracking, Fleet, POI, Geo-Fence and Playback for POC purpose.

1. Tracking

Users can monitor all the positions of their vechicles on Google Maps, there is a Traking Unit List to the screen left side, uses can drag this panel to any their desird postions, once user click on any item in Traking Unit List, maps will pan to the corresponding position, the corresponding marker will bounce for a while as well.

I adopt Spring WebSocket to transmit vehicle real-time position data to browsers, so that users can always know the vehicle'positions on the maps and events such as Engine-On and Engine-Off.

Take a look of screenshot:

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/tracking.png)

2. Fleet

Manager can assign the GPS devices to the vehicles, and assign employees to the vehicles, just a table as shown as the following:

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/fleet.png)

3. POI

Just like Tracking, users can see the POIs position on the maps, there is POIs list panel to the left side of screen.
When vehicle enters or leaves POIs, system will send notifications to you immediately.

Take a look of screenshot:

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/poi.png)

4. GeoFence

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/geofence.png)

5. Playback

![ScreenShot](https://raw.github.com/ReddieChen/GPS-Tracking-Web-User-Interface/master/images/playback.png)



