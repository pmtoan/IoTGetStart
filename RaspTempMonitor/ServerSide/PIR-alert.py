#!C:\Python35-32\python.exe
#Project Raspberry IOT Temperature monitoring
#Server side code to send notification request to server/send-push-notifications.php
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

def get_field():    #return temperature sent by pi through GET method in temp field
    # Create instance of FieldStorage 
    form = cgi.FieldStorage() 
    # Get data from client
    temp = form.getvalue('pir') 
    return temp

def alert(message): 
    url="http://127.0.0.1/sending-push-notifications.php"
    msg={'mes': message}
    data = urllib.parse.urlencode(msg)
    request = urllib.request.Request(url, data.encode('utf-8'))
    response = urllib.request.urlopen(request)
    save_log(message + " sent notifications to android devices")

html_structure('Raspberry IOT Project, Motion dectection')
alert(str(get_field()))