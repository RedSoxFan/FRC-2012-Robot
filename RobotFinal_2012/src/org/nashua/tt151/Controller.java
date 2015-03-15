package org.nashua.tt151;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.KinectStick;

public abstract class Controller {
    public static interface ButtonListener {
        public void onPress(boolean opt);
        public void onRelease(boolean opt);
    }
    public static class State {
        public static final int OFF = 0;
        public static final int PRESSED = 1;
        public static final int RELEASED = 2;
    }
    private static final int V_IS_PRESSED=0;
    private static final int V_OPTION=1;
    private int[][] state;
    private ButtonListener[] listeners;
    private int slots=0;
    public Controller(int slots) {
        this.slots=slots;
        state = new int[2][slots];
        listeners = new ButtonListener[slots];
        // Query Buttons Every 1ms Starting In 1ms
        /*new java.util.Timer().schedule(new java.util.TimerTask(){
            public void run(){
                queryButtons();
            }
        },1,1);*/
    }
    public void addButtonListener(int button, ButtonListener pl){
        // Register ButtonListener For Button b
        listeners[button-1]=pl;
    }
    public void clearListeners(){
        // Clear Listeners
        listeners=new ButtonListener[12];
    }
    public void clearState(int button){
        // Turn Button button OFF
        state[V_IS_PRESSED][button-1]=State.OFF;
    }
    public void clearAll(){
        // Clear All Items
        clearListeners();
        for (int i=1;i<slots+1;i++) clearState(i);
    }
    public boolean getOption(int button){
        // Return The Option Of Button button
        return state[V_OPTION][button-1]==1;
    }
    public abstract double getRawAxis(int axis);
    public abstract boolean getRawButton(int button);
    public int getState(int button){
        // Return The State Of Button button
        return state[V_IS_PRESSED][button-1];
    }
    public boolean isPressed(int button){
        // Return If Button button Is Pressed
        return state[V_IS_PRESSED][button-1]==State.PRESSED;
    }
    public void removeButtonListener(int button){
        // Remove ButtonListener For Button button
        listeners[button-1]=null;
    }
    public void queryButtons(){
        // Query Button States
        for (int b=0;b<slots;b++){
            if (getRawButton(b+1)&&state[V_IS_PRESSED][b]!=State.PRESSED) {
                // pressed
                state[V_IS_PRESSED][b]=State.PRESSED;
                if (listeners[b]!=null) {
                    System.out.println(listeners[b]==null);
                    listeners[b].onPress(state[V_OPTION][b]==1);
                }
            } else if (!getRawButton(b+1)&&state[V_IS_PRESSED][b]==State.PRESSED) {
                // released
                state[V_IS_PRESSED][b]=State.RELEASED;
                state[V_OPTION][b]=state[V_OPTION][b]==0?1:0; // toggle option
                if (listeners[b]!=null) {
                   System.out.println(listeners[b]==null);
                   listeners[b].onRelease(state[V_OPTION][b]==1);
                }
            }
        }
    }
    public boolean wasReleased(int button){
        // Return If Button button Is Released
        boolean x=state[V_IS_PRESSED][button-1]==State.RELEASED;
        if (x) clearState(button);
        return x;
    }
    public static class DualAction extends Controller {
        public static class Axis extends Map.DualAction.Axis{}
        public static class Button extends Map.DualAction.Button{}
        private Joystick joy;
        public DualAction(int slot) {
            super(12);
            joy = new Joystick(slot);
        }
        public Joystick getJoystick(){
            return joy;
        }
        public double getRawAxis(int axis) {
            return joy.getRawAxis(axis);
        }
        public boolean getRawButton(int button) {
            return joy.getRawButton(button);
        }
        public double getLeftX(){
            return joy.getRawAxis(Axis.LEFT_X);
        }
        public double getLeftY(){
            return joy.getRawAxis(Axis.LEFT_Y);
        }
        public double getRightX(){
            return joy.getRawAxis(Axis.RIGHT_X);
        }
        public double getRightY(){
            return joy.getRawAxis(Axis.RIGHT_Y);
        }
        public double getPadX(){
            return joy.getRawAxis(Axis.DPAD_X);
        }
        public double getPadY(){
            return joy.getRawAxis(Axis.DPAD_Y);
        }
    }
    public static class MSKinect extends Controller {
        public static class Button extends Map.Kinect.Button{}
        public static final int LEFT_ARM = Map.Kinect.LEFT_ARM;
        public static final int RIGHT_ARM = Map.Kinect.RIGHT_ARM;
        private KinectStick joy;
        public MSKinect(int slot) {
            super(8);
            joy = new KinectStick(slot);
        }
        public KinectStick getJoystick(){
            return joy;
        }
        public double getRawAxis(int axis) {
            return joy.getRawAxis(axis);
        }
        public double getY() {
            return joy.getY();
        }
        public boolean getRawButton(int button) {
            return joy.getRawButton(button);
        }
        public boolean isEnabled(){
            return getRawButton(9);
        }
    }
}