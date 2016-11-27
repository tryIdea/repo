REM "dbstart.cmd"
@echo off
set ORAHOME="bjsxt"
set ORASID="orcl"
net start Oracle%ORAHOME%OracleTNSListener
net start OracleService%ORASID%
pause


