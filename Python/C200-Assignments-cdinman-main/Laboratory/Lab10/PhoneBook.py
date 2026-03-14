from Contact import Contact

class PhoneBook:

    def __init__(self):
        """
        The phonebook keeps track of all contacts. This class is an example of 
        interacting with other classes a coder will make. 

        Instance variables provided for this constructor.
        """
        self.contactList=[]
        self.count=0
    
    def addContact(self, c):
        """
        Given a contact, determine if you are given a dictionary or an instance of 
        a Contact class. Handle the adding to our phone book appropriately and update the counter.

        If you are given a dictionary, assume that a dictionary has the following keys 
        (and the values are in the correct format):
        - name
        - number
        - email
        - birthday

        NOTE: Why do we have to manually update the counter?
        """
        if isinstance(c,Contact):
            self.contactList.append(c)
        else:
            name=c['name']
            mobile=c['number']
            email=c['email']
            birthday=['birthday']
            contact1=Contact(name,mobile,email,birthday)
            self.contactList.append(contact1)
        self.count+=1
    
    def getContactCount(self):
        """
        Returns the number of contacts stored in the 
        """
        return self.count
    
    def findContact(self, lName):
        """
        Given a last name, find the contact(s) and return the contact information. 

        Will be a list. 
        """
        finalLst=[]
        for x in self.contactList:
            if x.last==lName:
                finalLst.append(x)
        return finalLst
    



    def groupChat(self, message):
        """
        Send a message to every contact in the phonebook
        """
        for x in self.contactList:
            x.sendtext(message)
    def __str__(self):
        """
        Returns a string representation of the phonebook class. 

        The output will be
        > Phone Book: #

        Where # is the number of contacts in the phonebook. 
        """
        return "Phone Book: {}".format(self.count)

