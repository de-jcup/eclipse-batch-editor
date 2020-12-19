@echo off
REM Complete synax highlighting example file - this file has no real logic, just for syntax highlighting...
IF NOT "%OS%"=="Windows_NT" goto not_windows_nt
:label12345
:: variable literal highlighting
set variable1=12345 REM digits
set variable2="i am a string" REM string...
set variable3=%variable1% + %variable2%
c:
cd c:\users\thisone\special REM internal commands like cd are highlighted
doeSomething.bat --use-log-file ./xyz-log-config.xml REM recognized parameters are highlighted

:not_windows_nt
:: echo highlighting examples:
echo %variable1%
echo !%variable2! 
echo hello world will echo something
echo shall highlight only until pipe | dir
echo shall highlight complete line ^| dir
echo shall highlight only until here < dir
echo shall highlight complete line ^< dir
echo shall highlight only until here > dir
echo shall highlight only until here ^^> dir
echo shall highlight complete line ^> dir
echo shall highlight complete line ^>^>dir
echo shall highlight only until pipe & dir
echo shall highlight complete line ^& dir
echo.
ECHO.
