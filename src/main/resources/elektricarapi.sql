

USE `elektricarapi` ;

-- -----------------------------------------------------
-- Table `elektricarapi`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`category` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`category` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `created_by` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`company` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`company` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`users` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`users` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  `website` VARCHAR(255) NULL DEFAULT NULL,
  `company_id` BIGINT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_company` (`company_id` ASC) VISIBLE,
  CONSTRAINT `fk_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `elektricarapi`.`company` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`list` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`list` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `created_by` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_list_user` (`created_by` ASC) VISIBLE,
  CONSTRAINT `fk_list_user`
    FOREIGN KEY (`created_by`)
    REFERENCES `elektricarapi`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`materijal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`materijal` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`materijal` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `price` DECIMAL(10,0) NULL DEFAULT NULL,
  `created_by` BIGINT UNSIGNED NULL DEFAULT NULL,
  `updated_by` BIGINT UNSIGNED NULL DEFAULT NULL,
  `category_id` BIGINT UNSIGNED NULL DEFAULT NULL,
  `photo` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_materijal` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_materijal`
    FOREIGN KEY (`category_id`)
    REFERENCES `elektricarapi`.`category` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`material_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`material_list` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`material_list` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `list_id` BIGINT UNSIGNED NULL DEFAULT NULL,
  `material_id` BIGINT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_material_list_list_idx` (`list_id` ASC) VISIBLE,
  INDEX `fk_material_list_material_idx` (`material_id` ASC) VISIBLE,
  CONSTRAINT `fk_material_list_list`
    FOREIGN KEY (`list_id`)
    REFERENCES `elektricarapi`.`list` (`id`),
  CONSTRAINT `fk_material_list_material`
    FOREIGN KEY (`material_id`)
    REFERENCES `elektricarapi`.`materijal` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`roles` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`roles` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `elektricarapi`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `elektricarapi`.`user_role` ;

CREATE TABLE IF NOT EXISTS `elektricarapi`.`user_role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `role_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_security_user_id` (`user_id` ASC) VISIBLE,
  INDEX `fk_security_role_id` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_security_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `elektricarapi`.`roles` (`id`),
  CONSTRAINT `fk_security_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `elektricarapi`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


