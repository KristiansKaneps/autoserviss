#!/bin/bash
set -e

function setupMainInstanceSQL {
  sqlplus / as sysdba << EOF
CONNECT SYS/$ORACLE_PWD@$ORACLE_PDB AS SYSDBA;
CREATE SMALLFILE TABLESPACE ${ORACLE_USR^^} DATAFILE '$ORACLE_BASE/oradata/$ORACLE_SID/$ORACLE_PDB/${ORACLE_USR,,}.dbf'
  SIZE 100M AUTOEXTEND ON;
CREATE USER ${ORACLE_USR^^} IDENTIFIED BY $ORACLE_PWD
  DEFAULT TABLESPACE ${ORACLE_USR^^}
  TEMPORARY TABLESPACE TEMP
  QUOTA UNLIMITED ON ${ORACLE_USR^^};

GRANT CREATE SESSION TO ${ORACLE_USR^^};
GRANT CREATE TABLE TO ${ORACLE_USR^^};
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE TO ${ORACLE_USR^^};

exit;
EOF
}

function setupTestInstanceSQL {
  sqlplus / as sysdba << EOF
CONNECT SYS/$ORACLE_PWD@$ORACLE_PDB AS SYSDBA;
CREATE SMALLFILE TABLESPACE ${ORACLE_USR^^}_TEST DATAFILE '$ORACLE_BASE/oradata/$ORACLE_SID/$ORACLE_PDB/${ORACLE_USR,,}_test.dbf'
  SIZE 100M AUTOEXTEND ON;
CREATE USER ${ORACLE_USR^^}_TEST IDENTIFIED BY $ORACLE_PWD
  DEFAULT TABLESPACE ${ORACLE_USR^^}_TEST
  TEMPORARY TABLESPACE TEMP
  QUOTA UNLIMITED ON ${ORACLE_USR^^}_TEST;

GRANT CREATE SESSION TO ${ORACLE_USR^^}_TEST;
GRANT CREATE TABLE TO ${ORACLE_USR^^}_TEST;
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE TO ${ORACLE_USR^^}_TEST;

exit;
EOF
}

setupMainInstanceSQL;
setupTestInstanceSQL;