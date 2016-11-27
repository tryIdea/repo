REM "dbstop.cmd"
@echo off
set ORAHOME="bjsxt"
set ORASID="ORCL"
net stop OracleService%ORASID%
net stop Oracle%ORAHOME%OracleTNSListener
pause