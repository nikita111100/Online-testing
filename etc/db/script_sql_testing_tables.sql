insert into test (id,name,theme)
values ('1','How well do you know java','Java has been around forever. It seems like there’s nothing about this old favorite that we don’t know about. But do you really know everything about Java? Test your knowledge about Java and find out!');
insert into test (id,name,theme)
values ('2','Test IQ','This is an IQ test and it determines how well you do in life.');
insert into questions (id,text,test_id)
values ('1','что такое java?','1');
insert into possible_answer (id,text,correct_answer,questions_id)
values ('1','язык программирования','1','1');
insert into user_role (user_id,roles)
values ('1','ADMIN');
insert into user (id,active,name,password)
values ('1',1,'Nikita','123');
select * from questions;
select * from possible_answer;
select * from test;
insert into answer_test (id,result,user_id,test_id)
values ('1','0','1','1');
insert into answer_question (id,possible_answer_id,answer_test_id)
values ('1','1','1');
select * from answer_test where user_id = '1';
select * from  answer_question, answer_test where user_id = '1' and answer_test_id = '1';
select  * from answer_question inner join possible_answer
on  possible_answer.id  =  answer_question.possible_answer_id and correct_answer = '1' ;
insert into possible_answer (id,text,correct_answer,questions_id)
values ('2','язык программирования','0','1');
insert into answer_test (id,result,user_id,test_id)
values ('2','50','1','1');
insert into answer_question (id,possible_answer_id,answer_test_id)
values ('2','2','2');