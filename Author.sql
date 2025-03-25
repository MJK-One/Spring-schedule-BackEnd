CREATE TABLE Author (
                        AuthorID INT AUTO_INCREMENT PRIMARY KEY,
                        Email VARCHAR(100) NOT NULL UNIQUE,
                        createPostTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                        updatePostTime DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);