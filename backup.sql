insert into medico(nome, crm) values("Carlos","1010");
insert into medico(nome, crm) values("Miguel","1011");
insert into medico(nome, crm) values("Pedro","1012");
insert into medico(nome, crm) values("Josué","1013");
insert into medico(nome, crm) values("Marilía","1014");
insert into medico(nome, crm) values("Gabriela","1015");
insert into medico(nome, crm) values("Patrícia","1016");
insert into medico(nome, crm) values("Jorge","1017");
insert into medico(nome, crm) values("Fernando","1018");
insert into medico(nome, crm) values("Sofia","1019");

insert into paciente(nome,cpf) values("Olival","12345678911");
set @paciente_id = last_insert_id();
insert into endereco(paciente_id, rua, bairro, numero) values(@paciente_id, "Pedro Mota", "Centro", 123);
insert into telefone(paciente_id, numero) values(@paciente_id, "82999992210");

insert into paciente(nome,cpf) values("Pedro","12345678912");
set @paciente_id = last_insert_id();
insert into endereco(paciente_id, rua, bairro, numero) values(@paciente_id, "Radialista Antonio", "Juca Sampaio", 124);
insert into telefone(paciente_id, numero) values(@paciente_id, "82999992211");

insert into paciente(nome,cpf) values("Jessica","12345678913");
set @paciente_id = last_insert_id();
insert into endereco(paciente_id, rua, bairro, numero) values(@paciente_id, "Euclides da Silva", "Vila Maria", 125);
insert into telefone(paciente_id, numero) values(@paciente_id, "82999992212");

insert into paciente(nome,cpf) values("DEBUG","12345678914");
set @paciente_id = last_insert_id();
insert into endereco(paciente_id, rua, bairro, numero) values(@paciente_id, "DEBUB RUA", "DEBUGADOS", 126);
insert into telefone(paciente_id, numero) values(@paciente_id, "82999992213");

set @medico_id = (select id from medico where crm = "1010");
set @paciente_id = (select id from paciente where cpf="12345678911");
insert into atendimento(paciente_id, medico_id, data_atendimento) values(@paciente_id, @medico_id, "10/10/2025");

set @medico_id = (select id from medico where crm = "1010");
set @paciente_id = (select id from paciente where cpf="12345678912");
insert into atendimento(paciente_id, medico_id, data_atendimento) values(@paciente_id, @medico_id, "10/10/2025");

set @medico_id = (select id from medico where crm = "1011");
set @paciente_id = (select id from paciente where cpf="12345678913");
insert into atendimento(paciente_id, medico_id, data_atendimento) values(@paciente_id, @medico_id, "10/10/2025");

set @medico_id = (select id from medico where crm = "1011");
set @paciente_id = (select id from paciente where cpf="12345678914");
insert into atendimento(paciente_id, medico_id, data_atendimento) values(@paciente_id, @medico_id, "10/10/2025");