# TwojaOpinia - Full Stack Application

TwojaOpinia is a comprehensive survey application designed for both users and administrators. With a user-friendly interface, it facilitates user registration, login, and offers a seamless experience for sharing opinions on various surveys. Furthermore, administrators have the capability to manage surveys, users, and analyze data with users' answers etc.

## Technologies

### Front-End
- Java FX
- Java FXML
- CSS

### Back-End
- Java
- MySQL
- XAMPP (Local data base)

## Functions

### User

   1. **Registration and Login**
      - Users can create new account and log in to the application.

https://github.com/f1scher7/twoja-opinia/assets/115926717/0ebf1009-ab0e-4af5-9c56-15d1a2bc1015

   2. **User Dashboard**
      - Upon logging in, users will be welcomed with a dashboard featuring a greeting message and a quick snapshot of the application's survey activity statistics. On the left side, users can find their login avatar (#TODO) along with a menu that includes options such as Available Surveys, History Surveys, Account Settings, Back to Dashboard, and Logout.
   
   ![UserDashboard](https://github.com/f1scher7/twoja-opinia/assets/115926717/1ab4d415-7d51-4e9f-a7f9-6d85c1854768)

   3. **Available Surveys**
      - In this section, users can search for surveys or opt to browse surveys based on popularity or the most recently added ones.

https://github.com/f1scher7/twoja-opinia/assets/115926717/ed78fd4c-991f-42eb-8334-ebd9f2404758

   4. **Survey Voting**
      - When a user clicks on the desired survey, the Survey Voting window will appear. This window includes brief information about the survey, the list of questions, buttons for navigating to the next and previous questions, and a button to cancel the survey.

https://github.com/f1scher7/twoja-opinia/assets/115926717/93468244-1c1a-4b51-baed-05fc42c1f032
     
   5. **History Surveys**
      - In this window, users can see which surveys have been completed by them.
   
https://github.com/f1scher7/twoja-opinia/assets/115926717/25203da6-04dd-4990-8273-415ceadf7bdb

   6. **Account settings**
      - Users have the ability to modify all their personal information, such as name, surname, login, password, email, etc.
   
   ![Account_Settings](https://github.com/f1scher7/twoja-opinia/assets/115926717/4ac27f6b-f3af-47fd-8f7a-bc36649fb1be)

   7. **Back to dashboard**
      - User can return to the dashboard.
   
   8. **Log out**
      - User can log out and return to the login window.


### Admin

   1. **Login**
      - Admins can log in to the application using a login and password, just like regular users.
   
   2. **Admin Dashboard**
      - Upon logging in, admins will be welcomed with a dashboard featuring a greeting message and a quick snapshot of the application's survey activity statistics. On the left side, admin can find their login avatar (#TODO) along with a menu that includes options such as Manage Surveys, Results Analyzer, Manage Users, History of Added Surveys by Admin, Back to Dashboard, and Logout.
   
   ![Admin_Dashboard](https://github.com/f1scher7/twoja-opinia/assets/115926717/cb7dc646-20bb-4f43-ae14-f31368fa16d9)

   3. **Manage Surveys**
      - In this section, the admin can add new surveys and save them to the database. Additionally, the admin can delete surveys by their ID in the database.
        
   ![Manage_Surveys_Window](https://github.com/f1scher7/twoja-opinia/assets/115926717/5ad7121c-e6be-4833-976b-a31bb16d08f9)

   4. **Results Analyzer**
      - In the Result Analyzer window, there is a search bar to find surveys for analysis. Once a survey is selected, a graph appears for each question in the survey. Additionally, administrators can apply filters based on the country, city, and age of users.
        
   ![Result_Analyzer](https://github.com/f1scher7/twoja-opinia/assets/115926717/85cd77ea-304c-4191-996a-1a86f0947459)

   7. **Manage Users**
      - Admins can create new user or admin accounts, delete user accounts by login, or check user data using information from the database..
   
   ![Manage_User](https://github.com/f1scher7/twoja-opinia/assets/115926717/c1f6811c-56c7-4d65-8ba0-8eb634a18930)

   8. **History of Added Surveys**
      - Admins can view information about surveys they created in a table format.

   ![Added_Surveys_History](https://github.com/f1scher7/twoja-opinia/assets/115926717/6add50de-fb70-4051-852f-a3a2f06b7b90)
   
   9. **Back to dashboard**
      - User can return to the dashboard.
      
   10. **Log out**
      - User can log out and return to the login window.

## Application Security
   - Hashing User Passwords with Salt.
   - Utilizing Prepared Statements for Code-Data Separation.
   - #TODO

## Author

Maks Szyło maksymilian.fischer7@gmail.com

## License

This project is available under the MIT License. Details can be found in the LICENSE.md file.
