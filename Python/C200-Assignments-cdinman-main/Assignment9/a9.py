import numpy as np
import pandas as pd
#import matplotlib.pyplot as plt
import os
import math 
from sklearn.cluster import KMeans
#import matplotlib.pyplot as plt
#import seaborn as sn
from sklearn.metrics.cluster import rand_score
import math
import random



# problem 1
# The following 4 functions are filled in by us as per the HW PDF.
def cost_2(x):
    return 2000 + 500*x

def revenue(x):
    return 2000*x - 10*(x**2)

def profit(x):
    return revenue(x) - cost_2(x) 

def f(x):
    return x**6 - x - 1


# default h = 0.00001
# input function f and small amount (h)
# output approximation of the derivative of function f
def fp(f, h = 0.00001):
    def derivative(x):
        return (f(x + h) - f(x - h)) / (2 * h)
    return derivative
#works as intended

# input function f, it's derivative fp, value of x and h
# ouptut: root of f using newton-raphson
def newton(f, fp, x, h):
    maxIter=100  # Set a maximum number of iterations
    tolerance=h
    for _ in range(maxIter):
        fprime=fp(f)(x)
        xNew=x-f(x)/fprime
        if abs(xNew-x)<tolerance:
            return xNew
        x=xNew
    return x
#broken no clue how to fix

# Problem 2
# input dataframe for iris
# output: return 3 values 
# 1. a numpy array of feature values of least correlated features
# 2. pair of indices of least correlated features as tuple 
# 3. absolute value of correlation  

def lst_c_2(data):
    datanum = data.apply(pd.to_numeric, errors='coerce')
    datanum.dropna(inplace=True)
    matrix = np.corrcoef(datanum.values, rowvar=False)
    leastindices = np.unravel_index(np.argmin(np.abs(matrix - np.eye(matrix.shape[0]))), matrix.shape)
    leastvalues = datanum.iloc[:, leastindices].values
    leastvalue = np.abs(matrix[leastindices])
    return leastvalues, leastindices, leastvalue

#problem 3
# determine if x is even or not and return if

def even(x):
    return x%2==0

# input function, a, b, and number of intervals
# ouptut area under the function
def simpson(fn, a, b, n):
    if n % 2 == 0:
        raise ValueError("Number of subintervals must be odd")
    deltaX = (b - a) / n
    sumEnd = fn(a) + fn(b)
    for i in range(1, n):
        x_i = a + i * deltaX
        if even(i):
            sumEnd += 2 * fn(x_i)
        else:
            sumEnd += 4 * fn(x_i)

    return (deltaX / 3) * sumEnd

#problem 4

#the dictionary for the transation
aa_d = {}
#the FASTA file
DNA_d = []

#the correct translation
actual = "PLHSPHPANFCVFSRD-IPYSEHLRRGALDPGRFRGPRSELSEIERARSRDLRRGPGPPGGEAAARRPLEAAGPLAGPRRRSGVAGRGGFQRGDGAVRGGPGAGARPVEEAGQQRRRLHDRGPGKVRQAGRPRPQGPSLPKPPGRASPTFLSQDLPGFPRHEDLLLPPGPEPRLLTSQSPRPEGGGRAEPRRGAPGRPTPRAVRAEPPARVPAASGPGQLPGERLPCWAPVPGRAPAGWVRGACGAGAGE-ALSARRSSWATACW-PSPGTTPETSAPRCRRRWTSS-ATLSRRWFPSTAELWVGGRGIPRRPSPCLSKASPRSSLLAVLSRGQDARGRR"

#INPUT path and file name of amino acid file
#RETURN a dictionary 
#Key is a tuple (c0, c1, ... , cn) where ci are codons
#Value is a pair [name, abbreviation] for the amino acid
#make sure to close the file
def get_amino_acids(name):
    aa_d = {}
    try:
        with open(name, 'r') as file:
            lines = file.readlines()
            for line in lines:
                if line.strip():  # Ignore empty lines
                    parts = line.strip().split(' ')
                    codons = parts[1:]
                    full_name = parts[0].strip()
                    abbreviation = parts[-1].strip()
                    aa_d[tuple(codons)] = [full_name, abbreviation]
        return aa_d
    except FileNotFoundError:
        print(f"Error: File '{name}' not found.")
        return None



#INPUT path and file name of DNA sequence file
#RETURN a list [header, DNA]
#header is first line in the file
#DNA is a string of letters from remainder of file
#no whitespace
#make sure to close the file
def get_DNA(name):
    try:
        with open(name, 'r') as file:
            lines = file.readlines()
            amino_acids = {}
            for line in lines:
                parts = line.strip().split(',')
                codons = tuple(parts[:-1])
                names = parts[-1].split('_')
                amino_acids[codons] = [name.replace('_', ' ') for name in names]
            return amino_acids
    except FileNotFoundError:
        print(f"Error: File '{name}' not found.")
        return None


#INPUT FAST file
#RETURN a string representing the protein
#using the dictionary
def translate(DNA_d):
    header, sequence = DNA_d
    protein = ""
    for i in range(0, len(sequence), 3):
        codon = sequence[i:i+3]
        for key, value in aa_d.items():
            if codon in key:
                protein += value[1]
                break
        else:
            protein += "-"
    return protein



#problem 5
#input cost function and returns derivative
#output derivative

cost = lambda x:0.0001*(x**3) - 0.08*(x**2) + 40*x + 5000

def marginal_cost(cost):
    return(fp(cost))




#Problem 6
#INPUT temperature, initial temp, ambient temp, k
#OUTPUT time to reach T from T0 in Ta
def determine_t(T,T0,Ta,k):
    if T == Ta or T0 == Ta:
        print("Error: T and T0 must not be equal to Ta")
        return None
    change = math.log((T - Ta) / (T0 - Ta))
    return round(change / -k, 2)
#works as intended


#INPUT T0, Ta, k
#OUTPUT using the function above model the three data points
#using numpy polyfit and poly1d
def final_temp(T0,Ta,k):
    Tvalues = [30, 28, 26, 24]
    timeVal = [0, 5, 10, 15]
    coefficients = np.polyfit(timeVal, Tvalues, 1)
    poly = np.poly1d(coefficients)
    timefinal = determine_t(Tvalues[-1], T0, Ta, k)
    if timefinal is None:
        print("Error: determine_t function returned None")
        return None
    return round(timefinal, 2)
#broken no clue how to fix

#problem 7 
#INPUT string of digits
#OUTPUT list of list of digit sums
def n(xstr):
    def is_valid(a, b, c):
        try:
            return int(a) + int(b) == int(c)
        except ValueError:
            return False

    result = []
    for i in range(len(xstr) - 1):
        for j in range(i + 1, len(xstr)):
            for k in range(j + 1, len(xstr) + 1):
                if is_valid(xstr[i:j], xstr[j:k], xstr[k:]):
                    result.append([xstr[i:j], xstr[j:k], xstr[k:]])
    return result


#problem 8
#INPUT table of pairs [(year, YoY growth),...]
#RETURN a list of tuples ((x,y),z) where x,y is the interval that z probability is mapped to
def mc_p(data):
    totalgrow = sum(growth for _, growth in data)
    cumProb = 0
    cumDist = []
    for year, growth in data:
        probability = round(growth / totalgrow, 2)
        cumProb += probability
        cumDist.append(((round(cumProb - probability, 2), round(cumProb, 2)), growth))
    return cumDist

#works

#input data (need to have x_{i+1} - x_i = 1) for simplest model
#output polynomial 
def build_polyonomial(D):
    #Implement difference operator for values in list: lst at index x
    def delta(lst, x):
        return lst[x + 1] - lst[x]
 
    #Implement the delta operator on lst
    def Delta(lst):
        return [delta(lst, i) for i in range(len(lst) - 1)]
    
    #Create and return the difference table
    def dtable(lst):
        table = [lst]
        while len(lst) > 1:
            lst = Delta(lst)
            table.append(lst)
        return table
 
    #build rho 
    def rho(lst):
        diff_table = dtable(lst)
        rho_values = [diff[0] for diff in diff_table]
        return rho_values
    
    #build binomial function
    def prod(n):
        result = 1
        for i in range(1, n + 1):
            result *= i
        return result
    
    # return for the build polynomial should go here
    def build_polynomial_internal(D):
        rho_values = rho([y for _, y in D])
        n = len(D) - 1
        def binomial(x, n):
            return math.comb(n, x)
        def polynomial(x):
            result = 0
            for i in range(n + 1):
                term = binomial(i, n) * rho_values[i]
                for j in range(i):
                    term *= (x - D[j][0])
                result += term
            return result
        return polynomial
    return build_polynomial_internal(D)
#broken No clue how to fix

if __name__ == "__main__":
    """
    The code in "__main__" is not being graded, but a tool for you to test 
    your code outside of the `test_a7.py`. Feel free to add print statements. 
    """
    
    # General Instructions: Please comment the plotting related code (and the import of matplotlib and seaborn) 
    # before submitting to the Autograder. You can use the plots for testing on your VSC locally.
    
    """
    # #problem 1
    #find the root
    print(f"f(2) = {f(2)}")
    print(f"f(1) = {f(1)}")
    root  = newton(f,fp(f),2,.0001)
    print(f"f({root}) = {f(root)} ~ {round(f(root),2)}")

    # # # #find the maximal profit
    m = fp(profit)
    x = newton(m, fp(m), 1, .0001)
    print(f"x = {x}")
    print("The maximum profit is about ${0}".format(profit(round(x, 0))))
    print(profit(75))

    t = np.arange(0.0, 100.0)
    fig,ax = plt.subplots()

    ax.plot(t, profit(t),'g')
    ax.plot(75,profit(75), 'bo--')
    ax.set(xlabel ="Widgets Sold", ylabel="Profit ($)",
    title = "Maximal Profit = ${0}".format(profit(75)))
    ax.grid()
    plt.show()
    """


    """
    # Problem 2
    
    # How to install seaborn
    # Just as we installed packages during the lab, we will follow the same command. However, some students
    # reported difficulty with package installation, so we are listing the command that we have found to be
    # working consistently with most students.
    # if you can not install with the given commands, please reach out soon in OH or over InScribe
    
    # 1. Open a new Terminal in your VSC
    # On MAC, type the following command and hit enter
    # python3 -m pip install seaborn  
    
    # on Windows
    # py -m pip install seaborn
    
    # To verify the installation, open a new terminal in VSC and type python, hit enter
    # You will that the symbol in the terminla changed to >>>- this means now we are under Python's shell
    # Patse the following command and hit enter
    
    # import seaborn as sn

    # If the command is successful (you won't see any output because we are only importing) then you will see >>> again
    # If you see errors then it means that either seaborn is not installed or it could not be found by the default Python
    # that is used by VSC. In that case, the solution can involve different options,
    # so please take our help either in OH or simply write on InScribe.
        
    # The actual code starts here
    
    iris = pd.read_csv('Assignment9/iris.csv')
    print(iris.shape)
    print(iris.head())
    
    iris_features, pair, value = lst_c_2(iris)
    print(type(iris_features))
    
    i,j = pair
    print(f"Least corr columns: {iris.columns[i],iris.columns[j]} with {value}")
    
    # # using scikit-learn, cluster 
    result = KMeans(n_clusters=3, random_state=0, n_init="auto")
    iris_cluster_labels = result.fit_predict(iris_features)

    # # information
    print("The cluster labels:")
    print(iris_cluster_labels)
    print("The cluster centers:")
    print(result.cluster_centers_)
    rand_index = rand_score(iris_cluster_labels,iris['species'])
    print(f"The rand index is {round(rand_index,2)}")

    # # show how pure blocks are
    species = ['setosa', 'versicolor','virginica']
    dsp = { j:[]  for j in species }
    for i,j in enumerate(iris_cluster_labels):
        dsp[iris.species[i]].append(j)
    print("The three clusters and counts of members:")
    for k,v in dsp.items():
        print(f"{k} {v.count(0),v.count(1),v.count(2)}")

    
    # # plot k-means with actual side-by-side
    X,Y = [i[0] for i in iris_features],[i[1] for i in iris_features]
    colors = [['b','g','c'][i] for i in iris_cluster_labels]
    fig, ax = plt.subplots(1, 2)
    sc1 = sn.scatterplot(data=iris,x='petal_width',y='petal_length',hue=iris_cluster_labels,ax=ax[0])
    sc2 = sn.scatterplot(data=iris,x='petal_width',y='petal_length',hue='species',ax=ax[1])
    sc1.set(title="K-means")
    sc2.set(title="Actual")
    plt.show()
    """


    #problem 3
    """
    data = [[lambda x:3*(x**2)+1, 0,6,2],[lambda x:x**2,0,5,6],
        [lambda x:math.sin(x), 0,math.pi, 4],[lambda x:1/x, 1, 11, 6]]


    for d in data:
        f,a,b,n = d
        print(simpson(f,a,b,n))

    area = simpson(lambda t: 3*(t**2) + 1,0,6,10)
    t = np.arange(0.0, 10.0,.1)
    fig,ax = plt.subplots()
    s = np.arange(0,6.1,.1)
    ax.plot(t, (lambda t: 3*(t**2) + 1)(t),'g')
    plt.fill_between(s,(lambda t: 3*(t**2) + 1)(s)) 
    ax.grid()
    ax.set(xlabel ="x", ylabel=r"$f(x)=3x^2 + 1$",
    title = r"Area under the curve $\int_0^6\,f(x)$ ~" + f"{round(area,2)}")
    plt.show()
    """

    """
    #problem 4
    path = "Assignment9/"
    fn1, fn2 = "amino_acids.txt", "DNA.txt"
    aa_d = get_amino_acids(path+fn1)
    DNA_d = get_DNA(path+fn2)
    protein = translate(DNA_d)

    # print("Dictionary")
    print(aa_d)
    print("FASTA file")
    print(DNA_d)
    print("Translations match:", str(protein == actual))

    #should return "PLHS"    
    print(translate(["nothing", "CCACTGCACTCA"]))

    #should returns "D-"
    print(translate(["nothing", "GACTAA"]))
    """

    """
    #problem 5
    U,C = [],[]
    for unit in range(200,650,50):
        U.append(unit)
        mc = round(marginal_cost(cost)(unit),0)
        C.append(mc)
        print(f"For {unit} marginal cost is {mc}")
        
    plt.plot(U,C,'b-')
    plt.plot(300,round(marginal_cost(cost)(300)),'ro')
    plt.xlabel("Units of Production")
    plt.ylabel("Cost $")
    plt.title(r"Marginal cost Cost(x) =  $0.0001x^3 - 0.08x^2 + 40x + 5000$")
    plt.show()
    """
    
    #problem 6 
    """
    k = 0.19617
    T0 = 200
    Ta = 72
    T = 85
    print(determine_t(T,T0,Ta,k))
    print(final_temp(T0,Ta,k))
    """
    """
    #problem 7 
    data7 = ["11235813","1111223","1111213","11121114"]
    for d in data7:
        print(n(d))
    """
    """
    #problem 8
    data8 = [(2019,6),(2020,4),(2021,8),(2022,2),(2023,5)]
    print(mc_p(data8))
    """

    #extra credit
    """
    D = [(0,-2),(1,5),(2,7),(3,10)]
    pf = lambda x:x**3 - (11/2)*(x**2) + (23/2)*x - 2
    f = build_polyonomial(D)
    for i in range(7):
        print(i,pf(i),f(i))


    x = np.linspace(0,6,100)
    X,Y = [x for x,_ in D],[y for _,y in D]
    plt.plot(X,Y,'ro')
    plt.plot(x,f(x),'b-')
    plt.title("Newton Binomial Interpolation Formula")
    plt.show()
    """