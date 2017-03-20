# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table image (
  id                            integer auto_increment not null,
  username                      varchar(255),
  full_image                    varchar(255),
  thumb_image                   varchar(255),
  comments                      varchar(255),
  constraint pk_image primary key (id)
);

create table user (
  username                      varchar(255) not null,
  name                          varchar(255),
  password                      varchar(255),
  email                         varchar(255),
  constraint pk_user primary key (username)
);


# --- !Downs

drop table if exists image;

drop table if exists user;

