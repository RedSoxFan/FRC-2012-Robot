package org.nashua.tt151;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SimpleRobot;
import java.util.TimerTask;
import org.nashua.tt151.Controller.DualAction;

public class Main extends SimpleRobot {

    public static final Jaguar
            fl=new Jaguar(Map.Jaguar.FRONT_LEFT),
            fr=new Jaguar(Map.Jaguar.FRONT_RIGHT),
            bl=new Jaguar(Map.Jaguar.BACK_LEFT),
            br=new Jaguar(Map.Jaguar.BACK_RIGHT),
            st=new Jaguar(Map.Jaguar.SHOOTER_TOP),
            sb=new Jaguar(Map.Jaguar.SHOOTER_BOTTOM),
            drop=new Jaguar(Map.Jaguar.ARM),
            pickup=new Jaguar(Map.Jaguar.PICKUP);
    public static final Servo yaw = new Servo(Map.Servo.YAW);
    public static final Relay led = new Relay(Map.LED_SPIKE);
    public static final DualAction driver = new DualAction(1);
    public static final DualAction shooter = new DualAction(2);
    private static Dashboard dash;
    public static final String host = "10.1.51.5";

    protected void robotInit() {
        led.setDirection(Relay.Direction.kForward);
        dash = new Dashboard(host,null);
        new java.util.Timer().schedule(new TimerTask(){
            public void run() {
                try {
                    dash.updatePWM("Output",fl.getChannel(),"Front Left",round(fl.getSpeed()));
                    dash.updatePWM("Output",fr.getChannel(),"Front Right",round(fr.getSpeed()));
                    dash.updatePWM("Output",bl.getChannel(),"Back Left",round(bl.getSpeed()));
                    dash.updatePWM("Output",br.getChannel(),"Back Right",round(br.getSpeed()));
                    dash.updatePWM("Output",st.getChannel(),"Shooter T",voltage(st.getSpeed()));
                    dash.updatePWM("Output",sb.getChannel(),"Shooter B",voltage(sb.getSpeed()));
                    dash.updatePWM("Output",drop.getChannel(),"Dropper",round(drop.getSpeed()));
                    dash.updatePWM("Output",pickup.getChannel(),"Pickup",round(pickup.getSpeed()));
                    dash.updatePWM("Output",yaw.getChannel(),"Yaw Servo",round(yaw.get()));
                    dash.updateInfo("LedOn?",Function.ledOn ? "ON" : "OFF");
                } catch (Exception e){
                    dash = new Dashboard(host,null);
                }
            }
            public String round(double num) {
                return ""+((int)(num*100))+"%";
            }
            public String voltage(double perc) {
                return ""+(((int)(perc*120))/10.0)+"V";
            }
        },1,100);
    }
    public void operatorControl() {
        driver.addButtonListener(DualAction.Button.LEFT_BUMPER, new Controller.ButtonListener() {
            public void onPress(boolean opt) {
                drop.set(opt?-0.1:0.1);
                System.out.println("LeftBumperPressed");
            }
            public void onRelease(boolean opt) {
                System.out.println("LeftBumperReleased");
                drop.set(0);
            }
        });
        while(isEnabled() && isOperatorControl()){
            driver.queryButtons();
            shooter.queryButtons();
            pickup.set(driver.getOption(DualAction.Button.RIGHT_BUMPER)?1:0);
            System.out.println(driver.getOption(DualAction.Button.RIGHT_BUMPER));
            Function.pollShooter();
            Function.softChange();
            Function.tankDrive();
        }
    }
}
