			////////////////////////////////////////////////////////
			//																		//
			//				Fil: "Objekt.hpp"									//
			//																		//
			//				F�rfattad av: Andreas Sehr 2002,01,07		//
			//																		//
			//				Senast �ndrad:	2003,01,27						//
			//																		//
			////////////////////////////////////////////////////////
#ifndef __ADObjekt.hpp__
#define __ADObjekt.hpp__

enum Status { Null = 0, NotPressable, Hoover, Active, Pressed };

class Objekt
{
	public:
		Objekt();

		int 	getBottom	() const { return bottom;		}
		int 	getLeft		() const { return left;			}
		int 	getRight		() const { return right;		}
		Status getStatus	() const { return thisStatus;	}
		char* getText		() const { return text;			}
		int 	getTextWidth() const { return textWidth;	}
		int 	getTop		() const { return top;			}
		int 	getWidth		() const { return width;		}
		void 	setBottom 	(int initBottom) 	{ bottom = initBottom; 		}
		void 	setHeight 	(int initHeight) 	{ height = initHeight;		}
		void 	setLeft 		(int initLeft) 	{ left = initLeft; 			}
		void 	setRight 	(int initRight) 	{ right = initRight;			}
		void 	setStatus 	(Status initStatus){thisStatus = initStatus;	}
		void 	setText 		(char* initText);
		void 	setTextWidth(int initWidth) 	{ textWidth = initWidth;	}
		void 	setTop 		(int initTop) 		{ top = initTop;				}
		void 	setWidth 	(int initWidth) 	{ width = initWidth;			}

	protected:
		int 	bottom;
		int 	height;
		int 	left;
		int 	right;
		char*	text;
		int 	textWidth;
		int 	top;
		int 	width;
		Status thisStatus;
};

Objekt::Objekt()
{
	setText ("");
	thisStatus 	= Null;
	top 			= 1;
	bottom 		= top + height;
	left 			= 1;
	right 		= left + width;
}

void Objekt::setText(char* initText)
{
	text 			= initText;
	textWidth 	= strlen(text);
	width 		= textWidth;
	height 		= 1;
}

#endif /*__ADObjekt.hpp__*/