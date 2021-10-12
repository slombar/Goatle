#Goatle
authors: Sadie Lombardi <slombardi@wpi.edu> & Amy Orozco <aorozco@wpi.edu>

##a long-term persistence strategy, such as using local filesystems or relational databases

##a mobile sensor such as accelerometer, camera, or GPS; 
For our mobile sensor we used the accelerometer to change the color of the posts on the home page when the user shakes the device.
This was done using the SensorManager, SensorEvent, and SensorEventListener. When the user shakes the device,
the speed of the acceleration is calculated with a distance formula. The distance formula
calculates the difference between the last acceleration and the most recent acceleration, and if it is
above a certain threshold, it triggers our event. It also displays a text, "Woah!"

##(iv) a network component that communicates with either your own servers or a managed service.
Our network component utilized Pusher as its managed service, and runs a node.js server locally in order to communicate with the Pusher API.
The Pusher API is used to send json data and store the message on our Goatle channel. When the user enters a status into the field and clicks the post button,
the JSON data is generated as an event via the Pusher API, and is then sent to the server. The server is actively listening for a new status to be posted, and
once it has received a status to post, it sends it to the Pusher API. Once it's posted, the Pusher API is then used to retrieve the status and display it via a
RecycleView on the same page.


##Compilation Instructions
###To run the local server
You need to cd into the src/user-status-backend folder in a terminal window and run the command "node index.js" before launching the app in order
for it to run properly. It must be ran on an emulator due to the fact that a physical device has a different IP address and cannot access
a locally hosted server.