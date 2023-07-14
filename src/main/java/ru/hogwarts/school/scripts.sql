ALTER TABLE students
    ADD CONSTRAINT age_more_15 CHECK ( age > 15 );
ALTER TABLE students
    ADD CONSTRAINT name_unique UNIQUE (name);
ALTER TABLE students
    ALTER COLUMN name SET NOT NULL;
ALTER TABLE faculties
    ADD CONSTRAINT name_color_unitque UNIQUE (name, color);
ALTER TABLE students
    ALTER COLUMN name SET DEFAULT 20;