package check.out.game.maingame;

public class ConstShop {
    public static int //Effect priorities - called in ascending order.
            EP_AI_KEYBOARD      =0x0010_0100,

            EP_MOVE_SHOPPERS    =0x0030_0100,
            EP_PHYSICS_STEP     =0x0050_0100,
            EP_SPOTLIGHT        =0x0070_0100,
            EP_DRAW             =0x0070_0200;

    public static int //Artist priorities - called in ascending order.
            AP_SCREEN_CLEAR     =0x1000,
            AP_DEBUG_DRAW       =0x1800;

    public static int //Fermion set bits - use bitwise ORs to be in a set union.
            FB_SHOPPER          =1<<0;
}