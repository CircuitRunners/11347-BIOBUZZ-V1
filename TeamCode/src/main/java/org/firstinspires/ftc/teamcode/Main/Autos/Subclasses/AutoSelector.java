package org.firstinspires.ftc.teamcode.Main.Autos.Subclasses;

import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.Main.Autos.paths.autoFull;
import org.firstinspires.ftc.teamcode.Main.Autos.paths.autoPark;
import org.firstinspires.ftc.teamcode.Main.Autos.paths.autoPartial;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;

import java.util.HashMap;

public class AutoSelector {
    Follower follower;
    HashMap<Paths, String> descriptions = new HashMap<>();
    AutoCommands commands = new AutoCommands();
    public enum Paths {
        FULL_AUTO,
        PARTIAL,
        PARK_ONLY
    }

    public AutoSelector(Follower follower){
        descriptions.put(Paths.FULL_AUTO, "Full Auto");
        descriptions.put(Paths.PARTIAL, "Partial Auto");
        descriptions.put(Paths.PARK_ONLY, "Park Only");

        this.follower = follower;
    }

    public Paths[] getPaths(){
        return Paths.values();
    }
    public String getDescription(Paths path){
        return descriptions.get(path);
    }

    public Object[] getPath(Paths path) {
        switch (path) {
            case FULL_AUTO: {
                autoFull paths = new autoFull();
                return new Object[]{
                        paths.start,
                        sequential(
                                follow(follower, paths.path1()),
                                commands.flowerDown(),
                                follow(follower, paths.path2())
                        )
                };
            }
            case PARTIAL: {
                autoPartial paths = new autoPartial();
                return new Object[]{
                        paths.start,
                        sequential(
                                follow(follower, paths.path1())
                        )
                };
            }
            case PARK_ONLY: {
                autoPark paths = new autoPark();
                return new Object[]{
                        paths.start,
                        sequential(
                                follow(follower, paths.path1())
                        )
                };
            }
            default: {
                return null;
            }
        }
    }

    public Shooter getShooter(){
        return commands.getShooter();
    }
}
