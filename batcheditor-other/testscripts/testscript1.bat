@ECHO OFF
:: Check Windows version
IF NOT "%OS%"=="Windows_NT" GOTO Syntax

:: At least 2 command line arguments are required
IF [%2]==[] GOTO Syntax

:: Use local variables
SETLOCAL ENABLEEXTENSIONS

:: :: :: :: :: :: :: :: :: :: :: :: :: ::
:: Specify 1 Citrix server in the farm ::
:: :: :: :: :: :: :: :: :: :: :: :: :: ::
SET CtxSrv=MYCTXSRV01

:: Check if the server is online
PING %CtxSrv% -n 1 >NUL 2>&1
IF ERRORLEVEL 1 (
	ECHO.
	ECHO Server \\%CtxSrv% is not available
	GOTO Syntax
)

:: Main command; delims is an asterisc, followed by a tab and a space
:: Change "TCP/IP" search string if other protocols are used
FOR /F "tokens=1 delims=*	 " %%A IN ('PSEXEC \\%CtxSrv% QUERY FARM /CONTINUE 2^>NUL ^| FIND "TCP/IP" ^| SORT') DO CALL :MyCommand %%A %*

:: Done
ENDLOCAL
GOTO End


:MyCommand
:: Use local variables, for this server only
SETLOCAL
:: First argument is server name
SET Server=%1
:: The rest is the command
FOR /F "tokens=1*" %%X IN ('ECHO.%*') DO SET MyCommand=%%Y
:: Substitute server name for # in command
CALL SET MyCommand=%%MyCommand:#=%Server%%%
:: Execute the resulting command
%COMSPEC% /C %MyCommand%
:: End subroutine
ENDLOCAL
GOTO:EOF


:Syntax
ECHO.
ECHO 4AllCtxSrv.bat,  Version 1.12 for Windows 2000
ECHO Execute a command once for each server of a Citrix farm
ECHO.
ECHO Usage:    4AllCtxSrv  my_command  [ arguments ]
ECHO.
ECHO Where:    "my_command" will be executed once for each server in the farm.
ECHO           "arguments"  is the list of command line arguments for my_command.
ECHO           "#"          in command line arguments is substituted by server name.
ECHO.
ECHO Example:  4AllCtxSrv  echo  #
ECHO.
ECHO The server list is retrieved from a hardcoded Citrix server;
ECHO modify this server name before using this batch file for the first time.
ECHO.
ECHO Uses SysInternals's PSEXEC, available at http://www.sysinternals.com
ECHO.
ECHO Written by Rob van der Woude
ECHO http://www.robvanderwoude.com

:End