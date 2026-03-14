import types  # This is used to determine if you have function type


def task1(size, default):
    """
    Return a list of a SIZE x SIZE list of lists, that has all default values,
    except for the diagonal being all zeroes.

    Example:
    task2(3, -1)

    Produces (formatted to be readable)

    [
     [0, -1, -1],
     [-1, 0, -1],
     [-1, -1, 0]
    ]

    This must be done in form of a list comprehension.
    """
    return [[0 if x==y else default for x in range(size)] for y in range(size)]



def task2(words):
    """
    Given a list of words, return a dictionary of the words and the count of vowels in that word.

    Example:
    task2(["apple", "banana", "cherry", "date"]) -> {"apple": 5, "banana": 6, "cherry": 6, "date": 4}

    This must be done in form of a dictionary comprehension.
    """
    vowels="aeiou"
    return{word:sum(1 for letter in word if letter in vowels)for word in words}


def listPrinter(lst):
    """
    Do not modify, as this will print lists to be readable
    """
    print("[")
    for l in range(len(lst)):
        print(" " + str(lst[l]) + "," * (l != (len(lst) - 1)))
    print("]\n")


if __name__ == "__main__":
    print("Task 1")
    if isinstance(task1(3, -1), list):
        listPrinter(task1(3, -1))
        listPrinter(task1(5, 100))
    else:
        print("Task 2 is not return a list")
        print("Is return: ", type(task1(3, -1)))

    print()
    print("Task 2")
    if isinstance(task2(["apple", "banana", "cherry", "date"]), dict):
        print(task2(["apple", "banana", "cherry", "date"]))
    else:
        print("Task 2 does not return a dictionary")
        print("Is return: ", type(task2(["apple", "banana", "cherry", "date"])))

