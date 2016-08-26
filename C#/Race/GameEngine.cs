
using System;
using System.Windows.Forms;
using System.Drawing;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;
using Microsoft.DirectX.DirectInput;
using Direct3D = Microsoft.DirectX.Direct3D;
using DirectInput = Microsoft.DirectX.DirectInput;

namespace Race
{
	/// <summary>
	///
	/// Summary description for GameEngine.
	///
	/// </summary>
	public class GameEngine
	{
		/**********************************************************************
		*
		*
		*  MEMBERS
		*
		*
		**********************************************************************/

		private CommonObjects commonObjects;


		// Car

		private Car			car;
		private Mesh		carMesh;
		private Material[]	carMaterials;
		private Texture[]	carTextures;

		// Camera

		private Vector3[]		cameraPosition = new Vector3[20];

		// Timer

		private float		elapsedTime;

		// Map

		private Mesh		mapMesh;
		private Material[]	mapMaterials;
		private Texture[]	mapTextures;

		// Debug Texts
		public static RickisDXLib.Text	debugText;
		public static RickisDXLib.Text	carDebugText;

		/**********************************************************************
		*
		*
		*  PROPERTIES
		*
		*
		**********************************************************************/

		/**********************************************************************
		*
		*
		*  CONSTRUCTORS
		*
		*
		**********************************************************************/

		public GameEngine(CommonObjects commonObjects)
		{
			this.commonObjects = commonObjects;

			// Load car
			car = new Car(1000);
			//carMesh = LoadMesh(Application.StartupPath + "\\Models\\Car.x", ref carMaterials, ref carTextures);
			carMesh = Mesh.Box(commonObjects.D3DDevice, 2, 1, 1);


			// Load map
			//mapMesh = LoadMesh(Application.StartupPath + "\\Models\\Map.x", ref mapMaterials, ref mapTextures);
			mapMesh = Mesh.Box(commonObjects.D3DDevice, 40, 1, 40);

			// Init variables
			elapsedTime	= 0;

			// Set Debugtext
			debugText = new RickisDXLib.Text(commonObjects.D3DDevice, new System.Drawing.Font("Arial", 14), "Hi", 10, 10, Color.Blue);
			carDebugText = new RickisDXLib.Text(commonObjects.D3DDevice, new System.Drawing.Font("Arial", 14), "Hi", 200, 10, Color.Blue);

		}

		/**********************************************************************
		*
		*
		*  PUBLIC METHODS
		*
		*
		**********************************************************************/

		public void GetUserInput(KeyboardState state)
		{
			elapsedTime += DXUtil.Timer(DirectXTimer.GetElapsedTime);

			if (elapsedTime >= Car.MOVE_INTERVAL)
			{
				car.ControlCar(state, elapsedTime);
				elapsedTime = 0;
				car.MoveForward();

				for(int i = 0; i < 19; i++)
				{
					cameraPosition[i] = cameraPosition[i+1];
				}

				cameraPosition[19] = car.Position;
				cameraPosition[19].Y += 5;

				float backXDiff = (float)Math.Cos(car.Angle) * 10;
				float backZDiff = (float)Math.Sin(car.Angle) * 10;

				//cameraPosition[19].X -= backXDiff;
				//cameraPosition[19].Z -= backZDiff;

				float fTemp = 0.9f;

				Vector3 tempVect = Vector3.Lerp(cameraPosition[19], car.Position, fTemp);
				debugText.String = fTemp.ToString() + " \nCarpos:\n " + car.Position.ToString() + " \nCamerapos:\n " + cameraPosition[19].ToString() + " \nTempVect:\n " + tempVect.ToString();
				
				//cameraPosition[0] = new Vector3(0,10,0);
				//float backXDiff = (float)Math.Cos(car.Angle) * 50;
				//float backZDiff = (float)Math.Sin(car.Angle) * 50;

				//cameraPosition = new Vector3(car.Position.X - backXDiff, car.Position.Y + 20, car.Position.Z - backZDiff);
				//cameraPosition = new Vector3(0, car.Position.Y + 20, 0);
			}
		}

		public void Render()
		{
			// Setup light
			if (commonObjects.D3DDevice.DeviceCaps.VertexProcessingCaps.SupportsPositionalLights)
			{
				float backXDiff = (float)Math.Cos(car.Angle) * 100;
				float backZDiff = (float)Math.Sin(car.Angle) * 100;

				commonObjects.D3DDevice.Lights[0].Type = LightType.Point;
				commonObjects.D3DDevice.Lights[0].Position = new Vector3(car.Position.X - backXDiff, car.Position.Y + 100, car.Position.Z - backZDiff);
				commonObjects.D3DDevice.Lights[0].Diffuse = Color.White;
				commonObjects.D3DDevice.Lights[0].Attenuation0 = 0.9f;
				commonObjects.D3DDevice.Lights[0].Range = 500.0f;
				//commonObjects.D3DDevice.Lights[0].Commit();
				commonObjects.D3DDevice.Lights[0].Enabled = true;
			}

			// Position camera
			commonObjects.D3DDevice.Transform.View = Matrix.LookAtLH(cameraPosition[0], new Vector3(car.Position.X, car.Position.Y, car.Position.Z), new Vector3(0, 1, 0));
			//debugText.String = cameraPosition[0].X.ToString();
			// Material
			Material material = new Material();			

			// Draw car
			material.Diffuse = material.Ambient = Color.YellowGreen;
			commonObjects.D3DDevice.Material = material;

			commonObjects.D3DDevice.Transform.World = Matrix.RotationY(-car.Angle) * Matrix.Translation(car.Position);
			DrawMesh(carMesh, carMaterials, carTextures);
			//carMesh.DrawSubset(0);
			
			const int _size = 8;

			for(int i = -_size; i < _size; i++)
			{
				for(int j = -_size; j < _size; j++)
				{
					// Draw map
					if(i % 2 == 0)
					{
						if(j % 2 == 0)
							material.Diffuse = material.Ambient = Color.Red;
						else
							material.Diffuse = material.Ambient = Color.Green;
					}
					else
					{
						if(j % 2 == 0)
							material.Diffuse = material.Ambient = Color.Green;
						else
							material.Diffuse = material.Ambient = Color.Red;
					}

					commonObjects.D3DDevice.Material = material;
					

					commonObjects.D3DDevice.Transform.World = Matrix.Translation(new Vector3(j*40, 0, i*40));
					//DrawMesh(mapMesh, mapMaterials, mapTextures);
					mapMesh.DrawSubset(0);
				}
			}

			debugText.DrawText();
			carDebugText.DrawText();


			commonObjects.D3DDevice.Transform.World = Matrix.Translation(new Vector3(0, 0, 0));

			CustomVertex.PositionColored[] points = new CustomVertex.PositionColored[2];
			points[0] = new CustomVertex.PositionColored(car.Position.X, car.Position.Y, car.Position.Z, Color.Blue.ToArgb());
			points[1] = new CustomVertex.PositionColored(cameraPosition[19].X, cameraPosition[19].Y, cameraPosition[19].Z, Color.Blue.ToArgb());
			commonObjects.D3DDevice.VertexFormat = CustomVertex.PositionColored.Format;
			commonObjects.D3DDevice.DrawUserPrimitives(PrimitiveType.LineList, 1, points);

			
		/*	Vector3[] tempVector = new Vector3[2];
			tempVector[0] = new Vector3(20,20,20);
			tempVector[1] = new Vector3(25,25,25);
			Line l = new Line(commonObjects.D3DDevice);
			l.Width = 3;
			l.Antialias = true;
			l.DrawTransform( tempVector, Matrix.Translation(0, 0, 0) ,Color.Black.ToArgb());*/

		}

		/**********************************************************************
		*
		*
		*  PRIVATE METHODS
		*
		*
		**********************************************************************/

		private Mesh LoadMesh(string path, ref Material[] materials, ref Texture[] textures)
		{
			ExtendedMaterial[] mtrl;

			Mesh mesh = Mesh.FromFile(path, MeshFlags.Managed, commonObjects.D3DDevice, out mtrl);

			if (mtrl != null && mtrl.Length > 0)
			{
				materials	= new Material[mtrl.Length];
				textures	= new Texture[mtrl.Length];

				for (int i = 0; i < mtrl.Length; i++)
				{
					materials[i] = mtrl[i].Material3D;
				}
			}

			return mesh;
		}

		private void DrawMesh(Mesh mesh, Material[] materials, Texture[] textures)
		{
			for (int i = 0; i < materials.Length; i++)
			{
				//commonObjects.D3DDevice.Material = materials[i];
				mesh.DrawSubset(i);
			}
		}
	}
}