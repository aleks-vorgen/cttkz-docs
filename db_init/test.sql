TRUNCATE TABLE tasks CASCADE;
ALTER SEQUENCE tasks_id_seq RESTART;
TRUNCATE TABLE departments CASCADE;
ALTER SEQUENCE departments_id_seq RESTART;
TRUNCATE TABLE users CASCADE;
ALTER SEQUENCE users_id_seq RESTART;
TRUNCATE TABLE job_types CASCADE;
ALTER SEQUENCE job_types_id_seq RESTART;
TRUNCATE TABLE statuses CASCADE;
ALTER SEQUENCE statuses_id_seq RESTART;
TRUNCATE TABLE task_history CASCADE;
ALTER SEQUENCE task_history_id_seq RESTART;

INSERT INTO job_types (title)
VALUES ('Ремонт'),
       ('Списання'),
       ('Технічне обслуговування'),
       ('Технічна експертиза'),
       ('Оціночна експертиза'),
       ('Модернізація');

INSERT INTO statuses (title)
VALUES ('Призначено'),
       ('Прийнято до виконання'),
       ('Передано на склад'),
       ('Передано МВО'),
       ('Отримано складом'),
       ('Отримано МВО');

INSERT INTO departments(code, title)
VALUES ('test.code', 'test title'),
       ('test2 code', 'test2 title');

INSERT INTO users(username, fullname, department_id)
VALUES ('o.kulykov', 'Куликов Олексій Олександрович', 1);

INSERT INTO tasks (inv_number, serial_number, title, fullname_mvo, department_id, application_number_original, executor_id,
                   comment, job_type_id, created_user_id)
VALUES ('10485676', '123410987', 'title', 'FNMVO', 1, '№123 от 18.07.24', 1, 'comment', 1,
        1);
