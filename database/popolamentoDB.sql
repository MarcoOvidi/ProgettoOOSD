select * from user;insert into user (username,password,status,name,surname,registration_date,email ) 
value ("ciccio","bho",true,"marco","ovidi","2018-3-10","marco.o@univaq.it");

select * from user;insert into user (username,password,status,name,surname,registration_date,email ) 
value ("chacha","true",true,"luca","rossi","2018-1-01","rossi.l@univaq.it");

select * from user;

insert into document(title) values("La Divina Commedia");
insert into document(title) values("Odissea");

select * from document;

insert into scanning_project(ID_coordinator,ID_document,date)
value (1,1,"2018-10-01");


insert into scanning_project(ID_coordinator,ID_document,date)
value (1,2,"2018-10-01");

select * from scanning_project;

insert into scanning_project_partecipant(ID_scanning_project, ID_digitalizer_user) 
value(1,2);

insert into scanning_project_partecipant(ID_scanning_project, ID_digitalizer_user) 
value(2,2);

select * from scanning_project_partecipant;

insert into transcription_project(ID_coordinator,ID_document,date)
value (1,1,"2018-10-01");


insert into transcription_project(ID_coordinator,ID_document,date)
value (1,2,"2018-10-01");

select * from transcription_project;

insert into transcription_project_partecipant(ID_transcription_project, ID_transcriber_user) 
value(1,2);

insert into transcription_project_partecipant(ID_transcription_project, ID_transcriber_user) 
value(2,2);


select * from transcription_project_partecipant;



