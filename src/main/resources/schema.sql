DROP TABLE IF EXISTS TBL_SIDES;
  
CREATE TABLE TBL_SIDES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  sideId INT NOT NULL,
  sideEnum INT NOT NULL,
  data VARCHAR(250) NOT NULL
);