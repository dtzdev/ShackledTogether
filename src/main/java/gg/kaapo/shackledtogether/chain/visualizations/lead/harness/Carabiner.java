package gg.kaapo.shackledtogether.chain.visualizations.lead.harness;

import gg.kaapo.shackledtogether.ShackledTogether;
import gg.kaapo.shackledtogether.chain.visualizations.lead.LeadImplementation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

import java.util.concurrent.CompletableFuture;

public class Carabiner {

    private final ShackledTogether shackledTogether = ShackledTogether.getInstance();
    private Entity carabiner;
    private final Location spawnLocation;

    public Carabiner(Location location) {
        this.spawnLocation = location;
    }

    public CompletableFuture<Void> spawn() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        shackledTogether.getFoliaLib().getScheduler().runAtLocation(spawnLocation, task -> {
            Rabbit rabbit = (Rabbit) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.RABBIT);
            rabbit.setAI(false);
            rabbit.setGravity(false);
            rabbit.setInvisible(true);
            rabbit.setInvulnerable(true);
            rabbit.setCustomNameVisible(false);
            rabbit.setSilent(true);
            rabbit.setAware(false);
            rabbit.setLootTable(null);
            rabbit.setBaby();
            rabbit.setAgeLock(true);
            rabbit.setPersistent(false);
            rabbit.setCollidable(false);
            rabbit.setCustomName("shackledtogetheraeaeaeae");
            LeadImplementation.getMasterScoreboard().getTeam("shackledtogether").addEntry(rabbit.getUniqueId().toString());
            this.carabiner = rabbit;
            future.complete(null);
        });
        return future;
    }

    public void nuke() {
        if (carabiner == null) return;
        ShackledTogether.getInstance().getFoliaLib().getScheduler().runAtEntity(
                carabiner,
                task -> {
                    LeadImplementation.getMasterScoreboard()
                            .getTeam("shackledtogether")
                            .removeEntry(carabiner.getUniqueId().toString());
                    carabiner.remove();
                }
        );
    }
    public Entity getCarabiner() {
        return carabiner;
    }
}
