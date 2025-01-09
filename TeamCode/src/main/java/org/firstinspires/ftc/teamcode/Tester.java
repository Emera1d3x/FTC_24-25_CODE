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
    CRServo servoWheel;
    // JOYSTICK VARS
    float leftStickX;
    float leftStickY;
    // REVERSE CONTROL VARS
    int reverseControl = 1;
    boolean reverseControlCanToggle = true;
    ElapsedTime toggleTimer = new ElapsedTime();
    int curPosition = 0;

    public void initializeStuff(){
        motorArm = hardwareMap.get(DcMotor.class, "motorArm"); // Pin #2
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLBWheel = hardwareMap.get(DcMotor.class, "motorLB"); // Pin #?
        motorRBWheel = hardwareMap.get(DcMotor.class, "motorRB"); // Pin #?
        servoWheel = hardwareMap.get(CRServo.class, "servoWheel"); // Pin #0
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
        servoWheel.setPower(0.0);
        // STICK VALUES
        leftStickX = gamepad1.left_stick_x; // Right (+), Left (-)
        leftStickY = -gamepad1.left_stick_y*reverseControl; // Up (+), Down (-)
        // EXPONENTIAL ACCELERATION
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
        } else if (gamepad1.dpad_up){
            motorRBWheel.setPower(0.07);
            motorLBWheel.setPower(-0.07);
        } else if (gamepad1.dpad_down){
            motorRBWheel.setPower(-0.07);
            motorLBWheel.setPower(0.07);
        } else if (gamepad1.dpad_right){
            motorRBWheel.setPower(0.07);
        } else if (gamepad1.dpad_left){
            motorLBWheel.setPower(-0.07);
        }
        // ARM CONTROL
        if (gamepad1.right_trigger>0){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(1);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad1.left_trigger>0){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(-1);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad1.right_bumper) {
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(0.25);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad1.left_bumper){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(-0.25);
            curPosition = motorArm.getCurrentPosition();
        } else {
            motorArm.setTargetPosition(curPosition);
            motorArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        telemetry.addData("Temu:", String.valueOf(motorArm.getCurrentPosition()) + curPosition);
        // CLAW CONTROL
        if (gamepad1.a){servoWheel.setPower(0.6);}
        if (gamepad1.b){servoWheel.setPower(-0.6);}
    }
}
