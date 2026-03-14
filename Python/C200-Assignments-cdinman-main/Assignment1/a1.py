# We have added import math
# It's only needed once
import math

# Problem 1
#input radius r, height h
#return volume
def c(r,h):
    return(round(1/3*math.pi*(r**2)*h,2))
#a=c(2,5)
#print(a)

# Problem 2
#input t days
#output oxygen conten percent of it normal level
def f(t):
    return(round(100*((t**2+(10*t)+100)/(t**2+(20*t)+100)),2))
#b=f(9)
#print(b)


# Problem 3
#input t hours
#return percent watching tv
def P(t):
   return(round((0.01354*(t**4))-(0.49375*(t**3))+(2.58333*(t**2))+(3.8*(t))+(31.60704),2))
#c=P(3)
#print(c,"%")

# problem 4
#input x percent
#return millions of dollars
def cost(x):
    return(round((0.5*(x))/(100-x),2))
#d=cost(90)
#print("$",d, "Million")

# Problem 5
#input dosage a mg and years t
#return child dosage mg
def D(t,a):
    return(round(((t + 1)/(24))*a,2))
#e=D(4,500)
#print(e,"mg")

# Problem 6
#input number of susceptible, but healthy children
#output number of the infected children
# use math.ceil() before returning your final answer.
def I(S):
   return(math.ceil(192*math.log2(S/762))-S+763)
#f=I(100)
#print(f)
# Problem 7
#input number of items 
#output total cost 
# q > 0
def C(q):
    return((0.01*(q**3))-(0.6*(q**2))+(13*q)+1000)
#g=C(50)
#print(g)

#input number of items
#output average cost
def A(q):
    return(((0.01*(q**3))-(0.6*(q**2))+(13*q)+1000)/q)
#h=A(50)
#print(h)
# Problem 8
#input months t=0,...,11
#output items sold x 1000
def hh(t):
    return(math.floor(532 / (1 + 869 * math.exp(-1.33 * t))))

#i=hh(25)
#print(i)
# Problem 9
#input time seconds
#output feet
def height(t):
    return(round(-16*(t**2)+(64*t)+80,2))
#print(height(5))
# Problem 10
#input t hours
#output percent treatment
def B(t):
    return(round(((0.44*(t**4))+700)/((0.1*(t**4))+7),2))

#print(B(100))

# Problem 11
#input coefficients for quadratic and value
#output True if value is root, False otherwise
def quad(a,b,c,x):
    if (a*(x**2)+(b*x)+c) == 0:
        return(True)
    else:
        return(False)



# Problem 12 
#input P principle, n times per year, t years, r rate
#output dollars
def R(P,r,n,t):
    return(round(P*(((1+(r/n))**(n*t)-1)/(r/n)),2))

#Problem 13
#input dimensions w,l,h for width, length, height of a 
# rectangular solid
#output total surface area
def S(w,l,h):
    return(2*((w*l)+(h*l)+(h*w)))
#print(S(6,4,7))
#Problem 14
#input side s of a square
#output diagonal length 
def square_diagonal(s):
    return (s*math.sqrt(2))

#input diagonal of a square
#output area of largest circle inscribed in square
def circle_area(d):
   return(round((math.pi*(d**2))/(8),2))

#Problem 15
#input earned runs e, innings pitched i, total innings t
#output earned runs average
def ERA(e,i,t):
  return (round((e/i)*t,2))
#print(ERA(4,6,9))

#problem 16
#input temperature (F), wind speed (mph)
#output wind chill
def T_wc(temp,wind_speed):
   return math.floor(35.74+(0.6215*temp)-(35.75*(wind_speed**0.16))+(0.4275*temp*(wind_speed**0.16)))


#problem 17
#input n
#output approximate to n!
def fact_est(n):
   return math.floor(math.sqrt(2*(math.pi)*n)*((n/math.e)**n))



if __name__ == "__main__":
    """
    If you want to do some of your own testing in this file, 
    please put any print statements you want to try in 
    this if statement. As you complete the functions in this file, 
    you can uncommnet the following statements to test specific functions.
    
    Please remember to comment the statements before submitting the final version of your
    HW to the Autograder.
    And, please don't forget to push to github by the deadline.
    """

    #problem 1
    # volume of cone
    # print(c(2,5)) 
    # print(c(3,7))

    #problem 2
    #oxygen content
    # print(f(0))
    # print(f(10))

    #problem 3
    #tv watching
    # print(P(0))
    # print(P(3))
    # print(P(8))

    #problem 4
    #toxic waste
    # print(cost(50))
    # print(cost(70))
    # print(cost(90))

    #problem 5
    # cowling's rule
    # print(D(4,500))
    
    #problem 6
    #flu outbreak
    # S_6 = 100
    # print(I(S_6))
    # S_6 = 300
    # print(I(S_6)) 

    #problem 7
    #average cost
    #make your own inputs/outputs
    
    
    #problem 8
    # print(hh(0))
    # print(hh(5))
    # print(hh(10))

    #problem 9
    # print(height(5))
   
    #problem 10        
    #make your own inputs/outputs

    #problem 11
    #quadratic roots
    #print(quad(2,5,-12,-4))
    #print(quad(2,5,-12,3/2))
    #print(quad(2,5,-12,1))

    # problem 12
    # Sinking Fund
    #P = 22000
    #n = 1
    #t = 7
    #r = 6/100
    #print(R(P,r,n,t))
    # P = 500
    # n = 12
    # t = 20
    # r = 4/100
    # print(R(P,r,n,t))
    # P = 1200
    # n = 4
    # t = 10
    # r = 8/100
    # print(R(P,r,n,t))

    #problem 13
    #make your own inputs/outputs

    #problem 14
    #s_13 = 10
    #a_13  = circle_area(square_diagonal(s_13))
    #print(a_13)

    #problem 15
    #e_14,i_14,t_14 = 4,6,9
    #print(ERA(e_14,i_14,t_14))

    
    #problem 16
    #temp_15, wind_speed_15 = 2,5
    #print(T_wc(temp_15,wind_speed_15))

    #problem 17
    #n0_16 = 5
    #print(math.factorial(n0_16),fact_est(n0_16))
    #n0_16 = n0_16 * 10
    #print(math.factorial(n0_16),fact_est(n0_16))