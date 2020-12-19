@echo off

REM --------------------------------
REM - Example1: vars with %
REM --------------------------------
REM example1 from https://stackoverflow.com/questions/10558316/example-of-delayed-expansion-in-batch-file/10558905#10558905
set COUNT=0

for %%v in (1 2 3 4) do (
  set /A COUNT=%COUNT% + 1
  echo Count = %COUNT%
)
pause


REM --------------------------------
REM - Example2: vars with !
REM --------------------------------
REM example2 from https://stackoverflow.com/questions/10558316/example-of-delayed-expansion-in-batch-file/10558905#10558905
setlocal ENABLEDELAYEDEXPANSION
set COUNT=0

for %%v in (1 2 3 4) do (
  set /A COUNT=!COUNT! + 1
  echo Count = !COUNT!
)

pause