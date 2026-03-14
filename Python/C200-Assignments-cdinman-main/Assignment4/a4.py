import math

###########################################################################
# Functions for Problem 1
###########################################################################
#INPUT dlst = [day, month, year]
#RETURN string corresponding to the day of the week (i.e. "Mon", "Sun", etc)
week = {0:"Sun",1:"Mon", 2:"Tue", 3:"Wed", 4:"Thu", 5:"Fri", 6:"Sat"}
#had to move list around in order to get the math to work correctly
def a(dlst):
    d,m,y=dlst
    return y-((14-m)/12)

def b(dlst):
    x= a(dlst)+(a(dlst)/4)-(a(dlst)/100)+(a(dlst)/400)
    return  math.floor(x)
def c(dlst):
    d,m,y=dlst
    return m+(12*((14-m)/12))-2
    
    
def day(dlst):
    d,m,y=dlst
    x=(d+b(dlst)+(31*(c(dlst)/12)))%7
    for num,day in week.items():
        if x==num:
            return day
###########################################################################
# Functions for Problem 2
###########################################################################
#INPUT t = (a,b,c)
#RETURN return complex or real roots
def q(t):
    a,b,c=t
    disc=(b**2)-(4*a*c)

    if disc >= 0:

        r0=round((-b-math.sqrt(disc))/(2*a),2)
        r1=round((-b+math.sqrt(disc))/(2*a),2)

        return (r0,r1)
    elif disc <0 :
        c0=round((-b)/(2*a),2)
        c1=round(math.sqrt(abs(disc))/((2*a)),2)
        return complex(c0,c1), complex(c0,-c1)
    else:
        return None





###########################################################################
# Functions for Problem 3
###########################################################################
#INPUT coefficients for polynomial and linear function
#OUTPUT list of coefficients for answer

def sd(p, q):
    x = -(q[1] / q[0])
    
    y = p[0]
    ans=[y]
    for num in p[1:]:
        y=x*(y)+num
        ans.append(y)
    return ans

###########################################################################
# Functions for Problem 4
###########################################################################
#INPUT tuple of quadratic (ax^2 + bx + c)
#RETURN tuple (m,n) cofficients for real solutions a(x+m)^2 + n = 0
#CONSTRAINT round 2 places
def c_s(coefficients):
    a,b,c=coefficients
    m=round(b/(2*a),2)
    n=round(c-(b**2/(4*a)),2)
    return(m,n)


#INPUT coefficients for quadratic ax^2 + bx + c 
#RETURN return real roots uses c_s
def q_(coefficients):
    m,n=c_s(coefficients)
    x0 = complex(-m, math.sqrt(abs(n)))
    x1 = complex(-m, -math.sqrt(abs(n)))
    return x0, x1
#print(q_([1,4,15]))


###########################################################################
# Functions for Problem 5
###########################################################################
#INPUT List of numbers
#RETURN Various means
def mean(lst):
    sum=0
    for x in lst:
        sum+=x
    return round(sum/len(lst),2)
        
def var(lst):
    runtol=0
    var1=1/len(lst)
    for x in lst:
        runtol+=(x-mean(lst))**2
    return round(var1*runtol,2)

def std(lst):
    return round(math.sqrt(var(lst)),2)

def mean_centered(lst):
    nlst=[]
    for x in lst:
        nlst.append(x-mean(lst))
    return nlst



###########################################################################
# Functions for Problem 6
###########################################################################
#INPUT supply and demand coefficients
#RETURN solution of quadratic equations
def equi(s,d):
    a,b=q(s)
    c,d=q(d)
    x=(a+b+c+d)/2
    y1=(x-a)*(x-b)
    y2=(x-c)*(x-d)
    
    return y1,y2

#print(equi([1,1,4],[8,7,10]))

###########################################################################
# Functions for Problem 7
###########################################################################
#INPUT height of tree and age to approximate
#OUTPUT tuple (age, Boolean) where time - 5 <= age <= time + 5
def tree_age(height, time):
    alpha=-.2
    treeAge = round(math.log((120 / height - 1) / 200) / alpha,2)
    
    if time-5<=treeAge<=time+5:
        return treeAge, True
    else:
        return treeAge, False
    


    
###########################################################################
# Functions for Problem 8
###########################################################################
#INPUT 2x2 matrix
#OUTPUT determinant
def determinant(matrix):
    (a, b), (c, d) = matrix
    return a*d - b*c


#INPUT two equations as lists of coefficients ax + by = c
#OUTPUT solution or zero (won't check for when determinant is zero)
def solve(eq1,eq2):
    a,b,c=eq1
    d,e,f=eq2

    c0 = [[a], [d]]
    c1 = [[b], [e]]
    c2 = [[c], [f]]
    a = [[c2[0][0], c1[0][0]], [c2[1][0], c1[1][0]]]
    b = [[c0[0][0], c1[0][0]], [c0[1][0], c1[1][0]]]
    d = [[c0[0][0], c2[0][0]], [c0[1][0], c2[1][0]]]
    x=determinant(a)/determinant(b)
    y=determinant(d)/determinant(b)
    return round(x,2),round(y,2)
def f_1(x):
    return (1/4)*(-2*x + 11)

def f_2(x):
    return (1/3)*(5*x + 5)



###########################################################################
# Functions for Problem 9
###########################################################################
#INPUT values for annuity
#OUTPUT deposit amount needed
def deposit(S,i,n):
     return(S*(i/((1+i)**n-1)))

#INPUT sinking fund values except deposit
#OUTPUT a list of period, deposit, interest, accrued total fund
def sinking_fund(final_amt, r, m, y):
    i=r/m
    n=m*y
    R=round(deposit(final_amt,i,n),2)
    period=[]
    total=0

    for x in range(n):
        interest=round(total*i,2)
        total += (R+interest)
        period.append([x,R,interest,round(total,2)])
    return period




###########################################################################
# Functions for Problem 10
###########################################################################
#INPUT list of numbers
#OUTPUT Boolean if geometric series
def is_geometric_sequence(lst):
    if len(lst)<3:
        return False
    cr=lst[1]/lst[0]
    for x in range(1,len(lst)-1):
        if lst[x+1]/lst[x]!=cr:
            return False
    return True


###########################################################################
# Functions for Problem 11
###########################################################################
#INPUT portfolio of stock price, shares, market
#OUTPUT current total value
def value(portfolio,market):
    initial=0
    current=0
    for stock,detail in portfolio['stock'].items():
        if stock in market:
            initial+=detail[0]*detail[1]
            current+=market[stock]*detail[1]
    percent=round((current-initial)/initial,2)
    return (percent*100)

    

#PROBLEM 12
#INPUT a (possibly empty) list of numbers
#OUTPUT raise exception or smooth
#problem 12
def smooth(lst):
    if len(lst)==0:
        return "AveError: list empty"
    elif len(lst)==1:
        return(lst)
    rlist=[]
    for x in range(len(lst)-1):
        sum=(lst[x]+lst[x+1])
        y=round(sum/2,2)
        rlist.append(y)
    return rlist


 

#INPUT Weight in space and earth (pounds)
#OUTPUT altitude (kilometers)
#problem 13
def orbit(A_w, E_w):
    d=6400*(math.sqrt(E_w/A_w)-1)
    return math.ceil(d)

#INPUT pair of wind velocity, height 
#OUTPUT constant P value
#problem 14
def P_ws(v0,h0,v1,h1):
    P= math.log(v0/v1)/math.log(h0/h1)
    return round(P,3)



if __name__ == "__main__":
    """
    If you want to do some of your own testing in this file, 
    please put any print statements you want to try in 
    this if statement.

    You **do not** have to put anything here
    """

    # #problem 1
    #print(day([10,5,1992]))
    #print(day([14,2,1963]))
    #print(day([14,2,1972]))

    # #problem 2
    #print(q((3,4,2)))
    #print(q((1,3,-4)))
    #print(q((1,-2,-4)))

    # #problem 3
    #data_3 = [((2,-5,3,7),((1/2),-1)),((3,0,5,-1),(1,1)),((4,-8,-1,5),(2,-1))]

    #for d in data_3:
        #print(sd(*d))


    #problem 4 pairs should be identical
    #print(q((1,-4,-8)), q_((1,-4,-8)))
    #print(q((1,3,-4)),q_((1,3,-4)))
    #print(q((3,4,2)))#q_((3,4,2)))
   
    
    # #problem 5
    #lst = [1,3,3,2,9,10]

    #print(mean(lst))
    #print(var(lst))
    #print(std(lst))
    #print(mean(mean_centered(lst)))

    # #problem 6
    #s = (-.025, -.5, 60)
    #d = (0.02, .6, 20)
    #print(equi(s,d))
    
    #s = (5,7,-350)
    #d = (4,-8,1000)

    #print(equi(s,d))

    #problem 7
    #print(tree_age(50,20))
    #print(tree_age(100,20))


    # #problem 8
    #print(determinant([[1,2],[2,3]]))

    #eq1,eq2 = [1,1,3],[2,3,1]
    #print(solve(eq1,eq2))
    #eq1,eq2 = [[2,4,11],[-5,3,5]]
    #x_star,y_star = solve(eq1,eq2)
    #print(solve(eq1,eq2))
    #eq1,eq2 = [[3,-5,4],[7,4,25]]
    #print(solve(eq1,eq2))

    # Uncomment to see visualization
    # x = np.linspace(-2,6,100)
    # plt.plot(x,f_1(x),'r')
    # plt.plot(x,f_2(x),'b')
    # plt.plot(x_star,y_star,'go')
    # plt.show()

    # #problem 9
    #S = 30000
    #m = 4
    #r = 10/100
    #y = 2
    #for i in sinking_fund(S,r,m,y):
    #    print(i)

    #problem 10
    #data = [[1,2,4,6],[2,4,8,16],[10,30,90,270,810,2430]]
    #for d in data:
    #    print(is_geometric_sequence(d))

    #problem 11
    #portfolios =  {'A':{'stock':{'x':(41.45, 45),'y':(22.20,1000)}},'B':{'stock':{'x':(33.45,15),'y':(12.20,400)}}}
    #market = {'x':43.00, 'y':22.50}

    #for name, portfolio in portfolios.items():
    #    print(f"{name} {value(portfolio,market)}")

    #problem 12
    #data = [[], [1],[1,2],[1,2,2,3],[0,2,4,6,8]]
    #for d in data:
    #    print(smooth(d))  


    #problem 13
    #print(orbit(5,165))

    #problem 14
    #v0,h0 = 25,200
    #v1,h1 = 6,35
    #print(P_ws(v0,h0,v1,h1))