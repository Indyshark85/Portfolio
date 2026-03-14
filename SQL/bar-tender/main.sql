-- Create Table Monkey(
--   AGE int,
--   GRADE int,
--   NAME varchar(50),
--   GENDER varchar(6)
-- );

-- INSERT INTO Monkey Values (18, 12, "Conner Inman", "Monkey");
-- INSERT INTO Monkey Values (17, 12, "Riley", "Monkey");
-- INSERT INTO Monkey Values (17, 12, "Abby Seslar", "Female");
-- INSERT INTO Monkey Values (17, 12, "Seth Methling", "AC-130");

-- Select * from Monkey;

-- Select NAME from Monkey;

-- Select * from Monkey
-- Where GRADE = 12;

-- Update Monkey 
-- set AGE = 9999999
-- where GRADE = 12;
-- Select * from Monkey;

-- Delete from Monkey
-- Where GENDER = "Female";
-- Select * from Monkey;

CREATE TABLE planets (
   planetName varchar(40), 
   numMoons int,
   firstYearRecorded varchar(10),
   distanceFromSun varchar(40), 
   discoveredBy varchar(30)
);

INSERT INTO planets VALUES ("Mercury", 0, "250BC", "58.835 million km", "Galileo Galilei");

INSERT INTO planets VALUES ("Venus", 0, "1610", "107.57 million km", "Galileo Galilei");

INSERT INTO planets VALUES ("Mars", 2, "1610", "212.98 million km", "Galileo Galilei");

INSERT INTO planets VALUES ("Jupiter", 79, "1610", "766.93 million km", "Galileo Galilei");

INSERT INTO planets VALUES ("Saturn", 62, "1610", "1.4931 billion km", "Galileo Galilei");

INSERT INTO planets VALUES ("Uranus", 27, "1781", "2.9592 billion km", "William Herschel");

INSERT INTO planets VALUES ("Neptune", 14, "1846", "4.4762 billion km", "Urbain Le Verrier");

-- Select * from planets;

-- select planetName from planets;

-- select planetName from planets
-- where numMoons > 50;

delete from planets
where numMoons = 0;
select * from planets;
 

