@echo off
REM Complete synax highlighting example file - this file has no real logic, just for syntax highlighting...
IF NOT "%OS%"=="Windows_NT" goto not_windows_nt
:label12345
:: variable literal highlighting
echo %1
if %1%==1 echo "The value of first parameter is 1"

set variable1=12345 REM digits
set variable2="i am a string" REM string...
set variable3=%variable1% + %variable2%
c:
cd c:\users\thisone\special REM internal commands like cd are highlighted
doeSomething.bat --use-log-file ./xyz-log-config.xml REM recognized parameters are highlighted

:: look at https://ss64.com/nt/if.html
IF EXIST filename.txt (
    echo deleting filename.txt
    del filename.txt
 ) ELSE ( 
    echo The file was not found.
 )
 
SET /A var=1

IF /I "%var%" EQU "1" ECHO equality with 1
IF /I "%var%" NEQ "0" ECHO inequality with 0
IF /I "%var%" GTR "1" ECHO greater than 1
IF /I "%var%" GEQ "1" ECHO greater than or equal to 1
IF /I "%var%" LSS "1" ECHO less than 1
IF /I "%var%" LEQ "1" ECHO less than or equal to 1
 
IF [%1]==[] ECHO Value Missing
or
IF [%1] EQU [] ECHO Value Missing 

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
