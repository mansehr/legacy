CLS
SCREEN 12
COLOR 15
LOCATE 5, 30
PRINT "Addes basic Spel (tryck p† en tangent)"
FOR i = 1 TO 90
COLOR 1
LINE (1 + (i * 8), 1 + (i * 4))-(640 - (i * 8), 480 - (i * 4))
COLOR 2
LINE (1 - (i * 8), 1 + (i * 4))-(640 + (i * 8), 480 - (i * 4))
COLOR 3
LINE (1 - (i * 8), 1 - (i * 4))-(640 + (i * 8), 480 + (i * 4))
COLOR 4
LINE (1 + (i * 8), 1 + (i * 4))-(640 + (i * 8), 480 + (i * 4))
NEXT
DO
LOOP WHILE INKEY$ = ""
CLS

FOR i = 1 TO 12 STEP 2
FOR j = 1 TO 12
LOCATE j, i
PRINT "|"
LOCATE j, i + 1
PRINT "X"
NEXT
NEXT

FOR i = 1 TO 12
LOCATE i, 13
PRINT "|"
LOCATE 13, i
NEXT

