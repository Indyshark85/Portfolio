import math

def speedometer(pos1, pos2, time):
    """
    Problem Description
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Calculate the speed of the animal.
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Input:
        pos1 - the initial position of the animal
        pos2 - the final position of the animal
        time - the recorded time the animal spent between them
    Returns: The speed of the animal.
    """

    s=((pos2-pos1)/time)
    if s >=0:
        return s
    elif s < 0:
        return (-1*s)



def animal_average_speed(animal_type):
    """
    Problem Description
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Different types of animals can run at different speeds.
    Using if statements, return the (average) speed of the animal
    based on the type that is given.

    Animal     |  Animal Type |  Average speed (mph)
    ________________________________________________
    Sailfish   |  0           |   68
    Goose      |  1           |   88
    Wildebeest |  2           |   50
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Input: The type of animal
    Returns: Average speed of corresponding animal
    """
    
    if animal_type == 0:
        return(68)
    elif animal_type == 1:
        return (88)
    elif animal_type == 2:
        return(50)
    else:
        return("sorry that is not in the database")

def animal_speed_compare(animal1_pos1, animal1_pos2, animal1_time, animal2_type):
    """
    Problem Description
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Make sure to use the first two functions in this file to help you!
    Given the input data for the speed of some animal (animal1), and the type of a *different* animal (animal2),
    return a string based on whether the first animal's speed is greater than the average speed of the other animal.
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Input:
        animal1_pos1 - the initial position of the animal
        animal1_pos2 - the final position of the animal
        animal1_time - the time it took for the animal to travel between pos1 and pos2
        animal2_type - the type of animal to compare against
    Returns: "The animal is faster" or "The animal is slower"
    """
    as1=speedometer(animal1_pos1,animal1_pos2,animal1_time)
    as2=animal_average_speed(animal2_type)
    if as1>as2:
        return("The animal is faster")
    elif as1<as2:
        return("The animal is slower")

    

def myTestString(func, params):
    """
    Do not modify
    """
    return func.__name__ + "" + str(params) + " produces " + str(func(*params))


#Examples to see the difference between "print" and "return"
def function_that_prints():
    print("I printed")


def function_that_returns():
    return "I returned"


# For more information about the line below, refer to the link: https://docs.python.org/3/library/__main__.html
#   You can read more about it but it is to help when handling multiple files
#   Will be dicussed at a later point
if __name__ == "__main__":
    print("Final Test Code\n")
    print("Testing speedometer()")
    for x in [(0.0,400,5.0), (-1,5,0.5), (0,0,1.0), (6,-100,3)]:
        print(myTestString(speedometer, x))
    print("Testing animal_average_speed()")
    for x in [(0,), (1,),(2,), (3,)]:
        print(myTestString(animal_average_speed, x))
    print("Testing animal_speed_compare()")
    for x in [(0.0,400,5.0,1), (0.0,400,5.0,0), (25,150,1.0,2)]:
        print(myTestString(animal_speed_compare, x))
