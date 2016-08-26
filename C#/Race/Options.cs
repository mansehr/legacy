
using System;
using System.IO;
using System.Collections;

namespace Race
{
	/// <summary>
	///
	/// Summary description for Options.
	///
	/// </summary>
	public class Options
	{
		/**********************************************************************
		*
		*
		*  MEMBERS
		*
		*
		**********************************************************************/

		private string		path;
		private Hashtable	options;

		/**********************************************************************
		*
		*
		*  PROPERTIES
		*
		*
		**********************************************************************/

		public bool Windowed
		{
			get
			{
				object option = GetOption("Windowed");

				if (option == null)
				{
					return true;
				}
				else
				{
					return Convert.ToBoolean(option);
				}
			}
			set
			{
				SetOption("Windowed", value.ToString());
				Save();
			}
		}

		/**********************************************************************
		*
		*
		*  CONSTRUCTORS
		*
		*
		**********************************************************************/
		
		public Options(string path)
		{
			this.path = path;

			LoadOptions();
		}

		/**********************************************************************
		*
		*
		*  PUBLIC METHODS
		*
		*
		**********************************************************************/

		public object GetOption(string option)
		{
			return options[option];
		}

		public void SetOption(string key, string optionValue)
		{
			if (options[key] != null)
			{
				options[key] = optionValue;
			}
			else
			{
				options.Add(key, optionValue);
			}
		}

		public void Save()
		{
			StreamWriter writer = new StreamWriter(path);

			IDictionaryEnumerator enumerator = options.GetEnumerator();

			while (enumerator.MoveNext())
			{
				writer.Write(enumerator.Key + "=" + enumerator.Value);
			}

			writer.Close();
		}

		/**********************************************************************
		*
		*
		*  PRIVATE METHODS
		*
		*
		**********************************************************************/

		private void LoadOptions()
		{
			options	= new Hashtable();

			if (File.Exists(path))
			{
				StreamReader	reader = new StreamReader(path);
				string			line;
				int				equalPos;

				while ((line = reader.ReadLine()) != null)
				{
					line.Replace(" ", "");
					equalPos = line.IndexOf("=");

					options.Add(line.Substring(0, equalPos), line.Substring(equalPos + 1));
				}

				reader.Close();
			}
		}
	}
}