import math

#Problem 1

#INPUT n0 start colony size, m growth rate, t time
#RETURN final colony size
def N(n_0, m, t):
    return (n_0*(math.exp(m*t)))

#INPUT t days
#RETURN number of teeth
def N_t(t):
   return math.ceil(71.8*math.exp(-8.96*math.exp(-0.0685*t)))

#INPUT pressures Pi, Pf 
#RETURN work joules
def W(P_i, P_f):
    return math.ceil(8.314*300*math.log(P_i/P_f))


#INPUT V miles per hour, A area, C_l lift coefficient
#RETURN lbs 
def L(V,A,C_l):
    return math.ceil(0.0033*(V**2)*(A)*(C_l))



###########################################################################
# Functions for Problem 2
###########################################################################
#INPUT coef = (a,b,c)
#RETURN tuple ('up'|'down', (vertex, y-value of vertex))
###########################################################################
def q(coef):
    a,b,c=coef
    x=round((-b)/(2*a),2)
    y=round((a*x**2)+(b*x)+c,2)
    coord=[x,y]
    if a<0:
        open="down"
    else:
        open="up"
    return (open,coord)


###########################################################################
# Functions for Problem 3
###########################################################################
#INPUT object x and list lst
#RETURN True if object occurs in the list
def m(x, lst):
    for item in lst:
        if x==item:
            return True 
    return False
            

#INPUT receipt= [[x0,y0],[x1,y1],...,[xn,yn]]
# x is item, y is cost
# tax_rate is the tax on taxable items
# no_tax is a list of items not taxable
#RETURN total amount owed (round values to 2 nearest decimal places)
def amt(reciept, tax_rate, no_tax):
    total=0
    for item,price in reciept:
        if m(item,no_tax):
            total+=price
        else:
            total+=(price*(1+tax_rate))
    return round(total,2)
        



###########################################################################
# Functions for Problem 4
###########################################################################
#INPUT p0 = (x0,y0) p1 = (x1,y1)
#RETURN dictionary y = mx + b
def make_line(p0,p1):
    x0 , y0 , x1 , y1 = * p0 ,* p1
    m = round((y1 - y0)/(x1 - x0),2)
    b = round(y0 - (m*x0),2)
    return {'m':m, 'b':b}


#INPUT two lines as dictionary
#RETURN a point (x,y) of intersection or "same line", "parallel lines"  
#rounded to two places
def intersection(l0, l1): 
    m,b=l0['m'],l0['b']
    m1,b1=l1['m'],l1['b']

    if m==m1:
        if b==b1:
            return "same line"
        else:
            return "parallel lines"
    x = round((b1 - b) / (m - m1), 2)
    y = round(m * x + b, 2)

    return (x, y)





###########################################################################
# Functions for Problem 5
###########################################################################
#INPUT List of numbers
#RETURN Various means or error message

err_msg = ["Data Error: 0 values", "Data Error: 0 in data"]

def arithmetic_mean(nlst):
    if len(nlst)==0:
        return err_msg[0]
    for x in nlst:
        if x==0:
            return err_msg[1]
    return (sum(nlst)/len(nlst))

def geo_mean(nlst):
    sum=0
    if len(nlst)==0:
        return err_msg[0]
    for x in nlst:
        if x==0:
            return err_msg[1]
        else:
            sum+=math.log(x)
    return (math.exp(sum/len(nlst)))

def har_mean(nlst):
    divSum=0
    if len(nlst)==0:
        return err_msg[0]
    for x in nlst:
        if x==0:
            return err_msg[1]
        else:
            divSum+=1/x
    return (len(nlst)/divSum)

def RMS_mean(nlst):
    sum=0
    if len(nlst)==0:
        return err_msg[0]
    for x in nlst:
        if x==0:
            return err_msg[1]
        else:
            sum=sum+x**2
    return (math.sqrt(sum/len(nlst)))


###########################################################################
# Function for Problem 6
###########################################################################
#INPUT x object, list of objects, integer y
#RETURN true if x occurs at least y times, false otherwise
def occur_at_least(x, lst, y):
    count=0
    for num in lst:
        if num == x:
            count+=1
    if count>=y:
        return True
    else:
        return False
        
        
###########################################################################
# Functions for Problem 7
###########################################################################
#input two objects x,y and list
#returns True if x occurs strictly more than y in lst, False otherwise
def occurs_more(x,y,lst):
    countx=0
    county=0
    for num in lst:
        if num==x:
            countx+=1
        elif num==y:
            county+=1
    if countx>county or len(lst)==0:
        return True
    else:
        return False



#input two objects x, y and list
#return if the number of times x,y occur in list are equal, then return the list
#if x occurs more than y, then remove the occurrences from the left side until
#their counts are equal, then return the list
#if y occurs more than x, the same procedure
def equal_remove(x,y,lst):

    deflst=lst.copy()
    cx=deflst.count(x)
    cy=deflst.count(y)

    while cx != cy:
        if cx>cy:
            deflst.remove(x)
            cx=deflst.count(x)
        elif cy>cx:
            deflst.remove(y)
            cy=deflst.count(y)
    return deflst

    
        

###########################################################################
# Functions for Problem 8
###########################################################################
#INPUT list of numbers
#RETURN True if geometric series, False otherwise
def is_geo(xlst):
    if len(xlst)<=2:
        return 0
    div=xlst[1]/xlst[0]

    for x in range(2, len(xlst)):
        if xlst[x] != xlst[x-1]*div:
            return 0
    return 1

        
###########################################################################
# Functions for Problem 9
###########################################################################
#INPUT pair of points in 2D
#RETURN distance round to two decimal places
def net_displacement(p0,p1):
    x0,y0=p0
    x1,y1=p1
    return round((((x0-x1)**2)+((y0-y1)**2))**(1/2),2)  

#INPUT starting position (x,y) and list of one step directions w,e,s,n that move the positon
#of x,y
#RETURN a tuple containing final destination, distance, distance from start
def track(start_pos, movement):
    x,y=start_pos
    dis=0
    x1=x
    y1=y
    for var in movement:
        if var=='e':
            x1+=1
        elif var == 'w':
            x1 -= 1
        elif var == 'n':
            y1 += 1
        elif var == 's':
            y1 -= 1
        dis+=1
    end_pos=x1,y1
    travel=net_displacement(start_pos,end_pos)
    return (end_pos,dis,travel)
    
###########################################################################
# Functions for Problem 10
###########################################################################
#INPUT pair of tuples from tracking
#RETURN distance betweem two ending places 
def final_distance(m0,m1):
    p0,_,_=m0
    p1,_,_=m1
    fd=net_displacement(p0,p1)
    return fd

###########################################################################
# Functions for Problem 11
###########################################################################
#INPUT presidential percentage
#RETURN house seats needed
def h_p(pres):
    h = pres**3 / (pres**3 + (1 - pres)**3)
    return round(h, 3)





###########################################################################
# Functions for Problem 12
###########################################################################
#INPUT amt and list of donations
#RETURN tuple: amt, donations left, the amount of the goal left
def go_fund_me(amt,donations):
    total= 0
    rem=donations.copy()
    for x in donations:
        total+=x
        rem.remove(x)
        if total>=amt:
            break
        rem.append(x)
    
    if total>=amt:
        return(amt,rem,total-amt)
    else:
        return(amt,[],-(amt-total))
    




###########################################################################
# Functions for Problem 13
###########################################################################
#INPUT credit score cs and list of potential clients [[n0,cd0],[n1,cd1],...,[nm,cdm]] where n is name, cd is unweighted dictionary of credit values
#RETURN list of people and their score that is strictly greater than cs; if nobody qualifies, then return empty list
def loan(cr, lst):
    e_apps=[]
    for name, inp, in lst:
        wsum=0.35*inp['P']+0.30*inp['A']+0.15*inp['L']+0.10*inp['N']+0.10*inp['C']
        if wsum>=cr:
            e_apps.append([name, wsum])
    return e_apps

#Problem 14
#INPUT current temperature T(t) of fish (T_t, environment T_e, temperature of fish and lst of what dogs were doing hours ago]
#OUTPUT The time (in hours) that elapsed after the murder reported as a float 
# round to six decimal places
#you must determine k from problem and formula from description
def time(T_t, T_e, T_0):
    k = -math.log(T_t - T_e) / (T_0 - T_e)
    elapsed_time = math.log((T_0 - T_e) / (T_t - T_e)) / k
    return round(elapsed_time, 6)

#Problem 15
#Input height, gravity, initial velocity
#Output (t0,t1) that rocket is at that height
def rocket(data):
    h, g, v = data
    discriminant = v**2 - 4 * g * (-h)
    if discriminant < 0:
        return None
    t1 = round((-v + math.sqrt(discriminant)) / (2 * g),2)
    t2 = round((-v - math.sqrt(discriminant)) / (2 * g),2)
    
    return (t1, t2)

#problem 16
#input signals A,B
#output signals X,Y
def ad(A, B):
    if A==0 and B==0:
        return 0,0
    elif A==0 and B==1:
        return 1,0
    elif A==1 and B==0:
        return 1,0
    elif A==1 and B==1:
        return 0,1


if __name__ == "__main__":
    """
    If you want to do some of your own testing in this file, 
    please put any print statements you want to try in 
    this if statement.

    You **do not** have to put anything here
    """
    # #problem 1
    #print(N(500,100,4)) 
    #print(N_t(1000))
    #print(W(10,1))
    #print(L(33.8,512,0.515))

    #problem 2
    #print(q((-2.6,7.6,-10)))
    #print(q((1,-10.2,26.01)))

    #problem 3
    #receipt = [[1,1.45],[3,10.00],[2,1.45],[5,2.00]]
    #tax_rate,no_tax = 7/100, [33,5,2]
    #print(amt(receipt,tax_rate, no_tax))
    #print(amt(receipt,10/100,[]))

    # #problem 4
    #p0 = (32,32)
    #p1 = (29,5)
    #p2 = (15,10)
    #p3 = (49,25)
    #p4 = (15,30)
    #p5 = (50,15)
 
    #l0,l1 = make_line(p0,p1),make_line(p2,p3)
    #print(intersection(l0,l1))
    #l0 = make_line(p4,p5)
    #print(intersection(l0,l1))
    
    #p6,p7,p8 = (0,0),(1,1),(2,2)
    #p9,p10 = (0,1),(1,2)
    #print(intersection(make_line(p6,p7),make_line(p7,p8))) # same line
    #print(intersection(make_line(p6,p7),make_line(p9,p10))) # parallel lines

    #problem 5
    #print(arithmetic_mean([1,2,3]))
    #print(geo_mean([2,4,8]))
    #print(har_mean([1,2,3]))
    #print(RMS_mean([1,3,4,5,7]))
    #print(RMS_mean([8,4,3,9,0,5,10]))

    #problem 6
    #data6 = [[1,[1,2,1,2,1,1],4], [1,[1,2,1,2,1,1],3],
    #    [1,(1,2,1,2,1,0),4], ]

    #for d in data6:
    #    print(occur_at_least(*d))

    #problem 7
    #lst = [2,2,3,1,2,1,1,2]
    #print(occurs_more(1,2,lst))
    #print(occurs_more(2,3,lst))
    #print(occurs_more(2,3,[]))
 

    #print(equal_remove(1,2,lst))
    #print(equal_remove(1,3,lst))
    #print(equal_remove(2,3,lst))
    #print(occurs_more(2,3,(equal_remove(2,3,lst))))

    # #problem 8
    #xlst = [1/2,1/4,1/8,1/16,1/32]
    #print(is_geo(xlst))
    #xlst = [1,-3,9,-27]
    #print(is_geo(xlst))
    #xlst = [625,125,25]
    #print(is_geo(xlst))
    #xlst = [1/2,1/4,1/8,1/16,1/31]
    #print(is_geo(xlst))
    #xlst = [1,-3,9,-26]
    #print(is_geo(xlst))
    #xlst = [625,125,24]
    #print(is_geo(xlst))
    #print(is_geo([1/2,1/4]))

     #problem 9
    #data_m9 = [[(0,0), list(10*'n' + 15*'e' + 10*'s'+15*'w')],
    #    [(0,0), list(3*'n' + 4*'e')],
    #    [(1,2), list(3*'s' + 4*'w')]]

    #for d in data_m9:
    #    print(track(*d))

    #problem 10
    #data_m10 = [[(0,0), list(10*'n' + 15*'e' + 10*'s'+15*'w')],
    #      [(0,0), list(3*'n' + 4*'e')],
    #      [(1,2), list(3*'s' + 4*'w')]]

    #print(final_distance(track(*data_m10[1]),track(*(data_m10[2]))))

    # #problem 11
    #print (h_p(.6))


    # #problem 12
    #data12 = [[100,[10,15,20,30,29,13,15,40]],
    #    [100,[]],
    #    [100,[30,4]]]

    #for d in data12:
    #    print(go_fund_me(*d))
    #print(go_fund_me(50, [45,47,78]))

    #Problem 13
    #data = [['x',{'P':600, 'L':700,'A': 500, 'N': 170, 'C': 250}],
    #    ['y',{'P':550, 'L':720,'A': 500, 'N': 230, 'C': 250}],
    #    ['b',{'P':560, 'L':710,'A': 500, 'N': 221, 'C': 250}],
    #    ['c',{'P':800, 'L':700,'A': 200, 'N': 100, 'C': 150}],
    #    ['a',{'P':800, 'L':800,'A': 600, 'N': 250, 'C': 150}],
    #    ['z',{'P':800, 'L':800,'A': 500, 'N': 250, 'C': 150}]]
    #print(loan(550,data))

    #problem 14
    #initial scene of the crime data
   
    no_alibis = {"Ursala":[3,4],"Shilah":[2,2.5],"Kaiser":[1,2]}
    T_t = 81
    T_e = 65
    T_0 = 85
    time_discovered = 4 #PM Dr. D's living room
    suspects = []
#
    time_of_murder = time_discovered - time(T_t, T_e, T_0)
    for name,times in no_alibis.items():
        start,end = times
        if start <= time_of_murder <= end:
            suspects.append(name)
#
    print(f"The suspect(s) {suspects}")


    #problem 15
    #data_15 = (180, -16, 120)
    #print(rocket(data_15))

    #problem 16
    #for i_16 in [(0,0), (0,1), (1,0), (1,1)]:
    #    print(ad(*i_16))