# 2023-Bishop (DEMO MODE)

![5409](./img/garthwebbrobotics_small.jpg)
![Bishop](./img/bishop_logo.png)

## Garth Webb Chargers - 5409

[The Blue Alliance](https://www.thebluealliance.com/team/5409)

[Website](https://sites.google.com/hdsb.ca/garthwebrobotics/home)

[GitHub](https://github.com/FRC5409)

[YouTube](https://www.youtube.com/@gwssrobotics5409)

[Twitter](https://twitter.com/gwssrobotics)


## Documentaton

[Button Bindings](#button-bindings)

[CAN IDs](#can-ids)

[Auto routines diagram](./doc/Auto%20Routines%20Diagram.pdf)

## Button Bindings

[Spreadsheet](https://docs.google.com/spreadsheets/d/16q68sTLZL_yzSjSJYOGjy_1UXNXGKpiWCxEIEpy729s/edit?usp=sharing)

**The secondary controller will not actually perform any actions.**

**The secondary driver must hold the button, and the action will be performed when the primary driver presses the "activate" button.**

| Button      | Controller | Function                                           | Subsystem(s)          | Action |
| :---------: | :--------: | :------------------------------------------------- | :-------------------- | :----: |
| LT          | Primary    | Decelerate/reverse                                 | Drivetrain            | Hold   |
| RT          | Primary    | Accelerate                                         | Drivetrain            | Hold   |
| LB          | Primary    |                                                    |                       |        |
| RB          | Primary    |                                                    |                       |        |
| LS-X        | Primary    | Rotation                                           | Drivetrain            | Hold   |
| LS-Y        | Primary    |                                                    |                       |        |
| LS-B        | Primary    |                                                    |                       |        |
| RS-X        | Primary    |                                                    |                       |        |
| RS-Y        | Primary    |                                                    |                       |        |
| RS-B        | Primary    |                                                    |                       |        |
| X           | Primary    | Auto-close claw for cone                           | Claw, Arm, Telescope  | Hold   |
| Y           | Primary    | Auto-close claw for cube                           | Claw, Arm, Telescope  | Hold   |
| A           | Primary    | Open claw                                          | Claw                  | Press  |
| B           | Primary    | "Activate" secondary controller button             | \*                    | \*     |
| D-PAD UP    | Primary    |                                                    |                       |        |
| D-PAD DOWN  | Primary    |                                                    |                       |        |
| D-PAD LEFT  | Primary    |                                                    |                       |        |
| D-PAD RIGHT | Primary    |                                                    |                       |        |
| START       | Primary    |                                                    |                       |        |
| BACK        | Primary    |                                                    |                       |        |
| LT          | Secondary  | Manual arm                                         |                       |        |
| RT          | Secondary  | Manual arm                                         |                       |        |
| LB          | Secondary  | Decrease max driving speed                         |                       |        |
| RB          | Secondary  | Increase max driving speed                         |                       |        |
| LS-X        | Secondary  |                                                    |                       |        |
| LS-Y        | Secondary  |                                                    |                       |        |
| LS-B        | Secondary  |                                                    |                       |        |
| RS-X        | Secondary  |                                                    |                       |        |
| RS-Y        | Secondary  |                                                    |                       |        |
| RS-B        | Secondary  |                                                    |                       |        |
| X           | Secondary  |                                                    |                       |        |
| Y           | Secondary  | Move arm and retract to resting on intake position | Arm, Telescope        | Press  |
| A           | Secondary  |                                                    |                       |        |
| B           | Secondary  | Move arm and retract to idling position            | Arm, Telescope        | Press  |
| D-PAD UP    | Secondary  | Move arm and extend to top cube position           | Arm, Telescope        | Press  |
| D-PAD DOWN  | Secondary  | Move arm and retract to resting on intake position | Arm, Telescope        | Press  |
| D-PAD LEFT  | Secondary  | Move arm and retract ABOVE mid cone node position  | Arm, Telescope        | Press  |
| D-PAD RIGHT | Secondary  | Move arm and retract to double substation          | Arm, Telescope        | Press  |
| START       | Secondary  | Stop drivetrain                                    | Drivetrain            | Press  |
| BACK        | Secondary  | Stop drivetrain                                    | Drivetrain            | Press  |

### Button Bindings Legend

| Key         | Value              |
| :---------: | :----------------: |
| LT          | Left trigger       |
| RT          | Right trigger      |
| LB          | Left bumper        |
| RB          | Right bumper       |
| LS-X        | Left stick x-axis  |
| LS-Y        | Left stick y-axis  |
| LS-B        | Left stick button  |
| RS-X        | Right stick x-axis |
| RS-Y        | Right stick y-axis |
| RS-B        | Right stick button |
| X           | X                  |
| Y           | Y                  |
| A           | A                  |
| B           | B                  |
| D-PAD UP    | D-Pad up           |
| D-PAD DOWN  | D-Pad down         |
| D-PAD LEFT  | D-Pad left         |
| D-PAD RIGHT | D-Pad right        |
| START       | Start              |
| BACK        | Back               |
| *empty*     | Unbound button     |


## CAN IDs

[Spreadsheet](https://docs.google.com/spreadsheets/d/1NtnqaaMVDYO0TyJ946Wxg0dBtV19xBe5mVzWcAWxIAw/edit?usp=sharing)

| Part                      | ID  | CAN Bus         |
| :------------------------ | :-: | :-------------: |
| PDH                       | 1   | rio             |
| Pigeon 2.0                | 10  | rio             |
| CANdle                    | 19  | rio             |
| Falcon Left 1             | 20  | rio             |
| Falcon Left 2             | 21  | rio             |
| Falcon Left 3             | 22  | rio             |
| Falcon Right 1            | 23  | rio             |
| Falcon Right 2            | 24  | rio             |
| Falcon Right 3            | 25  | rio             |
| CANCoder (Left)           | 29  | rio             |
| CANCoder (Right)          | 30  | rio             |
| Shoulder SparkMax 2       | 32  | rio             |
| Shoulder SparkMax 1       | 33  | rio             |
| Left Claw ToF             | 36  | rio             |
| Right Claw ToF            | 37  | rio             |
| Telescope SparkMax        | 38  | rio             |
| Claw Falcon               | 39  | rio             |
| Intake Roller Falcon      | 28  | rio             |
| Intake Wrist SparkMax     | 34  | rio             |
| Intake Pivot SparkMax     | 35  | rio             |
