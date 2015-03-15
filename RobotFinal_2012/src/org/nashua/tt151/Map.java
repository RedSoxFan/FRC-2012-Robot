package org.nashua.tt151;

public class Map {
    public static class DualAction {
        public static class Axis {
            public static final int LEFT_X = 1;
            public static final int LEFT_Y = 2;
            public static final int RIGHT_X = 3;
            public static final int RIGHT_Y = 4;
            public static final int DPAD_X = 5;
            public static final int DPAD_Y = 6;
        }
        public static class Button {
            public static final int X = 1;
            public static final int A = 2;
            public static final int B = 3;
            public static final int Y = 4;
            public static final int LEFT_BUMPER = 5;
            public static final int RIGHT_BUMPER = 6;
            public static final int LEFT_TRIGGER = 7;
            public static final int RIGHT_TRIGGER = 8;
            public static final int BACK = 9;
            public static final int SELECT = 10;
            public static final int LEFT_JOY_DOWN = 11;
            public static final int RIGHT_JOY_DOWN = 12;
        }
    }
    public static class Hoop {
        public static final int HIGHEST=0; // Highest Most Left Hop
        public static final int TOP=1; // Top Hoop
        public static final int MIDDLE_LEFT=2; // Middle Left Hoop
        public static final int MIDDLE=3; // Middle Most Left Hop
        public static final int MIDDLE_RIGHT=4; // Middle Right Hoop
        public static final int BOTTOM=5; // Bottom Hoop
        public static final int LOWEST=6; // Lowest Most Right Hoop
    }
    public static class Jaguar {
        public static final int SHOOTER_TOP=5;
        public static final int SHOOTER_BOTTOM=6;
        public static final int FRONT_RIGHT=2;
        public static final int FRONT_LEFT=1;
        public static final int BACK_RIGHT=4;
        public static final int BACK_LEFT=3;
        public static final int ARM=7;
        public static final int PICKUP=8;
    }
    public static class Kinect {
        public static final int LEFT_ARM=1;
        public static final int RIGHT_ARM=2;
        public static class Button {
            public static final int HEAD_LEFT=1;
            public static final int HEAD_RIGHT=2;
            public static final int RIGHT_LEG_FWD=5;
            public static final int RIGHT_LEG_OUT=3;
            public static final int RIGHT_LEG_BACK=6;
            public static final int LEFT_LEG_FWD=7;
            public static final int LEFT_LEG_OUT=4;
            public static final int LEFT_LEG_BACK=8;
        }
    }
    public static class Servo {
        public static final int YAW=10;
        public static class Pos {
            public static class Yaw {
                public static final double LEFT = 0.38;
                public static final double RIGHT = 0.62;
                public static final double DEF = 0.5;
            }
        }
    }
    /* DIGITAL OUTPUT */
    public static final int LED_SPIKE=1;
}