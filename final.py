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
    GPIO.output(relayPin, False)
    time.sleep(0.25)
    while True:
	GPIO.output(relayPin, True)
        #Initializes an instance of Zbar to the commandline to detect barcode data-strings.
        p=os.popen('LD_PRELOAD=/usr/lib/arm-linux-gnueabihf/libv4l2.so zbarcam --nodisplay --raw /dev/video0','r')
        #Barcode variable read by Python from the commandline.
        print("Please Scan a QRcode to begin...")
        barcode = p.readline()
        barcodedata = str(barcode)[8:]

        if barcodedata:
            count = 0
            print("{0}".format(barcodedata))
            #Kills the webcam window by executing the bash file 
            os.system("/home/pi/huan/kill.sh")
            GPIO.output(relayPin, False)
            while True: 
                d = get_distance()
                if (d < 10): 
                    count += 1
                    if (count > 20):
                        GPIO.output(relayPin, True)
                        break
                else:
                    count = 0
                    time.sleep(0.01)
 
except KeyboardInterrupt:
    GPIO.output(relayPin, False)
    GPIO.cleanup()
