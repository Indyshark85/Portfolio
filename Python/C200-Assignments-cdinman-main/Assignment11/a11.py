import webbrowser
#mpl_toolkits.basemap import Basemap
#import matplotlib.pyplot as plt


#problem 1
#do not change this code
cnt = 0
def weigh(x,y):
    global cnt
    cnt+=1
    return sum(x)==sum(y)

#input list of numbers
#returns list of 3 list equal size + empty, [x], [x,y]
def split_weight(lst):
    n = len(lst)
    third = n // 3
    x=lst[:third]
    y=lst[third:2*third]
    z=lst[2*third:3*third]
    remaining = lst[3*third:]
    return x,y,z,remaining

#input recursive funciton takes a list of coins with one fake
#returns fake
#only use weigh for len(list) > 3
def find(lst):
    if len(lst) == 1:
        return lst[0]
    elif len(lst) == 2:
        return lst[0] if lst[0]!=lst[1] else lst[1]
    elif len(lst)==3:
        if weigh([lst[0]],[lst[1]]):
            return lst[2]
        elif weigh([lst[0]],[lst[2]]):
            return lst[1]
        else:
            return lst[0]
    else:
        x, y, z, remaining = split_weight(lst)
        if weigh(x,y):
            if remaining:
                return find(z+remaining)
            else:
                return find(z)
        elif weigh(x,z):
            if remaining:
                return find(y+remaining)
            else:
                return find(y)
        else:
            if remaining:
                return find(x+remaining)
            else:
                return find(x)


#Problem 2

#html class
class my_HTML:
    def __init__(self):
        self.top = "<html><head></head><body>"
        self.body = "" #the contents of the page
        self.bottom = "</body></html>"
    
    #use switch to look for h2
    def add_element(self,content,**tag):
        if 'element' in tag and tag['element']=='h2':
            self.body+=f"<h2 class='text-center'>{content}</h2>"
    
    #overload + 
    #must return self to cascade
    def __add__(self, image):
        self.body += f"<img src='{image}' class='center'><br>"
        return self

    #makes HTML page
    def make_page(self):
        return self.top+self.body+self.bottom
    
    #printable
    def __str__(self):
        return self.make_page()

    #save as name + .html
    def save(self,name):
        with open(f"{name}.html","w") as file:
            file.write(self.make_page())

#input english name (string)
#output egyptian name (string)
def translate(name):
    hieroglyphslet = {'a':'a', 'b': 'b', 'c': 'c', 'd': 'd',
                   'g': 'g','h': 'h','i': 'y','j': 'j','k': 'c',
                   'l': 'l','m': 'm','n': 'n','o': 'w','p': 'p',
                   'q': 'q','r': 'r','s': 's','t': 't','u': 'w',
                   'v': 'w','w': 'w','y': 'y'}
    name = name.lower()
    translated_name = ""
    for char in name:
        if char in hieroglyphslet:
            translated_name += hieroglyphslet[char]
        else:
            pass
    return translated_name

#input letter
#returns unique file name of jpg image for that letter
def get_image_filename(letter):
    hieroglyphs = {'a': 'eglypha.jpg', 'b': 'eglyphb.jpg', 'c': 'eglyphc.jpg', 'd': 'eglyphd.jpg',
                   'g': 'eglyphg.jpg','h': 'eglyphh.jpg','i': 'eglyphy.jpg',
                   'j': 'eglyphj.jpg','k': 'eglyphc.jpg','l': 'eglyphl.jpg','m': 'eglyphm.jpg','n': 'eglyphn.jpg',
                   'o': 'eglyphw.jpg','p': 'eglyphp.jpg','q': 'eglyphq.jpg','r': 'eglyphr.jpg','s': 'eglyphs.jpg',
                   't': 'eglypht.jpg','u': 'eglyphw.jpg','v': 'eglyphw.jpg','w': 'eglyphw.jpg',
                   'y': 'eglyphy.jpg'}
    if letter not in hieroglyphs:
        return "Letter does not translate"
    return hieroglyphs[letter]

#do not change
#checks your class implementation
def add_hieroglyphs(page,name):
    egypt_name = translate(name)
    for h in egypt_name:
        page = page + get_image_filename(h)


#3 nothing to see here, people
# You just have to get the code to run for this problem.
# make sure you install the basemap module by following the website mentioned in the PDF.

if __name__ == "__main__":

    #problem 1
    """
    print(split_weight([1,1,1,1,1,2,1,1]))
    coins = [1,1,1,1,1,1,1,1,1,1,1,1,1,2,1]
    fake = find(coins)
    print(fake,coins.index(fake),cnt)
    coins = [2,1,1,]
    fake = find(coins)
    print(fake,coins.index(fake),cnt)
    """

    #problem 2
    """   
    #read your name
    #file nmust be name.txt
    with open("Assignment11/name.txt") as file_object1:
        name = file_object1.readline()

    page = my_HTML()
    print(page)
    page.add_element(f"{name} = {translate(name)}", element = 'h2' )
    print(page)
    page = page + "eglyphTOP.jpg"
    add_hieroglyphs(page,name)
    page = page + "eglyphBOTTOM.jpg"
    print(page)
    page.save(translate(name))
    """

    # problem 3
    #just get it to run
    # setup Lambert Conformal basemap.
    # set resolution=None to skip processing of boundary datasets.
    """
    m = Basemap(width=12000000,height=9000000,projection='lcc',
        resolution=None,lat_1=45.,lat_2=55,lat_0=50,lon_0=-107.)
    m.bluemarble()
    plt.show()
    """
    print()