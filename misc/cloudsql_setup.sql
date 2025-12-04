-- Script de exemplo para configuração de banco Cloud SQL (MySQL ou PostgreSQL)
-- Estes comandos devem ser adaptados e executados manualmente pela equipe FortePix.

CREATE DATABASE fortepix;
CREATE USER fortepix_user WITH PASSWORD 'DEFINIR_SENHA_FORTE';
GRANT ALL PRIVILEGES ON DATABASE fortepix TO fortepix_user;
