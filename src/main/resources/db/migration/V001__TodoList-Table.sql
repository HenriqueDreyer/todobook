CREATE TABLE IF NOT EXISTS todobook.todo_list (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(60) NOT NULL,    
    PRIMARY KEY (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;