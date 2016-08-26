#include <iostream.h>
#include <stdlib.h>
#include "pekare.h"

void test (int ** testP);

{                                          
int main() 
 int hej = 10, hel;
 int *het;
 het = &hej;
 char hallo[] = "Hallo";

 hel = sizeof(hallo);
 
// het = myP.New (het);
  test(&het);

 cout << "&het: " << &het << endl; 
 cout << "*het: " << *het << endl;
 cout << "het: " << het << endl;

 cout << "hel: " << hel << endl;
   system ("PAUSE");
   return 0;
   
}

void test (int ** testP) {
 cout << "testP: " << testP << endl;
 cout << "*testP: " << *testP << endl;
}
