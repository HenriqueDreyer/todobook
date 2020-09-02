CREATE TABLE IF NOT EXISTS todobook.Item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(60) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    todolist_id BIGINT NOT NULL,
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

alter table todobook.Item add constraint fk_item_todolist_id
foreign key (todolist_id) references todo_list (id);