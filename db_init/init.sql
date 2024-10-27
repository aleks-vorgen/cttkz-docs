DROP TABLE IF EXISTS tasks CASCADE;
DROP SEQUENCE IF EXISTS tasks_id_seq;
--DROP TRIGGER IF EXISTS tasks_before_insert ON tasks;
--DROP TRIGGER IF EXISTS tasks_before_update ON tasks;
--DROP FUNCTION IF EXISTS tasks_before_insert();
--DROP FUNCTION IF EXISTS tasks_before_update();

DROP TABLE IF EXISTS task_history CASCADE;
DROP SEQUENCE IF EXISTS task_history_id_seq;

DROP TABLE IF EXISTS job_types;
DROP SEQUENCE IF EXISTS job_types_id_seq;

DROP TABLE IF EXISTS statuses;
DROP SEQUENCE IF EXISTS statuses_id_seq;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_id_seq;

CREATE TABLE job_types
(
    id    bigserial PRIMARY KEY,
    title varchar(100) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id      text PRIMARY KEY,
    name    text        NOT NULL,
    userpic text        NOT NULL,
    email   text        NOT NULL,
    gender  varchar(10) NOT NULL,
    locale  text        NOT NULL
);

CREATE TABLE statuses
(
    id    bigserial PRIMARY KEY,
    title varchar(100) UNIQUE NOT NULL
);

CREATE TABLE tasks
(
    id                          bigserial PRIMARY KEY,
    inv_number                  text        NOT NULL, --Инвентарник
    serial_number               text        NOT NULL, --Серийник
    title                       text        NOT NULL, --Наименование товара
    fullName_MVO                text        NOT NULL, --Изменится на FK к users
    department                  text        NOT NULL, --Возможно изменится на FK к departments
    application_number_original text UNIQUE NOT NULL, --Номер служебки оригинальный
    reg_number                  text UNIQUE,          --Рег номер для поиска
    executor                    text        NOT NULL, --Изменится на FK к users
    comment                     text,                 --Комментарий
    job_type_id                 bigint      NOT NULL, --FK на jobTypes (тип работы)
    status_id                   bigint,               --FK на statuses (статус задачи)
    created_at                  timestamp(0),         --Когда создано
    create_user                 text        NOT NULL, --Кем создано
    update_user                 text,                 -- Пользователь, который редактировал
    updated_at                  timestamp(0),         -- Время редактирования
    update_reason               text,                 -- Причина редактирования
    FOREIGN KEY (job_type_id) REFERENCES job_types (id),
    FOREIGN KEY (status_id) REFERENCES statuses (id)
    --FOREIGN KEY (create_user) REFERENCES users (id),
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
