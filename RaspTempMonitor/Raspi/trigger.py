import time
import sys
from time import sleep
from time import strftime
import RPi.GPIO as GPIO
from datetime import datetime
import threading
import urllib
import signal
from colorama import init, Fore, Back, Style

init()

# setup pi and pin11
GPIO.setmode(GPIO.BOARD)
GPIO.setup(11, GPIO.IN)

time_sleep_sensor = 1

def save_log(log_string):
    with open("raspi.log", "a+") as f:
        f.write(log_string + '\n')

def get_td():
    timeW = time.strftime("%H:%M:%S")
    dateW = time.strftime("%d-%m-%Y")
    return " at " + timeW + " on " + dateW

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
def temp_thread():
    temp = probe_temp("/sys/bus/w1/devices/28-0516b344c3ff/w1_slave")
    if temp != None:
        log = "Temperature from sensor is " + str(temp) + "*C" +get_td()
        print(Fore.GREEN + "[MONITORING] " + Fore.WHITE + "Temperature from sensor is " + Fore.YELLOW + str(temp) + "*C" + Fore.WHITE + get_td())
        save_log(log)
        url = 'http://192.168.137.1/write-to-database.py?temp='+str(temp)
        result = urllib.urlopen(url)
    else:
        log = "Can't read temperature from sensor" + get_td()
        print(Fore.RED + "[ERROR] " + Fore.WHITE + log)
        save_log(log)

def push_notification(notification):
    url="http://192.168.137.1/PIR-alert.py?pir=" + notification
    reponse = urllib.urlopen(url)
    log = "[ALERT] Sent notifications to androind devices" + get_td()
    save_log(log)
def motion():
    log = "Run motion detected thread --------" + get_td()
    print(Fore.CYAN + "[STATUS] " + Fore.WHITE + log)
    save_log(log)
    while True:
        current_state = GPIO.input(11)
        if current_state == 1:
            log = "Detected a symbol near PIR sensor" + get_td()
            print(Fore.YELLOW + "[ALERT] " + Fore.WHITE + log)
            save_log(log)
            push_notification("ALERT! PIR Motion Detected" + get_td())
            GPIO.setup(11, GPIO.IN, GPIO.PUD_DOWN)
            sleep(60)
if __name__ == "__main__":
    try:
        motion_thread = threading.Thread(name="worker", target=motion)
        motion_thread.start()
    except:
        log = "Failed to run motion detected thread" + get_td()
        print(Fore.RED + "[ERROR] " + Fore.WHITE + log)
        save_log(log)
    try:
        log = "Run temperature monitor thread -------" + get_td()
        print(Fore.CYAN + "[STATUS] " + Fore.WHITE + log)
        save_log(log)
        while True:
            temp_thread()
            #sleep(time_sleep_sensor)
    finally:
        GPIO.cleanup()
