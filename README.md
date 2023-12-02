# FindMyClassmates README FILE

To Run the app:
Pressing the play button will launch the app in the login screen. There are two ways you can start using the app:

-You can login:

  -Some sample login info is:
  
    "lb@usc.edu", password = "123123"
    
    "anniez@usc.edu", password = "123123"
    
-You can signup:

  -Please fill in all of the required fields
  
  -student ID must be 10 digits long
  
  -KNOWN BUG: the app WILL CRASH if a photo is not submitted in the "upload profile photo" button, so please do :)

Once inside the app, you will be directed to the Classes Tab.

-You can click on a dropdown menu which will display all of the classes in the department.

-Clicking a class will display options to register/drop the class, see reviews, or check the roster

-Registering/dropping the class will be done with one click of the button

-If you press reviews, you will be redirected to the reviews page

  -Reviews are ordered like post-it notes, so you must scroll to see all the reviews.
  
  -You can leave a upvote/downvote other reviews, and leave your own review
  
  -Currently, you can only DELETE your own review (once you have made it) as editing the review has a couple of unfixed bugs
  
-If you check the roster, you can click on the students to view their profile

  -Once in their profile, you can click "open chat" to be redirected to a chat interface with said student
  
  -If you click "block", you will be unable to send or receive messages from the blocked student.
  
-The chats tab is the middle tab, where you can see any chats that you have opened with other students.

  -The chat will update in real time (with Firebase), and will track a "delivered/seen" status on messages
  
-The Profile tab will show your profile picture, as well as editable profile attributes.

  -None of the attributes can be blank!
  
  -The ID attribute must be 10 digits
  
  -The status attribute must be either 'Undergraduate Student', 'Graduate Student', 'Faculty', or 'Staff'
  
  -Press "Save Changes" to save changes
  
  -The logout button will redirect you to the login screen.
  

-------------------------------------------------------
Emulator Device: Google Pixel 2

API version: 24
-------------------------------------------------------

Updated README, since 2.4:

Fixed BUGS: 

Upvotes/Downvotes can no longer happen infinitely

Registering and dropping a class updates properly alongside the Firebase database

Found a corrupted data within the database that was destroying the app flow, preventing the app from swiping to other tabs aside from the primary class tab.
  
  -Previously, swiping to the chat tab and profile tab would crash the app, due to the database trying to load faulty data.

Editing your profile is now functional

  -you can now upload a new profile picture

  -updating the profile will also update the firebase database properly

Editing a review is now functional, previously the review item was not updating properly.

  -a user can now update a review that they have left previously

  -deleting a user-left review also now works properly
