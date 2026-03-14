# ------------------------------------------
#
#   Project:      VEXcode Project
#   Author:       VEX
#   Created:
#   Description:  VEXcode EXP Python Project
#
# ------------------------------------------

# Library imports
from vex import *

# Begin project code
target = 8
tolerance = 1

def findWall():
    #original 15
    drivetrain.set_drive_velocity(30,PERCENT)
    print("getting tolerant")


    #find the wall
    for i in range(40): # safety limit

        distance = distance_1.object_distance(INCHES)

        if distance < 8: # wall detected
            break
           
        drivetrain.turn_for(RIGHT, 4, DEGREES)
        drivetrain.drive_for(FORWARD,5,INCHES)
        wait(20, MSEC)

       
def alignWall():
    #original 5
    drivetrain.set_drive_velocity(30,PERCENT)
    #fine tune placement on wall
    # changed from 20 to 5 in case of frick up
    for i in range(5):
        distance = distance_1.object_distance(INCHES)

        if abs(distance - target) <= tolerance:
            break

        if distance > target:
            #too far from wall -> turn right slightly
            drivetrain.set_drive_velocity(20, PERCENT)

            drivetrain.turn_for(RIGHT, 3, DEGREES)

            #original 1
            drivetrain.drive_for(FORWARD, 3,INCHES)
            print("adjusting rihgt dumbahh")
           
        #stupid dumb code from eric
        elif distance < target:
           
            drivetrain.turn_for(LEFT, 3, DEGREES)
            #original 1
            drivetrain.drive_for(FORWARD, 3, INCHES)
            print("adjusting lfet dumbahh")


   
        # our genoius code
        # elif distance < target:
        #     #too close -> turn left slightly
        #     drivetrain.turn_for(LEFT, 1.5, DEGREES)
        #     drivetrain.drive_for(FORWARD, 1,INCHES)

def drive():
    #
    #drivetrain.set_drive_velocity(35, PERCENT)
    drivetrain.drive(FORWARD)


def chickenWrap():
    drivetrain.stop()
    drivetrain.drive_for(FORWARD,1, INCHES)
    drivetrain.set_turn_velocity(30,PERCENT)
    # drivetrain.turn_for(RIGHT, 90,DEGREES)
    #eric, instead of turning 90, we turn 45, 2 times
    drivetrain.turn_for(RIGHT, 45, DEGREES)
    drivetrain.drive_for(FORWARD, 8, INCHES)
    drivetrain.set_turn_velocity(30,PERCENT)
    drivetrain.turn_for(RIGHT, 45, DEGREES)
    drivetrain.drive_for(FORWARD, 10, INCHES)


    drivetrain.set_turn_velocity(45,PERCENT)
    wait(0.01 , SECONDS)
    distance1 = distance_1.object_distance(INCHES)
    print(distance1)
    if distance1 > 9:
        drivetrain.stop()
        drivetrain.set_turn_velocity(45,PERCENT)
        drivetrain.turn_for(RIGHT, 90,DEGREES)
        print("turning")
   
    drivetrain.drive_for(FORWARD, 7.5,INCHES)
    alignWall()
    wait(200, MSEC)

    print("wrap")



# for the tail during turn seqeunce
def turn_90():
    drivetrain.set_turn_velocity(20,PERCENT)
    drivetrain.turn_for(RIGHT, 90, DEGREES)
   
    drivetrain.drive_for(REVERSE, 2, INCHES)
    drivetrain.turn_for(LEFT, 180, DEGREES)
    drivetrain.drive_for(FORWARD, 1, INCHES)
    print("turn 90")

def turn():
    drivetrain.stop()

    #eric
    drivetrain.drive_for(REVERSE, 1, INCHES)
    drivetrain.turn_for(RIGHT, 50 ,DEGREES)
    drivetrain.drive_for(REVERSE, 3, INCHES)
    drivetrain.turn_for(LEFT, 50, DEGREES)
    ## adjust this for fine tuning!!!! yippeee!
    #commenting this out because i dont need it nomo
    # drivetrain.drive_for(REVERSE, 5, INCHES)
    drivetrain.turn_for(LEFT, 80 ,DEGREES)
    # drivetrain.drive_for(REVERSE, 2,INCHES)

    for i in range(20):
        distance = distance_1.object_distance(INCHES)

        if abs(distance - target) <= tolerance:
            break

        if distance > target:
            #too far from wall -> turn right slightly
            drivetrain.turn_for(RIGHT, 3, DEGREES)
            drivetrain.drive_for(FORWARD, .5,INCHES)
           

        elif distance < target:
            #too close -> turn left slightly
            drivetrain.turn_for(LEFT, 3, DEGREES)
            drivetrain.drive_for(FORWARD, .5,INCHES)

    wait(20, MSEC)

    print("turn")

def tracer(distance):
    print("tracing wall")

    if abs(distance - target) <= tolerance:
        adj= False
   

    if distance < target - 2:
        #if the distance is smaller than 2 a wall is there and we turn 90

        turn()
        adj = False

    elif distance > target + tolerance :
        #too far from wall -> turn right slightly
        drivetrain.turn_for(RIGHT, 3, DEGREES)

        print("adjusting rite dumbahh")


    elif distance < target - tolerance :
        #too close -> turn left slightly
        drivetrain.turn_for(LEFT, 3, DEGREES)

        print("adjusting lef dumbahh")

    return


#snap to cardinal direction code here v



# setting the servo
COUNT = 0
servo_a.set_position(-40, DEGREES)
wait(0.5,SECONDS)
drivetrain.drive_for(FORWARD,8,INCHES)
findWall()
alignWall()

while True:

    distance = distance_1.object_distance(INCHES)

    brain.screen.clear_screen()
    brain.screen.set_cursor(1,1)
    brain.screen.next_row()
    brain.screen.print("Distance : ", distance)
    # print("Distance : ", distance)
    drivetrain.set_drive_velocity(35, PERCENT)
    drivetrain.drive(FORWARD)

    if distance > 10.8:
        drivetrain.stop()
        # commented out because too much
        # drivetrain.drive_for(FORWARD, 12.5, INCHES)    
        drivetrain.drive_for(FORWARD, 6, INCHES)
        chickenWrap()
    else:
        tracer(distance)

    wait(0.5, MSEC)

