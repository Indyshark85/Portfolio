
import numpy as np
import random as rn
import csv
import math
#import matplotlib
#import matplotlib.pyplot as plt
import csv
from sklearn.linear_model import LinearRegression
import pandas as pd
import os

#problem 1

#file open for payroll
#INPUT path+filename
#OUTPUT list of parent,child pairs
#CONSTRAINT use csv reader
def get_data_1(path, filename):
    with open(path,"r") as filename:
        output=csv.reader(filename)
        data=[]
        for x in output:
            data.append((float(x[0]),float(x[1])))
        return data
#copied from my A7 version of this code

#input data for univariate regression
#output slope, intercept, R^2, model
def my_scikit_LR(data6):
    
    X=np.array([[x] for x, _ in data6])
    y=np.array([y for _, y in data6])
    
    model=LinearRegression()
    model.fit(X, y)

    mHat=model.coef_[0]
    bHat=model.intercept_
    Rsq=model.score(X, y)

    return mHat,bHat,Rsq,model



#problem 2
#recursive functions

def p(n):
    if n:
        return p(n-1) + 0.02*p(n-1)
    else:
        return 10000

def p_t(n,acc=10000):
    if n<=0:
        return acc
    return p_t(n-1, acc+0.02*acc)

def p_w(n,acc=10000):
    end=acc
    while n>0:
        end+=0.02*end
        n-=1
    return end


def c(n):
    if n > 1:
        return 9*c(n-1) + 10**(n-1) - c(n-1)
    else:
        return 9

def c_t(n,acc1=9,acc2=1):
    if n <= 1:
        return acc1
    else:
        return c_t(n-1, 8*acc1+10**acc2, acc2+1)
      
   
def c_w(n, acc1=9, acc2=1):
    while n>1:
        acc1=9*acc1+10**acc2-acc1
        acc2+=1
        n-=1
    return acc1

def d(n):
    if n:
        return 3*d(n-1) + 1
    else:
        return 1

def d_t(n,acc=1):
    if n<=0:
        return acc
    return d_t(n-1,3*acc+1)
    
def d_w(n,acc=1):
    while n>0:
        acc= 3*acc+1
        n-=1
    return acc


#problem 3
def c_2(n,m):
    if m == 0 or n == m:
        return 1
    else:
        return c_2(n-1, m) + c_2(n-1, m-1)


def B(n):
    if n == 0:
        return 1
    else:
        return -sum(c_2(n+1, k)*B(k) for k in range(n))/(n+1)



#problem 4
#input function and epsilon
#output lambda expression (derivative)
def derivative(f,epsilon):
    return lambda x:(f(x+epsilon)-f(x-epsilon))/(2*epsilon)
    
def f(x):
    return x**2 - 3*x




# problem 5
#INPUT path and file name
#OUTPUT two lists of incomes and happiness 
#from income_data.csv
#use scikit-learn_LR 
def get_data_2(path,name):
    with open(path,"r") as filename:
        output=csv.reader(filename)
        next(output, None)
        data=[]
        for x in output:
            data.append((float(x[0]),float(x[1])))
        return data


#problem 6

#INPUTS ith candle, starting value of x, default width, and the four critical values: open, close, max\_p, min\_p.  
#RETURN three tuples: (point, width, height, color), topline, bottomline
#topline ((xt0,yt0),(xt1,yt1)) line from max to top middle of box
#bottomline ((xb0,yb0),(xb1,yb1)) line from min to bottom middle of box
def make(i,start,width_default,d):
    openP, closeP, maxP, minP = d

    x = start
    y = min(openP, closeP)
    width = width_default
    height = abs(openP - closeP)
    color = 'red' if openP > closeP else 'green'

    tx0 = start + width / 2
    ty0 = maxP
    tx1 = tx0
    ty1 = openP if openP > closeP else closeP

    bx0 = start + width / 2
    by0 = minP
    bx1 = bx0
    by1 = openP if openP < closeP else closeP

    return([[x,y],width,height,color],[(tx0,ty0),(tx1,ty1)],[(bx0, by1),(bx1, by0)])

#problem 7
#input list of unique positive integers [1,2], [1,2,3], ...
#output list of permutations [[1,2],[2,1]]  
#order of output does not matter
def permute(lst):
    Combos = [[]]

    for n in lst:
        newCombo = []
        for x in Combos:
            for i in range(len(x) + 1):
                newCombo.append(x[:i] + [n] + x[i:])
                if i < len(x) and x[i] == n:
                    break
        Combos = newCombo
    return Combos



if __name__ == "__main__":
    '''please add your own tests too'''
    
    # Windows skleanr install
    # Type the following command in the terminal (the terminal window within VSC) and press Enter
    # pip install -U scikit-learn
    
    # MAC
    # pip install -U scikit-learn

    #problem 1
    
    # Only when submitting to the Autograder, leave the path as blank string "", only provide the filename "payrollwins.txt"
    # To test on your system, you may need to provide the path as well. We encourage some testing to figure it out. 
    
    # you are welcome to test the plotting on your system, but 
    # before submitting to the Autograder, please comment out the plotting code.
    # and the import for matplotlib at the top. 
    """
    data6 = get_data_1("", "payrollwins.txt")
    print(f"Model built from parameters applied to 10: {(lambda x:0.1250*x + 67.498)(10)}")
    plt.plot([x for x,_ in data6],[y for _,y in data6],'ro')
    m_hat, b_hat, R_sq, model = my_scikit_LR(data6)
    print(f"m_hat: {m_hat}, b_hat: {b_hat}, R^2: {R_sq}")
    plt.plot([x for x, _ in data6], model.predict(np.array([[x] for x, _ in data6])), 'b')
    #plt.plot([x for x,_ in data6],model(np.array([[x] for x,_ in data6])),'b')
    print(f"Scikit Model applied to 10: {model.predict(np.array([[10]]))[0]}")
    #print(f"Scikit Model applied to 10: {model(np.array([[10]]))[0][0]}")
    plt.xlabel("$M cost")
    plt.ylabel("Wins")
    plt.title(f"Wins as function of $M cost R^2 = {round(R_sq,3)}")
    plt.show()
    """
    """
    # #Problem 2
    for i in range(5):
        print(p(i),p_t(i),p_w(i))
    
    # 10000 10000 10000
    # 10200.0 10200.0 10200.0
    # 10404.0 10404.0 10404.0
    # 10612.08 10612.08 10612.08
    # 10824.3216 10824.3216 10824.3216

    
    for i in range(1,7):
        print(c(i),c_t(i),c_w(i))
    
    # # 9 9 9 
    # # 82 82 82 
    # # 756 756 756 
    # # 7048 7048 7048 
    # # 66384 66384 66384 
    # # 631072 631072 631072 
    
    
    for i in range(5):
        print(d(i),d_t(i),d_w(i))

    # # 1 1 1 
    # # 4 4 4 
    # # 13 13 13 
    # # 40 40 40 
    # # 121 121 121 
   """ 
    """
    #problem 3
    for i in range(6):
        print(f"B({i}) = {B(i)}")
    # # B(0) = 1
    # B(1) = -0.5
    # B(2) = 0.16666666666666666
    # B(3) = -0.0
    # B(4) = -0.033333333333333305
    # B(5) = -7.401486830834377e-17
    """
    
    # # problem 4
    
    # you are welcome to test the plotting on your system, but 
    # before submitting to the Autograder, please comment out the plotting code.
    # and the import for matplotlib at the top. 

    """
    data = 2 
    epsilon = 10e-8
    print((derivative(f,epsilon)(data)))
    f_prime = derivative((lambda x:x**2-3*x),epsilon)
    print(f_prime(data))
    """
    """
    #uncomment to see the AI plot and your derivative in action!
    N = 50
    x = np.linspace(1,14,100)
    gm = np.zeros(N)
    r = np.zeros(N)
    def mean(lst):
        s_ = 0
        N = len(lst)
        for i in range(N):
            s_ += lst[i]
        m_ = round(s_/N,2)
        return m_

    def residuals(lst,m):
        s_ = 0
        N = len(lst)
        for i in range(N):
            s_ += (lst[i] - m)**2
        m_ = (1/2)*(s_/N)
        return m_
    data = [1,1,2,6,10,12,13,14]

    def update(w,data):
        eta = .2
        epsilon = 0.00001
        return w - eta*(derivative(lambda x:residuals(data,x),epsilon)(w))

    m_ = mean(data)
    fmean = 1
    for i in range(N):
        gm[i] = fmean
        r[i] = residuals(data,fmean)
        # print(fmean,residuals(data,fmean))
        fmean = update(fmean,data)

    print(gm[-1])
    print(m_)
    plt.plot(gm,r,'bo')
    for i in range(1,N):
        plt.plot([gm[i-1],gm[i]],[r[i-1],r[i]],'b--')
    plt.plot(m_,residuals(data,m_),'ro')
    plt.xlabel("Possible means")
    plt.ylabel("Error")
    plt.title(f"Using AI to search for the best mean {gm[-1]}")
    plt.show()
    """



    # #problem 5
    
    # you are welcome to test the plotting on your system, but 
    # before submitting to the Autograder, please comment out the plotting code.
    # and the import for matplotlib at the top. 

    """
    path,name = "", "income_data.csv"
    data4 = get_data_2(path,name)      
    plt.plot([x for x,_ in data4],[y for _,y in data4],'ro')
    m_hat, b_hat, R_sq, model = my_scikit_LR(data4)
    print(f"m_hat: {m_hat}, b_hat: {b_hat}, R^2: {R_sq}")
    plt.plot([x for x, _ in data4], model.predict(np.array([[x] for x, _ in data4]).reshape(-1, 1)), 'b')
    plt.xlabel("$ Income")
    plt.ylabel("Happines")
    plt.title(f"Happiness as a function of income R^2 = {round(R_sq,3)}")
    plt.show()
    """

    
    #problem 6
    
    # you are welcome to test the plotting on your system, but 
    # before submitting to the Autograder, please comment out the plotting code.
    # and the import for matplotlib at the top. 

    """
    data5 = [[20,15,32,10],[10,14,15,9],[22,23,27,9],[15,16,16,15],[26,12,30,2],[5,30,40,4]]


    fig = plt.figure()
    ax = fig.add_subplot(111)
    start = 0
    default_width = 10
   
    for i in range(len(data5)):
        candle_box,top_line,bottom_line = make(i,start,default_width,data5[i])

        print(candle_box)
        ax.add_patch(matplotlib.patches.Rectangle(*candle_box[0:3],color = candle_box[3]))
        plt.plot([x for x,_ in top_line],[y for _,y in top_line],'black')
        plt.plot([x for x,_ in bottom_line],[y for _,y in bottom_line],'black')
        start += default_width


    plt.xlabel("time (hour)")
    plt.ylabel("Stock X price")
    plt.title("Candlestick for XXX")  
    plt.xlim([0, 60]) #depends on number
    plt.ylim([0, 30]) #depends on price
  
    plt.show()
    

    fig = plt.figure()
    ax = fig.add_subplot(111)
    start = 0
    default_width = 2
   
    os.chdir("")

    wms = pd.read_csv('walmart_stock.csv', index_col='Date',parse_dates=['Date'])
    # print(wms.head())
    wms_c = wms[['Open','Close','High','Low']]
    print(wms_c.iloc[0].values.flatten().tolist())
    N_stocks = 50
    for i in range(N_stocks):
        candle_box,top_line,bottom_line = make(i,start,default_width,wms_c.iloc[i].values.flatten().tolist())

        # print(candle_box)
        ax.add_patch(matplotlib.patches.Rectangle(*candle_box[0:3],color = candle_box[3]))
        plt.plot([x for x,_ in top_line],[y for _,y in top_line],'black')
        plt.plot([x for x,_ in bottom_line],[y for _,y in bottom_line],'black')
        start += default_width


    plt.xlabel("time day")
    plt.ylabel("Walmart price $")
    plt.title("Candlestick for Walmart")  
    plt.xlim([0, N_stocks*default_width]) #depends on number
    plt.ylim([58, 63]) #depends on price
  
    plt.show()
    """

    #problem 7
    """
    print(f"[2,3] creates {math.factorial(len([1,2]))} elements")
    print(permute([1,2]))
    
    print(f"[1,2,3] creates {math.factorial(len([1,2,3]))} elements")
    print(permute([1,2,3]))

    print(f"[1,2,3,4] creates {math.factorial(len([1,2,3,4]))} elements")
    print(permute([1,2,3,4]))
    """
    