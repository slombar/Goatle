#Goatle
authors: Sadie Lombardi <slombardi@wpi.edu> & Amy Orozco <aorozco@wpi.edu>

##a long-term persistence strategy, such as using local filesystems or relational databases
We registered our app to firebase and from there cretated the database we used in our app. The database is composed of one collection being Posts. Each post has a username, date it was posted, post content and a collection of replies. A reply is composed of a username, date, and content. When the homepage loads, the information for the posts is retrieved from firestore. These posts are then stored and displayed in a recyclerView. When a post is clicked, a new screen is loaded. This new screen contains the post and any replies made to that post. The user is given the option to reply to the post. When a reply is created it is added to that post’s replies collection and the user is brought back to the previous screen with the new reply showing. The user also has the option to create a post when on the home screen. When a post is created it is added to the Posts collection. 

##a mobile sensor such as accelerometer, camera, or GPS; 
For our mobile sensor we used the accelerometer to return the user to the home screen and change the color of the when the user shakes the device. This was done using the SensorManager, SensorEvent, and SensorEventListener. When the user shakes the device, the speed of the acceleration is calculated with a distance formula. The distance formula calculates the difference between the last acceleration and the most recent acceleration, and if it is above a certain threshold, it triggers our event. It also displays a text, "Woah!"

##(iv) a network component that communicates with either your own servers or a managed service.
Our network component utilized Pusher as its managed service, and runs a node.js server locally in order to communicate with the Pusher API.
The Pusher API is used to send json data and store the message on our Goatle channel. When the user enters a status into the field and clicks the post button,
the JSON data is generated as an event via the Pusher API, and is then sent to the server. The server is actively listening for a new status to be posted, and
once it has received a status to post, it sends it to the Pusher API. Once it's posted, the Pusher API is then used to retrieve the status and display it via a
RecycleView on the same page.

##Compilation Instructions
###To run the local server
You need to cd into the src/user-status-backend folder in a terminal window and then run the command “node index.js”

##Design and Technical Achievements
###Colorblind Theme
According to Color Blind Awareness, color blindness affects 1 in 12 men  and 1 in 200 women. There are an estimated 300 million color blind people worldwide. It is important for developers to be conscious of how they design their themes for their apps because they should be accessible for all. For this design achievement, we created a colorblind friendly theme for our application. This will help our users who are colorblind and allow them to have a positive experience with the app. Since this app is based around improving the community of WPI, we wouldn’t want to leave out anyone with a disability. We researched many different colorblind friendly palettes and then created one of our own using color.adobe.com

###Local Server with Node.js
For this technical achievement, we created a locally hosted server using javascript and Node.js. We created a server that would be able to communicate with the Pusher API, instead of integrating our Firebase into the Pusher API. This is beneficial to our app because we are not storing status messages unnecessarily. The status messages are meant to be temporary, so it is completely unnecessary to store them in the database. Express is a web framework for javascript that is used to build servers that we used to achieve our locally hosted server.
