CREATE TABLE IF NOT EXISTS intern.groups (
    id SERIAL PRIMARY KEY,
    number INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS intern.students (
    id SERIAL PRIMARY KEY,
    family VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	age INTEGER NOT NULL,
    group_id INTEGER NOT NULL REFERENCES intern.groups (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS intern.subjects (
    id SERIAL PRIMARY KEY,
	subject VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS intern.groups_subjects (
	group_id INTEGER NOT NULL,
    subject_id INTEGER NOT NULL,
    PRIMARY KEY (group_id, subject_id),
    FOREIGN KEY (group_id) REFERENCES intern.groups (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES intern.subjects (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS intern.performance (
    id SERIAL PRIMARY KEY,
	student_id INTEGER NOT NULL,
    subject_id INTEGER NOT NULL,
    grade INTEGER NOT NULL,
    FOREIGN KEY (student_id) REFERENCES intern.students (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES intern.subjects (id) ON DELETE CASCADE

);


