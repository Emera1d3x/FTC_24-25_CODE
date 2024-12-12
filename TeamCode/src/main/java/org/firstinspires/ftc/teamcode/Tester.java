package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "CyberLyons")
public class Tester extends OpMode {
    // MATERIALS
    DcMotor motorArm;
    DcMotor motorLFWheel;
    DcMotor motorLBWheel;
    DcMotor motorRFWheel;
    DcMotor motorRBWheel;
    // JOYSTICK VARS
    float leftStickX;
    float leftStickY;
    float rightStickX;
    float rightStickY;


    public void initializeStuff(){
        //motorArm = hardwareMap.get(DcMotor.class, "motorArm");
        motorLBWheel = hardwareMap.get(DcMotor.class, "motorLB");
        motorRBWheel = hardwareMap.get(DcMotor.class, "motorRB");
        //clawServoRight = hardwareMap.get(Servo.class, "clawRight");
        //clawServoLeft = hardwareMap.get(Servo.class, "clawLeft");
    }

    @Override
    public void init() {
        initializeStuff();
        telemetry.addData("Launch Test:", "Successful");
        telemetry.update();
    }

    @Override
    public void loop() {
        // DEFAULTS
        //motorArm.setPower(0);
        motorLBWheel.setPower(0.0);
        motorRBWheel.setPower(0.0);
        // STICK VALUES
        leftStickX = gamepad1.left_stick_x; // Right (+), Left (-)
        leftStickY = -gamepad1.left_stick_y; // Up (+), Down (-)
        // MOVEMENT
        telemetry.addData("System Info", String.valueOf(leftStickX)+" "+String.valueOf(leftStickY));
        if (leftStickX<=0){
            motorRBWheel.setPower(leftStickY+Math.abs(leftStickX));
            motorLBWheel.setPower(-leftStickY);
        } else if (leftStickX>0){
            motorRBWheel.setPower(leftStickY);
            motorLBWheel.setPower(-leftStickY-leftStickX);
        }
        /*if (leftStickX>0){
            motorLBWheel.setPower(leftStickY);
            motorRBWheel.setPower((-leftStickY)+Math.abs(leftStickX));
        } else {
            motorLBWheel.setPower(leftStickY+Math.abs(leftStickX));
            motorRBWheel.setPower(-leftStickY);
        }*/
        // ARM CONTROL
        //if (gamepad1.right_bumper){motorArm.setPower(1);}
        //if (gamepad1.left_bumper){motorArm.setPower(-1);}
        // CLAW
    }
}
