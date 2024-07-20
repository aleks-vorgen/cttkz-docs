TRUNCATE TABLE tasks CASCADE;
ALTER SEQUENCE tasks_id_seq RESTART;
TRUNCATE TABLE job_types CASCADE;
ALTER SEQUENCE job_types_id_seq RESTART;
TRUNCATE TABLE statuses CASCADE;
ALTER SEQUENCE statuses_id_seq RESTART;

INSERT INTO job_types (title)
VALUES ('Ремонт'),
       ('Списання'),
       ('Технічне обслуговування'),
       ('Технічна експертиза'),
       ('Оціночна експертиза'),
       ('Модернізація');

INSERT INTO statuses (title)
VALUES ('Призначено'),
       ('Прийнято'),
       ('Отримано'),
       ('Передано на склад'),
       ('Передано МВО');

INSERT INTO tasks (inv_number, serial_number, title, fullname_mvo, department, application_number_original, executor,
                   comment, job_type_id, create_user)
VALUES ('10485676', '123410987', 'title', 'FNMVO', 'department', '№123 от 18.07.24', 'executor', 'comment', 1,
        'create_user');

INSERT INTO tasks (inv_number, serial_number, title, fullname_mvo, department, application_number_original, executor,
                   comment, job_type_id, create_user)
VALUES ('10481342', '123240987', 'title2', 'FNMVO2', 'department2', '№321 от 18.07.24', 'executor2', 'comment2', 2, 'create_user')
