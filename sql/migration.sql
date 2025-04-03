DROP TABLE IF EXISTS task_management_system.tag_list;
DROP TABLE IF EXISTS task_management_system.tags;
DROP TABLE IF EXISTS task_management_system.task_states;
DROP TABLE IF EXISTS task_management_system.task_priorities;
DROP TABLE IF EXISTS task_management_system.task_asignee;
DROP TABLE IF EXISTS task_management_system.tasks;
DROP TABLE IF EXISTS task_management_system.users;
DROP TABLE IF EXISTS task_management_system.workspaces;
DROP SCHEMA IF EXISTS task_management_system;

create schema if not exists task_management_system;
set schema 'task_management_system';

create table workspaces
(
    workspace_id serial primary key,
    name         varchar(50) unique not null
);

create table users
(
    user_id      serial primary key,
    name         varchar(25) not null check (length(name) >= 8) unique,
    password     varchar(25) not null check (length(password) >= 8),
    role         varchar(6)  not null check (role in ('admin', 'member')),
    workspace_id smallint    references workspaces (workspace_id) on delete set null
);

create table tasks
(
    task_id      serial primary key not null,
    name         varchar(50) not null,
    description  text,
    deadline     date,
    workspace_id smallint           not null,
    foreign key (workspace_id) references workspaces (workspace_id)
);

create table task_asignee
(
    user_id smallint not null,
    task_id smallint not null,
    primary key (user_id, task_id),
    foreign key (user_id) references users (user_id),
    foreign key (task_id) references tasks(task_id)
);

create table task_priorities
(
    task_id       smallint   not null,
    task_priority varchar(6) not null check (task_priority in ('LOW', 'MEDIUM', 'HIGH')),
    primary key (task_id, task_priority),
    foreign key (task_id) references tasks(task_id)
);

create table task_states
(
    task_id    smallint    not null,
    task_state varchar(12) not null check (task_state in ('NotStarted', 'InProgress', 'Completed')),
    primary key (task_id, task_state),
    foreign key (task_id) references tasks (task_id)
);
