
using System;
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Windows.Forms;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;
using Direct3D = Microsoft.DirectX.Direct3D;

namespace RickisDXLib
{
	public class Text
	{
		/**********************************************************************
		*
		*
		*  MEMBERS
		*
		*
		**********************************************************************/

		private Direct3D.Font font;
		private string _text = "";
		private int _x = 0;
		private int _y = 0;
		private Color _color = Color.Black;

		/**********************************************************************
		*
		*
		*  CONSTRUCTOR
		*
		*
		**********************************************************************/

		public Text(Direct3D.Device d3dDevice, System.Drawing.Font font)
		{
			this.font = new Direct3D.Font(d3dDevice, font);
		}

		public Text(Direct3D.Device d3dDevice, System.Drawing.Font font, 
					string text, int x, int y, Color color)
		{
			this.font = new Direct3D.Font(d3dDevice, font);
			this.setVariables(text, x, y, color);
		}

		/**********************************************************************
		*
		*
		*  PUBLIC METHODS
		*
		*
		**********************************************************************/

		public void DrawText()
		{
			font.DrawText(null, this._text, new Rectangle(this._x, this._y, 1000, 1000), DrawTextFormat.None, this._color.ToArgb());
		}

		public void DrawText(string text, int x, int y, Color color)
		{
			this.setVariables(text, x, y, color);
			this.DrawText();
		}

		public void setVariables(string text, int x, int y, Color color)
		{
			this._text = text;
			this._x = x;
			this._y = y;
			this._color = color;
		}
		public string String
		{
			set { this._text = value; }
			get { return String.Copy(_text); }
		}
		public int posX
		{
			set { this._x = value; }
			get { return this._x; }
		}
		public int posY
		{
			set { this._y = value; }
			get { return this._y; }
		}
		public Color color
		{
			set { this._color = value; }
			get { return this._color; }
		}
	}
}
