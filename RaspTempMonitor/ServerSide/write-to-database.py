#!C:\Python35-32\python.exe

#Project Raspberry IOT Temperature monitoring
#Server side code to read data sent by pi through GET method
#Create by Minh Toan
#Create on 5 October, 2017


#import neccessary modules
import cgi, cgitb
import os
import time
import datetime
import glob
import MySQLdb
from time import strftime
import requests
import json
import urllib.request
import urllib.parse

#define neccessary variables
#threshold store a safe threshold temperature, if over, send a notification to android device
threshold = 35
#mysql_host store mysql database hostname
mysql_host = "localhost"
#mysql_user store user to access mysql databse
mysql_user = "root"
#mysql_pass store password of user
mysql_pass = "toanpi"
#mysql_db store name of database
mysql_db   = "templog"


def get_field():    #return temperature sent by pi through GET method in temp field
    # Create instance of FieldStorage 
    form = cgi.FieldStorage() 
    # Get data from client
    temp = form.getvalue('temp') 
    return temp

def html_structure(statment):   #create and display a html structure on webview
    print ("Content-type:text/html\r\n\r\n")
    print ("")
    print ("")
    print (statment)
    print ("</br>")
    print ("")

def save_log(log_string):   # save log to log file
    with open("log.log", "a+") as f:
        f.write(log_string + '\n')

def write_to_database(temperature):
    # Connect mysql database 
    db = MySQLdb.connect(host=mysql_host, user=mysql_user, passwd=mysql_pass, db=mysql_db)
    cur = db.cursor()
    while True:
        dateWrite = time.strftime("%Y-%m-%d")   # Get current date
        timeWrite = time.strftime("%H:%M:%S")   # Get current time
        # create a sql insert statment
        sql = ("""INSERT INTO `temprecord` (`Date`, `Time`, `Temperature`) VALUES (%s, %s, %s);""", (dateWrite,timeWrite,temperature))
        try:    # insert success
            cur.execute(*sql)
            db.commit()
            save_log('write to database temlog, temperature is '+str(temperature)+' at '+str(timeWrite)+' on '+dateWrite+' by ' + mysql_user)
        except: # insert fail
            db.rollback()
            save_log('failed to write to database temlog, temperature is at '+str(timeWrite)+' on '+dateWrite+' by '+mysql_user)
        finally:
            cur.close()
            db.close()
            break

def alert(temperature): # Alert user if recorded temperature is above a threshold (30 degrees in this case) 
    if temperature>threshold:
        message = 'Alert! Current temperature recorded is ' + str(temperature) + " *C"
        url="http://127.0.0.1/sending-push-notifications.php"
        msg={'mes': message}
        data = urllib.parse.urlencode(msg)
        request = urllib.request.Request(url, data.encode('utf-8'))
        response = urllib.request.urlopen(request)
        dateWrite = time.strftime("%Y-%m-%d")   # Get current date
        timeWrite = time.strftime("%H:%M:%S")   # Get current time
        save_log("The temperature exceeds the safety threshold at "+str(timeWrite)+" on "+dateWrite+" sent notifications to android devices")

def main():
    html_structure('Raspberry IOT Project, Temperature monitoring')
    write_to_database(get_field())
    alert(float(get_field()))
main()