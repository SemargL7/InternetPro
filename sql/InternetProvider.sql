drop database if exists internetprovider;
create database internetprovider CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use internetprovider;

create table Services(
                         id int primary key not null auto_increment,
                         `name` nchar(225) not null);

create table Tariff(
                       id int primary key not null auto_increment,
                       idService int not null,
                       cost double not null default 0,
                       daysOfTariff int not null default 30,
                       foreign key (idService) references Services (id),
                       check(cost >= 0),
                       check(daysOfTariff >= 0));

create table `language`(
                           id int primary key not null auto_increment,
                           `language` nchar(45)
);

create table `description`(
                              idLanguage int not null,
                              idTariff int not null,
                              description_text text not null,
                              foreign key (idLanguage) references Language (id),
                              foreign key (idTariff) references Tariff (id)
);

create table Users(
                      id int primary key not null auto_increment,
                      `name` nchar(45) not null,
                      surname nchar(45) not null,
                      dateBirth date not null,
                      email nchar(225)not null unique,
                      `password` nchar(225) not null,
                      balance double not null default 0,
                      blocked boolean not null default true,
                      access enum('MANAGER','USER') not null default 'USER'
);

create table TariffConnected(
                                idUser int not null,
                                idTariff int not null,
                                dateOfLastConnection date not null,
                                foreign key (idUser) references Users (id),
                                foreign key (idTariff) references Tariff (id),
                                UNIQUE KEY my_uniq_id (idUser,idTariff)
);

insert into Services (name) value ('Internet');
insert into Services (name) value ('Telephone');
insert into Services (name) value ('Cable TV');
insert into Services (name) value ('IP-TV');

insert into `language` (`language`) value ('eng');
insert into `language` (`language`) value ('ua');

insert into Users (name,surname,dateBirth,email,password,access) value('Roman','Dubil','2003-07-13','romandubil03@gmail.com','0','MANAGER');