# Wallet Manager
Due to the advancement of online fast payment technology and online shopping popularity, people no longer need to use cash or bank cards. While improving technology and efficiency, people usually ignore their excessive spending. Simple consumption methods often do not make consumers feel that they are spending money to consume more than their budget. We hope that with the help of this software, users can better manage their finances, understand their consumption level and the proportion of necessary expenses to avoid the impact of excessive consumption on their lives. Our target audience will be the people who want to save money or have better management of their finances.

## Set Up
Confirm that your system has a Java 1.8 environment. 
A file “sqlite-jdbc-3.23.1.jar” is included in the folder and has already been added to the build path of the application.
“project.db” should be located in the same folder.

## How to Use Wallet Manager

### Sign Up/ Login
Upon startup, the user will land on the Wallet Manager Login page. To create a new user, select “sign up”.
User must enter a username that does not yet exist in the database and a password. Once the new user has submitted using the “sign up” button, the user can return to the login page to log in to the system.
Once logged in, the homepage will show all the information about the user’s purchases.
To log out, close the homepage window.
<p align="center">
  <img src="screenshots/login.png" title="login page">
  <img src="screenshots/sign up.png" title="sign up page">
</p>

### Homepage
By default, information about the user’s purchases is displayed in the order that the entries are added to the database. The user can view the purchases in ascending or descending order by toggling the header. For example, to view the purchases by descending price, the user can click on “Price” in the header until a downward facing carrot is shown. 
To view the purchases by category, the user can select the category from the drop down menu on the bottom left. The total amount spent in the category is displayed beside the dropdown menu. By default, the category selected is “total”.
The homepage has a pie chart representing the purchases based on the categories. The chart is automatically generated each time an entry is added or deleted by the user.
<p align="center">
  <img src="screenshots/homepage.png" title="homepage">
</p>

### Adding an Entry
To add an entry, the user can select “add” from the homepage.
User must enter or select a date, category, and price for the purchase. The user can also add a note to describe the purchase. Once the user submits valid information, a success message is returned and the user can return to the homepage by closing the “add product” window.
The homepage will be refreshed and now lists the new entry information.
<p align="center">
  <img src="screenshots/add page.png" title="add page">
</p>

### Deleting an Entry
To delete an entry, the user can select an entry from the home page and click the “delete” button on the bottom right.
The homepage will automatically refresh when the database is updated. 

### Check Budget
User can manage their budget by selecting “Check Budget” from the hom
For each category, a budget that the user does not want to exceed should be entered in the text box.
When user clicks “check”, the total spent in each category is subtracted from the budget and the remaining budget available to spend is displayed. 
<p align="center">
  <img src="screenshots/budget manager.png" title="check budger">
</p>

