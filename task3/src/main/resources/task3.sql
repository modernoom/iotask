-- ----------------------------
-- operation Table
-- ----------------------------
DROP TABLE IF EXISTS operation;
CREATE TABLE operation(
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          dir VARCHAR(100) DEFAULT NULL,
                          line VARCHAR(100) DEFAULT NULL,
                          STATUS INT DEFAULT NULL,
                          TIME DATETIME DEFAULT NULL,
                          content TEXT
)DEFAULT CHARSET=utf8;