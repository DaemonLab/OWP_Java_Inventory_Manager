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

### Step 2
Coming Soon...
