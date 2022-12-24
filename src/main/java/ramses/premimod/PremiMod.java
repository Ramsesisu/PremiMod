package ramses.premimod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Mod(
        modid = PremiMod.MOD_ID,
        name = PremiMod.MOD_NAME,
        version = PremiMod.VERSION
)
public class PremiMod {

    public static final String MOD_ID = "premimod";
    public static final String MOD_NAME = "PremiMod";
    public static final String VERSION = "1.0";

    @Mod.Instance(MOD_ID)
    public static PremiMod INSTANCE;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ListenerClass());
    }

    public static class ListenerClass {

        private final Timer timer = new Timer();

        @SubscribeEvent
        public void onChatMessage(ClientChatReceivedEvent event) {
            if (Objects.requireNonNull(Minecraft.getMinecraft().getCurrentServerData()).serverName.toLowerCase().contains("unicacity")) {
                EntityPlayerSP player = Minecraft.getMinecraft().player;

                List<String> content = new ArrayList<>(Arrays.asList(event.getMessage().getUnformattedText().split(" ")));

                for (String item : content) {
                    if (item.contains("#") || item.contains("[") || item.contains("]") || item.contains("+") || item.contains("/") || item.contains("\\") || item.contains("$")) { continue; }

                    if (item.length() == 15 && isStringUpperCase(item)) {
                        player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + "[" + TextFormatting.GOLD + "PremiMod" + TextFormatting.DARK_GRAY + "] " + TextFormatting.YELLOW + "Der Code " + TextFormatting.GOLD + item + TextFormatting.YELLOW + " wird eingegeben!"));

                        player.sendChatMessage("/premium " + item);
                    }

                    if (item.length() == 14 && isStringUpperCase(item)) {

                        player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + "[" + TextFormatting.GOLD + "PremiMod" + TextFormatting.DARK_GRAY + "] " + TextFormatting.YELLOW + "Der Code zu " + TextFormatting.GOLD + item + TextFormatting.YELLOW + " wird ausprobiert!"));

                        timer.scheduleAtFixedRate(new TimerTask() {
                            private char alphabet = 'A';

                            @Override
                            public void run() {
                                if (alphabet > 'Z') {
                                    cancel();
                                    return;
                                }

                                String tempitem = item + alphabet;
                                alphabet++;

                                player.sendChatMessage("/premium " + tempitem);
                            }
                        }, 0, TimeUnit.SECONDS.toMillis(1));
                    }
                }
            }
        }

        private boolean isStringUpperCase(String str){
            char[] charArray = str.toCharArray();
            for (char c : charArray) {
                if (Character.isLetter(c)) {
                    if (!Character.isUpperCase(c))
                        return false;
                }
            }
            return true;
        }
    }
}
