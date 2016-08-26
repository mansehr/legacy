#include <iostream.h>
#include <stdlib.h>

int main()
{
      char test;
      int i;
      
       //rad 1    
       test = 201;
       cout << test;       
       i =0; test = 205;
       while (i <= 30)
       {i++; cout << test;}     
      test = 187;
      cout << test << endl;
       
       //rad 2-6
        i = 2; 
        while (i <= 6)
        {
        i++;   
        test = 186;
        cout << test;        
        for( int p = 0; p <= 30; ++p)
           {test = 0;
           cout << test;}           
        test = 186;
        cout << test << endl;
        }    
       
     //rad 7  
       test = 200;
       cout << test;
       i =0; test = 205;
       while (i <= 30)
       {i++; cout << test;}
       test = 188;
       cout << test << endl;
             
      system("PAUSE");
      return 0;
}
