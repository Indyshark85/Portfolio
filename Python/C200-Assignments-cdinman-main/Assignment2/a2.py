import math

#problem 1
#input real number
#return real number
def g(x):
    if x != 0:
        return (x+2)
    else:
        return (1)


#problem 2
#input year 1977-1997
#return percent income or "error: year" if year 
#is outside range
def f(t):
    if 1977 <= t <= 1984:
        return((2/7)*(t-1977)+12)
    elif (1984 < t <= 1987):
        return((t-1977)+7)
    elif (1987 < t <= 1997):
        return((3/5)*(t-1977)+11)
    else:
        return "YearError: ",t 
    


#problem 3
#round to 2 decimal places
#input t years = 0, 1, then 2
#output dollars
def h(t):
    
    if 0<=t<=2:
        return round((110/((1/2)*t+1))-((26*((1/4)*(t**2)-1)**2)+52),2)
    else:
        return "YearError: ",t



#problem 4
#input tuple (a,b,c) coefficients
#output tuple roots (x_1, x_2) where x_1 >= x_2
def q(coefficients):
    a,b,c=coefficients
    x1=((-b+(math.sqrt((b**2)-4*(a*c))))/(2*a))
    x2=((-b-(math.sqrt((b**2)-4*(a*c))))/(2*a))
    return(x1,x2)

#problem 5
#input [arg1,op,arg2,ans]
#output arg1 op arg2 == ans
def eq(lst):
    arg1,op,arg2,ans = lst
    if op=="+":
        x=arg1+arg2
    elif op=="-":
        x=arg1-arg2
    elif op=="*":
        x=arg1*arg2
    elif op=="/":
        x=arg1/arg2
    elif op=="**":
        x=arg1**arg2
    if x==ans:
        return(True)
    else:
        return(False)
        
#problem 6
#input string of COVID symptoms "ABC", "ACB",...,"CBA"
#output 'very likely', 'likely', 'somewhat likely' based on severity
def covid(symptoms):
    if symptoms=="ABC" or symptoms=="ACB":
        return("very likely")
    elif symptoms=="BAC" or symptoms=="BCA":
        return ("likely")
    elif symptoms=="CAB" or symptoms=="CBA":
        return ("somewhat likely")

#problem 7
#INPUT two numbers
#RETURN maximum of the two
#You cannot use Python's max function
#You must use if, elif, else (or some combination)
def max2d(x,y):
    if x>y:
        return x
    else:
        return y

#INPUT 3 numbers
#RETURN maximum of the three
#You must use your max2D function
def max3d(x,y,z):
    return max2d(x,max2d(y,z))

#problem 8
#INPUT [name0,name1, votes] where votes is a non-empty list of 0,1
#RETURN a tuple (name, c, t) where name is the winner, c is the number of winning votes
#t is the total votes cast 
def decision(data):
    p0,p1,votes = data
    c0=0
    c1=0
    t=0
    for x in votes:
        if x==0:
            c0=c0+1
            t=t+1
        elif x==1:
            c1=c1+1
            t=t+1
    if c0 > c1:
        return p0,c0,t
    elif c0<c1:
        return p1,c1,t
    elif c0==c1:
        return "tie",c0,c1

#problem 9 
#INPUT three values: all have values or two have values and the remain has None
#OUTPUT for two values, return the computed None variable
#for three values return True or False using isclose(x,y,abs_tol = 0.001)
#remember to convert degrees to radians
def solve(theta,opposite,adjacent):
    if theta is None:
        theta = math.degrees(math.atan(opposite / adjacent))
        return theta
    elif opposite is None:
        co = math.tan(math.radians(theta)) * adjacent
        return co
    elif adjacent is None:
        ca = opposite / math.tan(math.radians(theta))
        return ca
    else:
        calc= math.tan(math.radians(theta))
        return math.isclose(calc,(opposite/adjacent), abs_tol=0.001)
        

#problem 10
#input home price and interest rate
#output payment
def future(A, r):
    n,t = 12,2  #assume monthly, two years
    #P = A * (r/n) / (1 - (1 + r/n)**(-n*t))
    P = A / ((1 + r/n)**(n*t) - 1) * (r/n)
    return round(P,2)
#problem 11
#input coefficients ax + by > c and a point
#output return True if equation true, false otherwise
def linear_query(a,b,c,point):
    x,y = point
    if a*(x)+b*(y)>c:
        return True
    else:
        return False

#problem 12
#input time, speed1, speed2 both heding in same direction
#output time train 2 reaches train 1
def train_type1(t0,s1,s2):
    t=((t0*s1)/(s2-s1))
    return round(t,2)
#input speed 1, speed 2, distance heading towards each other
#output time to reach each other
def train_type2(s1,s2,d):
    t=(d/(s1+s2))
    return round(t,2)


#problem 13
#input n >= k, use math module
#output nCr
def C(n,k):
    return (math.factorial(n)/(math.factorial(n-k)*math.factorial(k)))


#problem 14
#input side of equilateral triangle
#output area of largest circle inscribes
#use solve from problem 9
def circle(x):
    r=solve(60,(x/2),None)
    a=math.pi*r**2
    return round(a,2)

if __name__ == "__main__":
    """
    The code in "__main__" is not being graded, but a tool for you to test 
    your code outside of the unit testing Feel free to add print statements. 
    You should uncomment *after* you've completed the function
    """

    #problem 1 
    #print(g(0))
    #print(g(1))
    #print(g(1.01))

    #problem 2
    #print(f(1976))
    #print(f(1977))
    #print(f(1985))
    #print(f(1988))
    #print(f(2000))

    #problem 3
    #print(h(0))
    #print(h(1))
    #print(h(1.5))
    #print(h(2))
    #print(h(3))

    #problem 4
    #print(q((1,0,-1)))
    #print(q((6,-1,-35)))
    #print(q((1,-7,-7)))

    #problem 5
    #print(eq([14, "/",2, 7]))
    #print(eq([20, "*",19, 381]))
    #print(eq([20, "*",19, 380]))
    #print(eq([2,"**",3,8]))
    #print(eq([1.1,'-',1,.1])) #saw in class this doesn't work! (will return False)

    #problem 6
    #print(covid('ABC'),covid('ACB'))
    #print(covid('BAC'),covid('BCA'))
    #print(covid('CAB'),covid('CBA'))

    #problem 7
    #print(max3d(1,2,3))
    #print(max3d(1,3,2))
    #print(max3d(3,2,1))

    #problem 8
    #data0 = ['B','Z',[0,1,1,0,1,0,0]]
    #print(decision(data0))
    #data1 = ['B', 'Z',[1,0,1]]
    #print(decision(data1))
    #data2 = ['B', 'Z',[1,0,1,0,1,1,0,0]]
    #print(decision(data2))


    #problem 9
    #print(solve(5,None,105600))
    #print(solve(None,9238.9,105600))
    #print(solve(5,9238.8,None))
    #print(solve(5,9238.8,105600))
    #print(solve(5,9100,105600))

    #problem 10
    home_price, rate = 250000, 6/100
    t,n = 2,12                         #years, monthly
    payment = future(home_price,rate)
    print(f"{n} payments yearly for {t} years requires ${payment}")
    # #confirm this achieves 50000
    A = round(payment*((1 + rate/n)**(t*n)-1)/(rate/n),2)
    print(A)

    #problem 11
    #point1 = (-6,0)
    #point2 = (4,-5)
    #print(linear_query(3,-4,12,point1))
    #print(linear_query(3,-4,12,point2))
    
    #problem 12
    #t0_12,s1_12,s2_12 = 2,40,60
    #print(f"Time = {train_type1(t0_12,s1_12,s2_12)} hr")
    #d_12 = 400
    #print(f"Time = {train_type2(s1_12,s2_12,d_12)} hr")

    #problem 13
    #print(C(8,3))

    #problem 14
    x_14 = 14*math.sqrt(3)
    print(circle(x_14))