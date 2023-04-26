![Bilby Stampede](https://cdn.discordapp.com/attachments/794885191898365975/1081016440905277460/bishopLogo_3.png)

## Useful Documentation
[CAN ID Spreadsheet](https://docs.google.com/spreadsheets/d/1NtnqaaMVDYO0TyJ946Wxg0dBtV19xBe5mVzWcAWxIAw/edit#gid=1456793576)

[Motors and Gearing Ratios - Bishop](https://docs.google.com/spreadsheets/d/1mly-FWH9S1RMrAUBcaXnyuavnCqU-cXk0Q0pLDEhZ-Y/edit#gid=1544976692)

[Motors and Gearing Ratios - Windsor](https://docs.google.com/spreadsheets/d/1FxBIIsZFDOvoKsso25b7TmFgGUk4gB1KhH03Lld9y3U/edit#gid=1544976692)

[Driveteam button mapping](https://docs.google.com/document/d/1LmwfAIl3pLnZguX8B4lljc1ZuzqiQKjrft7fehE6e5s/edit)

[Auto routines diagram](./doc/Auto%20Routines%20Diagram.pdf)

## Libraries 
[LimelightHelpers](https://github.com/LimelightVision/limelightlib-wpijava)

[Pheonix](https://store.ctr-electronics.com/software/)

[Rev](https://docs.revrobotics.com/sparkmax/software-resources/spark-max-api-information)

## Button Bindings
<p align="center"><b>Comp Configuration<b><p>

| Binding | Controller | Subsystem/Command | Function | Toggle/Push | 
|:-------:|:----------:|:------------------|:---------|:-----------:|
|RT|1|Drivetrain|Accelerate|Push|
|LT|1|Drivetrain|Reverse|Push|
|LB|1|Drivetrain|Precision mode|Push|
|RB|1|Drivetrain|Precision mode|Push|
|LSB-X|1|Drivetrain|Left-Right|Push|
|LB|2|Limelight|Target node|Push|
|DPAD-UP|2|Arm|Place cube top|Push|
|DPAD-DOWN|2|Arm|Place cube bottom|Push|
|X|1|Arm|Grab loading zone|Push|
|A|1|Arm,Intake|Intake handoff|Push|


<p align="center"><b>Demo Configuration<b><p>

| Binding | Controller | Subsystem/Command | Function |
|:-------:|:----------:|:------------------|:---------|
|Y|TBD|Intake|Pivot up|
|A|TBD|Intake|Pivot down|
|X|TBD|Intake|Roller inwards|
|B|TBD|Intake|Roller outwards|


## CAN IDs

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
| Elevator SparkMax         | 38  | rio             |
| Falcon Claw               | 39  | rio             |
| Intake Roller Falcon      | 28  | rio             |
| Left ToF Claw             | 36  | rio             |
| Right ToF Claw            | 37  | rio             |
| Intake Wrist SparkMax     | 34  | rio             |
| Intake Pivot SparkMax     | 35  | rio             |
| Shoulder spark max        | 33  | rio             |
| Shoulder spark max        | 32  | rio             |


## Subsystems
|Abbrv|Subsystem|Purpose| 
|:---:|:--------|:------|
|DRV|Drivetrain|Driving|
|ITK|Intake|Intake|
|LLA|LimeLight April-Tags|V/ision|
|LLR|LimeLight Retro-Reflective|Vision|
|ELA|ElevatorArm|Telescopic Arm|

<p align="center">(<a href="#readme-top">back to top</a>)</p>
