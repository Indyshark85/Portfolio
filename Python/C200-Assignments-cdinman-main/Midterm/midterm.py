import random as rn

#problem 1
def buy(name,account, shares):
    total=account["fund"]
    buySum= stock[name]*shares
    total-=buySum
    if total>=0:
        if name in account:
            account["fund"]-=buySum
            account[name]=account[name][0]+shares,stock[name]
        else:
            account["fund"]-=buySum
            account[name]=shares,stock[name]
        return True 
    else:
        return False

            

#problem 2
def cnt_harmonic(lst):
    defdic={"X":0,"Y":0,"Z":0}
    for x in lst:
        Fordic={}
        for y in x:
            if y in Fordic:
                Fordic[y]+=1
            else:
                Fordic[y]=1
        largest=max(Fordic.values())
        if Fordic.get("X", 0)==1 and Fordic.get("Y",0)==1 and Fordic.get("Z",0)==1:
            continue
        else:
            for z in Fordic:
                if Fordic[z]==largest:
                    defdic[z]+=1
    return defdic




if __name__ == "__main__":

    #problem 1
    stock = {'A':200, 'B':20, 'C':125, 'D':90}
    account = {'fund':1000}

    print(account)
    print(buy('A',account,3))
    print(account)
    print(buy('B',account,4))
    print(account)
    print(buy('B',account,1))
    print(account)
    print(buy('D',account,4))
    print(account)

    #problem 2
    data = ['X','Y','Z']
    d_ = []
    for _ in range(rn.randint(4,10)):
        d_ += [[rn.choice(data),rn.choice(data),rn.choice(data)]]
    print(d_)

    print(cnt_harmonic(d_))

