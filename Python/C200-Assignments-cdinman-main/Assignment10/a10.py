#import matplotlib.pyplot as plt
import numpy as np
import sqlite3
import random as rn
#import matplotlib
import pandas as pd
from sklearn import tree
from sklearn.tree import DecisionTreeClassifier
import math
#import matplotlib.cm as cm

#problem 1
#translates a random int into a step along the random walk
#parameters: numpy array x for tracking the left/right location at index i,
#numpy array y for tracking the forward/backward location at index i, int i for the step index,
# The test_seed parameters is for Autograder's internal purposes. 
# You are encouraged to pass different value for test_seed (in the code below __main__) to see
# how different answers are returned everytime the code is run.  
#return: none

def step(x, y, i, test_seed):
    
    rn.seed(test_seed) #leave this
    direction = rn.randint(1,4) #leave this
    
    # TODO: implement this function
    # Solution
    if direction==1:
        x[i]=x[i-1]+1
        y[i]=y[i-1]
    elif direction==2:
        x[i]=x[i-1]-1
        y[i]=y[i-1]
    elif direction==3:
        x[i]=x[i-1]
        y[i]=y[i-1]+1
    else:
        x[i]=x[i-1]
        y[i]=y[i-1]-1

#Do not change -- it's for visualization, but please comment the following graphit() function before submitting to the Autograder
def graphit(x,y,n):
    plt.title("Random {0} Walk\nLast Location {1},{2}".format(n,int(x[n-1]),int(y[n-1])) )
    plt.plot(x,y) 
    plt.plot([x[1],x[1]],[y[1]-10,y[1]+10], "b-")
    plt.plot([x[1]-10,x[1]+10],[y[1],y[1]], "b-")
    plt.plot([x[n-1]-10,x[n-1]+10],[y[n-1],y[n-1]], "r-")
    plt.plot([x[n-1],x[n-1]],[y[n-1]-10,y[n-1]+10], "r-")
    plt.show() 



#Problem 2
#Commented methods you must complete
class Complex_Number:
    def __init__(self, rpart, ipart):
        self.rpart=rpart
        self.ipart=ipart
    
    def get_real(self):
        return self.rpart
    
    def get_imag(self):
        return self.ipart
    
    def __str__(self):
        sign_ = "+" if self.ipart >= 0 else "-"
        if self.rpart != 0:
            return f"({self.rpart}{sign_}{abs(self.ipart)}j)"
        else:
            return f"{abs(self.ipart)}j"

    def __mul__(self,other):
        real_part = self.get_real()*other.get_real() - self.get_imag()*other.get_imag()
        imag_part = self.get_real()*other.get_imag() + self.get_imag()*other.get_real()
        iy = Complex_Number(real_part, imag_part)
        return iy
    
    #calculates the addition of two ComplexNumbers, i.e. self and ix
    #parameters: MyComplexNumber self, other complex number
    #return: the value of the additional
    def __add__(self,ix):
        real_part = self.get_real()+ix.get_real()
        imag_part = self.get_imag()+ix.get_imag()
        return Complex_Number(real_part, imag_part)

    #calculates the modulus of the self MyComplexNumber
    #parameters: MyComplexNumber self
    #return: the value of the modulus
    def __abs__(self):
        return (self.get_real()**2+self.get_imag()**2)**0.5
    
    #calculates the powers -- you should use multiplication and return a new instance
    #x**0 = 1, x**1 = x, ...
    def __pow__(self, exponent):
        if exponent == 0:
            return Complex_Number(1, 0)
        modulus = abs(self)
        argument = math.atan2(self.get_imag(), self.get_real())
        real_part = modulus ** exponent * math.cos(exponent * argument)
        imag_part = modulus ** exponent * math.sin(exponent * argument)
        return Complex_Number(round(real_part), round(imag_part))


def mandelbrot(z,MAX_ITER):
    c = Complex_Number(-0.1, 0.65)
    for n in range(MAX_ITER):
        if abs(z) > 10:
            return n / MAX_ITER
        z = z ** 2 + c
    return 1


# Problem 3
# Complete the query functions
# Remember that
def query1(db_cursor):
    db_cursor.execute("SELECT * FROM Weather")
    return db_cursor.fetchall()

def query2(db_cursor):
    db_cursor.execute("SELECT * FROM Weather WHERE High < 80")
    return db_cursor.fetchall()

def query3(db_cursor):
    db_cursor.execute("SELECT city FROM Weather WHERE low > (SELECT Low FROM Weather WHERE City = 'Albuquerque')")
    return db_cursor.fetchall()
    
def query4(db_cursor):
    db_cursor.execute("SELECT City, Low FROM Weather WHERE Low = (SELECT MIN(Low) FROM Weather)")
    return db_cursor.fetchall()

def query5(db_cursor):
    db_cursor.execute("SELECT City, High FROM Weather WHERE High = (SELECT MAX(High) FROM Weather)")
    return db_cursor.fetchall()

def query6(db_cursor):
    db_cursor.execute("SELECT SUM(High)/COUNT(High), SUM(Low)/COUNT(Low) FROM Weather")
    return db_cursor.fetchall()

def query7(db_cursor):
    db_cursor.execute("SELECT Low, COUNT(City) FROM Weather GROUP BY Low")
    return db_cursor.fetchall()


#problem 4
#input file name
#output fitted tree model from skicit learn 
def build_tree_model(credit_data):

    df = pd.read_csv(credit_data)
    df.dropna(inplace=True)
    X = df[['B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J ', 'K']]
    y = df['A']
    treeModel = DecisionTreeClassifier()
    treeModel.fit(X, y)
    return treeModel



# TEST CASES
if __name__ == "__main__":
    """
    # problem 1
    # #PROBLEM 1
    # #number of steps
    n = 100000   #You should change the number of steps to see
    #             #to see how it affects the plot
    x = np.zeros(n) 
    y = np.zeros(n) 

    test_seed = 41 # you can change this value to see how everytime, a different random walk is produced
    
    # #fill array with step values 
    for i in range(1,n):
        step(x,y,i, test_seed+i)
    
    # Remember to comment the following function call before submitting to the Autograder. 
    # You are free to test it in VSC.
    graphit(x,y,n)
    """
    """
    # #problem 2
    x1 = Complex_Number(1,2)
    y1 = complex(1,2)
    print(x1,y1)
    print(abs(x1),abs(y1))
    for i in range(6):
        print(x1**i,y1**i)
    MAX_ITER = 300
    width, height = 500, 500
    xmin,xmax = -1.5,1.5 # -2.0,.5
    xwidth = xmax - xmin 
    ymin, ymax = -1.5, 1.5 # -1.0,2.0
    yheight = ymax - ymin - 1

    m_ = np.zeros((width, height))
    X = list(range(width))
    for x in range(width):
        for y in range(height):
            X,Y = (x / width) * xwidth + xmin,  (y / height) * yheight + ymin
            z = Complex_Number(X,Y)
            v = mandelbrot(z,MAX_ITER)
            m_[x,y] = math.log(v)*math.atanh(v)
    

    # The following code is for plotting, please comment it before submitting to the Autograder.
    fig, ax = plt.subplots()
    ax.imshow(m_, aspect='equal', interpolation='nearest', cmap=cm.twilight, )
    ax.imshow(m_, cmap="PuOr")
    plt.axis ('off')
    plt.show()
    """

    """
    # problem 3
    #Data for list comprehension
    data = [
        ('Phoenix', 'Arizona', 105.0, 90.0),('Tucson', 'Arizona', 101.0, 92.0),
        ('Flag Staff', 'Arizona', 105.0, 90), ('San Diego', 'California', 77.0, 60.0),
        ('Albuquerque', 'New Mexico', 80.0, 72.0), ('Nome', 'Alaska', 64.0 ,-54.0)
    ]
    
    
    # We have already put the code for reading from the database file, and
    # creating the table.
    
    connection = sqlite3.connect("mydatabase.db")
    my_cursor = connection.cursor()
    my_cursor.execute('''DROP TABLE IF EXISTS Weather''')
    my_cursor.execute(''' CREATE TABLE Weather (City text, State text, High real, Low real)''')

    my_cursor.execute("INSERT INTO Weather Values('Phoenix', 'Arizona', 105, 90)")
    my_cursor.execute("INSERT INTO Weather Values('Tucson', 'Arizona', 101, 92)")
    my_cursor.execute("INSERT INTO Weather Values('Flag Staff', 'Arizona', 105, 90)")
    my_cursor.execute("INSERT INTO Weather Values('San Diego', 'California', 77, 60)")
    my_cursor.execute("INSERT INTO Weather Values('Albuquerque', 'New Mexico', 80, 72)")
    my_cursor.execute("INSERT INTO Weather Values('Nome', 'Alaska', 64 ,-54)")
    
    
    # QUERY 1 Select all the tuples
    print(query1(my_cursor))
    print("List Comprehension: ", data)


    # # QUERY 2 
    # # Select All the tuples where the high temperature is less than 80
    print("\nQuery 2")
    print(query2(my_cursor))
    print("List Comprehension: ", [d for d in data if d[2] < 80 ])


    # # QUERY 3 
    # # Select All the cities where the low temperature is greater than the low of Albuquerque 
    print("\nQuery 3")
    print(query3(my_cursor))
    print("List Comprehension: ",[d[0] for d in data if d[3] > [d[3] for d in data if d[0] == 'Albuquerque'][0]])

    # # QUERY 4 
    # # Select the city and temperature with the smallest low temperature 
    print("\nQuery 4")
    print(query4(my_cursor))
    print("List Comprehension: ",[(d[0],d[3]) for d in data if d[3] in (sorted(data, key = lambda x:x[3])[0])])


    # # QUERY 5 
    # # Select the city temperature with the largest high temperature 
    print("\nQuery 5")
    print(query5(my_cursor))
    print("List Comprehension: ",[(d[0],d[2]) for d in data if d[2] in (sorted(data, key = lambda x:x[2],reverse=True)[0])])


    # # QUERY 6 
    # # Display the average High and Low temperatures
    # # You are not allowed to use Avg()
    print("\nQuery 6")
    print(query6(my_cursor))
    print("List Comprehension: ", [(sum([d[2] for d in data])/len(data),sum([d[3] for d in data])/len(data))])


    # # QUERY 7 
    # # Give the counts of cities by their Low temperatures
    print("\nQuery 7")
    print(query7(my_cursor))
    print("List Comprehension: ", [(i,list(map((lambda x: x[3]),data)).count(i)) for i in set(map((lambda x: x[3]),data))])
    
    # Remember to close the connection
    connection.close()
    """
    """
    #problem 4
    path, filename = "Assignment10/", "cr.csv"
    tree_model = build_tree_model(path + filename)
    unknown = pd.DataFrame(data = [[0.025892469,38,0,74.5,1,13,0,0,0,2],[0.762158561,44,4,0.224465502,21000,5,0,1,0,3]],columns = ['B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J ', 'K'])
    print(tree_model.predict(unknown))  #1,0
    
    #The following lines are for plotting the tree, you can test it on VSC, but comment them before submitting to the Autograder.
    tree.plot_tree(tree_model)
    plt.show()
    """
    print("This print statement is written to prevent indentation error while testing")