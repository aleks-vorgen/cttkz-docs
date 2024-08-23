DROP TABLE IF EXISTS tasks CASCADE;
DROP SEQUENCE IF EXISTS tasks_id_seq;
DROP TRIGGER IF EXISTS tasks_before_insert ON tasks;
DROP TRIGGER IF EXISTS tasks_before_update ON tasks;
DROP FUNCTION IF EXISTS tasks_before_insert();
DROP FUNCTION IF EXISTS tasks_before_update();

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
    application_number_original text UNIQUE NOT NULL, --Номер служебки оригинальный (мб нужно отдельную таб.)
    reg_number                  text UNIQUE,          --Рег номер для поиска
    executor                    text        NOT NULL, --Изменится на FK к users
    comment                     text,                 --Комменртарий
    job_type_id                 bigint      NOT NULL, --FK на jobTypes (тип работы)
    status_id                   bigint,               --FK на statuses (статус задачи)
    created_at                  timestamp(0),         --Создано
    create_user                 text        NOT NULL, --Кем создано
    updated_at                  timestamp(0),         --Отредактировано
    update_reason               text,                 --Причина редактирования
    update_user                 text,                 --Кем отредактировано
    deleted_at                  timestamp(0),         --Удалено
    delete_reason               text,                 --Причина удаления
    delete_user                 text,                 --Кем удалено
    FOREIGN KEY (job_type_id) REFERENCES job_types (id),
    FOREIGN KEY (status_id) REFERENCES statuses (id)
    --FOREIGN KEY (create_user) REFERENCES users (id),
    --FOREIGN KEY (update_user) REFERENCES users (id),
    --FOREIGN KEY (delete_user) REFERENCES users(id),
);

CREATE FUNCTION tasks_before_insert() RETURNS trigger AS
$tasks_before_insert$
BEGIN
    NEW.reg_number := to_char(CURRENT_TIMESTAMP, 'DDMMYYYYHH24MISS');
    NEW.status_id := 1;
    NEW.created_at := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$tasks_before_insert$ LANGUAGE plpgsql;

CREATE FUNCTION tasks_before_update() RETURNS trigger AS
$tasks_before_update$
BEGIN
    IF NEW.delete_user IS NOT NULL THEN
        NEW.deleted_at := CURRENT_TIMESTAMP;
    END IF;
    NEW.updated_at := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$tasks_before_update$ LANGUAGE plpgsql;

CREATE TRIGGER tasks_before_insert
    BEFORE INSERT
    ON tasks
    FOR EACH ROW
EXECUTE PROCEDURE tasks_before_insert();

CREATE TRIGGER tasks_before_update
    BEFORE UPDATE
    ON tasks
    FOR EACH ROW
EXECUTE PROCEDURE tasks_before_update();