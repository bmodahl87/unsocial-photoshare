# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table album (
  id                            integer auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  user_username                 varchar(255),
  constraint pk_album primary key (id)
);

create table following (
  following_id                  integer auto_increment not null,
  username                      varchar(255),
  following_username            varchar(255),
  user_username                 varchar(255),
  constraint pk_following primary key (following_id)
);

create table image (
  id                            integer auto_increment not null,
  username                      varchar(255),
  full_image                    varchar(255),
  thumb_image                   varchar(255),
  comments                      varchar(255),
  date                          date,
  user_username                 varchar(255),
  album_id                      integer,
  following_following_id        integer,
  constraint pk_image primary key (id)
);

create table notification (
  id                            integer auto_increment not null,
  has_been_viewed               tinyint(1) default 0,
  message                       varchar(255),
  user_username                 varchar(255),
  constraint pk_notification primary key (id)
);

create table user (
  username                      varchar(255) not null,
  name                          varchar(255),
  password                      varchar(255),
  email                         varchar(255),
  profile_image                 varchar(255),
  wall_image                    varchar(255),
  constraint pk_user primary key (username)
);

alter table album add constraint fk_album_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_album_user_username on album (user_username);

alter table following add constraint fk_following_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_following_user_username on following (user_username);

alter table image add constraint fk_image_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_image_user_username on image (user_username);

alter table image add constraint fk_image_album_id foreign key (album_id) references album (id) on delete restrict on update restrict;
create index ix_image_album_id on image (album_id);

alter table image add constraint fk_image_following_following_id foreign key (following_following_id) references following (following_id) on delete restrict on update restrict;
create index ix_image_following_following_id on image (following_following_id);

alter table notification add constraint fk_notification_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_notification_user_username on notification (user_username);


# --- !Downs

alter table album drop foreign key fk_album_user_username;
drop index ix_album_user_username on album;

alter table following drop foreign key fk_following_user_username;
drop index ix_following_user_username on following;

alter table image drop foreign key fk_image_user_username;
drop index ix_image_user_username on image;

alter table image drop foreign key fk_image_album_id;
drop index ix_image_album_id on image;

alter table image drop foreign key fk_image_following_following_id;
drop index ix_image_following_following_id on image;

alter table notification drop foreign key fk_notification_user_username;
drop index ix_notification_user_username on notification;

drop table if exists album;

drop table if exists following;

drop table if exists image;

drop table if exists notification;

drop table if exists user;

