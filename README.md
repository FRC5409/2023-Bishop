# **2023-Bishop**

## **Useful Documentation**
[CAN ID Spreadsheet](https://docs.google.com/spreadsheets/d/1NtnqaaMVDYO0TyJ946Wxg0dBtV19xBe5mVzWcAWxIAw/edit#gid=1456793576)

[Motors and Gearing Ratios - Bishop](https://docs.google.com/spreadsheets/d/1mly-FWH9S1RMrAUBcaXnyuavnCqU-cXk0Q0pLDEhZ-Y/edit#gid=1544976692)

[Motors and Gearing Ratios - Windsor](https://docs.google.com/spreadsheets/d/1FxBIIsZFDOvoKsso25b7TmFgGUk4gB1KhH03Lld9y3U/edit#gid=1544976692)

[Driveteam Button Mapping](https://docs.google.com/document/d/1LmwfAIl3pLnZguX8B4lljc1ZuzqiQKjrft7fehE6e5s/edit)

[Intake/Arm Setpoints](https://docs.google.com/document/d/1H3BSzr6iQv7rSsYxSoY_8pHKgct6spEGe-ud4h0Tcsw/edit?usp=sharing)

## **Button Bindings**

### Primary Controller

| Button | Action | Subsystem(s) | Execution | Notes |
| ------ | ------ | ------------ | --------- | ----- |
RT | Drive forward | DRV | Hold; depth-controlled |
LT | Drive backward | DRV | Hold; depth-controlled |
LB | Enable precision driving | DRV | Hold | Release to disable |
RB | Enable boosted driving | DRV | Hold | Release to disable |
LSB (left-right) | Turn left-right | DRV | Hold; depth-controlled |
A | Move intake to pickup position | ITK | Hold | Release to store cone |
B | Align to cone node | DRV | Hold |
Y | Capture cube | ELA | Hold | Release to let go of the cube |
X | Capture cone | ELA | Hold | Release to let go of the cone |
D-PAD UP | Test pivot upward | ITK | Hold | Testing only
D-PAD DOWN | Test pivot downward | ITK | Hold | Testing only

### Secondary Controller

| Button | Action | Subsystem(s) | Execution | Notes |
| ------ | ------ | ------------ | --------- | ----- |
| LB | Move and retract arm to idle position | ELA | Press once
| A | Move and extend arm to middle level | ELA | Press once
| B | Prepare and extend arm for single substation | ELA | Press once
| Y | Move and extend arm to high level | ELA | Press once
| X | Prepare and extend arm for double substation | ELA | Press once
| D-PAD UP | Extend arm | ELA | Press once |
| D-PAD DOWN | Retract arm | ELA | Press once |

*Button bindings are not finalized and are subject to change*

## **CAN ID's**
| CAN | CAN Bus | Component | Subsystem(s) |
|:---:|:--------|:----------|:-------------|
|2|rio|PDP|-
|3|rio|PCM|-
|10|rio|Pigeon 2.0|*DR*
|19|rio|CANdle|-
|20|drive|Falcon Left 1|*DR*, *LLR*
|21|drive|Falcon Left 2|*DR*, *LLR*
|22|drive|Falcon Left 3|*DR*, *LLR*
|22|rio|Shoulder spark-Max*|-
|23|drive|Falcon Right 1|*DR*, *LLR*
|24|drive|Falcon Right 2|*DR*, *LLR*
|24|rio|Elevator Spark-Max|*ELA*
|25|drive|Falcon Right 3|*DR*, *LLR*
|29|drive|CANCoder Right|*DR*
|30|drive|CANCoder Left|*DR*
|34|x|Falcon Intake 1|*ITK*
|28|x|Neo Intake 1|*ITK*
|x|x|Neo Intake 2|*ITK*

*CAN ID's are not finalized and are subject to change*

## **Subsystems**
|Abbreviation|Subsystem|Purpose| 
|:---:|:--------|:------|
|DRV|Drivetrain|Driving|
|ITK|Intake|Intake|
|LLA|LimeLight April-Tags|Vision|
|LLR|LimeLight Retro-Reflective|Vision|
|ELA|ElevatorArm|Telescopic Arm|
