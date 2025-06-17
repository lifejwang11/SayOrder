CREATE USER 'test'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON sentence_data.* TO 'test'@'localhost';
FLUSH PRIVILEGES;