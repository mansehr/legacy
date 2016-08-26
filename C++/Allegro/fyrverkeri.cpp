#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "allegro.h"

#define DEG(n)    ((n) * 180.0 / M_PI)       // Konvertera radiander till grader
#define ANT_PIX 270
#define ANT_EXPLODE 20

BITMAP *buffer;

struct Plot
{
   double x;
   double y;
   double dx;
   double dy;
   double speed;
   int time;
   int color;
};

class Explode
{
	public:
		Explode()
		{
			restart = -1;
			explodeX = 0;
			explodeX	= 0;
			explodeColor = 0;
		}

		void run()
		{
			if(restart < 0)
			{
				reset();
				restart = int(random() % 100) + 50;
      	}
      	else
      	{
      		restart -= 1;
				draw();
			}
		}
		void reset()
		{
			explodeX = (random() % 540)+50;
			explodeY = (random() % 380)+50;
			explodeColor = int(random() % 255);
			for(int i = 0; i < ANT_PIX; i++)
			{
				plot[i].time = int(random() % 100);
				plot[i].x = explodeX;
				plot[i].y = explodeY;
				plot[i].dx = cos(DEG(i*360/ANT_PIX));
				plot[i].dy = sin(DEG(i*360/ANT_PIX));
				plot[i].speed = random() % 3;
				plot[i].color = explodeColor;
         }
		}

		void draw()
		{
			for(int i = 0; i < ANT_PIX; i++)
			{
				if(plot[i].time > 0 && plot[i].time < 75)
				{
					putpixel(buffer, int(plot[i].x), int(plot[i].y), plot[i].color);

					plot[i].x += plot[i].dx*plot[i].speed;
					plot[i].y += plot[i].dy*plot[i].speed;
				}
				plot[i].time -= 1;

				if(plot[i].x > SCREEN_W || plot[i].x < 0
				|| plot[i].y > SCREEN_H || plot[i].y < 0
				|| plot[i].time < 0)
				{
					plot[i].time = 0;
				}
    		}
		}

	private:
		Plot plot[ANT_PIX];
		int explodeX;
		int explodeY;
		int explodeColor;
		int restart;
};

int main()
{
   Explode explode[ANT_EXPLODE];

   allegro_init();
   install_timer();
   install_keyboard();

   if (set_gfx_mode(GFX_AUTODETECT, 640, 480, 0, 0) != 0) {
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
      pallete[c].r = 127-c;
      pallete[c].g = c-64;
      pallete[c].b = 0;
   }
   for (int c=128; c<192; c++) {
      pallete[c].r = 0;
      pallete[c].g = 191-c;
      pallete[c].b = c-128;
   }
   for (int c=192; c<256; c++) {
      pallete[c].r = 0;
      pallete[c].g = 0;
      pallete[c].b = 255-c;
   }

   set_pallete(pallete);

   buffer = create_bitmap(SCREEN_W, SCREEN_H);

   while (!key[KEY_ESC])
   {
		rest(20);
		clear(buffer);
		for(int i = 0; i < ANT_EXPLODE; i++)
      {
			explode[i].run();
		}
      blit(buffer, screen, 0, 0, 0, 0, SCREEN_W, SCREEN_H);
   }

   destroy_bitmap(buffer);
   allegro_exit();
}