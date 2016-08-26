
using System;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;
using Microsoft.DirectX.DirectInput;
using Direct3D = Microsoft.DirectX.Direct3D;
using DirectInput = Microsoft.DirectX.DirectInput;

namespace Race
{
	/// <summary>
	///
	/// Summary description for Car.
	///
	/// </summary>
	public class Car
	{
		/**********************************************************************
		*
		*
		*  MEMBERS
		*
		*
		**********************************************************************/

		// Constants

		public const float	MAX_SPEED		= 2.0f;
		public const float	ACCELERATION	= 0.5f;
		public const float	MOVE_INTERVAL	= 0.01f; //10 miliseconds

		// Variables that describes the car

		private float		_weight;
		private float		_force;
		private float		_accel;
		private float		_speed;
		private float		_angle;
		private Vector3		_position;


		/**********************************************************************
		*
		*
		*  PROPERTIES
		*
		*
		**********************************************************************/

		public Vector3 Position
		{
			get { return _position; }
		}

		public float Angle
		{
			get { return _angle; }
		}

		/**********************************************************************
		*
		*
		*  CONSTRUCTORS
		*
		*
		**********************************************************************/
		
		public Car(float weight)
		{
			_force			= 0;
			_weight			= weight;
			_accel			= 0;
			_speed			= 0;
			_angle			= 0;
			_position		= new Vector3(0, 2, 0);
		}

		/**********************************************************************
		*
		*
		*  PUBLIC METHODS
		*
		*
		**********************************************************************/

		public void MoveForward()
		{
			float xDiff = (float)Math.Cos(_angle) * _speed;
			float zDiff = (float)Math.Sin(_angle) * _speed;

			_position = new Vector3(_position.X + xDiff, _position.Y, _position.Z + zDiff);
		}

		public void ControlCar(KeyboardState state, float time)
		{
			// Gasa/Accelerate
			if (state[Key.W])
			{
				if (_speed < 0)
				{
					_speed += 4*Car.ACCELERATION * time;
				}
				else
				{
					if (_speed <= Car.MAX_SPEED)
					{
						_speed += Car.ACCELERATION * time;
					}
				}
			}
			GameEngine.carDebugText.String = "Time: " + time.ToString() + "\nSpeed:" +_speed.ToString();
			
			// Break/Retardate
			if(state[Key.S])
			{
				if (_speed > 0)
				{
					_speed -= 4*Car.ACCELERATION * time;
				}
				else
				{
					if (_speed >= 0-Car.MAX_SPEED)
					{
						_speed -= Car.ACCELERATION * time;
					}
				}
			}

			// Retardata car when not driving
			if (!state[Key.W] && !state[Key.S])
			{
				if (_speed > 0)
				{
					_speed -= 0.5f*Car.ACCELERATION * time;
						
					if(_speed < 0)
					{
						_speed = 0;
					}
				}
				else if (_speed < 0)
				{
					_speed += 0.5f*Car.ACCELERATION * time;
						
					if(_speed > 0)
					{
						_speed = 0;
					}
				}
			}

			if(_speed != 0)
			{
				float roatation = 1.5f;

				// Turn left
				if (state[Key.A])
				{
					if (_speed > 0)
					{
						_angle += roatation * time;
					}
					else
					{
						_angle -= roatation * time;
					}
				}
				
				// Turn right
				if (state[Key.D])
				{
					if (_speed > 0)
					{
						_angle -= roatation * time;
					}
					else
					{
						_angle += roatation * time;
					}
				}
			}
		}

		/**********************************************************************
		*
		*
		*  PRIVATE METHODS
		*
		*
		**********************************************************************/
	}
}