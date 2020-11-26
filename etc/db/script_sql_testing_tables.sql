insert into test (id,test_name,test_theme)
values ('1','How well do you know java','programming');
insert into questions (id,text,test_id)
values ('1','что такое java?','1');
insert into possible_answer_question (id,text_answer,questions_id)
values ('1','язык программирования','1');
insert into user (id,name,role)
values ('1','Nikita','user');
select * from questions;
select * from possible_answer_question;
select * from test;
insert into answer_test (id,id_user,result,test_id)
values ('1','1','0','1');
insert into answer_question (id,id_user,answer_to_the_question,correct_answer,questions_id,possible_answer_question_id,answer_test_id)
values ('1','1','1','1','1','1','1');
select * from answer_test where id_user = '1';
select * from  answer_question where id_user = '1' and answer_test_id = '1';