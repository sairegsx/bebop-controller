package edu.stanford.aa122.bebopcontroller.controller;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;

import edu.stanford.aa122.bebopcontroller.R;
import edu.stanford.aa122.bebopcontroller.drone.BebopDrone;
import edu.stanford.aa122.bebopcontroller.view.JoystickView;

/**
 * Class to handle the manual control of the Bebop drone.
 *
 * @author Adrien Perkins <adrienp@stanford.edu>
 */
public class ManualController {

    /** the view containing the manual control elements */
    private View mView;

    /** the drone to control */
    private BebopDrone mBebopDrone;

    /** throttle/yaw joystick */
    private JoystickView mLeftJoystick;

    /** roll/pitch joystick */
    private JoystickView mRightJoystick;

    /**
     * Constructor for manual controller for the Bebop Drone
     * @param drone the Bebop drone to control
     * @param v the view containing the manual control buttons
     */
    public ManualController(BebopDrone drone, View v) {
        mView = v;
        mBebopDrone = drone;

        setupView();
    }

    /**
     * Setup the view elements.
     */
    private void setupView() {
        mLeftJoystick = (JoystickView) mView.findViewById(R.id.joystick_left);
        mLeftJoystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onControlChanged(int x, int y) {
                mBebopDrone.setGaz((byte) -y);
                mBebopDrone.setYaw((byte) x);
            }
        });

        mRightJoystick = (JoystickView) mView.findViewById(R.id.joystick_right);
        mRightJoystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onControlChanged(int x, int y) {
                // need to tell the drone to listen to roll pitch commands (if present)
                if (x == 0 && y == 0) {
                    mBebopDrone.setFlag((byte) 0);
                } else {
                    mBebopDrone.setFlag((byte) 1);
                }
                mBebopDrone.setPitch((byte) -y);
                mBebopDrone.setRoll((byte) x);
            }
        });
    }


    public void setMaxThrottle(int percentage) {
        mLeftJoystick.setJoystickMaxYControl(percentage);
    }

    public void setMaxRotation(int percentage) {
        mLeftJoystick.setJoystickMaxXControl(percentage);
    }

    public void setMaxTilt(int percentage) {
        mRightJoystick.setJoystickMaxYControl(percentage);
        mRightJoystick.setJoystickMaxXControl(percentage);
    }

}
