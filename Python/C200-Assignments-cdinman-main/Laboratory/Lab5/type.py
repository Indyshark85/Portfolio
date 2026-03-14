def addFive (x):
	"""
	Adds 5 to each item in list x and returns a new list
	"""
	finalList = []
	for i in x:
		result = i + 5
		finalList.append(result)
	return finalList
print(addFive([3, 1, 5]))

def even (x):
	"""
	Counts how many even numbers in a list
	""" 
	count = 0
	for i in x:
		if float(i) % 2 == 0:
			count += 1
	return count
print(even(['1','2','3','4','5'])) 

def parseDoc (mostCommon, letter):
	"""
	Given some list containing the most common words in a document and a 		given letter, return a new list containing the all of the words that start 	with a the given letter. You can assume that the first letter of each word 	is lower case. 
	"""
	letterList = []
	for word in mostCommon:
		if letter == word[0]:  
			letterList.append(word)
	return letterList
print(parseDoc(["hello", "apple", "dog", "habit", "health"], "h"))

def stringPractice (word1, word2):
	"""
	Given two words of equal length, return whether the two words are the 	same.
	"""
	same = True
	if word1 != word2: 
		same = False
	return same
print(stringPractice('sentence', 'sequence')) 


def testScores (scores, target):
	"""
	Given some list containing test scores and a target value, return the 		scores that meet or exceed that value. 
	"""
	meetsTarget = []
	for i in scores:
		if i >= target:
			meetsTarget.append(i)
	return meetsTarget
print(testScores([95, 80, 20, 55, 60, 90, 92], 50))