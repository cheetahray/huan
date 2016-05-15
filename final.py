import sys
import os
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

relayPin = 18
trigger_pin = 23
echo_pin = 24

GPIO.setup(trigger_pin, GPIO.OUT)
GPIO.setup(echo_pin, GPIO.IN)
GPIO.setup(relayPin, GPIO.OUT)

def send_trigger_pulse():
    GPIO.output(trigger_pin, True)
    time.sleep(0.001)
    GPIO.output(trigger_pin, False)

def wait_for_echo(value, timeout):
    count = timeout
    while GPIO.input(echo_pin) != value and count >0:
	count = count-1

def get_distance():
    send_trigger_pulse()
    wait_for_echo(True,5000)
    start = time.time()
    wait_for_echo(False, 5000)
    finish = time.time()
    pulse_len = finish - start
    distance_cm = pulse_len *340 *100 /2
    distance_in = distance_cm /2.5
    return (distance_cm)

try:
    while True:
        #Initializes an instance of Zbar to the commandline to detect barcode data-strings.
        p=os.popen('/usr/bin/zbarcam --prescale=300x200','r')
        #Barcode variable read by Python from the commandline.
        print("Please Scan a QRcode to begin...")
        barcode = p.readline()
        barcodedata = str(barcode)[8:]

        if barcodedata:
            count = 0
            print("{0}".format(barcodedata))
            #Kills the webcam window by executing the bash file 
            os.system("/home/pi/huan/kill.sh")
            GPIO.output(relayPin, True)
            while True: 
                if ( get_distance() < 10 and count > 2) :
                    count += 1
                    GPIO.output(relayPin, False)
                    break
 
except KeyboardInterrupt:
    GPIO.cleanup()
