
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";CREATE TABLE IF NOT EXISTS `accounts` (
  `accountID` int(8) unsigned NOT NULL auto_increment,
  `accountType` varchar(30) NOT NULL,
  `firstName` varchar(50) default NULL,
  `lastName` varchar(50) default NULL,
  `password` char(32) default NULL,
  `gender` tinyint(1) default NULL,
  `phone` varchar(20) default NULL,
  `email` varchar(200) NOT NULL,
  `address` varchar(200) default NULL,
  `authLevel` int(8) NOT NULL default '1',
  `creationTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`accountID`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=300 ;

CREATE TABLE IF NOT EXISTS `test_bills` (
  `billID` int(11) NOT NULL auto_increment,
  `bookingID` int(11) NOT NULL,
  `paymentType` varchar(50) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY  (`billID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=237 ;

CREATE TABLE IF NOT EXISTS `test_bookings` (
  `bookingID` int(11) NOT NULL auto_increment,
  `bookingOwnerID` int(11) NOT NULL,
  `creationTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `roomID` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY  (`bookingID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=396 ;

CREATE TABLE IF NOT EXISTS `test_extras` (
  `extraID` int(11) NOT NULL auto_increment,
  `bookingID` int(11) NOT NULL,
  `extraName` tinytext NOT NULL,
  `price` float NOT NULL,
  `date` date NOT NULL,
  `creationTime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`extraID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=51 ;

CREATE TABLE IF NOT EXISTS `test_rooms` (
  `roomID` int(11) NOT NULL auto_increment,
  `roomNumber` int(11) default NULL,
  `floor` int(11) default NULL,
  `roomType` varchar(30) default NULL,
  `price` float default NULL,
  `onsuite` tinyint(1) default NULL,
  `tv` tinyint(1) default NULL,
  `disabilityAccess` tinyint(1) default NULL,
  `elevator` tinyint(1) default NULL,
  `available` tinyint(1) default NULL,
  `singleBeds` int(11) default NULL,
  `queenBeds` int(11) default NULL,
  `kingBeds` int(11) default NULL,
  `phone` int(11) default NULL,
  `internet` tinyint(1) default NULL,
  `kitchen` tinyint(1) default NULL,
  `cleaned` tinyint(1) NOT NULL,
  PRIMARY KEY  (`roomID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;
