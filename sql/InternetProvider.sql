drop database if exists internetprovider;
create database internetprovider CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use internetprovider;

create table Service(
                        id int primary key not null auto_increment,
                        `name` nchar(225) not null);

create table Tariff(
                       id int primary key not null auto_increment,
                       cost double not null default 0,
                       daysOfTariff int not null default 30,
                       check(cost >= 0),
                       check(daysOfTariff >= 0));

create table Services(
                         idService int not null,
                         idTariff int not null,
                         foreign key (idService) references Service (id) ON DELETE CASCADE,
                         foreign key (idTariff) references Tariff (id) ON DELETE CASCADE,
                         UNIQUE KEY my_uniq_id (idService,idTariff)
);

create table `language`(
                           id int primary key not null auto_increment,
                           `language` nchar(45)
);

create table `description`(
                              idLanguage int not null,
                              idTariff int not null,
                              description_text text not null,
                              foreign key (idLanguage) references Language (id) ON DELETE CASCADE,
                              foreign key (idTariff) references Tariff (id) ON DELETE CASCADE
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
                                date_start_connection date not null,
                                date_end_connection date,
                                connection_status boolean not null,
                                foreign key (idUser) references Users (id) ON DELETE CASCADE,
                                foreign key (idTariff) references Tariff (id) ON DELETE CASCADE,
                                UNIQUE KEY my_uniq_id (idUser,idTariff)
);


insert into Users value(1,'Roman','Dubil','2003-07-13','romandubil03@gmail.com','C4CA4238A0B923820DCC509A6F75849B',1,false,'MANAGER');


insert into Service (name) value ('Internet');
insert into Service (name) value ('Telephone');
insert into Service (name) value ('Cable TV');
insert into Service (name) value ('IP-TV');

insert into `language` (`language`) value ('eng');
insert into `language` (`language`) value ('ua');

