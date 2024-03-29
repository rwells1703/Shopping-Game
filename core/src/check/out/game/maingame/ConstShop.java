package check.out.game.maingame;

public class ConstShop {
    public static final int //Effect priorities - called in ascending order.
        EP_AI_KEYBOARD =        0x0010_0100,
        EP_SLIPPERY_ICE =       0x0020_0100,
        EP_GLOOPY_BEANS =       0x0020_0110,
        EP_MOVE_SHOPPERS =      0x0030_0100,
        EP_LAUNCH_PROJECTILE =  0x0040_0100,
        EP_PHYSICS_STEP =       0x0050_0100,
        EP_GRAVITY =            0x0050_0200,
        EP_ACTION_ON_LANDING =  0x0050_0300,
        EP_SPOTLIGHT =          0x0070_0100,
        EP_DRAW =               0x0070_0200;

    public static final int //Artist priorities - called in ascending order.
        AP_SCREEN_CLEAR =       0x1000,
        AP_FLOOR_DRAW =         0x1400,
        AP_FLOOR_HAZARDS =      0x1800,
        AP_SHELVING_DRAW =      0x2000,
        AP_HOTBAR_DRAW =        0x2500,
        AP_PLAYER_DRAW =        0x2600,
        AP_RAY_CAST_DRAW =      0x2800,
        AP_DEBUG_DRAW =         0xFFFF;

    public static final int
        CP_ON_FLOORING =                0x00_00_10_10,
        CP_COLLECT_ITEMS =              0x00_00_00_20,
        CP_SHOPPER_SHELF_CRASH =        0x00_00_00_30,
        CP_SHOPPER_SHOPPER_CRASH =      0x00_00_00_40;

    public static final int //Fermion set bits - use bitwise ORs to be in a set union.
        FB_SHOPPER          =1<<0,
        FB_PERSON           =1<<1,
        FB_TERRAIN          =1<<2,
        FB_PROJECTILE       =1<<3,
        FB_COLLECTIBLE      =1<<4,
        FB_FLOORING         =1<<5;


    public static final float //Movement Constant.
        SHOPPERTORQUEFACTOR        = 3f,
        SHOPPERTHRUSTFACTOR        = 300f,
        SHOPPERBRAKEFACTOR         = 170f,
        SHOPPERLINEARDAMPING       = 3,
        SHOPPERLINEARDAMPINGDRIFT  = 4,
        SHOPPERANGULARDAMPING      = 10f,
        SHOPPERANGULARDAMPINGDRIFT = 7f,
        SHOPPEREMPTYDENISTY        = 1.4f,
        MASSLINEARDAMPINGFACTOR    = 0.3f,
        MASSANGULARDAMPINGFACTOR   = 0.15f;

    public static final float
        SHELF_UNIT_SIZE = 1f;

    public static final float
        ENEMY_THRUST_FACTOR_2 = SHOPPERTHRUSTFACTOR / 8f;

    public static final float
        ICE_THRUST_2 =          SHOPPERTHRUSTFACTOR / 16f,
        ICE_RING_RADIUS =       SHELF_UNIT_SIZE * 2.5f;

    public static final float
        BEAN_FORCE_DAMPING =    3f/8f,
        BEAN_TORQUE_DAMPING =   3f/8f,
        BEAN_RING_RADIUS =      SHELF_UNIT_SIZE * 2.5f;

    public static final float
        EXPLOSION_MAX_RADIUS = SHELF_UNIT_SIZE*10,
        EXPLOSIVE_COEFFICIENT = 300;

    public static final int
        NUM_PROJECTILE_TYPES = 3;
    public static final int 
        NUM_COLLECTIBLE_TYPES = 3;

    public static final int
        HOTBAR_MAX = 5;

    public static final int WIDTH = 8;
    public static final int TILE_WIDTH = 128;
//    public static final int HEIGHT = ;

    public static final boolean DEBUG_DRAW_BODIES = false;
    public static final boolean DEBUG_DRAW_ACTIVE_RAYS = false;
    public static final boolean DEBUG_DRAW_WAYPOINT_RAYS = false;

    public static boolean FULLSCREEN = false;

    public static float VIEWPORT_HEIGHT = 8;
}