package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CyberLyons")
public class Tester extends OpMode {
    // MATERIALS
    DcMotor motorArm;
    DcMotor motorLBWheel;
    DcMotor motorRBWheel;
    CRServo clawServoLeft;
    CRServo clawServoRight;
    // JOYSTICK VARS
    float leftStickX;
    float leftStickY;
    // REVERSE CONTROL VARS
    int reverseControl = 1;
    boolean reverseControlCanToggle = true;
    ElapsedTime toggleTimer = new ElapsedTime();

    public void initializeStuff(){
        motorArm = hardwareMap.get(DcMotor.class, "motorArm"); // Pin #0
        motorLBWheel = hardwareMap.get(DcMotor.class, "motorLB"); // Pin #?
        motorRBWheel = hardwareMap.get(DcMotor.class, "motorRB"); // Pin #?
        clawServoRight = hardwareMap.get(CRServo.class, "servoRight"); // Pin #1
        clawServoLeft = hardwareMap.get(CRServo.class, "servoLeft"); // Pin #0
        toggleTimer.reset();
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
        motorArm.setPower(0);
        motorLBWheel.setPower(0.0);
        motorRBWheel.setPower(0.0);
        clawServoLeft.setPower(0.0);
        clawServoRight.setPower(0.0);
        // STICK VALUES
        leftStickX = gamepad1.left_stick_x; // Right (+), Left (-)
        leftStickY = -gamepad1.left_stick_y*reverseControl; // Up (+), Down (-)
        // EXPONENTIAL ACCELERATION
        leftStickX = (float) Math.pow(leftStickX, 3); // Cubic scaling for smooth acceleration
        leftStickY = (float) Math.pow(leftStickY, 3); // Cubic scaling for smooth acceleration
        // MOVEMENT CONTROL
        telemetry.addData("System Info", String.valueOf(leftStickX)+" "+String.valueOf(leftStickY));
        telemetry.addData("Reloader:", String.valueOf(toggleTimer.seconds()));
        telemetry.addData("Can Press:", String.valueOf(reverseControlCanToggle));
        if (leftStickX<=0){
            motorRBWheel.setPower(leftStickY+Math.abs(leftStickX));
            motorLBWheel.setPower(-leftStickY);
            if (toggleTimer.seconds() > 5) {reverseControlCanToggle = true;}
            if (gamepad1.y && reverseControlCanToggle){reverseControl*=-1;reverseControlCanToggle=false;toggleTimer.reset();}
        } else if (leftStickX>0){
            motorRBWheel.setPower(leftStickY);
            motorLBWheel.setPower(-leftStickY-leftStickX);
            if (toggleTimer.seconds() > 5) {reverseControlCanToggle = true;}
            if (gamepad1.y && reverseControlCanToggle){reverseControl*=-1;reverseControlCanToggle=false;toggleTimer.reset();}
        }
        // ARM CONTROL
        if (gamepad1.right_bumper){motorArm.setPower(1);}
        if (gamepad1.left_bumper){motorArm.setPower(-1);}
        if (gamepad1.a){clawServoLeft.setPower(1);clawServoRight.setPower(-1);}
        if (gamepad1.b){clawServoLeft.setPower(-1);clawServoRight.setPower(1);}
        // CLAW CONTROL
        if (gamepad1.a){clawServoLeft.setPower(1);clawServoRight.setPower(-1);}
        if (gamepad1.b){clawServoLeft.setPower(-1);clawServoRight.setPower(1);}
    }
}
