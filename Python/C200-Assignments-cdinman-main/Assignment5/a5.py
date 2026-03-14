import math
#import numpy as np
#import matplotlib.pyplot as plt
#import matplotlib.pyplot as plt

###########################################################################
# Functions for Problem 1
###########################################################################
#INPUT a nested list of people encoded as 0's and 1's. x and y are the respective lists respresenting the people pairs.
#   You'll be comparing the smallest degree of difference between each sublist representing each person.
#RETURN person pair with the smallest degree (smallest degree of difference between the person pair lists)
def inner_prod(x, y):
    
    return sum(x[i]*y[i] for i in range(len(x)))
    
def mag(x):
    return math.sqrt(inner_prod(x,x))

def angle(x, y):
    cosTheta=inner_prod(x,y)/(mag(x)*mag(y))
    return round(math.degrees(math.acos(cosTheta)),2)

def match(people):
    matches=[]
    n=len(people)
    for x in range(n):
        for y in range(x+1,n):
            degree=angle(people[x],people[y])
            matches.append([people[x],people[y],degree])
    return matches

def best_match(scores):
    small=min(scores, key=lambda x:x[2])
    return tuple(small)


########################
# PROBLEM 2
########################

# Input: a list of values
# Output: Interval start, interval stop, value of the greatest sum
def msi(x):
    maxsum = float('-inf') 
    startindex = 0
    stopindex = 0
    for i in range(len(x)):
        for j in range(i + 1, len(x) + 1):
            intervalsum = sum(x[i:j])
            if intervalsum > maxsum:
                maxsum = intervalsum
                startindex = i
                stopindex = j
    return [startindex, stopindex, maxsum]


########################
# PROBLEM 3
########################

#INPUT parameters to LV model as mentioned in the PDF
#OUTPUT two lists history_rabbit, history_fox of populations
def rabbit_fox(br,dr,df,bf,rabbit,fox,time_limit):
    i = 0
    history_rabbit = []
    history_fox = []
    while i < time_limit:
        history_rabbit.append(rabbit)
        history_fox.append(fox)
        rabbit_new=math.ceil(rabbit+(rabbit*br)-(rabbit*fox*dr))
        new_fox=math.ceil(fox+(bf*dr*rabbit*fox)-(fox*df))
        #rabbit_new=(rabbit+rabbit*br-rabbit*fox*dr)
        #new_fox=(fox+bf*rabbit*fox-fox*df)
        rabbit= rabbit_new
        fox=new_fox
        i+=1
    return history_rabbit ,history_fox


########################
# PROBLEM 4
########################

# #INPUT positive number n
# #RETURN log of number base 2
def log_2(n):
    return math.log(n,2)

#INPUT list of immutable objects
#RETURN probability distribution as a list
def makeProbability(xlst):
    length=len(xlst)
    countdic={}
    prop=[]
    for x in xlst:
        if x in countdic:
            countdic[x]+=1
        else:
            countdic[x]=1
    for y in countdic.values():
        prop.append(y/length)
    return prop


#INPUT probability distribution
#RETURN non-negative number entropy
def entropy(xlst):
    ans=0
    for x in makeProbability(xlst):
        ans+=x*log_2(x)
    return -round(ans,2)




########################
# PROBLEM 5
########################

#INPUT list of 0s 1s
#OUTPUT longest sequence of 1s
def L(x):
    count1=0
    largeRun=0
    for nums in x:
        if nums==1:
            count1+=1
            if largeRun<count1:
                largeRun=count1
        else:
            count1=0
                
    return largeRun



########################
# PROBLEM 6
########################
#INPUT non-negative integer
#OUTPUT True if divisible by 9, False otherwise

def div_9(x):
    sum=0
    for num in str(x):
        sum+=int(num)
    if sum%9==0:
        return True
    else:
        return False
        

        


########################
# PROBLEM 7
########################
#driver cost
def driver_cost(hr_rate, t):
    return t*hr_rate

#operating cost
def operating_cost(distance, speed):
    return round(((((60+speed)/2)*distance)/100),3)

#total cost
def total_cost(speed, distance, hr_rate):
    timetaken = distance/speed
    return round(driver_cost(hr_rate, timetaken) + operating_cost(distance, speed),2)

#input distance, hourly rate, and acceptable speeds
#output tuple (total cost, optimal speed)
def min_cost(distance, hr_rate, speeds):
    ms=speeds[0]
    mc=float('inf')
    for speed in range(speeds[0], speeds[1]):
        cost = total_cost(speed, distance, hr_rate)
        if cost < mc:
            mc = cost
            ms = speed
    
    return round(mc, 2), ms


########################
# PROBLEM 8
########################
#logical implication
def imp(A,B):
    return not A or B

#various logical formulae
def S(P,Q,R):
    return imp(P and (not Q or not R), imp(P, not Q))
def T(A,B,C):
    return imp(imp(A, B) and imp(B, C), imp(A, C))
def U(E,F,G):
    return not T(E, F, G)
def V(X,Y,Z):
    return imp(U(X, Y, Z), S(X, Y, Z))
def M(g,gg,ggg):
    return True

#input Boolean functino of three inputs
#output ID as tautology, contradiction, or contingency
def kind(F):
    inputs = [False, True]
    outputs = set()

    for i in inputs:
        for j in inputs:
            for k in inputs:
                outputs.add(F(i, j, k))

    if all(outputs):
        return "tautology"
    elif not any(outputs):
        return "contradiction"
    else:
        return "contingency"

#Problem 9
#root finding formula for algorithm
def f0(x):
    return math.exp(-x)

def f1(x):
    return math.sqrt(4*x + 7)

def f2(x):
    return math.sqrt(math.sqrt((4*x)+7))

def f3(x):
   return pow((3*(x**2))+2,1/5)


# input function that finds x and initial guess
# output approximate positive root
def approx_root(f, initial_guess):
    x=initial_guess
    epsilon = 1e-7 #leave for students (you can check if output is less than epsilon to see if it is small enough)
    while abs(x-f(x))>epsilon:
        x=f(x)
    return x


########################
# PROBLEM 10
########################
#input sides a,b and angle between
#output length of the opposite side to angle
def cosine_law(a,b,angle):
    return round(math.sqrt(a**2+b**2-2*a*b*math.cos(math.radians(angle))),2)

#input start time, stop time, speed
#output distance 
def distance(start,stop,speed):
    return round((stop-start)*speed,2)


########################
# PROBLEM 11
########################

#input simple parabola
#leave for students
def f(x):
    return 12 - x**2

#input interval and function
#output rectangle dimensions of largest area
def op_rect(a,b,f):
    maxval = max(f(x) for x in [a, b])
    if f(a) > f(b):
        xmax = a    
    else:
        xmax = b
    y_max = maxval
    length = xmax
    width = y_max
   
    

    return (round(length, 2), round(width, 2))

print(op_rect
(
    19,
    16,
    f
))


#Problem 12
#various business models
#price 
def p(x):
    return(5.00-0.002*x)
#revenue
def R(x):
    return(x*p(x))
#cost
def C(x):
    return(3.00+1.10*x)
#profit
def P(x):
    return(R(x)-C(x))

#input revenue function and interval of units sold
#output maximal revenue tuple (revenue, unit)
def max_revenue(R,a,b):
    maxR=0
    maxU=a
    if a<b:
        for x in range(a,b+1):
            curR=P(x)
            if curR>maxR:
                maxR=curR
                maxU=x
    else:
        for x in range(b,a+1):
            curR=P(x)
            if curR>maxR:
                maxR=curR
                maxU=x
        
    return maxR,maxU


if __name__ == "__main__":
    """
    # # #problem 1
    people0 = [[0,1,1],[1,0,0],[1,1,1]]
    print(match(people0))
    print(best_match(match(people0)))

    people1 = [[0,1,1,0,0,0,1],
               [1,1,0,1,1,1,0],
               [1,0,1,1,0,1,1],
               [1,0,0,1,1,0,0],
               [1,1,1,0,0,1,0]]
    print(best_match(match(people1)))
    #output is ([1, 1, 0, 1, 1, 1, 0], [1, 0, 0, 1, 1, 0, 0], 39.23)
    
    x, y = (2,3,-1), (1,-3,5)
    print(angle(x, y)) #122.83

    x, y = (3,4,-1),(2,-1,1)
    print(angle(x, y)) #85.41

    x, y = (5,-1,1),(1,1,-1)
    print(angle(x, y)) #70.53
    



    #problem 2
    x2 = [7, -9, 5, 10, -9, 6, 9, 3, 3, 9]
    print(msi(x2))
    
    #problem 3
    
    br = 0.03
    dr = 0.0004
    df = 0.25
    bf = 0.11
    rabbit = 3000  #initial population size
    fox = 200  #initial population size
    time_limit = 2000
    history_rabbit, history_fox = rabbit_fox(br,dr,df,bf,rabbit,fox, time_limit)
    
    #uncomment to see time, rabbit, fox populations
    for j in range(0,2000,200):
        print(j, history_rabbit[j], history_fox[j])
    
    plt.plot(list(range(0,time_limit)),history_rabbit)
    plt.plot(list(range(0,time_limit)),history_fox)
    plt.xlabel("Time")
    plt.ylabel("Population Size")
    plt.legend(["Rabbit","Fox"])
    plt.title("Lotka-Volterra Model for Rabbit & Fox")
    plt.show()
    """
    
    
    
    

    # #Problem 4
    #data1 = [["a", "b", "a", "c", "c", "a"],[1],[1,2,3,4]]
    
    #1.46, -0.0, 2.0; 0 is minimal, log(n) is maximal
    #for d in data1:
    #    print(entropy(d)) 
    
    # # #Problem  5
    #data2 = [[0],[1],[1,1,0,1,1,1],[0,1,1,0],[0,1,1,1,0,0,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1]]
    #for d in data2:
    #    print(L(d))

    # # #Problem 6
    #data3 = [99,0,18273645,22,27]
    #for d in data3:
    #    print(div_9(d), not bool(d % 9))

    # # # Problem 7
    #s_a,s_b = 40,60
    #hr_rate = 14
    #distance = 100
    #print(min_cost(distance,hr_rate,(s_a,s_b)))
    #hr_rate = 15
    #print(min_cost(distance,hr_rate,(s_a,s_b)))
    
       
    # # problem 8
    #print(kind(S))
    #print(kind(T))
    #print(kind(U))
    #print(kind(V))
    #print(kind(M))
    
    
    #problem 9
    #x_star = approx_root(f1,4)
    #print(x_star, x_star**2 - 4*x_star - 7)

    #x_star = approx_root(f0,.5)
    #print(x_star, x_star - math.exp(-x_star))


    #x_star = approx_root(f2,4)
    #print(x_star, x_star**4 - 4*x_star - 7)

    #x_star = approx_root(f3,3)
    #print(x_star, x_star**5 - 3*(x_star**2) - 2)

    #problem 10
    #Sa = 24   
    #Sb = 18
    #start_a = 1
    #start_b = 1.5
    #stop = 3
    #a,b = 34,56

    #print(cosine_law(*(distance(start_a,stop, Sa), distance(start_b, stop, Sb)), a+b))
    
    #problem 11
    """
    a,b = 0,2 * math.sqrt(3)
    print(op_rect(a,b,f))
    
    x = np.linspace(-2*math.sqrt(3), 2*math.sqrt(3),100)
    y = 12 - x**2
    
    plt.plot(x,y,'r')
    plt.grid(True)
    plt.title(r"$f(x) = 12 - x^2$")
    plt.show()
    """
    #problem 12
    #p_,u_ = max_revenue(R,1,1000)
    #print(u_)    
    #visualization    
    # x = np.linspace(1,1299,100)
    # y = P(x)
    # plt.plot(x,y,'r')
    # plt.plot(u_,p_,'go')
    # plt.xlabel("Units")
    # plt.ylabel("Revenue $MM")
    # plt.title ("Maximizing Revenue")
    # plt.show()