REM "dbstart.cmd"
@echo off 
color 0c
cls 
echo ========================== 
echo 欢迎使用mysql启动程序 
echo %DATE% （by：杜福刚） 
echo ========================== 
echo. 
echo 请输入命令? 
echo （y键启动(任意键)；n停止） 
echo. 
set /p ly= ★命令★： 
if %ly%==y goto start
if %ly%==n goto stop 
:start
net start mysql56
pause 
exit
:stop
net stop mysql56
pause
exit