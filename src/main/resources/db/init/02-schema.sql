USE employees_db;

-- Tabla principal alineada con la entidad Employee del servicio
-- NOTA: usamos VARCHAR + CHECK para gender,
-- así se alinea con @Enumerated(EnumType.STRING) sin columnDefinition.
CREATE TABLE IF NOT EXISTS employees (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name        VARCHAR(60)  NOT NULL,
  second_name       VARCHAR(60)  NULL,
  last_name_paternal VARCHAR(60) NOT NULL,
  last_name_maternal VARCHAR(60) NOT NULL,
  age               INT          NOT NULL,
  gender            VARCHAR(10)  NOT NULL,
  birth_date        DATE         NOT NULL,
  position          VARCHAR(80)  NOT NULL,

  CONSTRAINT pk_employees PRIMARY KEY (id),
  CONSTRAINT chk_employees_age CHECK (age BETWEEN 18 AND 100),
  CONSTRAINT chk_employees_gender CHECK (gender IN ('MALE','FEMALE','OTHER'))
) ENGINE=InnoDB;

-- Índices útiles para consultas típicas (búsquedas por apellidos y puesto)
CREATE INDEX idx_employees_last_names ON employees (last_name_paternal, last_name_maternal);
CREATE INDEX idx_employees_position   ON employees (position);
