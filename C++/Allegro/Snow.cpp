#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "allegro.h"

#define DEG(n)    ((n) * 180.0 / M_PI)       // Konvertera radiander till grader
#define ANT_PIX 200

BITMAP *buffer;

struct Plot
{
   double x;
   double y;
   double dx;
   double dy;
   int time;
   int color;
};

int main()
{
   allegro_init();
   install_timer();
   install_keyboard();

   if (set_gfx_mode(GFX_AUTODETECT, 320, 200, 0, 0) != 0) {
      allegro_exit();
      printf("Error setting graphics mode\n%s\n\n", allegro_error);
      return 1;
   }
   PALLETE pallete;
   /* fill our pallete with a gradually altering sequence of colors */
   for (int c=0; c<64; c++) {
      pallete[c].r = c;
      pallete[c].g = 0;
      pallete[c].b = 0;
   }
   for (int c=64; c<128; c++) {
      pallete[c].r = 0;
      pallete[c].g = c-64;
      pallete[c].b = 0;
   }
   for (int c=128; c<192; c++) {
      pallete[c].r = 0;
      pallete[c].g = 0;
      pallete[c].b = c-128;
   }
   for (int c=192; c<256; c++) {
      pallete[c].r = c-192;
      pallete[c].g = c-192;
      pallete[c].b = c-192;
   }

   set_pallete(pallete);

   buffer = create_bitmap(SCREEN_W, SCREEN_H);
   Plot plot[ANT_PIX];
   for(int i = 0; i < ANT_PIX; i++)
   {
         plot[i].dx = 0;
         plot[i].time = 0;
         plot[i].y = (random() % 200)+1;
         plot[i].x = (random() % 320)+1;
         plot[i].dy = ((random() % 8000)*0.0001)+1;
         if(plot[i].dy > 5.5)
         {
            plot[i].dy -= 4;
         }
         if(plot[i].dy < 1)
            plot[i].color = 220;
         else if(plot[i].dy < 1.4)
            plot[i].color = 230;
         else
            plot[i].color = 255;
   }
   while (!key[KEY_ESC])
   {
      rest(20);
      clear(buffer);
      for(int i = 0; i < ANT_PIX; i++)
      {
            putpixel(buffer, int(plot[i].x), int(plot[i].y), plot[i].color);
            plot[i].y += plot[i].dy;
            if(plot[i].y > SCREEN_H)
            {
               plot[i].y = 0;
               plot[i].x = (random() % 320)+1;
            }
      }
      blit(buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);
   }

   destroy_bitmap(buffer);
   allegro_exit();
}