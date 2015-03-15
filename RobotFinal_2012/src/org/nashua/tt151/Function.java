package org.nashua.tt151;

import edu.wpi.first.wpilibj.Relay.Value;
import org.nashua.tt151.Controller.DualAction;

public class Function {
    private static double current=0, target=0;
    public static boolean ledOn = false;
    private Function(){}
    public static void tankDrive(){
        Main.fl.set(-Main.driver.getLeftY());
        Main.fr.set(-Main.driver.getRightY());
        Main.bl.set(Main.fl.get());
        Main.br.set(Main.fr.get());
    }
    public static void softChange(){
       current+=(target-current)/100.0;
       Main.sb.set(-current/12.0);
       Main.st.set(-current/12.0);
    }
    public static void pollShooter(){
        if(Main.shooter.wasReleased(DualAction.Button.RIGHT_JOY_DOWN)) target=0;
        if(Main.shooter.wasReleased(DualAction.Button.A)) target=5.0;
        if(Main.shooter.wasReleased(DualAction.Button.B)) target=5.5;
        if(Main.shooter.wasReleased(DualAction.Button.X)) target=6.0;
        if(Main.shooter.wasReleased(DualAction.Button.Y)) target=6.5;

        if(Main.shooter.wasReleased(DualAction.Button.LEFT_TRIGGER)) target-=1;
        if(Main.shooter.wasReleased(DualAction.Button.RIGHT_TRIGGER)) target+=1;
        if(Main.shooter.wasReleased(DualAction.Button.LEFT_BUMPER)) target-=0.1;
        if(Main.shooter.wasReleased(DualAction.Button.RIGHT_BUMPER)) target+=0.1;

        if(Main.shooter.wasReleased(DualAction.Button.BACK)) {
            Main.led.set(Value.kOff);
            ledOn = false;
        }
        if(Main.shooter.wasReleased(DualAction.Button.SELECT)) {
            Main.led.set(Value.kOn);
            ledOn = true;
        }

        Main.yaw.setPosition((Main.shooter.getLeftX()+1)/2.0*(Map.Servo.Pos.Yaw.RIGHT-Map.Servo.Pos.Yaw.LEFT)+Map.Servo.Pos.Yaw.LEFT);
    }
}