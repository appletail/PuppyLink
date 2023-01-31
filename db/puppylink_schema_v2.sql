-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema puppylink
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `puppylink` ;

-- -----------------------------------------------------
-- Schema puppylink
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `puppylink` DEFAULT CHARACTER SET utf8mb3 ;
USE `puppylink` ;

-- -----------------------------------------------------
-- Table `puppylink`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`members` (
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(40) NOT NULL,
  `name` VARCHAR(40) NULL DEFAULT NULL,
  `phone` VARCHAR(50) NULL DEFAULT NULL,
  `nickName` VARCHAR(100) NULL DEFAULT NULL,
  `joinDate` DATE NULL DEFAULT NULL,
  `activated` TINYINT(10) NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`board` (
  `boardNo` INT NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `contents` VARCHAR(2000) NULL DEFAULT NULL,
  `pictureURL` VARCHAR(100) NULL DEFAULT NULL,
  `likes` INT NULL DEFAULT NULL,
  `regDate` DATE NULL DEFAULT NULL,
  `members_email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`boardNo`),
  INDEX `fk_board_ members1_idx` (`members_email` ASC) VISIBLE,
  CONSTRAINT `fk_board_ members1`
    FOREIGN KEY (`members_email`)
    REFERENCES `puppylink`.`members` (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`comments` (
  `commentNo` INT NOT NULL AUTO_INCREMENT,
  `letter` VARCHAR(1000) NULL DEFAULT NULL,
  `regDate` DATE NULL DEFAULT NULL,
  `board_boardNo` INT NOT NULL,
  `members_email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`commentNo`),
  INDEX `fk_comments_board1_idx` (`board_boardNo` ASC) VISIBLE,
  INDEX `fk_comments_ members1_idx` (`members_email` ASC) VISIBLE,
  CONSTRAINT `fk_comments_ members1`
    FOREIGN KEY (`members_email`)
    REFERENCES `puppylink`.`members` (`email`),
  CONSTRAINT `fk_comments_board1`
    FOREIGN KEY (`board_boardNo`)
    REFERENCES `puppylink`.`board` (`boardNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`foundDation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`foundDation` (
  `businessNo` INT NOT NULL,
  `presidentName` VARCHAR(45) NULL DEFAULT NULL,
  `businessName` VARCHAR(100) NULL DEFAULT NULL,
  `startName` DATE NULL DEFAULT NULL,
  `members_email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`businessNo`),
  INDEX `fk_foundDation_ members1_idx` (`members_email` ASC) VISIBLE,
  CONSTRAINT `fk_foundDation_ members1`
    FOREIGN KEY (`members_email`)
    REFERENCES `puppylink`.`members` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`favorite` (
  `favoriteNo` INT NOT NULL AUTO_INCREMENT,
  `members_email` VARCHAR(100) NOT NULL,
  `foundDation_businessNo` INT NOT NULL,
  PRIMARY KEY (`favoriteNo`),
  INDEX `fk_favorite_ members1_idx` (`members_email` ASC) VISIBLE,
  INDEX `fk_favorite_foundDation1_idx` (`foundDation_businessNo` ASC) VISIBLE,
  CONSTRAINT `fk_favorite_ members1`
    FOREIGN KEY (`members_email`)
    REFERENCES `puppylink`.`members` (`email`),
  CONSTRAINT `fk_favorite_foundDation1`
    FOREIGN KEY (`foundDation_businessNo`)
    REFERENCES `puppylink`.`foundDation` (`businessNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`flightTicket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`flightTicket` (
  `ticketNo` VARCHAR(100) NOT NULL,
  `passengerName` VARCHAR(45) NOT NULL,
  `bookingReference` VARCHAR(45) NOT NULL,
  `depCity` VARCHAR(45) NULL DEFAULT NULL,
  `depDate` VARCHAR(45) NULL DEFAULT NULL,
  `arriveCity` VARCHAR(45) NOT NULL,
  `arriveDate` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ticketNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`volunteerWork`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`volunteerWork` (
  `volunteerNo` INT NOT NULL AUTO_INCREMENT,
  `depCity` VARCHAR(100) NOT NULL,
  `dest` VARCHAR(45) NOT NULL,
  `destTime` VARCHAR(45) NOT NULL,
  `status` TINYINT NULL DEFAULT NULL,
  `fileURL` VARCHAR(45) NULL DEFAULT NULL,
  `flightName` VARCHAR(45) NULL DEFAULT NULL,
  `regDate` VARCHAR(45) NULL DEFAULT NULL,
  `members_email` VARCHAR(100) NOT NULL,
  `flightTicket_ticketNo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`volunteerNo`),
  INDEX `fk_volunteerWork_ members1_idx` (`members_email` ASC) VISIBLE,
  INDEX `fk_volunteerWork_flightTicket1_idx` (`flightTicket_ticketNo` ASC) VISIBLE,
  CONSTRAINT `fk_volunteerWork_ members1`
    FOREIGN KEY (`members_email`)
    REFERENCES `puppylink`.`members` (`email`),
  CONSTRAINT `fk_volunteerWork_flightTicket1`
    FOREIGN KEY (`flightTicket_ticketNo`)
    REFERENCES `puppylink`.`flightTicket` (`ticketNo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `puppylink`.`authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`authority` (
  `authority_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`authority_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `puppylink`.`members_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `puppylink`.`members_authority` (
  `authority_name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`authority_name`, `email`),
  INDEX `fk_authority_has_ members_ members1_idx` (`email` ASC) VISIBLE,
  INDEX `fk_authority_has_ members_authority1_idx` (`authority_name` ASC) VISIBLE,
  CONSTRAINT `fk_authority_has_ members_authority1`
    FOREIGN KEY (`authority_name`)
    REFERENCES `puppylink`.`authority` (`authority_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_authority_has_ members_ members1`
    FOREIGN KEY (`email`)
    REFERENCES `puppylink`.`members` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
