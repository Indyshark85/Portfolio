def binary_search(arr,x):
    low=0
    high=len(arr)-1
    mid=0
    while low<=high:
        mid=(high+low)//2
        if arr[mid]<x:
            low=mid+1
        elif arr[mid]>x:
            high=mid-1
        else:
            return mid   
    return -1

array=[3,4,5,6,7,8,9]
x=4

result=binary_search(array,x)

if result!=-1:
    print("Element is present at index ",str(result))
else:
    print("not found")

def binarySearch(array,x,low,high):
    if high>=low:
        mid=low+(high-low)//2

        if array[mid]==x:
            return mid
        elif array[mid]>x:
            return binarySearch(array,x,low,mid-1)
        else:
            return binarySearch(array,x,mid+1,high)
    else:
        return -1
    
array=[3,4,5,6,7,8,9]
x=4

result1=binarySearch(array,x,0,len(array)-1)

if result1!=-1:
    print("Element is present at index ",str(result1))
else:
    print("not found")

