-- Crea base de datos y usuario de aplicación con privilegios mínimos
CREATE DATABASE IF NOT EXISTS employees_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

-- Variables que vienen del contenedor; si ejecutas esto fuera de Docker, ajusta credenciales:
--   APP_DB_USER, APP_DB_PASSWORD
-- Para idempotencia:
DROP USER IF EXISTS 'appuser'@'%';
CREATE USER IF NOT EXISTS 'appuser'@'%' IDENTIFIED BY 'appsecret';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX
  ON employees_db.* TO 'appuser'@'%';

FLUSH PRIVILEGES;
