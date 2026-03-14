###########################################################
# factorial
###########################################################

def factorial(n):
    """
    Recursive function to calculate the factorial of n

    Input:
        n (an integer)
    Returns:
        n! = n*(n-1)*...*2*1
    """
    if n==1:
        return 1
    else:
        return (n*factorial(n-1))
    

def tail_factorial(n, a=1):
    """
    Tail-recursive function to calculate the factorial of n

    Input:
        n (an integer)
    Returns:
        n! = n*(n-1)*...*2*1
    """
    if n==1:
        return a
    else:
        return (tail_factorial(n-1,a=a*n))

d = {}
def memo_factorial(n):
    """
    Memoized function to calculate the factorial of n

    Input:
        n (an integer)
    Returns:
        n! = n*(n-1)*...*2*1
    """
    if n==1:
       d[1]=1
    else:
        d[n]=n*memo_factorial(n-1)
    return d[n]

###########################################################
# only_ints
###########################################################

def only_ints(xlist):
    """
    Recursive function to return a list with all non-ints taken
    out of it.

    Input:
        xlist - a list of elements
    Returns:
        xlist, but with only the 'int'-type elements kept.

    """
    if not xlist:
        return []
    elif type (xlist[0]) != int:
        return [] + only_ints(xlist[1:])
    else:
        return [xlist[0]]+only_ints(xlist[1:])
        




def tail_only_ints(xlist, a=[]):
    """
    Recursive function to return a list with all non-ints taken
    out of it.

    Input:
        xlist - a list of elements
    Returns:
        xlist, but with only the 'int'-type elements kept.

    """
    if not xlist:
        return a
    elif type (xlist[0]) != int:
        return tail_only_ints(xlist[1:],a)
    else:
        return tail_only_ints(xlist[1:],a+[xlist[0]])


d = {}
def memo_only_ints(xlist):
    """
    Recursive function to return a list with all non-ints taken
    out of it.

    Input:
        xlist - a list of elements
    Returns:
        xlist, but with only the 'int'-type elements kept.

    """
    xtup = tuple(xlist)

    if xtup not in d.keys():
        if xlist==[]:
            d[xtup]=[]
        elif type (xlist[0]) != int:
            d[xtup]=memo_only_ints(xlist[1:])
        else:
            d[xtup]=[xlist[0]]+memo_only_ints(xlist[1:])
    
    return d[xtup]
    



if __name__ == "__main__":
    # Write your own print statements here
    # to briefly test your code
    print(factorial(5))
    print(tail_factorial(5))

    my_list0 = [1, 'apple', 3.14, 5, 'banana', 7, 8]
    filtered_list = tail_only_ints(my_list0)
    print(filtered_list)

    my_list1 = [1, 'apple', 3.14, 5, 'banana', 7, 8]
    filtered_list = memo_only_ints(my_list1)
    print(filtered_list)