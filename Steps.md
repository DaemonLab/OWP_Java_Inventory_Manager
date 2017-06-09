## Steps
### Step 1 -- Installing everything and testing
Install Eclipse, jdk, MySQL, and JDBC connector as given in the [README](README.md). Then open the mysql shell from command prompt. On default install, you can find it in the folder `C:\Program Files\MySQL\MySQL Workbench 6.3 CE`. Open command prompt in that directory (or cd into it) and then issue the following command:  
`mysql --user=root --password=mysqlpass`  
where u need to replace `mysqlpass` by the password for root account that you set during installation. Now, the mysql shell will open. Issue the following commands to create a database named `inventorydb`, which we will use in our project, we will also create a user named `inventoryuser` which will be used by our java application to login (as using root for every database is not recommended).  
```
CREATE DATABASE inventorydb;
CREATE USER 'inventoryuser'@'localhost' IDENTIFIED BY 'inventorypass';
GRANT ALL ON inventorydb.* TO 'inventoryuser'@'localhost' IDENTIFIED BY 'inventorypass';
```
We have set proper password for our user which will be used for login. For the first step though, lets just check that we can successfully connect to this database using the newly created user's credentials.  
Open Eclipse and create a new "Java Project". Add a new class named `InventoryManager` in the project. Now copy the mysql java connector jar file in the project's folder. Click on the project name in eclipse and press F5 to refresh. You will see the jar file in the package explorer pane. Right click on the project name and select "Properties" or press Alt+Enter. Goto "Libraries" tab and click on "Add JARs..." (first button on the window). Select the mysql connector jar file, it will appear in the "JARs and class folders on the build path" pane. Click OK to close the window.  
Type the code as given in "InventoryManager.java" file along with this project. The code is properly explained in comments. Build and run, you should be able to see "Connection Successful! :)" message in the Console pane on Eclipse window. This proves that we have succefully connected to mysql server. If you have changed the default instalation of MySQL then you might have to run the MySQL manually. Give yourself a pat on the back for getting this far! See you in the next step. :)  

### Step 2 -- Creating Login page and DatabaseHelper
We will create the swing GUI using code, if you wish you can look at downloading `WindowBuilder` in Eclipse to make the GUI via drag and drop. We will create a class `LoginPrompt` which will define a window used for Login into the database. We will also create a calss `DatabaseHelper` which will only contain static functions which will be used for all database related stuff, this helps keep code related to database in one file and work with only 1 connection object to the database. We will also clear the `InventoryManager` class and only create a new LoginPrompt in the main function. All the code is explained in comments.  
For now, the login prompt only displays a message indicated success/failure of login. Later on it will be used to open another window for managing the inventory.  
Many swing components and database functions are introduced in this step, take your time to go through the code and comments to understand them. Google is your friend for getting in-depth knowledge of all these components/functions. Happy Coding! :)  

### Step 3 -- HomePage and Adding entries into inventory
We'll add 2 more classes `HomeWindow` and `AddItemsScreen` both will extend JFrame and implement ActionListener. We will create a static function in `InventoryManager` which will be called by `LoginPrompt` on succeful login. This function will close the LoginPrompt and open the HomeWindow.  
HomeWindow will have 3 buttons to do different things. The `Add` button will open the `AddItemsScreen` which will allow adding items into the database table. A new function in `DatabaseHelper` is also created to take care of adding entries into the table. All this code should is either explained in comments or is simply derived from previous steps of this tutorial.  
To see the entries added into the database login into mysql in command prompt using the inventory user's name and password, i.e.:  
`mysql --user=inventoryuser --password=inventorypass`  
Issue the following commands to select the database and display all the entries in the table:  
```
use inventorydb;
select * from InventoryTable;
```
You will be able to see all the entries in the table which you can add on the `AddItemsScreen`. This was just to ensure our AddItemsScreen is working properly, in the next step we will allow viewing and editing the entries in our app itself.  

### Step 4 -- Allow viewing and editing database in another window
We will create a `ViewEditScreen` class in this step which will display all the rows of the database into a JTable. The benefit of using a `JTable` is that it allows editing of cells in the table and hence for updating the database we can see if the user has changed value in a cell. We will not allow changing the id as it is Primary Key in the database. We will populate the database in another thread so that the app does not hang while the data is being loaded. Also, since JTable does not provide a good event handler for when cells have been edited, so we will use a custom `TableCellListener` which will call our `actionPerformed` whenever a cell has been edited. The code for TableCellListener is acquired from [an online source](https://tips4java.wordpress.com/2009/06/07/table-cell-listener). Since it is not our code, hence we will put it into another java package.  
The code for `ViewEditScreen` has been thoroughly explained in the comments. We have also created a `WithdrawItemsScreen` which will be used to winthdraw items from inventory and print a receipt if required. We renamed `PRINT_ACTION_CMD` to `WITHDRAW_ACTION_CMD` as the later seems more appropriate. The backend code for WithdraItemsScreen will be completed in the next step. JTable and threads are introduced in this step, also update code for sql is used. Take your time to soak in all these concepts. Happy Coding! :)  

### Step 5 -- Allow proper withdrawing of items
In this step we will complete the withdrawing code, except the printing of receipt part. The `txtNames` will be changed to `cbNames` i.e. it will be made of ComboBox since we want to provide autocomplete support and it is easily available for combo boxes. We will use the library `GlazedLists`, download its latest jar file from [their official site](http://www.glazedlists.com) and put it into project folder. Then add the jar to build path like we did for mysql java connector. We will use a map to store all the info loaded from the database. The key will be names of item as we will use the name to identify the item. We will also use the keys to provide autocomplete. We will call `AutoCompleteSupport.install` function of GlazedLists and since it needs to be executed on the same thread as that of the Swing GUI hence we will request swing to call it using `SwingUtilities.invokeLater`.  
We will set ActionCommand for comboBoxes and the button so as to identify them when they call `actionPerformed`. We will also give names to JSpinners of quantity as they will call `stateChanged` when their value will change and they don't provide any actionCommand, so we need to identify them by their names. We will implement `ChangeListener` for this. The code will take care of changing the prices in the row and Total Price whenever either the quantity or the name of the product is changed. When user clicks on Withdraw the same items will be withdrawn from the inventory. The map will help us identify the quantity of items left. Currently we don't put any restrictions on how many items can be withdrawn, readers can easily add that check and not allow withdrawing more than the number of items present.  
We will add printing of receipt in the next step, we will also do some cosmetics like making the password field in the LoginPrompt better by hiding the text being typed. See you in the next step! :)  

### Step 6
Coming Soon...
