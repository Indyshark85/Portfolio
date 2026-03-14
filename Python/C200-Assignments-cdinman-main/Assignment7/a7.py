import math
import random as rn
import numpy as np
#import matplotlib.pyplot as plt
import os 
import csv


# problem 1
# recursion remove [] and () from list or tuples
# ignore [] () empty
# return values in the order as they occur in lst
def fl(lst):
    newlst=[]
    for x in lst:
        if isinstance(x,(list,tuple)):
            if x:
                newlst.extend(fl(x))
        else:
            newlst.append(x)
    return newlst

#PROBLEM 2
#list comprehension
#INPUT path, filename (remember that you need to form the correct path to read the file)
#OUTPUT list of parent,child pairs
#CONSTRAINT use csv reader
def get_data_1(path, filename):
    with open(path, "r") as filename:
        return [x for x in csv.reader(filename)]
    """
    with open(path,"r") as filename:
        output=csv.reader(filename)
        data=[]
        for x in output:
            data.append(x)
        return data
    """
    
#input parent name, family data
#output children
#constraint using comprehension
def get_child(name, data):
    return [child for parent,child in data if parent == name]

#input parent name, family data
#output true if has children
#constraint using comprehension
def has_children(name, data):
    return bool(get_child(name,data))

#input child name, family data
#output parent of child
#constraint using comprehension
def get_parent(name, data):
    parents=[parent for parent,child in data if child==name]
    return parents[0] if parents else []
    #return [parent for parent,child in data if child==name]
    """
    for parent,child in data:
        if child==name:
            return parent
    """

#input child name1, child name2, family data
#output true if children have same parent
#constraint using comprehension
def siblings(name1,name2,data):
    return bool(get_parent(name1,data)==get_parent(name2,data))
    """
    if get_parent(name1,data)==get_parent(name2,data):
        return True
    else:
        return False
    """

#input grandparent name1, grandchild name2, family data
#output true if name1 is grandparent to name2
#constraint using comprehension 
def grandparent(name1,name2,data):
    return bool(get_parent(get_parent(name2,data),data)==name1)
    """
    if get_parent(get_parent(name2,data),data)==name1:
        return True
    else:
        return False
    """
   
#input family data
#output all names
#constraint comprehension list only
def get_all(data):
    return [k for k in [i for i,j in data] + [j for i,j in data]]

#input name1, name2, family data
#output true if name1 and name 2 are cousins, i.e., have the same grandparents
def cousins(name1,name2,data):
    return bool(get_parent(get_parent(name1,data),data)==get_parent(get_parent(name2,data),data))
    """
    if get_parent(get_parent(name1,data),data)==get_parent(get_parent(name2,data),data):
        return True
    else:
        return False
    """

#problem 3
# Input size (total sum), numbers, starter tiles
# Ouptut all combinations of tiles that generate the sum
def tiles(n,v,lst):
    def generate(CS, CC):
        if sum(CC)==n:  
            return [CC]
        elif sum(CC)>n:
            return []
        else:
            combos = []
            for num in v:
                new_sum = CS + num
                if new_sum <= n:
                    new_combo = CC + [num]
                    combos += generate(new_sum, new_combo)
            return combos
            #return [newCombo for num in v for newCombo in generate(CS, CC + [num])]
            """
            combos=[]
            for num in v:
                combos+=generate(CS, CC+[num])
            return combos
            """
    combos = [combo for starter in lst for combo in generate(0, starter)]
    return combos

#problem 4
# Input A nested (list inside another list) list of numbers
# Output a list containing two objects: 1) maximum sum, 2) the list of boolean corresponding to the numbers being summed
def max_adjacent(lst):
    n = len(lst)
    if n==0:
        return [0,[]]
    elif n==1:
        return [lst[0],[1]]
    maxS=[0]*n
    included=[0]*n
    maxS[0]=lst[0]
    maxS[1]=max(lst[0], lst[1])
    included[0]=1
    included[1]=1 if lst[1]>=lst[0] else 0
    for i in range(2, n):
        include=lst[i]+maxS[i-2]
        exclude=maxS[i-1]
        if include>exclude:
            maxS[i]=include
            included[i]=1
        else:
            maxS[i]=exclude
            included[i]=0
    result=[0]*n
    i=n-1
    while i>=0:
        if included[i]==1:
            result[i]=1
            i-=2
        else:
            i-=1
    return[maxS[-1],result]



# PROBLEM 5
#INPUT path, filename (remember that you need to form the correct path to read the file)
#OUTPUT list of payroll and wins
#CONSTRAINT use csv reader
def get_data_4(path, filename):
    with open(path,"r") as filename:
        output=csv.reader(filename)
        data=[]
        for x in output:
            data.append((float(x[0]),float(x[1])))
        return data
        

#INPUT data points (x0,y0),...,(xn,yn)
#OUTPUT best regression slope m_hat, b_hat, R_sq
def std_linear_regression(data):
    n=len(data)
    XYp=sum(x*y for x,y in data)
    Xs=sum(x for x,_ in data)
    Ys=sum(y for _,y in data)
    Xsq=sum(x**2 for x,_ in data)
    Ysq=sum(y**2 for _,y in data)

    """
    XYp = Xs = Ys = Xsq = Ysq = 0
    for x,y in data:
        XYp+=x*y
        Xs+=x
        Ys+=y
        Xsq+=x**2
        Ysq+=y**2
    """
    Sxy=XYp-(Xs*Ys/n)
    Sxx=Xsq-(Xs**2/n)
    mhat=Sxy/Sxx
    bhat=(Ys-(mhat*Xs))/n
    
    SST = Ysq-((Ys**2)/n)
    SSE = Ysq-(bhat*Ys)-(mhat*XYp)
    R2 = (SST-SSE)/SST
    return round(mhat,3),round(bhat,3),round(R2,3) #this doesnt ever produce the correct solution

#Problem 6
# Input path, filename (remember that you need to form the correct path to read the file)
# Output two separate lists containing age and length, respectively
def get_fish_data(path,name):
    
    data=open(path,"r")
    alldata=data.read()
    ages=[]
    lens=[]
    lines=alldata.split('\n')
    for i in lines[1:]:
        vals=i.split(',')
        ages.append(int(vals[0]))
        lens.append(float(vals[1]))         
    data.close()
    return (ages,lens)
    

#INPUT two lists X (age) values and Y (length) values of data
#RETURN a polynomial of degree three
def make_function(X, Y,degree):
    coefs=np.polyfit(X, Y, 3)
    return(np.poly1d(coefs)) 


#Problem 7
#input string and positive integer n
#output a list of the longest string that have exactly n distinct symbols
def max_n(str, n):
    maxL=0
    maxSub=[]
    for i in range(len(str)):
        window={}
        distinct=0
        start=i
        for j in range(i,len(str)):
            if str[j] not in window:
                distinct+=1
                window[str[j]]=1
            else:
                window[str[j]]+=1
            if distinct>n:
                break
            if j-start+1>maxL and distinct==n:
                maxL=j-start+1
                maxSub=[str[start:j+1]]
            elif j-start+1==maxL and distinct==n:
                maxSub.append(str[start:j+1])
    return maxSub

#problem 8
#input model parameters and number of trials
#output the percent success rounded to two decimal places
def simulation(parameters, trials):
    amt,prob,goal=parameters
    wins=0
    for i in range(trials):
        money=amt
        while money>0 and money<goal:
            outcome=np.random.binomial(1,prob,1)[0]
            if outcome==1:
                money+=1
            else:
                money-=1
        if money==goal:
            wins+=1
    success = (wins/trials)
    return round(success,2)


#problem 9 candy box optimization
#input number of candies and space in square box
#output amount of empty space
def package(candies, space=9):
    SL=space
    empty=0
    for candy in candies:
        if candy > SL:
            empty+=SL
            SL=space-candy
        else:
            SL-=candy
    empty+=SL
    return empty



if __name__ == '__main__':
    #uncomment to help
    """
    #problem 1
    data_0 = [[],tuple(),[[],tuple()],[1],[1,2,[3,4],5],[(1,2,[3]),4,[5,(6,(7))]],[[[[(1,2,3)]]]]]
    for d in data_0:
        print(fl(d))
    
    #problem 2
    
    # Only when submitting to the Autograder, leave the path as blank string "", only provide the filename "family.txt"
    # To test on your system, you may need to provide the path as well. We encourage some testing to figure it out.  
    """
    """
    data_1 = get_data_1("Assignment7/family.txt", "family.txt")
    print(data_1)
    print(has_children('0',data_1)) #true
    print(has_children('7',data_1)) #false
    print(get_child('6',data_1))
    print(get_parent('g',data_1))
    print(siblings('7','A',data_1)) #true
    print(siblings('2','7',data_1)) #false
    print(grandparent('0','3',data_1)) #true
    print(grandparent('0','7',data_1)) #false
    print(get_all(data_1))
    print(cousins('3','6',data_1)) #true
    print(cousins('3','5',data_1)) #false
    """
    
    
    """
    #problem 3
    n = 6
    v = [1,2,3]
    print(tiles(n,v,[[i] for i in v]))
    for i in tiles(n,v,[[i] for i in v]):
        print(sum(i), end="")
    print()
    n = 4
    v = [1,2]
    print(tiles(n,v,[[i] for i in v]))
    for i in tiles(n,v,[[i] for i in v]):
        print(sum(i), end="")
    
    
    
    #problem 4
    data = [[5,1,4,1,5],[5,6,2,4],[4,5,1,1],[1,5,10,4,1],[1,1,1,1,1]]
    for d in data:
        print(max_adjacent(d))
    
    
    #problem 5
    
    # Only when submitting to the Autograder, leave the path as blank string "", only provide the filename "payrollwins.txt"
    # To test on your system, you may need to provide the path as well. We encourage some testing to figure it out.  
    
    data_4 = get_data_4("Assignment7/payrollwins.txt", "payrollwins.txt")
    m_hat, b_hat, R_sq  = std_linear_regression(data_4)
    print(m_hat,b_hat,R_sq)
    plt.plot([x for x,_ in data_4],[y for _,y in data_4],'ro')
    plt.plot([x for x,_ in data_4],[m_hat*x + b_hat for x,_ in data_4],'b')
    plt.xlabel("$M Payroll")
    plt.ylabel("Season Wins")
    plt.title(f"Least Squares: m = {m_hat}, b = {b_hat}, R^2 = {R_sq} ")
    plt.ylabel("Y")
    plt.show()
    
    
    # #problem 6
    
    # Only when submitting to the Autograder, leave the path as blank string "", only provide the filename "fish_data.txt"
    # To test on your system, you may need to provide the path as well. We encourage some testing to figure it out.  
    
    path_5, name_5 = "","fish_data.txt"
    X,Y = get_fish_data(path_5,name_5)
    data5 = [[i,j] for i,j in zip(X,Y)]
    print(data5)
    
    plt.plot(X,Y,'ro')
    xp = np.linspace(1,14,10)
    degree = 3
    p3 = make_function(X,Y,degree) #use np make poly function
    plt.plot(xp,p3(xp),'b')
    plt.xlabel("Age (years)")
    plt.ylabel("Length (inches)")
    plt.title("Rock Bass Otolith")
    plt.show()
    
    """
    """
    #problem 7
    data = ["aaaba", "abcba", "abbcde","aaabbbaaaaaac","abcdeffg"]
    for d in data:
        for i in range(1,7):
            print(f"{d} with {i} max is\n {max_n(d,i)}")
    """
    """
    #problem 8
    model_parameters = (2,.6,4) #starting amount, probablity of win, goal
    print(simulation(model_parameters,100000))

    
    #problem 9
    candies = [[3,3,3],[3,1,2,2,1],[1,2,2,2,3],[3,2,2,3,1,3]]
    for c in candies:
        print(package(c))
    """

    print()