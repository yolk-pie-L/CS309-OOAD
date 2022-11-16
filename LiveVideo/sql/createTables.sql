drop table user, course, assignment, assign_urls, administrator, section;
drop table announcement, danmu, comment, course_invitation;
drop table user_login_log, user_payment_log;
drop table stu_assign, stu_course, stu_section;


CREATE TABLE user
(
    id          int auto_increment primary key,
    username    varchar(20)                                not null,
    usertype    enum ('Administrator','Student','Teacher') not null,
    mail        varchar(50),
    password    varchar(50)                                not null,
    photo_url   varchar(50),
    account     bigint                                              default 0,
    create_time timestamp                                  not null default CURRENT_TIMESTAMP,
    update_time timestamp                                  null     default null on update CURRENT_TIMESTAMP,
    is_delete   int                                                 default 0,
    CONSTRAINT unique (username, is_delete),
    CONSTRAINT unique (mail, is_delete)
);

CREATE TABLE course
(
    id          int auto_increment primary key,
    course_name varchar(20)                              not null,
    teacher_id  int                                      not null,
    tag         varchar(20),
    charge      bigint                                   not null,
    description text,
    status      enum ('APPROVED', 'FAILED', 'REVIEWING') not null,
    picture_url varchar(50),
    create_time timestamp                                not null default CURRENT_TIMESTAMP,
    update_time timestamp                                null     default null on update CURRENT_TIMESTAMP,
    is_delete   int                                               default 0,
    CONSTRAINT unique (course_name, teacher_id, is_delete),
    FOREIGN KEY (teacher_id) REFERENCES user (id)
);

CREATE TABLE assignment
(
    id              int auto_increment primary key,
    assignment_name varchar(20) not null,
    course_id       int         not null,
    deadline        timestamp,
    total_grade     int,
    is_assignment   boolean     not null,
    description     text,
    create_time     timestamp   not null default CURRENT_TIMESTAMP,
    update_time     timestamp   null     default null on update CURRENT_TIMESTAMP,
    CONSTRAINT unique (assignment_name, course_id),
    FOREIGN KEY (course_id) REFERENCES course (id)
);

CREATE TABLE assign_urls
(
    assign_id  int         not null,
    assign_url varchar(50) not null,
    FOREIGN KEY (assign_id) REFERENCES assignment(id)
);

CREATE TABLE administrator
(
    user_id   int not null,
    canAdd    boolean default false,
    canRemove boolean default false,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE section
(
    id           int auto_increment primary key,
    section_name varchar(20) not null,
    course_id    int         not null,
    video_url    varchar(50),
    create_time  timestamp   not null default CURRENT_TIMESTAMP,
    update_time  timestamp   null     default null on update CURRENT_TIMESTAMP,
    is_delete    int                  default 0,
    CONSTRAINT unique (section_name, course_id, is_delete),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE announcement
(
    id        int auto_increment primary key,
    course_id int      not null,
    date      datetime not null,
    context   text     not null,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE danmu
(
    id         int auto_increment primary key,
    context    text not null,
    section_id int  not null,
    color      char default 'w',
    send_time  int  not null,
    FOREIGN KEY (section_id) REFERENCES section(id)
);

CREATE TABLE comment
(
    id         int auto_increment primary key,
    parent_id  int      not null references comment (id),
    reply_id   int      not null references comment (id),
    context    text     not null,
    user_id    int      not null,
    section_id int ,
    date       datetime not null,
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY (section_id) REFERENCES section(id)
);

CREATE TABLE course_invitation
(
    course_id       int      not null,
    invitation_code char(20) not null unique,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE user_login_log
(
    user_id      int         not null,
    login_time   datetime    not null,
    session_id   varchar(25) not null,
    login_status char default 'y',
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE user_payment_log
(
    user_id   int      not null,
    pay_time  datetime not null,
    course_id int      not null,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE stu_assign
(
    user_id int not null,
    ass_id  int not null,
    grade   int,
    ass_url varchar(50),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (ass_id) REFERENCES assignment(id)
);

CREATE TABLE stu_course
(
    user_id   int not null,
    course_id int not null,
    star      int check (star between 0 and 5),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE stu_section
(
    user_id    int not null,
    section_id int not null,
    rate       int not null default 0,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (section_id) REFERENCES section(id)
);