package io.github.bioplethora.api.world;

import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.BPSpawnParticlePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EffectUtils {

    public static <T extends ParticleOptions> int sendParticles(ServerLevel serverLevel, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pXSpeed, double pYSpeed, double pZSpeed) {
        BPSpawnParticlePacket sspawnparticlepacket = new BPSpawnParticlePacket(pType, false, pPosX, pPosY, pPosZ, (float)pXOffset, (float)pYOffset, (float)pZOffset, (float)pXSpeed, (float)pYSpeed, (float)pZSpeed, pParticleCount);
        int i = 0;

        for(int j = 0; j < serverLevel.players().size(); ++j) {
            ServerPlayer serverplayerentity = serverLevel.players().get(j);
            if (sendParticles(serverplayerentity, false, pPosX, pPosY, pPosZ, sspawnparticlepacket)) {
                ++i;
            }
        }

        return i;
    }

    public static <T extends ParticleOptions> void createParticleBall(ServerLevel serverLevel, T pType, double pPosX, double pPosY, double pPosZ, double pSpeed, int pSize) {
        for(int i = -pSize; i <= pSize; ++i) {
            for(int j = -pSize; j <= pSize; ++j) {
                for(int k = -pSize; k <= pSize; ++k) {
                    double d3 = (double)j + (serverLevel.random.nextDouble() - serverLevel.random.nextDouble()) * 0.5D;
                    double d4 = (double)i + (serverLevel.random.nextDouble() - serverLevel.random.nextDouble()) * 0.5D;
                    double d5 = (double)k + (serverLevel.random.nextDouble() - serverLevel.random.nextDouble()) * 0.5D;
                    double d6 = (double) Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / pSpeed + serverLevel.random.nextGaussian() * 0.05D;
                    sendParticles(serverLevel, pType, pPosX, pPosY, pPosZ, 1, 0, 0, 0, d3 / d6, d4 / d6, d5 / d6);
                    if (i != -pSize && i != pSize && j != -pSize && j != pSize) {
                        k += pSize * 2 - 1;
                    }
                }
            }
        }
    }

    public static void addCircleParticleForm(ServerLevel serverLevel, Entity entity, ParticleOptions particle, int count, double offsetCircle, double speed) {
        serverLevel.sendParticles(particle, entity.getX(), entity.getY(), entity.getZ(), count, offsetCircle, offsetCircle, offsetCircle, speed);
    }

    public static void addCircleParticleForm(Level world, Entity entity, ParticleOptions particle, int count, double offsetCircle, double speed) {
        addCircleParticleForm(world, entity, particle, count, offsetCircle, speed, 0);
    }

    public static void addCircleParticleForm(Level world, Entity entity, ParticleOptions particle, int count, double offsetCircle, double speed, double yOffset) {
        if (!world.isClientSide()) {
            ((ServerLevel) world).sendParticles(particle, entity.getX(), entity.getY() + yOffset, entity.getZ(), count, offsetCircle, offsetCircle, offsetCircle, speed);
        }
    }

    public static void addEffectNoIcon(LivingEntity target, MobEffect effect, int duration, int amplifier) {
        target.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false, false));
    }

    private static boolean sendParticles(ServerPlayer pPlayer, boolean pLongDistance, double pPosX, double pPosY, double pPosZ, BPSpawnParticlePacket packet) {
        if (pPlayer.level.isClientSide()) {
            return false;
        } else {
            BlockPos blockpos = pPlayer.blockPosition();
            if (blockpos.closerToCenterThan(new Vec3(pPosX, pPosY, pPosZ), pLongDistance ? 512.0D : 32.0D)) {
                BPNetwork.sendPacketToPlayer(pPlayer, packet);
                return true;
            } else {
                return false;
            }
        }
    }
}
