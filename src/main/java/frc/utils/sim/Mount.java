package frc.utils.sim;

public interface Mount {

    public enum MountType {
        kLinear, kArm,
    }

    MountType getMountType();

    double getDisplacementX();

    double getDisplacementY();

    double getAngle();

    double getMountX();

    double getMountY();
}
