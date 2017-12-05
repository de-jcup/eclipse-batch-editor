@echo off
REM test script 2 - check echo handling
echo hello world will echo something
echo shall highlight only until pipe | dir
echo shall highlight only until here < dir
echo shall highlight only until here > dir
echo shall highlight only until here ^^> dir
echo shall highlight only until pipe & dir
echo.
ECHO.
:: Here the complete lines should be highlighted!
echo shall highlight complete line ^| dir
echo shall highlight complete line ^& dir
echo shall highlight complete line ^< dir
echo shall highlight complete line ^> dir
echo shall highlight complete line ^>^>dir

:: Just check uppercase handling
echo xxxx set something
ECHO xxxx set something
ECHO xxxx set something | lslsls
