CLS
SCREEN 12
PRINT "Press esc to exit!"
DO
FOR i = 1 TO 15
FOR j = 1 TO 700 STEP 10
COLOR i
CIRCLE (100, 150), j
LINE (1 + (i * 8), 1 + (i * 4))-(640 - (i * 6), 480 - (i * 2))
NEXT
NEXT
LOOP UNTIL INKEY$ = CHR$(27)

