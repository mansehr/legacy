
using System;
using Microsoft.DirectX.Direct3D;
using Direct3D = Microsoft.DirectX.Direct3D;

namespace Race
{
	/// <summary>
	///
	/// Summary description for CommonObjects.
	///
	/// </summary>
	public class CommonObjects
	{
		/**********************************************************************
		*
		*
		*  MEMBERS
		*
		*
		**********************************************************************/

		private Options				_options		= null;
		private Direct3D.Device		_d3dDevice		= null;
		private string				_rootPath		= string.Empty;

		/**********************************************************************
		*
		*
		*  PROPERTIES
		*
		*
		**********************************************************************/

		public Options Options
		{
			get { return _options; }
			set { _options = value; }
		}

		public Direct3D.Device D3DDevice
		{
			get { return _d3dDevice; }
			set { _d3dDevice = value; }
		}	

		public string RootPath
		{
			get { return _rootPath; }
			set { _rootPath = value; }
		}
	}
}