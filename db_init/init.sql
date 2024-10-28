DROP TABLE IF EXISTS tasks CASCADE;
DROP SEQUENCE IF EXISTS tasks_id_seq;
--DROP TRIGGER IF EXISTS tasks_before_insert ON tasks;
--DROP TRIGGER IF EXISTS tasks_before_update ON tasks;
--DROP FUNCTION IF EXISTS tasks_before_insert();
--DROP FUNCTION IF EXISTS tasks_before_update();

DROP TABLE IF EXISTS task_history CASCADE;
DROP SEQUENCE IF EXISTS task_history_id_seq;

DROP TABLE IF EXISTS list_tasks;
DROP SEQUENCE IF EXISTS list_tasks_id_seq;

DROP TABLE IF EXISTS job_types;
DROP SEQUENCE IF EXISTS job_types_id_seq;

DROP TABLE IF EXISTS statuses;
DROP SEQUENCE IF EXISTS statuses_id_seq;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_id_seq;

DROP TABLE IF EXISTS departments;
DROP SEQUENCE IF EXISTS departments_id_seq;

CREATE TABLE job_types
(
    id    bigserial PRIMARY KEY,
    title varchar(100) UNIQUE NOT NULL
);

CREATE TABLE departments
(
    id    bigserial PRIMARY KEY,
    code  text UNIQUE NOT NULL,
    title text UNIQUE NOT NULL
);

CREATE TABLE users
(
    id            bigserial PRIMARY KEY,
    username      text UNIQUE NOT NULL,
    fullname      text        NOT NULL,
    department_id bigint      NOT NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE statuses
(
    id    bigserial PRIMARY KEY,
    title varchar(100) UNIQUE NOT NULL
);

CREATE TABLE tasks
(
    id                          bigserial PRIMARY KEY,
    inv_number                  text        NOT NULL, -- Інвентарний номер обладнання
    serial_number               text        NOT NULL, -- Серійний номер обладнання
    title                       text        NOT NULL, -- Назва задачі
    fullName_MVO                text        NOT NULL, -- МВО
    department_id               bigint      NOT NULL, -- Підрозділ
    application_number_original text UNIQUE NOT NULL, -- Номер службової записки (Особистий кабінет/паперовий документ)
    reg_number                  text UNIQUE,          -- Реєстраційний номер (для пошуку)
    executor_id                 bigint      NOT NULL, -- Кому призначено
    comment                     text,                 -- Коментар
    job_type_id                 bigint      NOT NULL, -- Тип роботи
    status_id                   bigint,               -- Статус задачі
    created_at                  timestamp(0),         -- Коли створено
    created_user_id             bigint      NOT NULL, -- Ким створено
    updated_user_id             bigint,               -- Ким редаговано
    updated_at                  timestamp(0),         -- Коли редаговано
    update_reason               text,                 -- Причина редагування
    FOREIGN KEY (department_id) REFERENCES departments (id),
    FOREIGN KEY (executor_id) REFERENCES users (id),
    FOREIGN KEY (job_type_id) REFERENCES job_types (id),
    FOREIGN KEY (status_id) REFERENCES statuses (id),
    FOREIGN KEY (created_user_id) REFERENCES users (id),
    FOREIGN KEY (updated_user_id) REFERENCES users (id)
);

CREATE TABLE list_tasks --Отношение задач к пользователям
(
    id      bigserial PRIMARY KEY,
    task_id bigint NOT NULL,
    user_id text   NOT NULL,
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE task_history
(
    id            bigserial PRIMARY KEY,
    task_id       bigint       NOT NULL, -- Ссылка на исходную задачу
    update_user   text         NOT NULL, -- Пользователь, который редактировал
    updated_at    timestamp(0) NOT NULL, -- Время редактирования
    update_reason text,                  -- Причина редактирования
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE OR REPLACE FUNCTION tasks_before_insert() RETURNS trigger AS
$tasks_before_insert$
BEGIN
    NEW.reg_number := to_char(CURRENT_TIMESTAMP, 'YYYYMMDDHH24MISS');
    NEW.status_id := 1;
    NEW.created_at := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$tasks_before_insert$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION tasks_before_update() RETURNS trigger AS
$tasks_before_update$
BEGIN
    NEW.created_at := OLD.created_at;
    --INSERT INTO task_history (task_id, update_user, updated_at, update_reason)
    --VALUES (OLD.id, OLD.update_user, OLD.updated_at, OLD.update_reason);

    RETURN NEW;
END;
$tasks_before_update$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER tasks_before_insert
    BEFORE INSERT
    ON tasks
    FOR EACH ROW
EXECUTE PROCEDURE tasks_before_insert();

CREATE OR REPLACE TRIGGER tasks_before_update
    BEFORE UPDATE
    ON tasks
    FOR EACH ROW
EXECUTE PROCEDURE tasks_before_update();
