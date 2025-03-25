CREATE TABLE Schedule (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          Task VARCHAR(255) NOT NULL,
                          Name VARCHAR(100) NOT NULL,
                          AuthorID INT NOT NULL,
                          Password VARCHAR(255) NOT NULL,
                          createPostTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updatePostTime DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (AuthorID) REFERENCES Author(AuthorID) ON DELETE CASCADE
);