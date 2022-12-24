package me.rqmses.elytramod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Mod(
        modid = Elytramod.MOD_ID,
        name = Elytramod.MOD_NAME,
        version = Elytramod.VERSION
)
public class Elytramod {

    public static final String MOD_ID = "elytramod";
    public static final String MOD_NAME = "ElytraMod";
    public static final String VERSION = "1.1";

    @Mod.Instance(MOD_ID)
    public static Elytramod INSTANCE;


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ListenerClass());
    }

    public static class ListenerClass {

        boolean elytramode = false;
        Point startLocation;
        Point oldLocation;

        String prefix = TextFormatting.DARK_GRAY + "[" + TextFormatting.DARK_AQUA + "ElytraMod" + TextFormatting.DARK_GRAY + "] ";

        @SubscribeEvent
        public void onScroll(InputEvent.MouseInputEvent event) throws AWTException {
            if (elytramode) {
                Robot KeyPresser = new Robot();
                startLocation = MouseInfo.getPointerInfo().getLocation();
                int i = Integer.signum(Mouse.getEventDWheel());
                if (i > 0) {
                    int startX = startLocation.x;
                    int startY = startLocation.y;
                    int endY = startY - 200;
                    KeyPresser.mouseMove(startX, endY);
                    oldLocation = startLocation;
                }
                if (i < 0) {
                    int startX = startLocation.x;
                    int startY = startLocation.y;
                    int endY = startY + 400;
                    KeyPresser.mouseMove(startX, endY);
                    oldLocation = startLocation;
                }
            }
        }

        @SubscribeEvent
        public void onKlick(InputEvent.KeyInputEvent event) throws AWTException {
            if (Keyboard.isKeyDown(Keyboard.KEY_SCROLL)) {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                float sensitivity = ((float) Math.round(Minecraft.getMinecraft().gameSettings.mouseSensitivity * 200)) / 100;
                if (elytramode) {
                    player.sendMessage(new TextComponentString(prefix + TextFormatting.AQUA + "Elytra-Modus " + TextFormatting.RED + "deaktiviert" + TextFormatting.AQUA + "."));
                    elytramode = false;
                } else {
                    if (String.valueOf(sensitivity).equals("0.79")) {
                        player.sendMessage(new TextComponentString(prefix + TextFormatting.AQUA + "Elytra-Modus " + TextFormatting.GREEN + "aktiviert" + TextFormatting.AQUA + "."));
                        elytramode = true;
                        oldLocation = MouseInfo.getPointerInfo().getLocation();
                    } else {
                        player.sendMessage(new TextComponentString(prefix + TextFormatting.AQUA + "Deine Empfindlichkeit muss bei " + TextFormatting.BOLD + "78%" + TextFormatting.AQUA + " liegen."));
                    }
                }
                return;
            }

            if (elytramode) {
                Robot KeyPresser = new Robot();
                Point startLocation = MouseInfo.getPointerInfo().getLocation();
                if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                    int startX = startLocation.x;
                    int startY = startLocation.y;
                    int endX = startX - 50;
                    KeyPresser.mouseMove(endX, startY);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                    int startX = startLocation.x;
                    int startY = startLocation.y;
                    int endX = startX - 450;
                    KeyPresser.mouseMove(endX, startY);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                    int startX = startLocation.x;
                    int startY = startLocation.y;
                    int endX = startX + 50;
                    KeyPresser.mouseMove(endX, startY);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                    int startX = startLocation.x;
                    int startY = startLocation.y;
                    int endX = startX + 450;
                    KeyPresser.mouseMove(endX, startY);
                }
            }
        }
    }
}
