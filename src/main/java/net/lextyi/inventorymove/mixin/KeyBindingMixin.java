package net.lextyi.inventorymove.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.lextyi.inventorymove.mixinterface.IKeyBinding;

@Mixin(KeyBinding.class)
public class KeyBindingMixin implements IKeyBinding
{
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public boolean isActallyPressed()
    {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        int code = boundKey.getCode();
        return InputUtil.isKeyPressed(handle, code);
    }
}
