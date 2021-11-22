CREATE DATABASE IF NOT EXISTS JDBC_Assignment;
USE JDBC_Assignment;

DROP TABLE IF EXISTS tracks;

CREATE TABLE tracks (
id varchar(30),
artistName varchar(255),
trackName varchar(255),
albumName varchar(255),
milsecPlayed varchar(255),
danceability float,
energy float,
loudness float,
speechiness float,
acousticness float,
instrumentalness float,
liveness float,
valence float,
tempo float,
milsecDuration varchar(255));

INSERT INTO `tracks` (`id`, `artistName`, `trackName`, `albumName`, `milsecPlayed`, 
`danceability`, `energy`, `loudness`, `speechiness`, `acousticness`, `instrumentalness`,
 `liveness`, `valence`, `tempo`, `milsecDuration`) VALUES (0,'Slipknot','The Devil in I',
 '.5: The Gray Chapter (Special Edition)','208011',0.398,0.939,-2.865,0.0648,0.00591,
 0.000881,0.357,0.235,92.027,'342821'),(1,'Slipknot','Psychosocial',
 'All Hope Is Gone (10th Anniversary)','184543',0.568,0.981,-3.889,0.0887,0.00269,0.00275,
 0.0243,0.307,135.146,'284120'),(2,'Taylor Swift','Blank Space','1989 (Deluxe)','231826',
 0.76,0.703,-5.412,0.054,0.103,0,0.0913,0.57,95.997,'231827'),(3,'Hozier','To Be Alone',
 'Hozier','323639',0.558,0.433,-6.485,0.0259,0.363,0.000296,0.196,0.262,66.951,'323640'),
 (4,'Arctic Monkeys','Suck It and See','Suck It and See','108592',0.339,0.932,-5.017,0.057,
 0.00105,0.114,0.255,0.586,128.291,'225987'),(5,'Halsey','Colors','BADLANDS','249499',
 0.512,0.812,-4.809,0.0617,0.0534,0,0.092,0.521,100.02,'249500'),(6,'Fall Out Boy',
 'Centuries','American Beauty/American Psycho','170839',0.393,0.858,-2.868,0.0729,0.00359,
 0,0.102,0.56,176.042,'228360'),(7,'Halsey','Colors','BADLANDS','1580',0.512,0.812,-4.809,
 0.0617,0.0534,0,0.092,0.521,100.02,'249500'),(8,'Fall Out Boy','Centuries',
 'American Beauty/American Psycho','87958',0.393,0.858,-2.868,0.0729,0.00359,0,0.102,0.56,
 176.042,'228360'),(9,'Milky Chance','Stolen Dance','Sadnecessary','237149',0.885,0.581,
 -8.813,0.0378,0.427,0.000204,0.0759,0.728,114.016,'313684'),(10,'Florence + The Machine',
 'No Light, No Light','Ceremonials (Original Deluxe Version)','274853',0.426,0.749,-5.611,
 0.0523,0.00793,0.0000153,0.44,0.17,131.965,'274853'),(11,'The Strumbellas','Spirits',
 'Hope','40510',0.556,0.658,-6.075,0.0265,0.164,0,0.113,0.787,80.529,'203653'),
 (12,'Arctic Monkeys','Why\'d You Only Call Me When You\'re High?','AM','5003',0.691,0.631,
 -6.478,0.0368,0.0483,0.0000113,0.104,0.8,92.004,'161124'),(13,'Royal Blood',
 'Little Monster','Royal Blood','212309',0.454,0.882,-4.251,0.06,0.00122,0.00156,0.138,
 0.497,95.999,'212310'),(14,'The Vaccines','Norgaard',
 'What Did You Expect from The Vaccines?','98400',0.322,0.847,-4.716,0.0349,0.00000492,
 0.0246,0.25,0.86,173.917,'98400'),(15,'The Kooks','Bad Habit','Listen','41197',0.733,
 0.882,-4.199,0.0389,0.051,0.00000992,0.131,0.854,123.071,'221413'),(16,'X Ambassadors',
 'Renegades','VHS','195200',0.526,0.862,-6.003,0.0905,0.0144,0.0597,0.229,0.528,90.052,
 '195200'),(17,'Hozier','Angel Of Small Death & The Codeine Scene','Hozier','219214',
 0.377,0.638,-5.754,0.0545,0.213,0.0000797,0.12,0.369,92.644,'219214'),(18,'M83','Outro',
 'Hurry up, We\'re Dreaming','70400',0.162,0.256,-11.296,0.0346,0.0332,0.0923,0.0621,
 0.0784,108.893,'247027'),(19,'Florence + The Machine','Ship To Wreck',
 'How Big, How Blue, How Beautiful (Deluxe)','234526',0.564,0.923,-3.843,0.0325,0.00147,
 0,0.277,0.587,141.991,'234526'),(20,'Amber Run','I Found','5AM (Expanded Edition)',
 '273000',0.567,0.303,-12.481,0.0351,0.862,0.00563,0.104,0.252,124.949,'273000');

#Stored function
DROP FUNCTION IF EXISTS get_avg_listening_time;
DELIMITER //
CREATE FUNCTION get_avg_listening_time
(
	artist_param VARCHAR(100)
)
RETURNS DECIMAL(9,2)
DETERMINISTIC
BEGIN
	DECLARE avg_time DECIMAL(9,2); 
		SELECT avg(milsecPlayed)
		INTO avg_time
		FROM tracks
		WHERE artistName = artist_param;
	RETURN avg_time;
END//
delimiter ;

#Stored function
DROP FUNCTION IF EXISTS capitalise;
DELIMITER //
CREATE FUNCTION capitalise
(
	input varchar(100)
)
RETURNS varchar(100)
DETERMINISTIC
BEGIN
DECLARE firstLetterOfInput VARCHAR(1);
DECLARE restOfInput VARCHAR(100);
SET firstLetterOfInput = SUBSTRING(input, 1, 1);
SET restOfInput = SUBSTRING(input, 2);
RETURN CONCAT(UPPER(firstLetterOfInput), restOfInput);
END//
delimiter ;


DROP TRIGGER IF EXISTS capitalise_fields;
DELIMITER //
CREATE TRIGGER capitalise_fields
	BEFORE UPDATE ON tracks
    FOR EACH ROW
BEGIN
	SET NEW.artistName = capitalise(NEW.artistName);
	SET NEW.trackName = capitalise(NEW.trackName);
	SET NEW.albumName = capitalise(NEW.albumName);

END//
delimiter ;

select * from tracks;