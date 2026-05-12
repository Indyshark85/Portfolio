#region VEXcode Generated Robot Configuration
from vex import *
import urandom
import math

# The Brain object represents the VEX EXP brain (the main controller)
brain = Brain()

# ---------- Hardware Setup ----------
# Inertial sensor (IMU) on the brain – used here only to seed the random number generator
brain_inertial = Inertial()

# Motor 1 controls the SHOULDER joint (the long upper arm)
#   True = motor is reversed so that spinning "forward" physically lifts the shoulder
motor_1 = Motor(Ports.PORT1, True)

# Potentiometer on three-wire port A – reads the SHOULDER angle (theta1)
potent = PotentiometerV2(brain.three_wire_port.a)

# Limit switches are physical bump switches that trip when a joint reaches its travel boundary
# front_switch trips when the SHOULDER rotates too far forward
front_switch = Limit(brain.three_wire_port.b)
# back_switch trips when the SHOULDER rotates too far backward
back_switch  = Limit(brain.three_wire_port.c)

# front_switch_elb trips when the ELBOW rotates too far forward
front_switch_elb = Limit(brain.three_wire_port.d)

# Motor 6 controls the ELBOW joint (the short forearm)
#   False = motor is not reversed; spinning "forward" rotates the elbow downward
motor_6 = Motor(Ports.PORT6, False)

# back_switch_elb trips when the ELBOW rotates too far backward
back_switch_elb = Limit(brain.three_wire_port.e)

# Potentiometer on three-wire port F – reads the ELBOW angle (theta2)
potent_elb = PotentiometerV2(brain.three_wire_port.f)

# Servo on three-wire port G – controls the GRIPPER
#   set_position(50, DEGREES)  → open
#   set_position(-50, DEGREES) → closed (gripping)
servo_grip = Servo(brain.three_wire_port.g)

# Distance sensor on Port 10 – measures how far away the target object is (in inches)
distance_10 = Distance(Ports.PORT10)

# Wait for all sensors to initialize before reading them
wait(100, MSEC)


# ---------- Random Seed Initialization ----------
def initializeRandomSeed():
    """
    Seeds the random number generator using the current accelerometer
    readings and system timer so results differ on each power-on.
    Not used for control logic – included by default in VEXcode templates.
    """
    wait(100, MSEC)
    xaxis = brain_inertial.acceleration(XAXIS) * 1000
    yaxis = brain_inertial.acceleration(YAXIS) * 1000
    zaxis = brain_inertial.acceleration(ZAXIS) * 1000
    systemTime = brain.timer.system() * 100
    urandom.seed(int(xaxis + yaxis + zaxis + systemTime))

initializeRandomSeed()
#endregion

# ============================================================
#   Project:      VEX Robotic Arm – Inverse Kinematics Grasp
#   Description:  Reads distance to a target can, computes the
#                 required shoulder and elbow joint angles using
#                 the Law of Cosines, then drives both joints to
#                 those angles and closes the gripper.
# ============================================================

from vex import *
import math

# ---------- Global Motor Speed ----------
# Both motors run at 100% velocity; incremental spin_for calls are
# used to achieve fine-grained position control.
motor_1.set_velocity(100, PERCENT)
motor_6.set_velocity(100, PERCENT)


# ============================================================
# SECTION 1 – SENSOR READING
# ============================================================

def get_angles():
    """
    Reads both potentiometers and converts their raw degree values
    into physically meaningful joint angles.

    SHOULDER (theta1):
      Raw potentiometer value minus a calibration offset of 62°.
      theta1 > 90° means the shoulder is rotated forward (toward
      the distance sensor / front of the robot).
      theta1 = 90° corresponds to the arm pointing straight up.

    ELBOW (theta2):
      The elbow potentiometer does not move linearly from 0 to 360°;
      it sweeps symmetrically around a center value of 268°.
      Using the formula  180 - |268 - raw|  maps this onto a
      convention where:
        theta2 = 0   → forearm is straight (collinear with upper arm)
        theta2 > 0   → elbow is bent

    Returns:
        (theta1, theta2) both rounded to the nearest integer degree.
    """
    theta1 = potent.angle(DEGREES) - 62           # shoulder angle
    theta2 = 180 - abs(268 - potent_elb.angle(DEGREES))  # elbow angle
    return round(theta1), round(theta2)


# ============================================================
# SECTION 2 – FORWARD KINEMATICS
# ============================================================

# Physical arm dimensions (inches)
#   l1 = length of the upper arm (shoulder to elbow pivot)
#   l2 = length of the forearm  (elbow pivot to gripper)
# NOTE: l2 is set to 12.23 in the main loop; 11.23/6.77 in comments
#       below reflect an earlier hardware configuration.

def get_a_cart(l1, l2, t1, t2):
    """
    Forward kinematics: given joint angles, compute the (x, y)
    position of the gripper tip in the robot's reference frame.

    Coordinate convention:
      x = horizontal distance from the shoulder pivot (positive = forward)
      y = vertical height above the shoulder pivot  (positive = up)

    The key insight is that the elbow angle t2 is measured RELATIVE
    to the upper arm – not to the horizontal.  The forearm's absolute
    angle in the world frame is therefore:

        alpha = t2 - t1

    Upper arm endpoint:
        x1 = l1 * cos(t1)
        y1 = l1 * sin(t1)

    Forearm contribution (using world-frame angle alpha):
        x2 = l2 * cos(alpha)
        y2 = l2 * sin(alpha)

    When t1 > 90° the upper arm is tilted past vertical, so its
    x-component reverses sign.  The gripper x-position is then:
        x = -x1 + x2

    The sign of the y contribution from the forearm depends on
    whether the elbow bends upward or downward relative to the
    shoulder-to-gripper line, captured by comparing t2 to (180 - t1).

    Args:
        l1 (float): upper arm length (inches)
        l2 (float): forearm length (inches)
        t1 (int):   shoulder angle (degrees)
        t2 (int):   elbow angle (degrees)

    Returns:
        (x, y): gripper Cartesian coordinates (inches)
    """
    # alpha is the forearm's angle in the world frame
    alpha = t2 - t1

    # Convert degrees to radians for math.cos / math.sin
    a = (t1    / 180) * math.pi   # shoulder angle in radians
    b = (alpha / 180) * math.pi   # forearm world-angle in radians

    # Upper arm endpoint components
    x1 = math.cos(a) * l1
    y1 = math.sin(a) * l1

    # Forearm offset components
    x2 = math.cos(b) * l2
    y2 = math.sin(b) * l2

    x = y = 0  # default (only computed when t1 > 90)

    if t1 > 90:
        # Upper arm past vertical → x-component is negative
        x = -1 * x1 + x2

        if t2 > (180 - t1):
            # Elbow bent above the arm-to-gripper line → forearm y subtracts
            y = y1 - y2
        else:
            # Elbow bent below the arm-to-gripper line → forearm y adds
            y = y1 + y2

    return x, y


# ============================================================
# SECTION 3 – SAFETY / LIMIT SWITCH HANDLER
# ============================================================

def stopper():
    """
    Checks all four limit switches (shoulder front/back, elbow front/back).
    If any switch is currently pressed, the corresponding motor is nudged
    in the safe direction (away from the switch) until the switch releases.

    This function is called inside every closed-loop control while-loop
    to prevent the arm from forcing itself against a hard stop.

    Shoulder motors:
      front_switch pressed → motor_1 spins REVERSE (pulls arm back)
      back_switch pressed  → motor_1 spins FORWARD (pushes arm forward)

    Elbow motors:
      front_switch_elb pressed → motor_6 spins REVERSE
      back_switch_elb pressed  → motor_6 spins FORWARD
    """
    if front_switch.pressing():
        while front_switch.pressing():
            motor_1.spin_for(REVERSE, 10, DEGREES)
            print("throw it back shoulder")

    if back_switch.pressing():
        while back_switch.pressing():
            motor_1.spin_for(FORWARD, 10, DEGREES)
            print("throw it forward shoulder")

    if front_switch_elb.pressing():
        while front_switch_elb.pressing():
            motor_6.spin_for(REVERSE, 10, DEGREES)
            print("throw it back elbow")

    if back_switch_elb.pressing():
        while back_switch_elb.pressing():
            motor_6.spin_for(FORWARD, 10, DEGREES)
            print("throw it front elbow")


# ============================================================
# SECTION 4 – INVERSE KINEMATICS HELPER
# ============================================================

def get_the_bla2():
    """
    Re-computes the "wanted_angle" (the shoulder-triangle angle that
    determines how far the gripper is from the target) based on the
    CURRENT sensor readings and the previously measured reach distance.

    This function is called repeatedly inside the shoulder control loop
    to get a fresh angle estimate after each motor increment.

    The calculation uses the Law of Cosines on the triangle formed by:
        side a = l1  (upper arm)
        side b = read_distance  (desired horizontal reach)
        side c = l2  (forearm)

    The angle at the shoulder vertex of that triangle is alpha2:
        cos(alpha2) = (l1² + read_distance² - l2²) / (2 * l1 * read_distance)

    The "wanted_angle" represents the elevation angle of the reach
    vector above the table surface:
        wanted_angle = (180 - t1) - alpha2

    The control goal is to drive wanted_angle to ~9°, meaning the
    gripper approaches the can at a shallow ~9° downward angle.

    NOTE: read_distance, l1, and l2 are read from the enclosing
    scope (they are set in the main loop before this is called).

    Returns:
        wanted_angle (float): current approach elevation angle (degrees)
    """
    t1, t2 = get_angles()
    bla2    = (math.pow(l1, 2) + math.pow(read_distance, 2) - math.pow(l2, 2)) \
              / (2 * l1 * read_distance)
    rad2    = math.acos(bla2)                  # angle in radians
    alpha2  = rad2 * (180 / math.pi)           # convert to degrees
    wanted_angle = (180 - t1) - alpha2
    print("bla2 bla2")
    return wanted_angle


# ============================================================
# SECTION 5 – PRESET POSITION MOVER
# ============================================================

def OG(pos1, pos2):
    """
    Moves the arm to a preset configuration by independently driving
    each joint to a target angle using simple proportional bang-bang
    control (spin 40° increments until within tolerance).

    This is used to return the arm to the home/stow position after
    each grasp cycle (OG(70, 10)).

    Args:
        pos1 (int): desired shoulder angle from vertical, i.e. the arm
                    will be positioned so that t1 = (180 - pos1).
                    pos1 = 70 → t1 = 110° (arm tilted 70° forward of vertical)
        pos2 (int): desired value of (180 - t2), effectively the
                    "unfolded" elbow angle.
                    pos2 = 10 → elbow nearly straight.

    Control logic:
        Shoulder: spin FORWARD  if t1 < (180 - pos1)
                  spin REVERSE  if t1 > (180 - pos1)
        Elbow:    spin FORWARD  if (180 - t2) < pos2
                  spin REVERSE  if (180 - t2) > pos2
    """
    t1, t2 = get_angles()

    # --- Shoulder convergence ---
    while t1 < (180 - pos1):
        motor_1.spin_for(FORWARD, 40, DEGREES)
        t1, t2 = get_angles()
        print("forward shoulder")
    while t1 > (180 - pos1):
        motor_1.spin_for(REVERSE, 40, DEGREES)
        t1, t2 = get_angles()
        print("backward shoulder")

    # --- Elbow convergence ---
    while (180 - t2) < pos2:
        motor_6.spin_for(FORWARD, 10, DEGREES)
        t1, t2 = get_angles()
        print(180 - t2)
        print("forward elbow")
    while (180 - t2) > pos2:
        motor_6.spin_for(REVERSE, 10, DEGREES)
        t1, t2 = get_angles()
        print(180 - t2)
        print("backward elbow")


# ============================================================
# SECTION 6 – RELEASE / STOW SEQUENCE
# ============================================================

def has_can():
    """
    Called after successfully gripping a can.  Retracts the arm to a
    safe stow position and then opens the gripper to release the can.

    Sequence:
      1. Drive shoulder backward (decrease t1) until t1 <= 75°
         (arm is relatively upright / retracted).
      2. Drive elbow until (180 - t2) <= 5° (elbow nearly straight).
      3. Open gripper: servo_grip.set_position(50, DEGREES).

    The motion order (shoulder first, then elbow) prevents the gripper
    from sweeping through objects in front of the robot while retracting.
    """
    t1, t2 = get_angles()

    # Retract shoulder until t1 ≤ 75°
    while t1 > 75:
        motor_1.spin_for(REVERSE, 100, DEGREES)
        t1, t2 = get_angles()

    # Straighten elbow until near-zero bend
    while (180 - t2) > 5:
        motor_6.spin_for(REVERSE, 20, DEGREES)
        t1, t2 = get_angles()

    # Release the can
    print("releasing the can")
    servo_grip.set_position(50, DEGREES)


# ============================================================
# SECTION 7 – MAIN EXECUTION LOOP
# ============================================================

# Move arm to home position before starting grasp cycles
# OG(70, 10): shoulder at 70° from vertical, elbow nearly straight
OG(70, 10)

cans = 3   # grasp counter (loops while cans <= 3, i.e., exactly once then exits)

while cans <= 3:

    # --- 7.1  Open gripper and read target distance ---
    servo_grip.set_position(50, DEGREES)   # ensure gripper is open
    dis = distance_10.object_distance(INCHES)
    print("distance: ", dis)

    # Add 5" for sensor-to-shoulder offset and 2" for half-can width
    read_distance = dis + 5 + 2
    print(read_distance)

    # Link lengths used throughout the kinematics calculations
    l1 = 11.23   # upper arm length (inches)
    l2 = 12.23   # forearm length   (inches)

    # Read current joint angles for logging
    t1, t2 = get_angles()
    print(t1, " + ", t2)

    # Compute current gripper Cartesian position (for debug only)
    x, y = get_a_cart(l1, l2, t1, t2)
    print(x, " + ", y)

    # --- 7.2  Inverse kinematics – ELBOW angle ---
    # Use the Law of Cosines to find the elbow angle that places the
    # gripper tip exactly at the target reach distance:
    #
    #   cos(theta2) = (l1² + l2² - d²) / (2 * l1 * l2)
    #
    # where d = read_distance (the desired reach to the can center)
    bla = (math.pow(l1, 2) + math.pow(l2, 2) - math.pow(read_distance, 2)) \
          / (2 * l1 * l2)
    rad   = math.acos(bla)                  # elbow angle in radians
    alpha = rad * (180 / math.pi)           # convert to degrees
    rounded = round(alpha)                  # integer target for control

    # --- 7.3  Elbow convergence loop ---
    # Increment/decrement elbow in 10° steps until within ±2° of target
    while abs(t2 - rounded) > 2:
        stopper()   # check limit switches each iteration

        if t2 > rounded:
            motor_6.spin_for(FORWARD, 10, DEGREES)
        elif t2 < rounded:
            motor_6.spin_for(REVERSE, 10, DEGREES)

        t1, t2 = get_angles()   # re-read after each motor step

    print("I got the elbow twin")

    # --- 7.4  Inverse kinematics – SHOULDER angle ---
    # Now solve for the shoulder angle that aligns the arm along the
    # reach vector toward the can.
    #
    # Second Law of Cosines application (triangle: shoulder, elbow, target):
    #   cos(alpha2) = (l1² + d² - l2²) / (2 * l1 * d)
    #
    # alpha2 is the angle at the SHOULDER vertex of this triangle.
    # The "wanted_angle" is the elevation of the final reach vector
    # above horizontal:
    #   wanted_angle = (180 - t1) - alpha2
    #
    # Target: wanted_angle ≈ 9° (shallow approach angle to the can)
    t1, t2 = get_angles()
    bla2   = (math.pow(l1, 2) + math.pow(read_distance, 2) - math.pow(l2, 2)) \
             / (2 * l1 * read_distance)
    rad2   = math.acos(bla2)
    alpha2 = rad2 * (180 / math.pi)
    wanted_angle = (180 - t1) - alpha2
    print(wanted_angle)

    # Early exit: if already within 2° of target, skip shoulder loop
    if round(wanted_angle) == 7:
        break

    # --- 7.5  Shoulder convergence loop ---
    # Spin 50° increments until wanted_angle ≈ 9°
    while round(wanted_angle) > 9:
        stopper()
        motor_1.spin_for(FORWARD, 50, DEGREES)
        print("spinned 50 degrees")
        wanted_angle = get_the_bla2()    # re-compute with fresh sensor data
        print(wanted_angle)

    while round(wanted_angle) < 9:
        stopper()
        motor_1.spin_for(REVERSE, 50, DEGREES)
        print("spinned 50 degrees")
        wanted_angle = get_the_bla2()
        print(wanted_angle)

    print("i got the point twin")

    # --- 7.6  Grasp sequence ---
    servo_grip.set_position(-50, DEGREES)  # close gripper onto the can

    # Retract arm and release can at stow position
    has_can()

    # Return to home position for next cycle
    OG(70, 10)

    cans += 1  # increment counter

    # Play celebratory sound after the final grasp
    if cans >= 3:
        brain.play_sound(SoundType.TADA)