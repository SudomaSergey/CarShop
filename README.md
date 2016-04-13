About the "Car Shop" Java application by Sergey Sudoma.

Link to youtube overview - https://youtu.be/YLs-pfmwFSg

GUI application is developed to be used as a terminal point-of-sale application in shop of any kind.
Main purpose is automation of sales process and possibility of generation of Stock and Sales reports .

Application is designed to work in two modes:
- CRUD mode with the DERBY DB
- ORM mode powered by hibernate with the MySQL DB

Main points of app:  
1. MVC model  
2. mode selection is performed via editing "launcher.properties" configuration file.     
   If properties file is unavailable or corrupted app launches the CRUD with the Derby DB mode.  
3. being launched application creates DB (in accordance to selected mode) and inserts initial data;  
4. list of goods and goods' images are loaded from the related table of DB;  
5. app allows to sell any goods stored in the appropriate table of the related DB;  
6. reports are generated on the basis of actual sales;      
7. system of notifications and warnings makes user not to forget to insert mandatory data  

Used technologies: J2SE, ORM via Hibernate, POJO, Annotations, HQL, JDBC, MVC, SWING   
Used RDBMS: Derby, MySQL  
