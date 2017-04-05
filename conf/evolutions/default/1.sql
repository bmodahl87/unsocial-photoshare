# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table follower (
  id                            integer auto_increment not null,
  username                      varchar(255),
  follower_username             varchar(255),
  user_username                 varchar(255) not null,
  constraint pk_follower primary key (id)
);

create table following (
  id                            integer auto_increment not null,
  username                      varchar(255),
  following_username            varchar(255),
  user_username                 varchar(255) not null,
  constraint pk_following primary key (id)
);

create table image (
  id                            integer auto_increment not null,
  username                      varchar(255),
  full_image                    varchar(255),
  thumb_image                   varchar(255),
  comments                      varchar(255),
  user_username                 varchar(255) not null,
  follower_id                   integer not null,
  following_id                  integer not null,
  constraint pk_image primary key (id)
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

alter table follower add constraint fk_follower_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_follower_user_username on follower (user_username);

alter table following add constraint fk_following_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_following_user_username on following (user_username);

alter table image add constraint fk_image_user_username foreign key (user_username) references user (username) on delete restrict on update restrict;
create index ix_image_user_username on image (user_username);

alter table image add constraint fk_image_follower_id foreign key (follower_id) references follower (id) on delete restrict on update restrict;
create index ix_image_follower_id on image (follower_id);

alter table image add constraint fk_image_following_id foreign key (following_id) references following (id) on delete restrict on update restrict;
create index ix_image_following_id on image (following_id);


# --- !Downs

alter table follower drop foreign key fk_follower_user_username;
drop index ix_follower_user_username on follower;

alter table following drop foreign key fk_following_user_username;
drop index ix_following_user_username on following;

alter table image drop foreign key fk_image_user_username;
drop index ix_image_user_username on image;

alter table image drop foreign key fk_image_follower_id;
drop index ix_image_follower_id on image;

alter table image drop foreign key fk_image_following_id;
drop index ix_image_following_id on image;

drop table if exists follower;

drop table if exists following;

drop table if exists image;

drop table if exists user;

