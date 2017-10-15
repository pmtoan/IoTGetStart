import time
from time import sleep
from time import strftime
import RPi.GPIO as GPIO
from datetime import datetime
import threading
import urllib

# setup pi and pin11
GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.IN)

time_sleep_sensor = 1

def save_log(log_string):
    with open("log.log", "a+") as f:
        f.write(log_string + '\n')

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
        return None

def temp():
    timeW = time.strftime("%H:%M:%S")
    dateW = time.strftime("%d-%m-%Y")
    temp = probe_temp("/sys/bus/w1/devices/28-0516b344c3ff/w1_slave")
    if temp != None:
        print("Temperature from sensor is "+str(temp)+"*C at "+timeW+" on "+dateW)
        save_log("Temperature from sensor is "+str(temp)+"*C at "+timeW+" on "+dateW)
        url = 'http://192.168.137.1/write-to-database.py?temp='+str(temp)
        result = urllib.urlopen(url)
    else:
        save_log("Error in reading temperature from sensor at "+timeW+" on "+dateW)

def push_notification(notification):
    url="http://192.168.137.1/PIR-alert.py?pir=" + notification
    reponse = urllib.urlopen(url)
    timeW = time.strftime("%H:%M:%S")
    dateW = time.strftime("%d-%m-%Y")
    save_log("Sent notifications to androind devices at "+timeW+" on "+dateW)
def motion():
    while True:
        current_state = GPIO.input(11)
        if current_state==1:
            timeW = time.strftime("%H:%M:%S")
            dateW = time.strftime("%d-%m-%Y")
            print("Detected a symbol near PIR sensor at "+timeW+" on "+dateW)
            save_log("Detected a symbol near PIR sensor at "+timeW+" on "+dateW)
            push_notification("Detected a symbol near PIR sensor at "+timeW+" on "+dateW)
            sleep(60)
            GPIO.setup(11, GPIO.IN, GPIO.PUD_DOWN)
motion_td = threading.Thread(name="worker", target=motion)
motion_thread.start()
if __name__ == "__main__":
    try:
        while True:
            temp()
            sleep(time_sleep_sensor)
    finally:
            GPIO.cleanup()
