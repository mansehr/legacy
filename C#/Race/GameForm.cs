
using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;
using Microsoft.DirectX.DirectInput;
using Direct3D = Microsoft.DirectX.Direct3D;
using DirectInput = Microsoft.DirectX.DirectInput;

namespace Race
{
	/// <summary>
	/// 
	/// This is the main form in the game, and it will handle all DirectX components.
	/// 
	/// </summary>
	public class GameForm : System.Windows.Forms.Form
	{
		/**********************************************************************
		*
		*
		*  MEMBERS
		*
		*
		**********************************************************************/

		// Constants

		public const int				WIDTH	= 800;
		public const int				HEIGHT	= 600;

		// DirectX device variables

		private Direct3D.Device			d3dDevice				= null;
		private PresentParameters		presentationParameters	= null;
		private bool					d3dDeviceLost			= false;

		private DirectInput.Device		diDevice				= null;

		//

		private Options					options					= new Options(Application.StartupPath + "\\Options.txt");

		private CommonObjects			commonObjects			= null;

		private ActiveDialog			activeDialog			= ActiveDialog.Playing;

		private GameEngine				gameEngine				= null;

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

		public GameForm()
		{
			// Set form properties that will only affect the windowed form
			this.ClientSize			= new System.Drawing.Size(GameForm.WIDTH, GameForm.HEIGHT);
			this.Name				= "GameForm";
			this.StartPosition		= FormStartPosition.CenterScreen;
			this.Text				= "Race";
			this.FormBorderStyle	= FormBorderStyle.FixedDialog;
			this.MaximizeBox		= false;

			// Make sure that all painting occours inside the paint event
			this.SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.Opaque, true);
		}

		/**********************************************************************
		*
		*
		*  ENTRY POINT AND DISPOSE METHOD
		*
		*
		**********************************************************************/

		/// <summary>
		/// The main entry point for the application.
		/// </summary>
		[STAThread]
		static void Main() 
		{
			using (GameForm game = new GameForm())
			{
				game.Show();

				if (game.InitializeDirect3D() && game.InitializeDirectInput())
				{
					Application.Run(game);
				}
				else
				{
					MessageBox.Show("Unable to init DirectX.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
				}
			}
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			base.Dispose( disposing );
		}

		/**********************************************************************
		*
		*
		*  INITIALIZATION METHODS
		*
		*
		**********************************************************************/

		public bool InitializeDirect3D()
		{
			try
			{
				presentationParameters = new PresentParameters();
				
				presentationParameters.Windowed					= options.Windowed;
				presentationParameters.SwapEffect				= SwapEffect.Discard;
				presentationParameters.EnableAutoDepthStencil	= true;
				presentationParameters.AutoDepthStencilFormat	= DepthFormat.D16;

				if (!presentationParameters.Windowed)
				{
					presentationParameters.Windowed				= false;
					presentationParameters.BackBufferCount		= 1;
					presentationParameters.BackBufferFormat		= Direct3D.Manager.Adapters.Default.CurrentDisplayMode.Format;
					presentationParameters.BackBufferWidth		= GameForm.WIDTH;
					presentationParameters.BackBufferHeight		= GameForm.HEIGHT;
				}

				// Get device index of the default device
				int adapterOrdinal = Direct3D.Manager.Adapters.Default.Adapter;

				// Get caps object so we can determine if the device support specific things
				Direct3D.Caps caps = Direct3D.Manager.GetDeviceCaps(adapterOrdinal, Direct3D.DeviceType.Hardware);

				// Get the create flags ready
				CreateFlags createFlags;

				if (caps.DeviceCaps.SupportsHardwareTransformAndLight)
				{
					createFlags = CreateFlags.HardwareVertexProcessing;
				}
				else
				{
					createFlags = CreateFlags.SoftwareVertexProcessing;
				}

				if (caps.DeviceCaps.SupportsPureDevice)
				{
					createFlags |= CreateFlags.PureDevice;
				}

				// Creat the device and hook up some events
				d3dDevice = new Direct3D.Device(0, Direct3D.DeviceType.Hardware, this, createFlags, presentationParameters);

				d3dDevice.DeviceReset		+= new EventHandler(d3dDevice_DeviceReset);
				d3dDevice.DeviceLost		+= new EventHandler(d3dDevice_DeviceLost);
				d3dDevice.DeviceResizing	+= new CancelEventHandler(d3dDevice_DeviceResizing);
				
				d3dDevice_DeviceReset(d3dDevice, null);

				InitializeObjects();

				// Direct3D was successfully initialized - return true
				return true;
			}
			catch (Exception)
			{
				// Direct3D could not be initialized - return false
				return false;
			}
		}

		private void d3dDevice_DeviceReset(object sender, EventArgs e)
		{
			// Setup camera
			d3dDevice.Transform.Projection	= Matrix.PerspectiveFovLH((float)Math.PI/4, this.Width/this.Height, 1.0f, 1000.0f);
			d3dDevice.Transform.View		= Matrix.LookAtLH(new Vector3(0, 80, -100), new Vector3(0, 20, 0), new Vector3(0, 1, 0));

			

			// Set the light in the overall scene to be white
			//d3dDevice.RenderState.Ambient = Color.White;

			// Texture rendering properties
			if (d3dDevice.DeviceCaps.TextureFilterCaps.SupportsMinifyAnisotropic)
			{
				d3dDevice.SamplerState[0].MinFilter = TextureFilter.Anisotropic;
			}
			else if (d3dDevice.DeviceCaps.TextureFilterCaps.SupportsMinifyLinear)
			{
				d3dDevice.SamplerState[0].MinFilter = TextureFilter.Linear;
			}

			if (d3dDevice.DeviceCaps.TextureFilterCaps.SupportsMagnifyAnisotropic)
			{
				d3dDevice.SamplerState[0].MagFilter = TextureFilter.Anisotropic;
			}
			else if (d3dDevice.DeviceCaps.TextureFilterCaps.SupportsMagnifyLinear)
			{
				d3dDevice.SamplerState[0].MagFilter = TextureFilter.Linear;
			}
		}

		private void d3dDevice_DeviceLost(object sender, EventArgs e)
		{
			// Dispose any vertext buffers and Pool.Default
		}

		private void d3dDevice_DeviceResizing(object sender, CancelEventArgs e)
		{
			// Automatic resizing must be disabled in order for the recovery
			// process to work in fullscreen mode
			e.Cancel = true;
		}

		private void AttemptRecovery()
		{
			try
			{
				d3dDevice.TestCooperativeLevel();
			}
			catch (DeviceLostException)
			{
				System.Threading.Thread.Sleep(500);
			}
			catch (DeviceNotResetException)
			{
				try
				{
					d3dDevice.Reset(presentationParameters);
					d3dDeviceLost = false;
				}
				catch (DeviceLostException)
				{
				}
			}
		}

		public bool InitializeDirectInput()
		{
			try
			{
				diDevice = new DirectInput.Device(SystemGuid.Keyboard);
				diDevice.SetCooperativeLevel(this, CooperativeLevelFlags.Background | CooperativeLevelFlags.NonExclusive);
				diDevice.Acquire();

				return true;
			}
			catch (Exception)
			{
				return false;
			}
		}

		public void InitializeObjects()
		{
			// Prepare common objects
			commonObjects = new CommonObjects();

			commonObjects.Options	= options;
			commonObjects.D3DDevice	= d3dDevice;

			// Fix dialogs
			gameEngine = new GameEngine(commonObjects);
		}

		/**********************************************************************
		*
		*
		*  RENDER METHOD
		*
		*
		**********************************************************************/

		protected override void OnPaint(PaintEventArgs e)
		{
			if (d3dDeviceLost)
			{
				AttemptRecovery();

				if (d3dDeviceLost)
				{
					return;
				}
			}
			
			GetUserInput();

			d3dDevice.Clear(ClearFlags.Target | ClearFlags.ZBuffer, Color.White.ToArgb(), 1.0f, 0);
			d3dDevice.BeginScene();

			switch (activeDialog)
			{
				case ActiveDialog.MainMenu:
					break;
				case ActiveDialog.Playing:
					gameEngine.Render();
					break;
			}
			
			d3dDevice.EndScene();

			try
			{
				d3dDevice.Present();
			}
			catch (DeviceLostException)
			{
				d3dDeviceLost = true;
			}

			// Causes the render metohd to fire again
			this.Invalidate();
		}

		/**********************************************************************
		*
		*
		*  INPUT METHODS
		*
		*
		**********************************************************************/

		private void GetUserInput()
		{
			KeyboardState state = diDevice.GetCurrentKeyboardState();
			
			if (state[Key.F1])
			{
				SwtichDisplayMode();
			}

			switch (activeDialog)
			{
				case ActiveDialog.MainMenu:
					break;
				case ActiveDialog.Playing:
					gameEngine.GetUserInput(state);
					break;
			}
		}

		/**********************************************************************
		*
		*
		*  PRIVATE METHODS
		*
		*
		**********************************************************************/

		private void SwtichDisplayMode()
		{
			if (d3dDevice != null)
			{
				d3dDevice.Dispose();
			}

			options.Windowed = !options.Windowed;

			if (options.Windowed)
			{
				this.FormBorderStyle = FormBorderStyle.FixedDialog;
				this.TopMost = false;
			}

			if (!InitializeDirect3D())
			{
				MessageBox.Show("Unable to swithc mode.");
			}
		}
	}
}