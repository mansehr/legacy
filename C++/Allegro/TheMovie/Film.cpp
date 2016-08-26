#include "math.h"
#include <allegro.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

BITMAP *theWorld;
BITMAP *starShip;
BITMAP *explosion;
BITMAP *screenBuffer;
PALLETE pal;


void main()
{
   allegro_init();
   install_keyboard();
   install_timer();
   set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0);

   explosion = load_bmp("explosion.bmp",pal);
   starShip = load_bmp("rymdskepp.bmp",pal);
   theWorld = load_bmp("theWorld.bmp",pal);
   screenBuffer = create_bitmap(SCREEN_W, SCREEN_H);

   set_pallete(pal);

   int X = 0, initX = retrace_count + 1100;
   int textX, initTextX = retrace_count + 40;
   char *text;
   while (X < 500 && !key[KEY_ESC])
   {
      X = (retrace_count-initX)/3;
      textX = 640-(retrace_count-initTextX);

      blit(theWorld, screenBuffer, 0, 0, 0, 0, 640, 480);

      if (X > 300)
      {
         blit(explosion, screen, 0, 0, 0, 0, 640, 480);
      }
      else
      {
         draw_sprite(screenBuffer, starShip, X, 220);
      }

      sprintf(text,"StarshipX: %d  TextX: %d", X, textX );
      textout(screenBuffer, font, text, 5, 5, 13);
      textout(screenBuffer, font, "The year is 2569, the date is 12 of february! The time 12:34 local!..
This happends............................Bzzz Were going too chrash. Bzzz....Watchout....................
Bzzzzzz.....Bzzzzz............NOOOOOOOOOOOOOOOOOOOOOOOOO" , textX, 60, 200);

      blit(screenBuffer, screen, 0, 0, 0, 0, 640, 480);
   }


   textout(screen, font, "Scary isnt it......" , 120, 40, 200);
   textout(screen, font, "Press [A Key] to exit.." , 100, 180, 200);

   readkey();


   destroy_bitmap(screenBuffer);
   destroy_bitmap(theWorld);
   destroy_bitmap(explosion);
   destroy_bitmap(starShip);
}
