insert into user (username,password,status,name,surname,registration_date,email ) 
value ("ciccio","bho",true,"marco","ovidi","2018-3-10","marco.o@univaq.it");

insert into user (username,password,status,name,surname,registration_date,email ) 
value ("chacha","true",true,"luca","rossi","2018-1-01","rossi.l@univaq.it");

insert into user (username,password,status,name,surname,registration_date,email ) 
value ("cocco","hash",true,"mario","bianchi","2018-1-18","bianchi@univaq.it");

select * from user;

insert into document(title) values("La Divina Commedia");
insert into document(title) values("Odissea");

select * from document;

insert into scanning_project(ID_coordinator,ID_document,date)
value (1,1,"2018-10-01");


insert into scanning_project(ID_coordinator,ID_document,date)
value (1,2,"2018-10-01");

select * from scanning_project;

insert into scanning_project_digitalizer_partecipant(ID_scanning_project, ID_digitalizer_user) 
value(1,2);

insert into scanning_project_digitalizer_partecipant(ID_scanning_project, ID_digitalizer_user) 
value(2,2);

select * from scanning_project_digitalizer_partecipant;

insert into transcription_project(ID_coordinator,ID_document,date)
value (1,1,"2018-10-01");


insert into transcription_project(ID_coordinator,ID_document,date)
value (1,2,"2018-06-28");

select * from transcription_project;

insert into transcription_project_transcriber_partecipant(ID_transcription_project, ID_transcriber_user) 
value(1,2);

insert into transcription_project_transcriber_partecipant(ID_transcription_project, ID_transcriber_user) 
value(2,2);

insert into transcription_project_transcriber_partecipant(ID_transcription_project, ID_transcriber_user) 
value(2,3);

insert into personal_collection (ID_user,ID_document) value (1,1);

insert into document_metadata (author,description,ID_document) value("gianfranco","che brutto",1);
insert into document_metadata (author,description,ID_document) value("lanfranco","che bello",2);
insert into page(number,image,ID_digitalizer,ID_document) value (1,"./image/1",1,1);



select * from transcription_project_transcriber_partecipant;



