insert into test (id,name,theme)
values ('1','How well do you know java','programming');
insert into questions (id,text,test_id)
values ('1','что такое java?','1');
insert into possible_answer (id,text_answer,questions_id)
values ('1','язык программирования','1');
insert into user (id,name,role)
values ('1','Nikita','user');
select * from questions;
select * from possible_answer;
select * from test;
insert into answer_test (id,result,user_id,test_id)
values ('1','0','1','1');
insert into answer_question (id,possible_answer_question_id,answer_test_id)
values ('1','1','1');
select * from answer_test where user_id = '1';
select * from  answer_question, answer_test where user_id = '1' and answer_test_id = '1';