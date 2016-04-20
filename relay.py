import time
import sys
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)

RELAY_INP1 = 24 # GPIO #24 
RELAY_INP2 = 25 # GPIO #25

GPIO.setup(RELAY_INP1, GPIO.OUT)
GPIO.setup(RELAY_INP2, GPIO.OUT)

GPIO.output(RELAY_INP1, True)
GPIO.output(RELAY_INP2, True)

relay = 0
onoff = 0

def prog_exit():
    GPIO.output(RELAY_INP1, True)
    GPIO.output(RELAY_INP2, True)

    what = GPIO.cleanup()
    print what

    if what:
        printf("Failed to close the library, deallocating any allocated memory and closing /dev/mem\n")
        printf("\nPress any key to exit...")

try:
    while True:
        print("\nUsage: relay(1 or 2) on/off(1 or 0), ex. 1 0 (relay 1 off) \n")
        print("3 0 all off, 3 1 all on, 0 0 to exit\n")
        print("relay turn on/off: ({0} {1}): ".format(relay, onoff))
        relay = input("")
        onoff = input("")
        print("relay turn on/off: ({0} {1}): ".format(relay, onoff))
        sys.stdin.flush()
    
        if onoff == 0:
            onoff = True
        elif onoff == 1:
            onoff = False
        else:
            printf("keyin the worng relay status!\n")
        prog_exit()
        
        if relay == 0:
            prog_exit()
        elif relay == 1:
            GPIO.output(RELAY_INP1, onoff)
        elif relay == 2:
            GPIO.output(RELAY_INP2, onoff)
        elif relay == 3:
            GPIO.output(RELAY_INP1, onoff)
            GPIO.output(RELAY_INP2, onoff)
        else:
            print("Keyin the wrong relay number!\n")
            prog_exit()        
except (KeyboardInterrupt):
    prog_exit()
    
