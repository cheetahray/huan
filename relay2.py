import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)

relayPin = 18

GPIO.setup(relayPin, GPIO.OUT)

print "staring..."
while True:
 print "Set Output False"
 GPIO.output(relayPin, False)
 time.sleep(1)
 print "Set Output True"
 GPIO.output(relayPin, True)
 time.sleep(1)
