import math

########################
# PROBLEM 1
########################

#recursive functions
# Input: a number n
# Output: equation 1 and 2, problem 1
def p(n):
    if n==0:
        return 10000
    else:
        return p(n - 1) + 0.02 * p(n - 1)

# Input: a number n
# Output: equation 3 and 4, problem 1
def c(n):
    if n==1:
        return 9
    else:
        return 9 * c(n - 1) + 10**(n - 1) - c(n - 1)

# Input: a number n
# Output: equation 5 and 6, problem 1
def d(n):
    if n==0:
        return 1
    else:
        return 3*d(n-1)+1

# Input: a number n
# Output: equation 7 and 8, 9, problem 1
def f(n):
    if n==0 or n==1:
        return 1
    else:
        return f(n-1)+f(n-2)
 
# Input: a number n
# Output: equation 10 and 11 problem 1  
def e(n):
    if n==1:
        return 12
    else:
        return -5*e(n-1)

# Input: number c and i
# Output: equation 12  problem 1  
def M(c, i):
    if c==0:
        return 1
    elif c<0 or f(i)>10:
        return 0
    else:
        return M(c-f(c),i+1)+M(c,i+1)

# Input: number n and k
# Output: equation 13  problem 1  
def c_2(n,k):
    if k==0 or n==k:
        return 1
    else:
        return c_2(n-1,k-1)+c_2(n-1,k)
    



########################
# PROBLEM 2
########################
# Input: two strings
# Output: Output created as per recursion for f1 in problem2
def f1(xstr1, xstr2):
    if xstr1=="":
        return ""
    elif xstr1[0] in xstr2:
        return "1"+f1(xstr1[1:],xstr2)
    else:
        return "0"+f1(xstr1[1:],xstr2)

# Input: a list
# Output: Output created as per recursion for f2 in problem2
def f2(lst):
    if lst==[]:
        return 0
    else:
        return math.log2(lst[0])+f2(lst[1:])
    
# Does not work correctly but returns

# Input: a list and objects x and y
# Output: Output created as per recursion for f3 in problem2
def f3(lst,x,y):
    xc=0
    xy=0
    for elem in lst:
        if elem == x:
            xc+=1
        elif elem == y:
            xy+=1
    if xc == xy:
        return True
    else:
        return False




########################
# PROBLEM 3
########################
# Input: A list
# Output: pyramid constructed as per recursion described in problem 3

def m(lst):
    if len(lst)<=1:
        return []
    elif len(lst) ==2:
        return [[lst[0]+lst[1]]]
    else:
        newLst=[]
        for x in range(len(lst)-1):
            newLst.append(lst[x]+lst[x+1])
        return m(newLst)+[newLst]



########################
# PROBLEM 4
########################

# Input: a message to be encoded,  value of shift
# Output: encoded message
def encode(msg,shift):
    encoded=""
    for char in msg:
        if char==" ":
            shifted=chr(((ord("{")-ord('a')+shift)%27)+ord('a'))
            encoded+=shifted
        else:
            shifted=chr(((ord(char)-ord('a')+shift)%27)+ord('a'))
            encoded+=shifted
    return encoded

    
# Input: encoded message, value of shift
# Output: decoded message
def decode(msg,shift):
    decoded = ""
    for char in msg:
        shifted_char = chr(((ord(char)-ord('a')-shift)%27)+ord('a'))
        decoded += shifted_char
    decodedspace="" #I am very aware there is a more efficient way to do this 
    for char in decoded:
        if char=="{":
            decodedspace+=" "
        else:
            decodedspace+=char
    return decodedspace



########################
# PROBLEM 5
########################
# Input: day, name of stock, account, number of shares
# Output: stock name, number of shares purchased, price per share, total amount spent. Return Flase, if insufficient funds
stock = {0: {'A':200, 'B':20, 'C':125, 'D':90},
             1: {'A':190, 'B':25, 'C':122, 'D':91},
             2: {'A':195,  'B':30,'C':122, 'D':80}}

def buy(day, name, account, shares):
    currentday=stock[day]
    totalbuy=currentday[name]*shares
    if account["fund"]-totalbuy<0:
        return False
    elif name in account:
        account["fund"]-=totalbuy
        account[name]+=shares
    else:
        account["fund"]-=totalbuy
        account[name]=shares
    return (name,shares,currentday[name],totalbuy)



# Input: day, name of stock, account, number of shares
# Output: stock name, number of shares sold, value of stock on that day, total amount earned. 

def sell(day, name, account, shares):
    currentday=stock[day]
    
    if name not in account:
        return False
    elif account[name]<shares:
        totalsell=currentday[name]*account[name]
        account["fund"]+=totalsell
        account[name]-=account[name]
        if account[name]<=0:
            del account[name]
    else:
        totalsell=currentday[name]*shares
        account["fund"]+=totalsell
        account[name]-=shares
        if account[name]<=0:
            del account[name]
    return (name,shares,currentday[name],totalsell)

########################
# PROBLEM 6
########################
# Input: a number p
# Output: return True if p is prime, False otherwise
def prime(p):
    if p<=1:
        return False
    for x in range (2,int(p**0.5) + 1):
        if p%x == 0:
            return False
    else:
        return True





if __name__ == "__main__":


    ## Problem 1
    ## p(0) = 10000
    ## p(1) = 10200.0
    ## p(2) = 10404.0
    ## p(3) = 10612.08
    ## p(4) = 10824.3216
    #for i in range(5):
    #    print(p(i))
 
    
    #for i in range(2,6):
    #    print(c(i))
    #print(c(1))
    ## c(2) = 82
    ## c(3) = 756
    ## c(4) = 7048
    ## c(5) = 66384    

    #for i in range(5):
    #    print(d(i))
    ## d(0) = 1
    ## d(1) = 4
    ## d(2) = 13
    ## d(3) = 40
    ## d(4) = 121
    
    #for i in range(6):
    #    print(f(i))
    ## f(0) = 1
    ## f(1) = 1
    ## f(2) = 2
    ## f(3) = 3
    ## f(4) = 5
    ## f(5) = 8

    #for i in range(1,6):
    #    print(e(i))
    # e(1) = 12
    # e(2) = -60
    # e(3) = 300
    # e(4) = -1500
    # e(5) = 7500

    #for i in range(5):
    #    for j in range(5):
    #        print(M(i,j), " ", end="")
    ## M((0, 0)) = 1
    ## M((0, 1)) = 1
    ## M((0, 2)) = 1
    ## M((0, 3)) = 1
    ## M((0, 4)) = 1
    ## M((1, 0)) = 6
    ## M((1, 1)) = 5
    ## M((1, 2)) = 4
    ## M((1, 3)) = 3
    ## M((1, 4)) = 2
    ## M((2, 0)) = 6
    ## M((2, 1)) = 5
    ## M((2, 2)) = 4
    ## M((2, 3)) = 3
    ## M((2, 4)) = 2
    ## M((3, 0)) = 6
    ## M((3, 1)) = 5
    ## M((3, 2)) = 4
    ## M((3, 3)) = 3
    ## M((3, 4)) = 2
    ## M((4, 0)) = 0
    ## M((4, 1)) = 0
    ## M((4, 2)) = 0
    ## M((4, 3)) = 0
    ## M((4, 4)) = 0    

    #for i in range(2,6):
    #    print(c_2(5,i))
    ## c_2(5,2) = 10
    ## c_2(5,3) = 10
    ## c_2(5,4) = 5
    ## c_2(5,5) = 1

    """
    #problem 2
    data_2r = [["abe432","e2"],["1010","1"],["A man, a plan a canal--Panama.", "aA "]]
    for d in data_2r:
        print(f1(*d))

    data_2r = [[1],[1,2],[1,2,1],[1,1,2],[2,1,1],[2,2,1,1]]
    for d in data_2r:
        print(f2(d))

    data_2r = [([1,2,1,3,2],1,2),([1,2,1,3,2],1,3)]
    for d in data_2r:
        print(f3(*d))
    """
    
    # problem 3
    #x = [[1,2,3,4,5],[1],[3,4],[5,10,22],[1,2,3,4,5,6]]
    #for i in x:
    #    print(m(i))
    
    #problem 4
    """
    data = ["abc xyz","the cat", "i love ctwohundred"]
    for i,j in enumerate(data,start=2):
        print(f"original msg {j}")
        print(f"encoded  msg {encode(j,i)}")
        print(f"decoded  msg {decode(encode(j,i),i)}")

    secret_msg = encode("the quick brown fox jumps over the lazy dog", 24)
    print(secret_msg)
    print(decode(secret_msg,24))
    """

    """
    #problem 5
        # #problem 1
    stock = {0: {'A':200, 'B':20, 'C':125, 'D':90},
             1: {'A':190, 'B':25, 'C':122, 'D':91},
             2: {'A':195,  'B':30,'C':122, 'D':80}}
   
    account = {'fund':1000}

    day = 0
    print(f"day = {day}")
    print(account)
    
    print(buy(day, 'A',account,3))
    print(account)
    print(buy(day,'B',account,4))
    print(account)
    print(buy(day, 'B',account,1))
    print(account)
    print(buy(day,'D',account,4))
    print(account)
    day = 1
    print(f"day = {day}")
    print(sell(day,'A',account,2))
    print(account)
    print(buy(day,'D',account,1))
    print(account)
    print(sell(day,'B',account,6))
    print(account)
    print(sell(day,'C',account,3))
    print(account)
    day = 2
    print(f"day = {day}")
    print(sell(day,'A',account,3))
    print(account)
    print(buy(day, 'B',account,2))
    print(account)
    """
    """
    #problem 6
    ps = []
    for p in range(2,100):
        if prime(p):
            ps.append(p)
    print(ps)
    print()
    """