#!C:\Python35-32\python.exe
import os
import time
import datetime
from time import strftime
import urllib.request
print ("Content-type:text/html\r\n\r\n")
print ("")
print ("")
print ("Raspberry IOT Project, Temperature monitoring\n")
print ("")
print ("")

temperature = 28
url = 'http://127.0.0.1/write-to-database.py?temp='+str(temperature)
result = urllib.request.urlopen(url)

from time import sleep
import RPi.GPIO as GPIO
from datetime import datetime
import threading

GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.IN)

def probe_temp(tempfile):    #Returns tamperature value at the time of interrupt
        try:
            file = open(tempfile, 'r')
            lines = file.readlines()
            file.close()
        except:
            return None
        status = lines[0][-4:-1]

        if status == 'YES':
                tempstr = lines[1][-6:-1]
                tempval = float(tempstr)/1000
                return tempval
        else:
                print "Error in reading file, please try again"
def temp():
        temp = probe_temp("/sys/bus/w1/devices/28-0516b344c3ff/w1_slave")
        dt = str(datetime.now())
        print str(temp) + " at "+ dt

def motion():
        while True:
                current_state = GPIO.input(11)
                if current_state==1:
                        dt = str(datetime.now())
                        print "Detected... " + " at " + dt
                        sleep(60)
                        GPIO.setup(11, GPIO.IN, GPIO.PUD_DOWN)
motion_thread = threading.Thread(name="worker", target=motion)
motion_thread.start()
try:
        while True:
                temp()
                sleep(60)
finally:
        GPIO.cleanup()
