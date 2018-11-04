create table if not exists addresses
(
  id            bigint        not null constraint addresses_pkey primary key,
  address       varchar(100),
  city          varchar(30),
  postal_code   varchar (25)
);

create table if not exists users
(
  id           bigint      not null constraint users_pkey primary key,
  first_name   varchar(30),
  last_name    varchar(30),
  address_id   bigint,
  phone_number varchar (25),
  FOREIGN KEY (address_id) REFERENCES addresses (id)
);

INSERT INTO "addresses" (id,address,city,postal_code) VALUES (1,'Ap #787-8313 Lorem Av.','Tirupati','6403');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (2,'P.O. Box 677, 8665 Ante Road','Patan','093464');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (3,'Ap #297-4407 Quisque St.','Fresia','77676');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (4,'446-1577 Et, Av.','Montresta','1649');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (5,'336-2965 Class Street','Swindon','B2N 2E9');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (6,'5833 Orci Avenue','Belgrade','5639');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (7,'5375 Non St.','San Diego','A21 8LQ');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (8,'Ap #625-9099 Sed Street','Edmundston','10909');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (9,'P.O. Box 201, 7310 Neque Av.','Gaasbeek','8522');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (10,'244-7489 Morbi Avenue','Pichilemu','839549');
INSERT INTO "addresses" (id,address,city,postal_code) VALUES (11,'22-231 Winston Avenue','New Yourk','12351');

INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (1,'Beatrice','Clay',10,'1622060902999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (2,'Eric','Maynard',9,'1629022861299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (3,'Angelica','Wilkinson',3,'1690082105199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (4,'Ina','Kirby',8,'1649062317499');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (5,'Josiah','Casey',9,'1613071294299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (6,'Lucian','Poole',8,'1616061666099');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (7,'Carissa','Dudley',6,'1636041360899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (8,'Olga','Farmer',6,'1604122925899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (9,'Colton','Hamilton',8,'1678100422799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (10,'Uta','Rutledge',3,'1606012386899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (11,'Amelia','Gibson',2,'1699041990599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (12,'Veda','Shaffer',2,'1683091468899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (13,'Ria','Henson',1,'1609081188299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (14,'Uriel','Wynn',10,'1673021237799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (15,'Christine','Holcomb',10,'1660060878299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (16,'Danielle','Williamson',8,'1600122213699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (17,'Aileen','Rosario',4,'1647011841299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (18,'Benedict','Burch',3,'1685031792499');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (19,'Gabriel','Fuller',9,'1699102507399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (20,'Orla','Mendoza',5,'1639050449599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (21,'Eve','Gardner',6,'1635081929299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (22,'Chandler','Nieves',3,'1655071581099');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (23,'Moses','Bowers',2,'1645011267699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (24,'Amber','Richard',2,'1624012489599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (25,'Amy','Holland',1,'1687112899099');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (26,'Maryam','Hubbard',7,'1651012156299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (27,'Raya','Norman',5,'1618052419999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (28,'Ebony','Mcgee',7,'1664020495899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (29,'Lenore','Spears',3,'1607112431699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (30,'Lyle','Daugherty',2,'1681041150199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (31,'McKenzie','Fox',2,'1650100150199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (32,'Jenna','Wade',8,'1629052268199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (33,'Rebekah','Navarro',1,'1617010113599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (34,'Ivan','Padilla',1,'1691030496599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (35,'Cullen','Garrison',4,'1693041025699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (36,'Hayfa','Summers',5,'1620110847999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (37,'Wynter','Ochoa',6,'1643101554399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (38,'Brandon','Daniels',2,'1663120710999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (39,'Brent','Tyson',3,'1696111387799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (40,'Jasper','Sanford',1,'1629051731799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (41,'Quamar','Jacobson',10,'1641050884699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (42,'Karen','Boyer',4,'1627071071899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (43,'Leonard','Cline',1,'1657021330499');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (44,'Mark','Weiss',2,'1681032655699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (45,'Ivan','Wheeler',7,'1676071519899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (46,'Ivory','Perez',9,'1637041156199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (47,'Yoshio','Pope',4,'1618051484499');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (48,'Vincent','Kidd',10,'1671072429999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (49,'Jessamine','Price',6,'1690102921999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (50,'Jesse','Riley',2,'1692031582299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (51,'Thane','Brown',1,'1644043077499');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (52,'Timon','Anthony',2,'1680112191399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (53,'Brenna','Ramos',5,'1654101545999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (54,'Brett','Bates',1,'1624022013399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (55,'Kuame','Rollins',2,'1696091171199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (56,'Lillith','Conner',7,'1621020380699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (57,'Rahim','Beach',4,'1631062680399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (58,'Harlan','Evans',7,'1695071977199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (59,'Briar','Hurley',6,'1625012006699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (60,'Alisa','Pena',1,'1695030183999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (61,'Uma','Hawkins',1,'1640101858199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (62,'Carter','Carpenter',8,'1651120100799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (63,'Vladimir','Cooper',1,'1661022510199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (64,'Carly','Meyers',4,'1687050553899');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (65,'Kuame','Cunningham',10,'1647011718199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (66,'Ethan','Duke',2,'1682031797399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (67,'Savannah','Hebert',10,'1630062378199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (68,'Bernard','Hudson',7,'1612072026499');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (69,'Dane','Barry',3,'1648100130599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (70,'Macaulay','Vazquez',7,'1606081171999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (71,'Channing','Middleton',1,'1615052566999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (72,'Garrett','Thomas',9,'1693020404199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (73,'Kiayada','Cross',8,'1697120135099');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (74,'Yvette','Mcleod',4,'1698081414199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (75,'Giacomo','Anderson',7,'1652021821099');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (76,'Jaime','Hamilton',3,'1679072345099');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (77,'Beau','Grimes',9,'1699102724699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (78,'Jasper','Delgado',3,'1617101953599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (79,'Cassidy','Talley',3,'1666110440699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (80,'Miranda','Paul',2,'1608100700299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (81,'Veronica','Gordon',3,'1666120263599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (82,'Adrienne','Bender',4,'1627110689199');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (83,'Wanda','Casey',4,'1629060627599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (84,'Allistair','Guthrie',1,'1633071367999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (85,'Randall','Osborne',7,'1677120835599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (86,'Bert','Patton',6,'1655112464799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (87,'Shelley','Vaughan',10,'1622061118699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (88,'Whilemina','Mcmahon',1,'1649092099399');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (89,'Jared','Whitney',9,'1684122468599');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (90,'Maya','Adams',7,'1665012910299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (91,'Brett','Terrell',7,'1604011934799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (92,'Troy','Walter',2,'1622011730699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (93,'Benjamin','Burns',9,'1624090401999');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (94,'Aphrodite','Eaton',4,'1671110113699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (95,'Calista','Reid',5,'1644091314299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (96,'Dara','Gould',9,'1613111091699');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (97,'Wylie','Finley',4,'1692120163799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (98,'Kamal','Newton',2,'1634050358799');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (99,'Lee','Hicks',3,'1684101404299');
INSERT INTO "users" (id,first_name,last_name,address_id,phone_number) VALUES (100,'Lance','Stone',8,'1607082892799');