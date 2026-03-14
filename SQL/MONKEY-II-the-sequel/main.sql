CREATE TABLE homes (
 id INT NOT NULL,
 address VARCHAR(150) NOT NULL,
 saleDate DATE, 
 price DOUBLE NOT NULL,
 PRIMARY KEY(id));
 
 INSERT INTO homes (id, address, saleDate, price)
 VALUES
 (1, '123 Main St', '2022-10-01',200000.00),
 (2, '34 Windsong Lane', '2021-11-14',310456.00),
 (3, '45 Stones Crossing Rd', '2021-12-09', 450500),
 (4, '89 Windbourne Lane', '2021-09-01', 255600),
 (5, '21 South Sample St', '2022-10-01', 1985000);

--1
--Select * from homes;

--2
-- delete from homes
-- where price < 400000;

-- Select * from homes;

--3
-- select address from homes
-- where saleDate = "2022-10-01";

 Create Table customers(
  id INT,
  name VARCHAR(250),
  contact VARCHAR(150),
  address VARCHAR(200),
  city VARCHAR(150),
  zip VARCHAR(20),
  country VARCHAR(100),
  PRIMARY KEY(id));
  
  Insert into customers(id, name, contact, address, city, zip, country)
  Values
  (1,'Great Lakes Food Market','Howard Snyder','2732 Baker Blvd.','Eugene','97403','USA'),
(2,'Hungry Coyote Import Store','Yoshi Latimer','City Center Plaza 516 Main St.','Elgin','97827','USA'),
(3,'Lazy K Kountry Store','John Steel','12 Orchestra Terrace','Walla Walla','46143','USA'),
(4,'Lets Stop N Shop','Jaime Yorres','87 Polk St. Suite 5','San Francisco','94117','USA'),
(5,'Lonesome Pine Restaurant','Fran Wilson','89 Chiaroscuro Rd.','Seattle','98128','USA'),
(6,'Old World Delicatessen','Rene Phillips','2743 Bering St.','Anchorage','99508','USA'),
(7,'Rattlesnake Canyon Grocery','Paula Wilson','2817 Milton Dr.','Albuquerque','87110','USA'),
(8,'Save-a-lot Markets','Jose Pavarotti','187 Suffolk Ln.','Boise','83720','USA'),
(9,'Split Rail','Art Braunschweiger','P.O. Box 555','Lander','82520','USA'),
(10,'The Big Cheese','Liz Nixon','89 Jefferson Way Suite 2','Portland','97201','USA'),
(11,'The Cracker Box','Liu Wong','55 Grizzly Peak Rd.','Boise','83720','USA'),
(12,'Trails Head Gourmet Provisioners','Helvetius Nagy','722 DaVinci Blvd.','Kirkland','98034','USA'),
(13,'White Clover Markets','Karl Jablonski','305 - 14th Ave. S. Suite 3B','Seattle','98128','USA');

--4
-- Select * from customers
-- where city ="Seattle";

--5
-- select contact from customers
-- where zip = 83720;

--6

-- update customers
-- set city ="Greenwood"
-- where zip= 46143;
-- Select * from customers;

--7
-- delete from customers
-- where contact ="liz Nixon";
-- Select * from customers;

CREATE TABLE Cars (
 Id INT NOT NULL,
 Model VARCHAR(100) NOT NULL,
 Status VARCHAR(50) NOT NULL,
 TotalCost INT NOT NULL,
 PRIMARY KEY(Id));

INSERT INTO Cars(Id, Model, Status, TotalCost)
VALUES
    ('1', 'Ford F-150', 'READY', 230),
    ('2','Ford F-150', 'REPAIR', 200),
    ('3','Ford Mustang', 'WAITING', 100),
    ('4','Toyota Prius', 'REPAIR', 1254),
    ('5','Nissan Titan', 'REPAIR', 10987),
    ('6','Honda Pilot', 'READY', 9875),
    ('7','Jeep Cherokee', 'READY', 11500),
    ('8','Kia Sportage', 'WAITING', 8765),
    ('9','Cadillac XLR', 'REPAIR', 13500),
    ('10','Rolls Royce Phantom', 'READY', 300000),
    ('11','Ford Pronto', 'REPAIR', 12800)
;

--8
-- Select * from Cars;

--9
-- Select * from Cars
-- where TotalCost > 11000;

--10

-- Select * from Cars
-- where Status ="REPAIR";

--11
-- Update Cars
-- set status ="READY"
-- where status="REPAIR";
-- Select * from Cars;

--12
delete from Cars
where model = "Honda Pilot";
select * from Cars;