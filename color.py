import smbus
import time
bus = smbus.SMBus(1)
# I2C address 0x29
# Register 0x12 has device ver. 
# Register addresses must be OR'ed with 0x80
bus.write_byte(0x29,0x80|0x12)
ver = bus.read_byte(0x29)
# version # should be 0x44
if ver == 0x44:
 print "Device found\n"
 bus.write_byte(0x29, 0x80|0x00) # 0x00 = ENABLE register
 bus.write_byte(0x29, 0x01|0x02) # 0x01 = Power on, 0x02 RGB sensors enabled
 bus.write_byte(0x29, 0x80|0x14) # Reading results start register 14, LSB then MSB
 while True:
  color = "NONE"
  data = bus.read_i2c_block_data(0x29, 0)
  clear = clear = data[1] << 8 | data[0]
  red = data[3] << 8 | data[2]
  green = data[5] << 8 | data[4]
  blue = data[7] << 8 | data[6] 
  red = red >> 8
  green = green >> 8
  blue = blue >> 8
  if (red > 200 & green < 50 & blue < 50) :
	color = "RED,"
  if (red < 50 & green > 200 & blue < 50) :
	color = "GREEN"
  if (red < 50 & green < 50 & blue > 200) :
	color = "BLUE"
  if (red > 200 & green > 200 & blue < 50) :
	color = "YELLOW"
  crgb = "C: %s, R: %s, G: %s, B: %s\n" % (color, red, green, blue)
  print crgb
  time.sleep(0.005)
else: 
 print "Device not found\n"
