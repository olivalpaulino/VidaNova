create database if not exists vidanova;
use vidanova;
create table if not exists paciente(
    id int auto_increment primary key,
    nome varchar(255) not null,
    cpf varchar(14) unique not null
);

create table if not exists endereco(
	id int auto_increment primary key,
    paciente_id int not null,
    numero int,
    bairro varchar(255),
    rua varchar(255),
    foreign key (paciente_id) references paciente(id)
);

create table if not exists telefone(
	id int auto_increment primary key,
    paciente_id int not null,
    numero varchar(15),
    foreign key(paciente_id) references paciente(id)
);

create table if not exists medico(
	id int auto_increment primary key,
    nome varchar(255) not null,
    crm varchar(20) unique not null
);

create table if not exists atendimento(
	id int auto_increment primary key,
    paciente_id int not null,
    medico_id int not null,
    data_atendimento varchar(10) not null,
    foreign key (paciente_id) references paciente(id),
    foreign key (medico_id) references medico(id)
);
