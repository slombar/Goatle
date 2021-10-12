#Goatle
authors: Sadie Lombardi <slombardi@wpi.edu> & Amy Orozco <aorozco@wpi.edu>

##a long-term persistence strategy, such as using local filesystems or relational databases

##a mobile sensor such as accelerometer, camera, or GPS; 

##(iv) a network component that communicates with either your own servers or a managed service.
Our network component utilized Pusher as its managed service, and runs a node.js server locally in order to communicate with the Pusher API.
The Pusher API is used to send json data and store the message on our Goatle channel. When the user enters a status into the field and clicks the post button,
the JSON data is generated as an event via the Pusher API, and is then sent to the server. The server is actively listening for a new status to be posted, and
once it has received a status to post, it sends it to the Pusher API. Once it's posted, the Pusher API is then used to retrieve the status and display it via a
RecycleView on the same page.