CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    registerDate DATE DEFAULT CURDATE(),
    admin BOOLEAN NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT,
    title VARCHAR(128) NOT NULL,
    author VARCHAR(128) NOT NULL,
    year INT NOT NULL,
    pageCount INT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS borrows (
    id INT AUTO_INCREMENT,
    userId INT NOT NULL,
    bookId INT NOT NULL,

    borrowDate DATE NOT NULL,
    dueDate DATE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (bookId) REFERENCES books(id)
);