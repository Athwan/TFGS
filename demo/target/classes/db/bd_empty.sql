DROP TABLE IF EXISTS `mydb`.`Persona`;

CREATE TABLE IF NOT EXISTS `mydb`.`Persona` (
  `DNI` VARCHAR(9) NOT NULL,
  `nombre` VARCHAR(45) NULL,
  `apellidos` VARCHAR(45) NULL,
  `telefono` INT NULL,
  `password` VARCHAR(100) NULL,
  PRIMARY KEY (`DNI`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `mydb`.`Apartamento`;

CREATE TABLE IF NOT EXISTS `mydb`.`Apartamento` (
  `ID_Apartamento` INT NOT NULL AUTO_INCREMENT,
  `ubicacion` VARCHAR(100) NULL,
  PRIMARY KEY (`ID_Apartamento`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `mydb`.`Gastos`;

CREATE TABLE IF NOT EXISTS `mydb`.`Gastos` (
  `ID_Gastos` INT NOT NULL,
  `apartamentoID` INT NOT NULL,
  `iva` DOUBLE NULL,
  `total_con_IVA` DOUBLE NULL,
  `total_gasto` DOUBLE NULL,
  `NIF_proveedor` VARCHAR(9) NULL,
  `nombre_proveedor` VARCHAR(45) NULL,
  `gasto_concepto` VARCHAR(90) NULL,
  `pagoCompletado` TINYINT NULL,
  `fecha` DATE NULL,
  PRIMARY KEY (`ID_Gastos`),
  INDEX `fk_Gastos_Apartamento1_idx` (`apartamentoID` ASC),
  CONSTRAINT `fk_Gastos_Apartamento1`
    FOREIGN KEY (`apartamentoID`)
    REFERENCES `mydb`.`Apartamento` (`ID_Apartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `mydb`.`Tarifa`;

CREATE TABLE IF NOT EXISTS `mydb`.`Tarifa` (
  `ID_Tarifa` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NULL,
  `precio_base` DOUBLE NULL,
  PRIMARY KEY (`ID_Tarifa`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `mydb`.`Intermediarios`;

CREATE TABLE IF NOT EXISTS `mydb`.`Intermediarios` (
  `ID_Intermediario` INT NOT NULL AUTO_INCREMENT,
  `nombre_intermediario` VARCHAR(45) NULL,
  PRIMARY KEY (`ID_Intermediario`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `mydb`.`Ingresos`;

CREATE TABLE IF NOT EXISTS `mydb`.`Ingresos` (
  `ID_Ingresos` INT NOT NULL AUTO_INCREMENT,
  `Persona_DNI` VARCHAR(9) NOT NULL,
  `intermediario` INT NOT NULL,
  `apartamento` INT NOT NULL,
  `tarifa` INT NOT NULL,
  `fecha_entrada` DATE NULL,
  `fecha_salida` DATE NULL,
  `num_coches` INT NULL,
  `num_personas` INT NULL,
  `descuento` DOUBLE NULL,
  `total_iva` DOUBLE NULL,
  `total_factura` DOUBLE NULL,
  `observaciones` VARCHAR(150) NULL,
  PRIMARY KEY (`ID_Ingresos`),
  INDEX `fk_Ingresos_Persona2_idx` (`Persona_DNI` ASC),
  INDEX `fk_Ingresos_Intermediarios1_idx` (`intermediario` ASC),
  INDEX `fk_Ingresos_Apartamento2_idx` (`apartamento` ASC),
  INDEX `fk_Ingresos_Tarifa2_idx` (`tarifa` ASC),
  CONSTRAINT `fk_Ingresos_Persona2`
    FOREIGN KEY (`Persona_DNI`)
    REFERENCES `mydb`.`Persona` (`DNI`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ingresos_Intermediarios1`
    FOREIGN KEY (`intermediario`)
    REFERENCES `mydb`.`Intermediarios` (`ID_Intermediario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ingresos_Apartamento2`
    FOREIGN KEY (`apartamento`)
    REFERENCES `mydb`.`Apartamento` (`ID_Apartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ingresos_Tarifa2`
    FOREIGN KEY (`tarifa`)
    REFERENCES `mydb`.`Tarifa` (`ID_Tarifa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;
