echo off

REM the next dir command after the pipe symbol is executed by batch
IF NOT "%OS%"=="Windows_NT" echo I am not windows NT|dir
IF "%OS%"=="Windows_NT" echo I am windows NT not anything else ...|dir

REM next line does not output anything but just creates the file
echo info > info-bugscript3.bat.txt
rm info-bugscript3.bat.txt

REM looking at https://ss64.com/nt/echo.html gives following information:
REM 
REM Command characters will normally take precedence over the ECHO statement
REM e.g. The redirection and pipe characters: & < > | ON OFF
REM 
REM To override this behaviour you can escape each command character with ^ as follows:
REM 
REM ECHO Nice ^&Easy
REM ECHO Salary is ^> Commision
REM ECHO Name ^| Username ^| Expiry Date

@rem others...
ECHO Nice ^&Easy
ECHO Salary is ^> Commision
ECHO Name ^| Username ^| Expiry Date
rem next line is not output...
ECHO:Off offshore... not visible
ECHO:Off On Holiday
ECHO:on At work...
ECHO:%department%
ECHO Example:  4AllCtxSrv  echo  #
echo example:  cd del

