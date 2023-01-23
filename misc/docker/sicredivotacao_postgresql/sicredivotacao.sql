SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET default_tablespace = '';
SET default_with_oids = false;
SET TimeZone = 'America/Sao_Paulo';

/*******************************************************************************
   Drop Tables
********************************************************************************/

DROP TABLE IF EXISTS associado;
DROP TABLE IF EXISTS pauta;
DROP TABLE IF EXISTS sessao;
DROP TABLE IF EXISTS voto;

/*******************************************************************************
   Create Tables
********************************************************************************/

CREATE TABLE associado
(
    "id" BIGSERIAL,
    "nome" VARCHAR(200) NOT NULL,
    "cpf" VARCHAR(11) NOT NULL,
    "status" VARCHAR(14) NOT NULL,
    CONSTRAINT "PK_Associado" PRIMARY KEY  ("id")
);

CREATE TABLE pauta
(
    "id" BIGSERIAL,
    "titulo" VARCHAR(160) NOT NULL,
    "descricao" VARCHAR(400) NOT NULL,
    "status" VARCHAR(8),
    CONSTRAINT "PK_Pauta" PRIMARY KEY  ("id")
);

CREATE TABLE sessao
(
    "id" BIGSERIAL,
    "pauta_id" INT NOT NULL,
    "duracao" INT NOT NULL,
    "abertura" TIMESTAMP NOT NULL,
    "fechamento" TIMESTAMP NULL,
    "ativo" BOOLEAN NOT NULL,
    CONSTRAINT "PK_Sessao" PRIMARY KEY  ("id")
);

CREATE TABLE voto
(
    "id" BIGSERIAL,
    voto VARCHAR(3) NOT NULL,
    "sessao_id" INT NOT NULL,
    "associado_id" INT NOT NULL,
    CONSTRAINT "PK_Voto" PRIMARY KEY  ("id")
);
/***************************** **************************************************
   Create Primary Key Unique Indexes and Foreign Keys
********************************************************************************/

CREATE INDEX "IFK_AssociadoId" ON associado ("id");
CREATE INDEX "IFK_PautaId" ON pauta ("id");
CREATE INDEX "IFK_SessaoId" ON sessao ("id");
CREATE INDEX "IFK_VotoId" ON voto ("id");

ALTER TABLE sessao ADD CONSTRAINT "FK_SessaoPautaId"
    FOREIGN KEY ("pauta_id") REFERENCES pauta ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE voto ADD CONSTRAINT "FK_VotoSessaoId"
    FOREIGN KEY ("sessao_id") REFERENCES sessao ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE voto ADD CONSTRAINT "FK_VotoAssociadoId"
    FOREIGN KEY ("associado_id") REFERENCES associado ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

/*******************************************************************************
   Populate Tables
********************************************************************************/

INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_A', '81616590602', 'ABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_B', '59581419616', 'UNABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_C', '93840255694', 'ABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_D', '84015009665', 'UNABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_E', '22617375641', 'UNABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_F', '54625637600', 'ABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_G', '06114867688', 'UNABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_H', '44809851664', 'ABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_I', '67296918675', 'UNABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_J', '13732345637', 'ABLE_TO_VOTE');
INSERT INTO associado ("nome", "cpf", "status") VALUES ('Associado_K', '33846848697', 'UNABLE_TO_VOTE');

INSERT INTO pauta ("titulo", "descricao", "status") VALUES ('Pauta1', 'Descricao1', 'ANALISE');
INSERT INTO pauta ("titulo", "descricao", "status") VALUES ('Pauta2', 'Descricao2', 'ANALISE');

INSERT INTO sessao ("pauta_id", "duracao", "abertura", "ativo") VALUES ('1', '20', now()::timestamp, true);

INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '1');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '2');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '3');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '4');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '5');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '6');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('NAO', '1', '7');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('NAO', '1', '8');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('NAO', '1', '9');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('SIM', '1', '10');
INSERT INTO voto ("voto", "sessao_id", "associado_id") VALUES ('NAO', '1', '11');



