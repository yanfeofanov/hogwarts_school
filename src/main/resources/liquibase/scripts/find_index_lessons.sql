-- liquibase formatted sql
--changeset yfeofanov:1
CREATE INDEX user_name_index ON students (name);
--changeset yfeofanov:2
CREATE INDEX faculty_name_color_index ON faculties (name,color)
