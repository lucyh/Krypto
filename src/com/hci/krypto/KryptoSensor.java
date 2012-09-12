package com.hci.krypto;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

/* **
 * http://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-gyro
 * http://www.touchqode.com/misc/20101025_jsug/20101025_touchqode_sensors.pdf
 */
public class KryptoSensor
{
	// Create a constant to convert nanoseconds to seconds.
	private static final float NS2S = 1.0f / 1000000000.0f;
	private static final float EPSILON = 45.0f;
	private final float[] deltaRotationVector = new float[4];
	private float timestamp;
	private SensorManager   mSensorManager;
	private Sensor 			mSensor;
//	private KryptoSensor				ks;
//	private SensorManager   			sm;
//	private Sensor 						sen;
//	private SensorEventListener 		listener;



	public KryptoSensor( SensorManager mSensorManager, Sensor mSensor )
	{
        this.mSensorManager = mSensorManager;
        this.mSensor = mSensor;
//      sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//  	  sm.registerListener(listener, sen, SensorManager.SENSOR_DELAY_NORMAL);
//  	  sen = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//  	  sm.registerListener((SensorEventListener) this, sen, SensorManager.SENSOR_DELAY_NORMAL);
//      ks = new KryptoSensor( sm, sen );
      
        
	}

	@SuppressLint({ "FloatMath", "FloatMath" })
	@TargetApi(9)
	public void onSensorChanged(SensorEvent event) 
	{
	  // This timestep's delta rotation to be multiplied by the current rotation
	  // after computing it from the gyro sample data.
	  if (timestamp != 0) {
	    final float dT = (event.timestamp - timestamp) * NS2S;
	    // Axis of the rotation sample, not normalized yet.
	    float axisX = event.values[0];
	    float axisY = event.values[1];
	    float axisZ = event.values[2];

	    // Calculate the angular speed of the sample
	    float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

	    // Normalize the rotation vector if it's big enough to get the axis
	    // (that is, EPSILON should represent your maximum allowable margin of error)
	    if (omegaMagnitude > EPSILON) {
	      axisX /= omegaMagnitude;
	      axisY /= omegaMagnitude;
	      axisZ /= omegaMagnitude;
	    }

	    // Integrate around this axis with the angular speed by the timestep
	    // in order to get a delta rotation from this sample over the timestep
	    // We will convert this axis-angle representation of the delta rotation
	    // into a quaternion before turning it into the rotation matrix.
	    float thetaOverTwo = omegaMagnitude * dT / 2.0f;
	    float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
	    float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
	    deltaRotationVector[0] = sinThetaOverTwo * axisX;
	    deltaRotationVector[1] = sinThetaOverTwo * axisY;
	    deltaRotationVector[2] = sinThetaOverTwo * axisZ;
	    deltaRotationVector[3] = cosThetaOverTwo;
	  }
	  timestamp = event.timestamp;
	  float[] deltaRotationMatrix = new float[9];
	  SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
	    // User code should concatenate the delta rotation we computed with the current rotation
	    // in order to get the updated rotation.
	    // rotationCurrent = rotationCurrent * deltaRotationMatrix;
   }
}
