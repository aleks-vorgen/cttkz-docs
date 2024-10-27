TRUNCATE TABLE tasks CASCADE;
ALTER SEQUENCE tasks_id_seq RESTART;
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

INSERT INTO tasks (inv_number, serial_number, title, fullname_mvo, department, application_number_original, executor,
                   comment, job_type_id, create_user)
VALUES ('10485676', '123410987', 'title', 'FNMVO', 'department', '№123 от 18.07.24', 'executor', 'comment', 1,
        'create_user');
