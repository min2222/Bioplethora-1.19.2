package io.github.bioplethora.particles;

import java.awt.Color;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.bioplethora.registry.BPParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

/**
 * @Credit MaxBogomol
 */
public class WindPoofParticleData implements ParticleOptions {

    private Color tint;
    private double diameter;

    public WindPoofParticleData(Color tint, double diameter) {
        this.tint = tint;
        this.diameter = constrainDiameterToValidRange(diameter);
    }

    public Color getTint() {
        return tint;
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public ParticleType<?> getType() {
        return BPParticles.WIND_POOF.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeInt(tint.getRed());
        buf.writeInt(tint.getGreen());
        buf.writeInt(tint.getBlue());
        buf.writeDouble(diameter);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %i %i %i", this.getType(), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
    }

    private static double constrainDiameterToValidRange(double diameter) {
        final double MIN_DIAMETER = 0.05;
        final double MAX_DIAMETER = 1.0;
        return Mth.clamp(diameter, MIN_DIAMETER, MAX_DIAMETER);
    }

    public static final Codec<WindPoofParticleData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("tint").forGetter(d -> d.tint.getRGB()),
                    Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
            ).apply(instance, WindPoofParticleData::new)
    );

    private WindPoofParticleData(int tintRGB, double diameter) {
        this.tint = new Color(tintRGB);
        this.diameter = constrainDiameterToValidRange(diameter);
    }

    public static final Deserializer<WindPoofParticleData> DESERIALIZER = new Deserializer<WindPoofParticleData>() {

        @Nonnull
        @Override
        public WindPoofParticleData fromCommand(@Nonnull ParticleType<WindPoofParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double diameter = constrainDiameterToValidRange(reader.readDouble());

            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            reader.expect(' ');
            int red = Mth.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            reader.expect(' ');
            int green = Mth.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            reader.expect(' ');
            int blue = Mth.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
            Color color = new Color(red, green, blue);

            return new WindPoofParticleData(color, diameter);
        }

        @Override
        public WindPoofParticleData fromNetwork(@Nonnull ParticleType<WindPoofParticleData> type, FriendlyByteBuf buf) {

            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            int red = Mth.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
            int green = Mth.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
            int blue = Mth.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
            Color color = new Color(red, green, blue);

            double diameter = constrainDiameterToValidRange(buf.readDouble());

            return new WindPoofParticleData(color, diameter);
        }
    };
}
