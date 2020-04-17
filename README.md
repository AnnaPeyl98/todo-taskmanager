## TO-DO TASK MANAGER

This application for work with your tasks.

## INSTALLING
For working with this application you should install postgreSQL
```$xslt
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ `lsb_release -cs`-pgdg main" >> /etc/apt/sources.list.d/pgdg.list'

wget -q https://www.postgresql.org/media/keys/ACCC4CF8.asc -O - | sudo apt-key add -

sudo apt-get update
sudo apt-get install postgresql-9.6
```
Login in database
```
sudo -u postgres psql
```
Create database eisetasks:
```
CREATE DATABASE eisetasks;
```
Create users "anna" with password "anna" and give rights
```
CREATE USER anna WITH ENCRYPTED PASSWORD 'anna';
GRANT ALL PRIVILEGES ON DATABASE eisetasks TO anna;
```
## RUN
Enter in console in root directory
```
mvn package
```
then go to directory target and enter in console
```
java -jar name_file.jar
```
when name_file.jar - your jar-file this project.

## API
<https://app.swaggerhub.com/apis-docs/pawlaz/base-web-development-restfull-task-manager/6.0.0>
You can send the following requests:
1. **This request with method GET return all tasks**
    ```$xslt
    http://localhost:8080/tasks
    ```
    This request may have the following query parameters:

    * **status**

        Determines the status of the tasks you want to receive. Tasks have two statuses: "inbox" and "done".
        **Default value - "inbox"**
    
    * **order**

        It determines the sorting of tasks by the date of addition; it can take the values ​​"desc" and "asc". 
        **The default value is "desc"**
    * **page**
    
        Specifies the page number you want to display. **The default value is 1.**
    
    * **size**

        The number of displayed tasks on one page. It can take values ​​from 10 to 50 inclusive. **The default value is 25.**
    
2. **This request with method POST add task in repository and return it**
    ```
    http://localhost:8080/tasks
    ```
    
    In the body of the request, you should send json with the text and status fields, where text is the task text and status is its execution status. 
    Tasks have two statuses: "inbox" and "done". Example:
    ```
    {
	    "text":"do homework",
	    "status":"inbox"
    }
    ```
3. **This request with the GET method will return the task with the specified id**, if the task is not found, then the code 404 will be returned

     ```
      http://localhost:8080/tasks/{id}
     ```
4. **This request with the PATCH** method will correct the old status and the text for the new one in the task with the specified id, which you will send in the body of the request in json format.

     ```
      http://localhost:8080/tasks/{id}
     ```
     Example body request:
     ```
        {
        	"text":"do homework",
        	"status":"done"
        }
     ```
5. **This request with the DELETE method will remove the task with the specified id from the repository**
    ```
      http://localhost:8080/tasks/{id}
     ```